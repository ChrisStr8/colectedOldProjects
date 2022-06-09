import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

public class Sort1 {
    static class CountEl{
        String[] array;
        int[] inds;
        
        CountEl(int N){
            array = new String[N];
            inds = new int[N];
        }
        void setArray(String[] array){
            System.arraycopy(array,0,this.array,0,array.length);
        }
        void setInds(int[] inds){
            System.arraycopy(inds, 0, this.inds, 0, inds.length);
        }
    }
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
    public static CountEl countingSort(String[] array, LinkedHashMap map, int[] count){        
        //Create another kind of array 'count' with integer index for the sake of simplicity
        int N = array.length;

        //Count frequency
        for(int i=0; i<N; i++){
            int key = (Integer)map.get(array[i]);
            count[key+1] += 1;
        }
        
        //Trim count
        LinkedHashMap map2 = new LinkedHashMap();
        ArrayList<Integer> countL = new ArrayList<Integer>();
        countL.add(0);
        int j = 0;
        for(int i=0; i<count.length; i++){
            if(count[i] >0){
                countL.add(count[i]);
                map2.put(Integer.toString(i-1),j);
                j++;
            }
        }
        
        for(int i=1; i<countL.size(); i++){
            int prevval = countL.get(i-1);
            int curval = countL.get(i);
            countL.set(i, prevval+curval);
        }
        
        //Move item
        String[] aux = new String[array.length];
        int[] inds = new int[array.length];
        for(int i=0; i<N; i++){
            String key1 = Integer.toString((Integer)map.get(array[i]));
            if(map2.containsKey(key1)){
                int idx =(Integer)map2.get(key1);
                int pos = countL.get(idx);
                inds[i] = pos;
                aux[pos] = array[i];
                countL.set(idx, countL.get(idx)+1);
            }
        }
        System.arraycopy(aux, 0, array, 0, N);
        CountEl celm = new CountEl(N);
        celm.setArray(array);
        celm.setInds(inds);

        return celm;
    }
    
    public static String[] sort(String[] array) {

	// START OF SORTING ALGORITHM
	//LSD Radix Sort
        String[] ar = new String[array.length];
        int L = array[0].length();
        
//        int c = 0; //sort by only the first character of the string
        for(int c = L-1; c>=0; c--){ //sort by the full string
            //Get one character from the full array
            for(int i=0; i<array.length; i++){
                ar[i] = Character.toString(array[i].charAt(c));
            }

            //The counting variables for counting sort
            //LinkedHashMap count = new LinkedHashMap();
            LinkedHashMap keyToInt = new LinkedHashMap();
            int k = 0;
            if(!isInteger(ar[0])){
                int startC = 65;
                int Nchar  = 26;
                
                for(int i=startC; i<(startC + Nchar); i++){
                    String str = Character.toString((char) i);            
                    keyToInt.put(str,k);
                    k++;
                }
            }
            else{
                for (int i=0; i<10; i++){
                    keyToInt.put(Integer.toString(i),k);
                    k++;
                }
            }
            keyToInt.put("-",k);
            
            //create empty 'count' array
            int[] count = new int[keyToInt.size()];
            
            CountEl celm = countingSort(ar, keyToInt, count);

            String[] A = new String[array.length];
            for(int i=0; i<ar.length; i++){
                A[celm.inds[i]] = array[i];
            }
            
            System.arraycopy(A,0, array, 0, A.length);
            
        }
	// END OF SORTING ALGORITHM
	return array;
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
	    array = sort(array);
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
