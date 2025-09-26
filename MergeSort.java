class MergeSort {

    private static final int INSERTION_SORT_THRESHOLD = 16;

    // The arr[left .. middle]
    // The arr[middle+1 .. right]
    public static void merge(int arr[], int buffer[], int left, int middle, int right) {
        // Copying left site into buffer
        for (int i = left; i <= middle; i++) {
            buffer[i] = arr[i];
        }

        int i = left; // index for buffer
        int j = middle + 1; // index for right site (from arr[])
        int k = left; // index for initial arr[]

        // Linear merge
        while (i <= middle && j <= right) {
            if (buffer[i] <= arr[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = arr[j++];
            }
        }

        // Left remains (because right site already in here, so do not need to copy)
        while (i <= middle) {
            arr[k++] = buffer[i++];
        }
    }

    public static void mergeSort(int arr[], int buffer[], int left, int right) {
        if (right - left + 1 <= INSERTION_SORT_THRESHOLD) {
            insertionSort(arr, left, right);
            return;
        }

        if (left < right) {
            int middle = left + (right - left) / 2;
            mergeSort(arr, buffer, left, middle);
            mergeSort(arr, buffer, middle + 1, right);
            merge(arr, buffer, left, middle, right);
        }

    }

    public static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void sort(int[] arr) {
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        int arr[] = { 12, 11, 13, 5, 6, 7, 4, 2, 9, 8, 1, 11, 24, 512, 51, 22, 22, 32 };

        sort(arr);

        for (int i : arr) {
            System.out.print(i + " ");
        }

    }
}