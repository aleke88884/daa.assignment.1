import java.util.Random;

class QuickSort {
    private static final Random rand = new Random();

    static int partition(int[] arr, int low, int high) {
        // ------ Randomized pivot ------
        int pivotIndex = low + rand.nextInt(high - low + 1);
        swap(arr, pivotIndex, high);

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * QuickSort with depth tracking for testing recursion bounds
     */
    public static int quickSortWithDepth(int[] arr, int low, int high) {
        return quickSortWithDepthHelper(arr, low, high, 0);
    }

    private static int quickSortWithDepthHelper(int[] arr, int low, int high, int currentDepth) {
        if (low >= high) {
            return currentDepth;
        }

        int maxDepth = currentDepth + 1;
        int pi = partition(arr, low, high);

        // Recursively sort both partitions and track max depth
        int leftDepth = quickSortWithDepthHelper(arr, low, pi - 1, currentDepth + 1);
        int rightDepth = quickSortWithDepthHelper(arr, pi + 1, high, currentDepth + 1);

        return Math.max(maxDepth, Math.max(leftDepth, rightDepth));
    }

    /**
     * Optimized QuickSort using iteration to reduce stack usage
     */
    static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int pi = partition(arr, low, high);

            // Recursive on smaller partition, iterate on larger ones
            if (pi - low < high - pi) {
                // left side is smaller
                quickSort(arr, low, pi - 1);
                low = pi + 1; // iteration on right side
            } else {
                // right side is smaller
                quickSort(arr, pi + 1, high);
                high = pi - 1; // iteration on left side
            }
        }
    }

    /**
     * Pure recursive version for comparison (not optimized)
     */
    static void quickSortRecursive(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortRecursive(arr, low, pi - 1);
            quickSortRecursive(arr, pi + 1, high);
        }
    }

    /**
     * Test method to verify sorting correctness
     */
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Test 1: Basic functionality
        System.out.println("=== Test 1: Basic QuickSort ===");
        int[] arr1 = { 10, 7, 8, 9, 1, 5, 3, 12, 4 };
        System.out.print("Original: ");
        printArray(arr1);

        quickSort(arr1, 0, arr1.length - 1);
        System.out.print("Sorted:   ");
        printArray(arr1);
        System.out.println("Is sorted: " + isSorted(arr1));

        // Test 2: Depth tracking
        System.out.println("\n=== Test 2: Depth Tracking ===");
        int[] arr2 = { 64, 34, 25, 12, 22, 11, 90, 5, 77, 30 };
        System.out.print("Array: ");
        printArray(arr2);

        int maxDepth = quickSortWithDepth(arr2, 0, arr2.length - 1);
        System.out.print("Sorted: ");
        printArray(arr2);
        System.out.println("Max recursion depth: " + maxDepth);
        System.out.println("Expected bound (~2*log2(n)): " +
                (2 * Math.ceil(Math.log(arr2.length) / Math.log(2))));

        // Test 3: Edge cases
        System.out.println("\n=== Test 3: Edge Cases ===");

        // Empty array
        int[] empty = {};
        quickSort(empty, 0, 0);
        System.out.println("Empty array sorted: " + java.util.Arrays.toString(empty));

        // Single element
        int[] single = { 42 };
        quickSort(single, 0, 0);
        System.out.println("Single element: " + java.util.Arrays.toString(single));

        // Already sorted
        int[] sorted = { 1, 2, 3, 4, 5 };
        int sortedDepth = quickSortWithDepth(sorted, 0, sorted.length - 1);
        System.out.println("Already sorted depth: " + sortedDepth);

        // Reverse sorted
        int[] reverse = { 5, 4, 3, 2, 1 };
        int reverseDepth = quickSortWithDepth(reverse, 0, reverse.length - 1);
        System.out.println("Reverse sorted depth: " + reverseDepth);

        // All equal
        int[] allEqual = { 7, 7, 7, 7, 7 };
        int equalDepth = quickSortWithDepth(allEqual, 0, allEqual.length - 1);
        System.out.println("All equal depth: " + equalDepth);
    }

    private static void printArray(int[] arr) {
        for (int val : arr) {
            System.out.print(val + " ");
        }
        System.out.println();
    }
}