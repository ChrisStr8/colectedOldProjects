
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Sort2 {

    public static void merge(ArrayList<String> A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        String[] L = new String[n1 + 1];
        String[] R = new String[n2 + 1];

        for (int i = 0; i < n1; i++) {
            L[i] = A.get(p + i);
        }

        for (int j = 0; j < n2; j++) {
            R[j] = A.get(q + j + 1);
        }

        L[n1] = "ZZZZZZ"; //'max' string
        R[n2] = "ZZZZZZ"; //'max' string
        int i = 0, j = 0;

        for (int k = p; k <= r; k++) {
            if (L[i].compareTo(R[j]) <= 0) {
                A.set(k,L[i]);
                i = i + 1;
            } else {
                A.set(k, R[j]);
                j = j + 1;
            }
        }
    }

    public static void mergeSort(ArrayList<String> A, int p, int r) {
        if (p < r) {
            double qd = Math.floor((p + r) / 2);
            int q = (int) qd;
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }

    public static void sort(String[] array) {

        // START OF SORTING ALGORITHM
        // Merge Sort
        int p = 0;
        int r = array.length - 1;
        ArrayList<String> arList = new ArrayList<String>(Arrays.asList(array));
        mergeSort(arList, p, r);
        array = arList.toArray(array);
        // END OF SORTING ALGORITHM
    }

    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("FAILED: Too many arguments. "
                    + "Only need one (optional): data file name.");
            return;
        }

        // String fileName = "sample.txt";
        String fileName = "data-100000.txt";
        if (args.length == 1) {
            fileName = args[0];
        }

        try {
            Scanner s = new Scanner(new File(fileName));

            // Read the contents of the file one line at a time into an ArrayList.
            ArrayList<String> l = new ArrayList<String>();

            while (s.hasNext()) {
                l.add(s.next());
            }

            String[] array = new String[l.size()];
            for (int i = 0; i < array.length; i++) {
                array[i] = l.get(i);
            }


            System.out.println("ORIGINAL:\n");
            for (String item : array) {
                System.out.println(item);
            }


            // SORT.
            long start = System.nanoTime();
            sort(array);
            long length = System.nanoTime() - start;


            System.out.println("\nSORTED:\n");
            for (String item : array) {
                System.out.println(item);
            }


            System.out.println("I sorted " + array.length + " strings and took "
                    + length / 1000 + " microseconds to do this.");
        } catch (FileNotFoundException e) {
            System.out.println("FAILED: Cannot find file called " + fileName);
        }
    }
}
