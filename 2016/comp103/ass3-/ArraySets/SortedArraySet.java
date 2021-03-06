// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 3
 * Name:Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */

import java.util.*;


/**
 * SortedArraySet - a Set collection;
 *
 *  The implementation uses an array to store the items
 *  and a count variable to store the number of items in the array.
 * 
 *  The items in the set should be stored in positions
 *    0, 1,... (count-1) of the array.
 * 
 *  The length of the array when the set is first created should be 10. 
 * 
 *  Items in the array are kept in order, based on the "compareTo()" method.
 *  
 *  Binary search is used for searching for items.  
 *  
 *  Note that a set does not allow null items or duplicates.
 *  Attempting to add null should throw an IllegalArgumentException exception
 *  Adding an item which is already present should simply return false, without
 *    changing the set.
 *  It should always compare items using equals()  (not using ==)
 *  When the array is full, it will create a new array of double the current
 *    size, and copy all the items over to the new array
 */

// We need "extends Comparable" so that we can use the "compareTo()" method
public class SortedArraySet <E extends Comparable> extends AbstractSet <E> {

    // Data fields
    /*# Copy your declrations from ArraySet here */
    /*# YOUR CODE HERE */
    private int count;
    private int size;
    private E[] data;

    // --- Constructors --------------------------------------

    @SuppressWarnings("unchecked")  // this will stop Java complaining about the cast
    public SortedArraySet() {
        /*# Copy your code from ArraySet (constructor) here AND change "Object" to "String" */
        /*# YOUR CODE HERE */
        this.size = 10;
        this.count = 0;
        this.data = (E[]) new String[size];
    }

    // --- Methods --------------------------------------
    /** 
     * @return the number of items in the set  
     */
    public int size () {
        /*# Copy your code from ArraySet size() method here */
        /*# YOUR CODE HERE */
        return count;
    }

    /** 
     *  Adds the specified item to this set 
     *  (if it is not already in the set).
     *  Will not add a null value (throws an IllegalArgumentException in this case).
     *  
     *  @param item the item to be added to the set
     *  @return true if the collection changes, and false if it did not change.
     */
    @SuppressWarnings("unchecked")  // this will stop Java complaining
    public boolean add(E item) {
        /*# Copy your code from ArraySet add(E item) here 
         *  AND then modify it to insert the item at the right place
         *  so that the array data remains sorted 
         *  make use of a helper method "findIndexOf" 
         */
        /*# YOUR CODE HERE */
        if(item==null){throw new IllegalArgumentException();}
        else if(contains(item)){return false;}
        ensureCapacity();
        int index = findIndexOf(item);
        if(data[index]==null) {
            this.data[index] = item;
            count+=1;
            return true;
        } else if (data[index] != null) {
            E[] newData = (E[]) new String[size];
            System.arraycopy(this.data, 0, newData, 0, (index - 1));
            newData[index] = item;
            count += 1;
            System.arraycopy(this.data, index, newData, (index + 1), (count - index - 1));
            this.data = newData;
            return true;
        }
        return false;
    }

    /** 
    * @return true if this set contains the specified item.
    *        
    */
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare 
    public boolean contains(Object item) {
        E itm = (E) item;

        /*# Copy your code from ArraySet contains(Object item) method here 
         *  then potentially modify it to ensure that it works 
         *  with the new version of findIndexOf 
         */
        /*# YOUR CODE HERE */
        if(item==null){return false;}
        else {
            return data[findIndexOf(itm)].equals(itm);
        }
    }

    /** 
    *  Removes an item matching a given item.
    *  @return true if the item was present and then removed.
    *  Makes no changes to the set and returns false if the item is not present.
    */
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare 
    public boolean remove (Object item) {
        E itm = (E) item;

        /*# Copy your code from ArraySet remov(Object item) method here
         *  then modify it to ensure that 
         *  a) the array data remains sorted after the removal 
         *  b) the code works with the new version of findIndexOf
         */
        /*# YOUR CODE HERE */
        if(item==null){return false;}
        else if(contains(item)){
            this.data[findIndexOf(itm)] = null;
            count-=1;
        }
        return false;
    }

    /** 
    * 
    * Ensures data array has sufficient capacity (length)
    * to accomodate a new item 
    */
    @SuppressWarnings("unchecked")  // this will stop Java complaining about the cast
    private void ensureCapacity () {
        /*# Copy your code from ArraySet ensureCapacity() method here*/
        /*# you only need to change "Object" to "String" */
        /*# YOUR CODE HERE */
        if (size()==size){
            int newSize=size*2;
            E[] newData = (E[]) new String[newSize];
            System.arraycopy(this.data, 0, newData, 0, count);
            this.data = newData;
            this.size = newSize;
        }
    }

    // It is much more convenient to define the following method 
    // and use it in the methods above.

    /** 
    *  Finds the index of where an item is in the dataarray,
    *  (or where it ought to be, if it's not there).
    *  Assumes that the item is not null.
    *  
    *  Uses binary search and requires that the items are kept in order.
    *  Uses the "compareTo()" method to compare two items with each other, e.g., as in
    *  "item1.compareTo(item2)", resulting in 
    *  0, if the items are equal,
    *  a value lower than 0, if item1 is smaller than item2,
    *  a value greater than 0, if item1 is greater than item2.
    *         
    *  @return the index of the item, or 
    *               the index where it should be inserted, if it is not present       
    */
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare 
    private int findIndexOf(E item) {
        /*# YOUR CODE HERE */
        int index = -1;
        int upper = count-1;
        int lower = 0;
        int midpoint;
        boolean notFound = true;
        while(notFound){
            midpoint = (int) (lower+(upper-lower)*0.5);
            if(item.compareTo(data[midpoint])<0){
                upper = midpoint;
            } else if(item.compareTo(data[midpoint])>0){
                lower = midpoint;
            }else if(upper==lower){
                index = midpoint;
                notFound = false;
            }
        }
        return index;
    }

    // --- Iterator and Comparator --------------------------------------
    /** ---------- The code below is already written for you ---------- **/

    /** 
     * @return an iterator over the items in this set. 
     * 
     */
    public Iterator <E> iterator() {
        return new SortedArraySetIterator(this);
    }

    private class SortedArraySetIterator implements Iterator <E> {
        // needs fields, constructor, hasNext(), next(), and remove()

        // fields
        private SortedArraySet<E> set;
        private int nextIndex = 0;
        private boolean canRemove = false;

        // constructor
        private SortedArraySetIterator(SortedArraySet<E> s) {
            set = s;
        }

        /**
         * @return true if iterator has at least one more item
         */
        public boolean hasNext() {
            return (nextIndex < set.count);
        }

        /** 
         * Returns the next element or throws a 
         * NoSuchElementException exception if none exists. 
         * 
         * @return next item in the set
         */
        public E next() {
            if (nextIndex >= set.count)
                throw new NoSuchElementException();

            canRemove = true;

            return set.data[nextIndex++];
        }

        /** 
         *  Removes the last item returned by the iterator from the set.
         *  Can only be called once per call to next.
         */
        public void remove() {
            if (! canRemove)
                throw new IllegalStateException();

            set.remove(set.data[nextIndex-1]);
            canRemove = false;
        }
    }
}

