// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

import java.util.*;

/**
 * Class ImagesDouble implements a list of images.
 * 
 * Each image is represented with an ImageNodeDouble object.
 * The ImageNodeDouble objects form a linked list. 
 * 
 * An object of this class maintains the reference to the first image node and
 * delegates operations to image nodes as necessary. 
 * 
 * An object of this class furthermore maintains a "cursor", i.e., a reference to a location in the list.
 * 
 * The references to both first node and cursor may be null, representing an empty collection. 
 * 
 * @author Thomas Kuehne
 */

public class ImagesDouble implements Iterable<String>
{
    private ImageNodeDouble head;     // the first image node
    private ImageNodeDouble cursor;   // the current point for insertion, removal, etc. 

    /**
     * Creates an empty list of images.
     */
    public ImagesDouble() {
        cursor = head = null;
    }

    /**
     * @return the fileName of the image at the current cursor position.
     * 
     * This method relieves clients of Images from knowing about image nodes and the 'getFileName()' method.
     */
    public String getImageFileNameAtCursor() {
        // deal with an inappropriate call gracefully
        if (cursor == null)
            return "";  // the correct response would be to throw an exception.

        return cursor.getFileName();
    }

    /**
     * @return the current cursor position.
     * 
     * Used by clients that want to save the current selection in order to restore it after an iteration.
     */
    public ImageNodeDouble getCursor() {
        return cursor;
    }

    /**
     * Sets the cursor to a new node.
     * 
     * @param newCursor the new cursor position
     */
    public void setCursor(ImageNodeDouble newCursor) {
        cursor = newCursor;
    }

    /**
     * Positions the cursor at the start
     *    
     * For the core part of the assignment.
     */
    public void moveCursorToStart() {
        /*# YOUR CODE HERE */
        cursor = head;
    }

    /**
     * Positions the cursor at the end
     *
     * For the core part of the assignment.
     * 
     * HINT: Consider the list could be empty. 
     */
    public void moveCursorToEnd() {
        /*# YOUR CODE HERE */
        if (head==null){cursor=null;}
        else if(head.getNext()==null){cursor=head;}
        else{
            ImageNodeDouble next = head;
            while(next!=null){
                next=next.getNext();
                if(next==null){break;}
                cursor=next;
            }
        }
    }

    /**
     * Moves the cursor position to the right. 
     */
    public void moveCursorRight() {
        // is it impossible for the cursor to move right?
        if (cursor == null  ||  cursor.getNext() == null)
            return;

        // advance the cursor
        cursor = cursor.getNext();
    }

    /**
     * Moves the cursor position to the left.
     * 
     * Assumption: 'cursor' points to a node in the list!
     */
    public void moveCursorLeft() {  
        // is it impossible for the cursor to move left?
        if (cursor == null  ||  cursor.getLast() == null)
            return;

        // advance the cursor
        cursor = cursor.getLast();
    }

    /**
     * Returns the number of images
     * 
     * @return number of images
     */
    public int count() {
        if (head == null)          // is the list empty?
            return 0;                // yes -> return zero

        return head.count();   // no -> delegate to linked structure
    }

    /**
     * Adds an image after the cursor position
     * 
     * For the core part of the assignment.
     * 
     * @param imageFileName the file name of the image to be added
     * 
     * HINT: Consider that the current collection may be empty.
     * HINT: Create a new image node here and and delegate further work to method 'insertAfter' of class ImageNodeDouble.
     * HINT: Pay attention to the cursor position after the image has been added. 
     * 
     */
    public void addImageAfter(String imageFileName) {
        /*# YOUR CODE HERE */
        ImageNodeDouble newNode = new ImageNodeDouble(imageFileName, null, null);
        if (head == null){
            head = newNode;
            moveCursorToStart();
        }
        else{cursor.insertAfter(newNode);}
    }

