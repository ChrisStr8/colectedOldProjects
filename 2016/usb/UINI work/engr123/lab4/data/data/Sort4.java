
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sort4 {
    static class BinaryTree {
        BinaryTree leftSubTree;
        String node;
        BinaryTree rightSubTree;

        BinaryTree(String item) {
            leftSubTree = null;
            node = item;
            rightSubTree = null;
        }
    }

    public static BinaryTree insert(BinaryTree searchTree, String item) {
        if (searchTree == null) {
            return new BinaryTree(item);
        } else {
            if (item.compareTo(searchTree.node) < 0) {
                searchTree.leftSubTree = insert(searchTree.leftSubTree, item);
            } else {
                searchTree.rightSubTree = insert(searchTree.rightSubTree, item);
            }

            return searchTree;
        }
    }

    public static void inOrder(BinaryTree searchTree, ArrayList<String> strList) {
        if (searchTree != null) {
            inOrder(searchTree.leftSubTree, strList);
            strList.add(searchTree.node);
            inOrder(searchTree.rightSubTree, strList);
        }
    }

    public static void sort(String[] array) {

        // START OF SORTING ALGORITHM
        // Tree Sort
        BinaryTree searchTree = null;
        for (int i = 0; i < array.length; i++) {
            searchTree = insert(searchTree, array[i]);
        }
        ArrayList<String> strList = new ArrayList<String>();
        inOrder(searchTree, strList);
        array = strList.toArray(array);

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
