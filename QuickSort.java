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

    static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int pi = partition(arr, low, high);

            // Recursive on smaller partition, iterate on larger ones

            if (pi - low < high - pi) {
                // left site is smaller
                quickSort(arr, low, pi - 1);
                low = pi + 1; // iteration on right site
            } else {
                // right site is smaller
                quickSort(arr, pi + 1, high);
                high = pi - 1; // iteration on left site
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = { 10, 7, 8, 9, 1, 5, 3, 12, 4 };
        int n = arr.length;

        quickSort(arr, 0, n - 1);

        for (int val : arr) {
            System.out.print(val + " ");
        }
    }

}
