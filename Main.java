import java.util.Arrays;
import java.util.Random;

public class Main {

    private static final Random rand = new Random();

    // Quick Sort TESTS
    private static void testQuickSort() {
        System.out.println("=== QuickSort Tests ===");

        // correctness: random arrays
        for (int t = 0; t < 5; t++) {
            int n = 20;
            int[] arr = rand.ints(n, -100, 100).toArray();
            int[] copy = arr.clone();
            QuickSort.quickSort(arr, 0, n - 1);
            Arrays.sort(copy);
            if (!Arrays.equals(arr, copy)) {
                throw new AssertionError("QuickSort failed on random array");
            }
        }
        System.out.println("QuickSort correctness OK");

        // adversarial: already sorted
        int[] sorted = new int[1000];
        for (int i = 0; i < sorted.length; i++)
            sorted[i] = i;
        int[] copySorted = sorted.clone();
        QuickSort.quickSort(sorted, 0, sorted.length - 1);
        Arrays.sort(copySorted);
        if (!Arrays.equals(sorted, copySorted)) {
            throw new AssertionError("QuickSort failed on sorted array");
        }
        System.out.println("QuickSort adversarial OK");

        // recursion depth bounded test
        int n = 100000;
        int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();
        QuickSort.quickSort(arr, 0, n - 1);
        // if recursion depth was too high, it would StackOverflow
        System.out.println("QuickSort recursion depth bounded OK");
    }

    private static void testDeterministicSelectSort() {
        System.out.println("=== DeterministicSelect Tests ===");
        for (int trial = 0; trial < 100; trial++) {
            int n = rand.nextInt(50) + 1;
            int[] arr = rand.ints(n, -1000, 1000).toArray();
            int k = rand.nextInt(n);

            int result = DeterministicSelect.select(arr.clone(), 0, n - 1, k);

            int[] sorted = arr.clone();
            Arrays.sort(sorted);
            if (result != sorted[k]) {
                throw new AssertionError("Select failed trial " + trial);
            }
        }
        System.out.println("DeterministicSelect OK across 100 trials");
    }

    private static void testMergeSort() {
        System.out.println("=== MergeSort Tests ===");
        for (int t = 0; t < 10; t++) {
            int n = rand.nextInt(100) + 1;
            int[] arr = rand.ints(n, -1000, 1000).toArray();
            int[] copy = arr.clone();

            MergeSort.sort(arr);
            Arrays.sort(copy);

            if (!Arrays.equals(arr, copy)) {
                throw new AssertionError("MergeSort failed");
            }
        }
        System.out.println("MergeSort correctness OK");
    }

    private static void testClosestPairClass() {
        System.out.println("=== ClosestPair (standalone) Tests ===");
        Random rand = new Random();

        // 1. Проверка корректности против brute force
        for (int trial = 0; trial < 5; trial++) {
            int n = 200; // малый размер, чтобы brute force работал быстро
            ClosestPair.Point[] points = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
            }
            double fast = ClosestPair.closest(points);
            double slow = bruteForceClosestPair(points);
            if (Math.abs(fast - slow) > 1e-6) {
                throw new AssertionError("ClosestPair mismatch on trial " + trial);
            }
        }
        System.out.println("ClosestPair correctness (vs brute force) OK");

        // 2. Проверка на больших массивах
        int n = 200_000;
        ClosestPair.Point[] points = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ClosestPair.Point(rand.nextDouble() * 10_000, rand.nextDouble() * 10_000);
        }
        long start = System.currentTimeMillis();
        double d = ClosestPair.closest(points);
        long end = System.currentTimeMillis();
        System.out.printf("ClosestPair large test OK, n=%d, dist=%.6f, time=%d ms\n", n, d, (end - start));
    }

    // Вспомогательный метод для сравнения
    private static double bruteForceClosestPair(ClosestPair.Point[] pts) {
        double min = Double.MAX_VALUE;
        int n = pts.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                min = Math.min(min, dist);
            }
        }
        return min;
    }

    public static void main(String[] args) {
        testQuickSort();
        testDeterministicSelectSort();
        testMergeSort();
        testClosestPairClass();
        System.out.println("=== All Tests Passed ===");
    }

}
