/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package consulo.msbuild.dotnet.impl.library;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code lib/<tfm>} folder of a NuGet package that a project of a target framework uses - the nearest compatible
 * framework, like NuGet's {@code FrameworkReducer} for the frameworks a package usually ships:
 * {@code netX.Y} (.NET 5+), {@code netcoreappX.Y}, {@code netstandardX.Y} and {@code net4xx} (.NET Framework).
 *
 * @author VISTALL
 */
final class NuGetFrameworks {
    private enum Family {
        // .NET 5+ and .NET Core: netcoreapp
        CORE,
        STANDARD,
        FRAMEWORK
    }

    private record Framework(Family family, int major, int minor) {
        int version() {
            return major * 100 + minor;
        }
    }

    private static final Pattern NET_CORE = Pattern.compile("net(?:coreapp)?(\\d+)\\.(\\d+)(?:-.*)?");
    private static final Pattern NET_STANDARD = Pattern.compile("netstandard(\\d+)\\.(\\d+)");
    private static final Pattern NET_FRAMEWORK = Pattern.compile("net(\\d)(\\d)(\\d?)");

    private NuGetFrameworks() {
    }

    /**
     * @param project the target framework of the project ({@code net10.0}, {@code netstandard2.0}, {@code net472})
     * @param folders the {@code lib} subfolders of a package
     * @return the folder to use; null if none is compatible
     */
    @Nullable
    static String select(String project, List<String> folders) {
        Framework target = parse(project);
        if (target == null) {
            return null;
        }
        String best = null;
        int bestRank = Integer.MAX_VALUE;
        for (String folder : folders) {
            Framework candidate = parse(folder);
            if (candidate == null) {
                continue;
            }
            int rank = rank(target, candidate);
            if (rank < bestRank) {
                bestRank = rank;
                best = folder;
            }
        }
        return best;
    }

    /**
     * @return how far a compatible candidate is from the target - smaller is nearer; MAX_VALUE if not compatible
     */
    private static int rank(Framework target, Framework candidate) {
        switch (target.family()) {
            case CORE:
                if (candidate.family() == Family.CORE && candidate.version() <= target.version()) {
                    return target.version() - candidate.version();
                }
                // .NET Core 2.0+ and .NET 5+ implement .NET Standard 2.0, .NET Core 3.0+ 2.1
                int standard = target.version() >= 300 ? 201 : target.version() >= 200 ? 200 : 106;
                if (candidate.family() == Family.STANDARD && candidate.version() <= standard) {
                    return 10_000 + standard - candidate.version();
                }
                return Integer.MAX_VALUE;
            case STANDARD:
                return candidate.family() == Family.STANDARD && candidate.version() <= target.version()
                    ? target.version() - candidate.version() : Integer.MAX_VALUE;
            case FRAMEWORK:
                if (candidate.family() == Family.FRAMEWORK && candidate.version() <= target.version()) {
                    return target.version() - candidate.version();
                }
                // .NET Framework 4.6.1+ uses .NET Standard 2.0 libraries
                if (target.version() >= 461 && candidate.family() == Family.STANDARD && candidate.version() <= 200) {
                    return 10_000 + 200 - candidate.version();
                }
                return Integer.MAX_VALUE;
            default:
                return Integer.MAX_VALUE;
        }
    }

    @Nullable
    private static Framework parse(String moniker) {
        String text = moniker.toLowerCase(Locale.ROOT);
        Matcher matcher = NET_STANDARD.matcher(text);
        if (matcher.matches()) {
            return new Framework(Family.STANDARD, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        }
        matcher = NET_CORE.matcher(text);
        if (matcher.matches()) {
            return new Framework(Family.CORE, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        }
        matcher = NET_FRAMEWORK.matcher(text);
        if (matcher.matches()) {
            // net472 -> 4.7.2 as 472, net45 -> 450
            int third = matcher.group(3).isEmpty() ? 0 : Integer.parseInt(matcher.group(3));
            return new Framework(Family.FRAMEWORK, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) * 10 + third);
        }
        return null;
    }

    /**
     * Compares NuGet versions: numeric parts first, a release before its prereleases
     */
    static int compareVersions(String left, String right) {
        String[] leftParts = left.split("-", 2);
        String[] rightParts = right.split("-", 2);
        List<Integer> leftNumbers = numbers(leftParts[0]);
        List<Integer> rightNumbers = numbers(rightParts[0]);
        for (int i = 0; i < Math.max(leftNumbers.size(), rightNumbers.size()); i++) {
            int l = i < leftNumbers.size() ? leftNumbers.get(i) : 0;
            int r = i < rightNumbers.size() ? rightNumbers.get(i) : 0;
            if (l != r) {
                return Integer.compare(l, r);
            }
        }
        boolean leftRelease = leftParts.length == 1;
        boolean rightRelease = rightParts.length == 1;
        if (leftRelease != rightRelease) {
            return leftRelease ? 1 : -1;
        }
        return leftRelease ? 0 : leftParts[1].compareTo(rightParts[1]);
    }

    private static List<Integer> numbers(String version) {
        List<Integer> numbers = new ArrayList<>();
        for (String part : version.split("\\.")) {
            try {
                numbers.add(Integer.parseInt(part));
            }
            catch (NumberFormatException e) {
                numbers.add(0);
            }
        }
        return numbers;
    }
}