    /**
     * Adds an image before the cursor position
     * 
     * For the completion part of the assignment.
     * 
     * @param imageFileName the file name of the image to be added
     * 
     * HINT: Create a new image node here and then
     *         1. Consider that the current collection may be empty.
     *         2. Consider that the head may need to be adjusted.
     *         3. if necessary, delegate further work to 'insertBefore' of class ImageNodeDouble.
     * HINT: Pay attention to the cursor position after the image has been added. 
     * 
     */ 
    public void addImageBefore(String imageFileName) {  
        /*# YOUR CODE HERE */
        ImageNodeDouble newNode = new ImageNodeDouble(imageFileName, null, null);
        if (cursor==head && head!=null){
            newNode.setNext(head);
            head = newNode;
        }else if(head==null){
            head = newNode;
            cursor = head;
        }else {
            head.insertBefore(newNode, cursor);
        }
    }

    /**
     * Removes all images.
     *   
     * For the core part of the assignment.
     */
    public void removeAll()
    {
        /*# YOUR CODE HERE */
        this.moveCursorToStart();
        while(head!=null){remove();}
    }

    /**
     * Removes an image at the cursor position
     *
     * For the core part of the assignment.
     * 
     * HINT: Consider that the list may be empty.
     * 
     * HINT: Handle removing at the very start of the list in this method and 
     * delegate the removal of other nodes by using method 'removeNodeUsingPrevious' from class ImageNodeDouble. 
     * 
     * HINT: Make sure that the cursor position after the removal is correct. 
     */

    public void remove() {
        /*# YOUR CODE HERE */
        if (cursor==null){}
        else if(cursor.equals(head)){cursor=head=cursor.getNext();}
        else{
            ImageNodeDouble previous = head.nodeBefore(cursor);
            cursor.removeNodeUsingPrevious(previous);
            cursor=previous.getNext();
            if (cursor==null){moveCursorToEnd();}
        }
    }

    /**
     * Reverses the order of the image list so that the last node is now the first node, and 
     * and the second-to-last node is the second node, and so on
     * 
     * For the completion part of the assignment.
     * 
     * HINT: Make sure there is something worth reversing first.
     * HINT: You will have to use temporary variables.
     * HINT: Don't forget to update the head of the list.
     */
    public void reverseImages() {
        /*# YOUR CODE HERE */   
        if (head!=null && head.getNext()!=null){
            ImageNodeDouble oldCursor = cursor;
            moveCursorToEnd();
            ImageNodeDouble newHead = cursor;
            while(!cursor.equals(head)){
                cursor.setLast(cursor.getNext());
                cursor.setNext(head.nodeBefore(cursor));
                cursor = cursor.getNext();
            }
            head.setNext(null);
            head = newHead;
            cursor = oldCursor;
        }
    }

    /** 
     * @return an iterator over the items in this list of images. 
     * 
     * For the completion part of the assignment.
     * 
     */
    public Iterator <String> iterator() {
        /*# YOUR CODE HERE */ 
        return new ImagesDoubleIterator(this); 
    }

    /** 
     * Support for iterating over all images in an "Images" collection. 
     * 
     * For the completion part of the assignment.
     * 
     */
    private class ImagesDoubleIterator implements Iterator<String> {

        // needs fields, constructor, hasNext(), next(), and remove()

        // fields
        /*# YOUR CODE HERE */ 
        private ImagesDouble images;
        private ImageNodeDouble next;

        // constructor
        private ImagesDoubleIterator(ImagesDouble images) {
            /*# YOUR CODE HERE */   
            this.images = images;
            next = images.head;
        }

        /**
         * @return true if iterator has at least one more item
         * 
         * For the completion part of the assignment.
         * 
         */
        public boolean hasNext() {
            /*# YOUR CODE HERE */   
            return next!=null;
        }

        /** 
         * Returns the next element or throws a 
         * NoSuchElementException exception if none exists. 
         * 
         * For the completion part of the assignment.
         *  
         * @return next item in the set
         */
        public String next() {
            /*# YOUR CODE HERE */   
            if (!hasNext()){throw new NoSuchElementException();}
            else{
                String nextFile = next.getFileName();
                next = next.getNext();
                return nextFile;
            }
        }

        /** 
         *  Removes the last item returned by the iterator from the set.
         *  Not supported by this iterator.
         */
        public void remove() {
            throw new UnsupportedOperationException();        
        }
    }
}
