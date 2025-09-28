import java.util.*;

public class DeterministicSelect {

    public static int select(int[] arr, int left, int right, int k) {
        while (true) {
            if (left == right) {
                return arr[left];
            }

            int pivotIndex = medianOfMedians(arr, left, right);
            pivotIndex = partition(arr, left, right, pivotIndex);

            if (k == pivotIndex) {
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }

        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;

        if (n <= 5) {
            Arrays.sort(arr, left, right + 1);
            return left + n / 2;
        }

        int numMedians = (int) Math.ceil((double) n / 5);
        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(arr, subLeft, subRight + 1);
            int medianIndex = subLeft + (subRight - subLeft) / 2;
            swap(arr, left + i, medianIndex);
        }

        int mid = left + (numMedians - 1) / 2;
        return selectIndex(arr, left, left + numMedians - 1, mid);
    }

    private static int selectIndex(int[] arr, int left, int right, int k) {
        if (left == right) {
            return left;
        }

        int pivotIndex = medianOfMedians(arr, left, right);
        pivotIndex = partition(arr, left, right, pivotIndex);

        if (k == pivotIndex) {
            return pivotIndex;
        } else if (k < pivotIndex) {
            return selectIndex(arr, left, pivotIndex - 1, k);
        } else {
            return selectIndex(arr, pivotIndex + 1, right, k);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = { 12, 3, 5, 7, 4, 19, 26 };
        int k = 3;
        int result = select(arr, 0, arr.length - 1, k);
        System.out.println("The " + (k + 1) + "-th smallest element is " + result);
    }
}
