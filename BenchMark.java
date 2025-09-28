import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchMark {

    private static final Random rand = new Random();

    // ================== QuickSort ==================
    private static void benchmarkQuickSort(String filename) throws IOException {
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
    }

    // ================== MergeSort ==================
    private static void benchmarkMergeSort(String filename) throws IOException {
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
    }

    // ================== DeterministicSelect ==================
    private static void benchmarkDeterministicSelect(String filename) throws IOException {
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
    }

    // ================== ClosestPair ==================
    private static void benchmarkClosestPair(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write("n,time_ms\n");

        for (int n = 1000; n <= 20000; n += 2000) {
            ClosestPair.Point[] points = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new ClosestPair.Point(rand.nextDouble() * 10000, rand.nextDouble() * 10000);
            }

            long start = System.nanoTime();
            ClosestPair.closest(points);
            long end = System.nanoTime();

            long elapsed = (end - start) / 1_000_000;
            writer.write(n + "," + elapsed + "\n");
            System.out.printf("ClosestPair n=%d, time=%d ms%n", n, elapsed);
        }

        writer.close();
    }

    // ================== MAIN ==================
    public static void main(String[] args) throws IOException {
        benchmarkQuickSort("quicksort_results.csv");
        benchmarkMergeSort("mergesort_results.csv");
        benchmarkDeterministicSelect("deterministicselect_results.csv");
        benchmarkClosestPair("closestpair_results.csv");

        System.out.println("=== All benchmarks done. CSV files created. ===");
    }
}
