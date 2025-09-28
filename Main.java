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
}
