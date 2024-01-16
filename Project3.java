import java.util.Arrays;
import java.util.Random;

public class Project3 {

    public static void main(String[] args) {
        test();
        analyze(10);
        analyze(100);
        analyze(1000);
        analyze(10000);
        analyze(100000);
    }

    public static void test() {
        int[] testList = {27, 8, 64, 13, 392, 87, 725, 101, 1106, 77};
        System.out.println("Original list: " + Arrays.toString(testList));
        System.out.println("Insertion Sort: " + Arrays.toString(insertionSort(testList.clone())));
        System.out.println();
        System.out.println("Original list: " + Arrays.toString(testList.clone()));
        System.out.println("Bubble Sort: " + Arrays.toString(bubbleSort(testList.clone())));
        System.out.println();
        System.out.println("Original list: " + Arrays.toString(testList.clone()));
        System.out.println("Selection Sort: " + Arrays.toString(selectionSort(testList.clone())));
        System.out.println();
        System.out.println("Original list: " + Arrays.toString(testList.clone()));
        System.out.println("Shellsort: " + Arrays.toString(shellSort(testList.clone())));
        System.out.println();
        System.out.println("Original list: " + Arrays.toString(testList.clone()));
        System.out.println("Mergesort: " + Arrays.toString(mergeSort(testList.clone())));
        System.out.println();
        System.out.println("Original list: " + Arrays.toString(testList.clone()));
        System.out.println("Quicksort: " + Arrays.toString(quickSort(testList.clone(), 0, testList.length-1)));
        System.out.println();
    }

    //clocks the runtime of each sorting method takes @int size as parameter to determine list length
    public static void analyze(int size) {
        Random randomNumber = new Random();
        int[] randomList = new int[size];
        for (int i = 0; i < size; i++) {
            randomList[i] = randomNumber.nextInt();
        }
        int[] cloned1 = randomList.clone();
        int[] cloned2 = randomList.clone();
        int[] cloned3 = randomList.clone();
        int[] cloned4 = randomList.clone();
        int[] cloned5 = randomList.clone();
        int[] cloned6 = randomList.clone();

        System.out.printf( "List of (size %d): %n", size);

        long start = System.nanoTime();
        insertionSort(cloned1);
        long stop = System.nanoTime();
        System.out.printf("\t\t\tInsertion Sort: %d ns (%.4f ms)%n", (stop - start), ((stop - start) / 1000000.0));

        start = System.nanoTime();
        bubbleSort(cloned2);
        stop = System.nanoTime();
        System.out.printf("\t\t\tBubble Sort: %d ns (%.4f ms)%n", (stop - start), ((stop - start) / 1000000.0));

        start = System.nanoTime();
        selectionSort(cloned3);
        stop = System.nanoTime();
        System.out.printf("\t\t\tSelection Sort: %d ns (%.4f ms)%n", (stop - start), ((stop - start) / 1000000.0));

        start = System.nanoTime();
        shellSort(cloned4);
        stop = System.nanoTime();
        System.out.printf("\t\t\tShellsort: %d ns (%.4f ms)%n", (stop - start), ((stop - start) / 1000000.0));

        start = System.nanoTime();
        mergeSort(cloned5);
        stop = System.nanoTime();
        System.out.printf("\t\t\tMergesort: %d ns (%.4f ms)%n", (stop - start), ((stop - start) / 1000000.0));

        start = System.nanoTime();
        quickSort(cloned6, 0, cloned6.length-1);
        stop = System.nanoTime();
        System.out.printf("\t\t\tQuicksort: %d ns (%.4f ms)%n", (stop - start), ((stop - start) / 1000000.0));
    }

    //sorts integers by inserting the smallest int at each subsequent index
    public static int[] insertionSort(int[] list) {
        for (int i = 1; i < list.length; ++i) {
            int insert = list[i];
            int j = i - 1;
            while (j >= 0 && list[j] > insert) {
                list[j + 1] = list[j];
                j -= 1;
            }
            list[j + 1] = insert;
        }
        return list;
    }

    //sorts integers by comparing and sorting every set of pairs in the list
    public static int[] bubbleSort(int[] list) {
        boolean nextPass = true;
        for (int i = 0; i < list.length - 1 && nextPass; i++) {
            nextPass = false;
            for (int j = 0; j < list.length - i - 1; j++) {
                if (list[j] > list[j + 1]) {
                    int temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    nextPass = true;
                }
            }
        }
        return list;
    }

