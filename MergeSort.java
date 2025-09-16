class MergeSort{



    // The arr[left .. middle]
    // The arr[middle+1 .. right]
    public static void merge(int arr[],int left,int middle,int right){
        
        // Find sizes of two subarrays to be merged
        int n1 = middle - left + 1;
        int n2 = right - middle;
    
        // Create temp arrays
        int leftArray[] = new int[n1];
        int rightArray[] = new int[n2];    

        // Copy data to temp arrays
        for(int i = 0;i<n1;i++){
            leftArray[i] = arr[left + i];
        }
        for(int j = 0;j<n2;j++){
            rightArray[j] = arr[middle + 1 + j];
        }


        int i = 0,j = 0;
        int k = left;

        while(i < n1 && j < n2){
            if(leftArray[i] <= rightArray[j]){
                arr[k] = leftArray[i];
                i++;
            }
            else{
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i< n1){
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while(j<n2){
            arr[k] = rightArray[j];
            j++;
            k++;
        }

    }
    public static void mergeSort(int arr[],int left, int right){
        if(left< right){
            int middle = left + (right - left) / 2;

            mergeSort(arr,left,middle);
            mergeSort(arr, middle+1, right);

            merge(arr, left, middle, right);
        }
    }

    public static void main(String[] args) {
        int arr []  = {12,11,13,5,6,7};
        mergeSort(arr, 0, arr.length - 1);

        
        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i] + " ");
        }   
    }
}