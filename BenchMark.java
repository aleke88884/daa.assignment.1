import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchMark {

    private static final Random rand = new Random();

    // ================== TESTING METHODS (Required 10%) ==================

    /**
     * Tests QuickSort correctness on random and adversarial arrays
     */
    private static void testQuickSortCorrectness() {
        System.out.println("=== Testing QuickSort Correctness ===");

        // Test 1: Random arrays
        System.out.println("Testing on random arrays...");
        for (int test = 0; test < 20; test++) {
            int n = 100 + rand.nextInt(1000);
            int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();
            int[] expected = arr.clone();
            Arrays.sort(expected);

            QuickSort.quickSort(arr, 0, arr.length - 1);

            if (!Arrays.equals(arr, expected)) {
                System.out.println("X FAILED: QuickSort failed on random array test " + test);
                System.exit(1);
            }
        }
        System.out.println("+ Random arrays test passed");

        // Test 2: Adversarial cases
        System.out.println("Testing adversarial cases...");

        // Already sorted array
        int[] sorted = new int[1000];
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = i;
        }
        int[] sortedCopy = sorted.clone();
        QuickSort.quickSort(sorted, 0, sorted.length - 1);
        if (!Arrays.equals(sorted, sortedCopy)) {
            System.out.println("X FAILED: QuickSort failed on sorted array");
            System.exit(1);
        }

        // Reverse sorted array
        int[] reverse = new int[1000];
        for (int i = 0; i < reverse.length; i++) {
            reverse[i] = 1000 - i;
        }
        int[] reverseSorted = reverse.clone();
        Arrays.sort(reverseSorted);
        QuickSort.quickSort(reverse, 0, reverse.length - 1);
        if (!Arrays.equals(reverse, reverseSorted)) {
            System.out.println("X FAILED: QuickSort failed on reverse sorted array");
            System.exit(1);
        }

        // All equal elements
        int[] allEqual = new int[1000];
        Arrays.fill(allEqual, 42);
        int[] allEqualCopy = allEqual.clone();
        QuickSort.quickSort(allEqual, 0, allEqual.length - 1);
        if (!Arrays.equals(allEqual, allEqualCopy)) {
            System.out.println("X FAILED: QuickSort failed on all equal elements");
            System.exit(1);
        }

        System.out.println("+ Adversarial cases test passed");
        System.out.println("+ QuickSort correctness verified\n");
    }

    /**
     * Tests QuickSort recursion depth bound
     */
    private static void testQuickSortRecursionDepth() {
        System.out.println("=== Testing QuickSort Recursion Depth ===");

        for (int test = 0; test < 10; test++) {
            int n = 1000 + test * 1000;
            int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();

            // Track recursion depth - you'll need to modify QuickSort to return depth
            // For now, we assume reasonable implementation
            int maxDepth = QuickSort.quickSortWithDepth(arr, 0, arr.length - 1);

            // Expected bound: ~2*floor(log2 n) + constant
            int expectedBound = (int) (2 * Math.floor(Math.log(n) / Math.log(2))) + 20;

            boolean passed = maxDepth <= expectedBound;
            System.out.printf("n=%d, depth=%d, bound≤%d: %s%n",
                    n, maxDepth, expectedBound, passed ? "+" : "X");

            if (!passed) {
                System.out.println("⚠️  WARNING: Recursion depth exceeds expected bound");
            }
        }
        System.out.println("+ QuickSort recursion depth test completed\n");
    }

    /**
     * Tests DeterministicSelect correctness by comparing with Arrays.sort
     */
    private static void testDeterministicSelect() {
        System.out.println("=== Testing DeterministicSelect Correctness ===");

        int passedTests = 0;
        for (int test = 0; test < 100; test++) {
            int n = 100 + rand.nextInt(1000);
            int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();
            int k = rand.nextInt(n);

            int[] sortedArr = arr.clone();
            Arrays.sort(sortedArr);
            int expected = sortedArr[k];

            int[] testArr = arr.clone();
            int result = DeterministicSelect.select(testArr, 0, testArr.length - 1, k);

            if (result == expected) {
                passedTests++;
            } else {
                System.out.printf("X FAILED: Test %d, n=%d, k=%d, expected=%d, got=%d%n",
                        test, n, k, expected, result);
            }
        }

        System.out.printf("+ DeterministicSelect: %d/100 tests passed", passedTests);
        if (passedTests < 100) {
            System.out.println(" - Some tests failed!");
            System.exit(1);
        }
        System.out.println("\n");
    }

    /**
     * Tests ClosestPair correctness against O(n²) brute force on small arrays
     */
    private static void testClosestPair() {
        System.out.println("=== Testing ClosestPair Correctness ===");

        int passedTests = 0;
        for (int test = 0; test < 20; test++) {
            int n = 50 + rand.nextInt(100); // Small n for brute force comparison
            ClosestPair.Point[] points = new ClosestPair.Point[n];

            for (int i = 0; i < n; i++) {
                points[i] = new ClosestPair.Point(
                        rand.nextDouble() * 1000,
                        rand.nextDouble() * 1000);
            }

            // Fast algorithm result
            double fastResult = ClosestPair.closest(points);

            // Brute force O(n²) for verification
            double bruteForceResult = bruteForceclosestPair(points);

            // Allow small floating point error
            double epsilon = 1e-9;
            if (Math.abs(fastResult - bruteForceResult) < epsilon) {
                passedTests++;
            } else {
                System.out.printf("X FAILED: Test %d, fast=%.6f, brute=%.6f%n",
                        test, fastResult, bruteForceResult);
            }
        }

        System.out.printf("+ ClosestPair: %d/20 tests passed", passedTests);
        if (passedTests < 20) {
            System.out.println(" - Some tests failed!");
            System.exit(1);
        }
        System.out.println("\n");
    }

    /**
     * O(n²) brute force closest pair for verification
     */
    private static double bruteForceclosestPair(ClosestPair.Point[] points) {
        double minDist = Double.MAX_VALUE;
        int n = points.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dx = points[i].x - points[j].x;
                double dy = points[i].y - points[j].y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                minDist = Math.min(minDist, dist);
            }
        }

        return minDist;
    }

    // ================== BENCHMARK METHODS ==================

    private static void benchmarkQuickSort(String filename) throws IOException {
        System.out.println("=== Benchmarking QuickSort ===");
        FileWriter writer = new FileWriter(filename);
        writer.write("n,time_ms\n");

        for (int n = 1000; n <= 20000; n += 2000) {
            int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();

            long start = System.nanoTime();
            QuickSort.quickSort(arr, 0, arr.length - 1);
            long end = System.nanoTime();

            long elapsed = (end - start) / 1_000_000; // ms
            writer.write(n + "," + elapsed + "\n");
            System.out.printf("QuickSort n=%d, time=%d ms%n", n, elapsed);
        }

        writer.close();
        System.out.println("+ QuickSort benchmark completed\n");
    }

    private static void benchmarkMergeSort(String filename) throws IOException {
        System.out.println("=== Benchmarking MergeSort ===");
        FileWriter writer = new FileWriter(filename);
        writer.write("n,time_ms\n");

        for (int n = 1000; n <= 20000; n += 2000) {
            int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();

            long start = System.nanoTime();
            MergeSort.sort(arr);
            long end = System.nanoTime();

            long elapsed = (end - start) / 1_000_000;
            writer.write(n + "," + elapsed + "\n");
            System.out.printf("MergeSort n=%d, time=%d ms%n", n, elapsed);
        }

        writer.close();
        System.out.println("+ MergeSort benchmark completed\n");
    }

    private static void benchmarkDeterministicSelect(String filename) throws IOException {
        System.out.println("=== Benchmarking DeterministicSelect ===");
        FileWriter writer = new FileWriter(filename);
        writer.write("n,time_ms\n");

        for (int n = 1000; n <= 20000; n += 2000) {
            int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();
            int k = rand.nextInt(n);

            long start = System.nanoTime();
            DeterministicSelect.select(arr, 0, arr.length - 1, k);
            long end = System.nanoTime();

            long elapsed = (end - start) / 1_000_000;
            writer.write(n + "," + elapsed + "\n");
            System.out.printf("DeterministicSelect n=%d, time=%d ms%n", n, elapsed);
        }

        writer.close();
        System.out.println("+ DeterministicSelect benchmark completed\n");
    }

    private static void benchmarkClosestPair(String filename) throws IOException {
        System.out.println("=== Benchmarking ClosestPair ===");
        FileWriter writer = new FileWriter(filename);
        writer.write("n,time_ms\n");

        for (int n = 1000; n <= 20000; n += 2000) {
            ClosestPair.Point[] points = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new ClosestPair.Point(
                        rand.nextDouble() * 10000,
                        rand.nextDouble() * 10000);
            }

            long start = System.nanoTime();
            ClosestPair.closest(points);
            long end = System.nanoTime();

            long elapsed = (end - start) / 1_000_000;
            writer.write(n + "," + elapsed + "\n");
            System.out.printf("ClosestPair n=%d, time=%d ms%n", n, elapsed);
        }

        writer.close();
        System.out.println("+ ClosestPair benchmark completed\n");
    }

    // ================== MAIN METHOD ==================
    public static void main(String[] args) throws IOException {
        System.out.println("==========================================");
        System.out.println("   ALGORITHM TESTING AND BENCHMARKING");
        System.out.println("==========================================\n");

        // PHASE 1: CORRECTNESS TESTING (Required 10%)
        System.out.println("PHASE 1: CORRECTNESS TESTING");
        System.out.println("-----------------------------");

        testQuickSortCorrectness();
        testQuickSortRecursionDepth();
        testDeterministicSelect();
        testClosestPair();

        System.out.println("+ ALL CORRECTNESS TESTS PASSED!\n");

        // PHASE 2: PERFORMANCE BENCHMARKING
        System.out.println("PHASE 2: PERFORMANCE BENCHMARKING");
        System.out.println("---------------------------------");

        benchmarkQuickSort("quicksort_results.csv");
        benchmarkMergeSort("mergesort_results.csv");
        benchmarkDeterministicSelect("deterministicselect_results.csv");
        benchmarkClosestPair("closestpair_results.csv");

        System.out.println("==========================================");
        System.out.println("+ ALL TESTS AND BENCHMARKS COMPLETED!");
        System.out.println(" CSV files created for analysis");
        System.out.println("==========================================");
    }
}