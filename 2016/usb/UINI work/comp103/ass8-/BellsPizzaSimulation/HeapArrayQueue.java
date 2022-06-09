// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 8
 * Name:Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
import java.util.*;

/**
 * Implements a priority queue based on a heap that is
 * represented with an array.
 */
public class HeapArrayQueue <E extends Comparable<? super E> > extends AbstractQueue <E> { 

    @SuppressWarnings("unchecked") 
    private E[] data = (E[])(new Comparable[7]);
    private int count = 0;

    public int size() {
        return count;
    }

    public boolean isEmpty() { 
        return size() == 0; 
    }

    /**
     * Returns the element with the top priority in the queue. 
     * 
     * HINT: This is like 'poll()' without the removal of the element. 
     * 
     * @return the next element if present, or 'null' if the queue is empty.
     */
    public E peek() {
        /*# YOUR CODE HERE */
        if (data==null){return null;}
        return data[0];
    }

    /**
     * Removes the element with the top priority from the queue and returns it.
     * 
     * HINT: The 'data' array should contain a heap so the element with the top priority
     * sits at index '0'. After its removal, you need to restore the heap property again,
     * using 'sinkDownFromIndex(...)'.
     * 
     * @return the next element in the queue, or 'null' if the queue is empty.
     */
    public E poll() {
        /*# YOUR CODE HERE */
        if(data[0]==null){return null;}
        count--;
        E element = data[0];
        data[0] = null;
        sinkDownFromIndex(1);
        return element;
    }

    /**
     * Enqueues an element.
     * 
     * If the element to be added is 'null', it is not added. 
     * 
     * HINT: Make use of 'ensureCapacity' to make sure that the array can 
     * accommodate one more element. 
     * 
     * @param element - the element to be added to the queue
     * 
     * @return true, if the element could be added
     */
    public boolean offer(E element) {
        /*# YOUR CODE HERE */
        if (element == null) return false;
        ensureCapacity();
        if(data[0]==null){
            data[0]=element;
            count++;
            return true;
        }
        for (int i=0; i<data.length; i++){
            if(data[i]==null){
                data[i] = element;
                count++;
                return true;
            }
            if(data[i].compareTo(element)>0){
                bubbleUpFromIndex(i);
                data[i] = element;
                count++;
                return true;
            }
        }
        return false;
    }

    private void sinkDownFromIndex(int nodeIndex) {
        /*# YOUR CODE HERE */
        System.arraycopy(data, nodeIndex, data, nodeIndex - 1, data.length - nodeIndex);
    }

    private void bubbleUpFromIndex(int nodeIndex) {
        /*# YOUR CODE HERE */
        E lastElement = data[nodeIndex+1];
        data[nodeIndex+1] = data[nodeIndex];
        data[nodeIndex] = null;
        for (int i=nodeIndex+2; i<data.length; i++){
            E element = data[i];
            data[i] = lastElement;
            lastElement = element;
        }
    }

    /**
     * Swaps two elements in the supporting array.
     */
    private void swap(int from, int to) {
        E temp = data[from];
        data[from] = data[to];
        data[to] = temp;
    }

    /**
     *  Increases the size of the supporting array, if necessary
     */
    private void ensureCapacity() {
        if (count == data.length) {
            @SuppressWarnings("unchecked") 
            E[] newData = (E[])new Comparable[data.length * 2];

            // copy data elements
            for (int loop = 0; loop < count; loop++) {
                newData[loop] = data[loop];
            }
            data = newData;
        }
        return;
    }

    // no iterator implementation required for this assignment
    public Iterator<E> iterator() { 
        return null; 
    }
}