    //sorts integers by replacing the lowest int with every subsequent index at each pass
    public static int[] selectionSort(int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < list.length; j++) {
                if (list[j] < list[min]) {
                    min = j;
                }
            }
            int temp = list[min];
            list[min] = list[i];
            list[i] = temp;
        }
        return list;
    }

    //sorts integers by halving the lists and comparing the ints in the same positions in both halves
    public static int[] shellSort(int[] list) {
        for (int split = list.length / 2; split > 0; split /= 2) {
            for (int i = split; i < list.length; i++) {
                int temp = list[i];
                int j;
                for (j = i; j >= split && list[j - split] > temp; j -= split) {
                    list[j] = list[j - split];
                }
                list[j] = temp;
            }
        }
        return list;
    }

    //sorts integers by separating them into two trees
    public static int[] mergeSort(int[] list) {
        if (list.length <= 1) {
            return list;
        }

        int[] half1 = new int[list.length / 2];
        System.arraycopy(list, 0, half1, 0, half1.length);
        mergeSort(half1);
        int[] half2 = new int[list.length - list.length / 2];
        System.arraycopy(list, half1.length, half2, 0, half2.length);
        mergeSort(half2);
        merge(half1, half2, list);

        return list;
    }

    //merges two lists together @ int list 1, @ int list 2, @ temporary list
    private static void merge(int[] list1, int[] list2, int[] tempList) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < list1.length && j < list2.length) {
            if (list1[i] <= list2[j]) {
                tempList[k++] = list1[i++];
            } else {
                tempList[k++] = list2[j++];
            }
        }
        while (i < list1.length) {
            tempList[k++] = list1[i++];
        }
        while (j < list2.length) {
            tempList[k++] = list2[j++];
        }
    }

    //sorts list in conjuction with a pivot index parameters @ int list, @ first/low point, @last/high point
    private static int[] quickSort(int[] list, int first, int last) {

        if (last > first) {
            int pivot = partition(list, first, last);
            quickSort(list, first, pivot - 1);
            quickSort(list, pivot + 1, last);
        }
        return list;
    }

    //divides and organizes the list amongst both sides of the pivot @int[] list, @first int, @last int
    private static int partition(int[] list, int first, int last) {
        int pivot = list[last];
        int i = (first - 1);
        for (int j = first; j <= last - 1; j++) {
            if (list[j] < pivot) {
                i++;
                int temp = list[i]; //swaps values
                list[i] = list[j];
                list[j] = temp;
            }
        }
        int temp = list[i + 1];
        list[i + 1] = list[last];
        list[last] = temp;

        return (i + 1);
    }
}

/*
    All of the sorting methods perform incredibly quickly when dealing with small lists. They are all able to perform
their function in a matter of nanoseconds. As the list sizes increase, so do the performance times, which is expected.
Sorting algorithms with a time complexity of Θ(nlogn) such as Shellsort, Mergesort, and Quicksort are much better
suited for lists of very large sizes, such as 10,000 and 100,000. Compared to Insertion, Bubble, and Selection sorts,
the external sorting methods perform significantly faster with large arrays.

    The Bubble Sort method in particular takes an incredibly long time compared to the other methods when dealing with
long lists. The program often takes up to a full minute to sort and print the results of the Bubble Sort method compared
to the other sort methods for the 100,000 size list. Quicksort is the best sorting method for large lists and is the
most consistently quick method. Bubble Sort consistently seems to be the least efficient method. The exchange sorts
(Insertion, Bubble, and Selection) have the time complexity of Θ(n^2) which means their runtime grows exponentially with
the size of the input.

    Runtime is determined by numerous factors such as list size and range of values. It is important to know how and
when to apply each type of sorting method to ensure that your program has the best performance and runtime. For very
small lists, any sorting method will generally suffice. Even though Exchange sorts are faster than Θ(nlogn) algorithms
for smaller lists, the difference is too minor to make and significant difference. However, when dealing with larger
lists it is better to go with an external sort method. Breaking a list into smaller sub-lists helps to greatly decrease
the amount of comparisons and iterations.

 */