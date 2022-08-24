public class Task_9 {
    public static void main(String args[]) {
        int arr[] = {12, 11, 13, 5, 6, 7};

        Task_9 ob = new Task_9();
        ob.sort(arr);

        System.out.println("Sorted array is: ");
        printArray(arr);
    }

    public void sort(int arr[]) {
        int n = arr.length;

        // Build heap (regroup the array)
        for (int i = n / 2 - 1; i >= 0; i--)
            buildHeap(arr, n, i);

        // One by one we extract the elements from the heap
        for (int i = n - 1; i >= 0; i--) {
            // Moving the current root to the end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            buildHeap(arr, i, 0);
        }
    }

    // The procedure for converting to a binary heap of a subtree with a root node = i
    void buildHeap(int arr[], int n, int i) {
        int largest = i;           // The largest element as root heap
        int leftElem = 2 * i + 1;  // left element in heap  = 2 * i + 1
        int rightElem = 2 * i + 2; // right element in heap = 2 * i + 2

        // If the left child element is larger than the root
        if (leftElem < n && arr[leftElem] > arr[largest]) {
            largest = leftElem;
        }

        // If the right child element is larger than the largest element
        if (rightElem < n && arr[rightElem] > arr[largest]) {
            largest = rightElem;
        }

        // If the largest element is not the root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively transform the subtree
            buildHeap(arr, n, largest);
        }
    }

    // print array
    static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
