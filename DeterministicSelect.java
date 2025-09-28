class DeterministicSelect {

    // finding k smallest element (k -- 0 based)
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

    private static int medianOfMedians(int[] arr, int left, int right) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'medianOfMedians'");
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partition'");
    }
}