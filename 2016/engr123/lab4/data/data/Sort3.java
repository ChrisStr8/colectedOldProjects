import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Sort3{
    public static void sort(String[] array) {

	// START OF SORTING ALGORITHM
        java.util.Arrays.sort(array); // Java's built-in quick sort algorithm.

	// END OF SORTING ALGORITHM
    }
    
    public static void main(String[] args) {
	if (args.length > 1) {
	    System.out.println("FAILED: Too many arguments. " +
			       "Only need one (optional): data file name.");
	    return;
	}
	
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
	    
	    System.out.println("I sorted " + array.length + " strings and took " +
			       length/1000 + " microseconds to do this.");
	} catch (FileNotFoundException e) {
	    System.out.println("FAILED: Cannot find file called " + fileName);
	}
    }
}
