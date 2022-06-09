// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/**
 * Class ImageNodeDouble implements a node that forms a linked list data structure in conjunction with other nodes of the same type.
 * 
 * A node represents an images by storing the filename of the image.
 * A node furthermore links to their successor node.
 * 
 * @author Thomas Kuehne 
 */
public class ImageNodeDouble {

    // A string that contains the full file name of an image file.
    private String imageFileName;

    // A reference to the next ImageNodeDouble in the linked list.
    private ImageNodeDouble next;
    
    // A reference to the last ImageNodeDouble in the linked list.
    private ImageNodeDouble last;

    /**
     * Creates an image node
     * 
     * @param imageFileNameStr the file name of the image to be represented by this node
     * @param nextNode the reference to the next node in the list
     */
    public ImageNodeDouble(String imageFileNameStr, ImageNodeDouble nextNode, ImageNodeDouble lastNode)
    {
        this.imageFileName = imageFileNameStr;
        this.next = nextNode;
        this.last = lastNode;
    }

    /**
     * Returns the filename of the image. 
     */
    public String getFileName() {
        return imageFileName;
    }

    /** 
     * Returns the successor of this node.
     */
    public ImageNodeDouble getNext() {
        return next;
    }
    
    /** 
     * Returns the predecessor of this node.
     */
    public ImageNodeDouble getLast() {
        return last;
    }

    /**
     * Changes the predecessor of this node.
     */
    public void setNext(ImageNodeDouble newNext) {
        this.next = newNext;
    }
    
    /**
     * Changes the successor of this node.
     */
    public void setLast(ImageNodeDouble newLast) {
        this.last = newLast;
    }

    /**
     * Returns the number of elements in the list starting from this node 
     *
     * For the core part of the assignment.
     * 
     * @return the number of nodes in the list starting at this node.
     * 
     */
    public int count() {
        /*# YOUR CODE HERE */
        int c = 1;
        if(next!=null){c+=this.next.count();}
        return c;
    }

    /**
     * Inserts a node after this node.
     * 
     * @param newNode the node to be inserted
     * 
     */
    public void insertAfter(ImageNodeDouble newNode) {
        newNode.setNext(this.getNext());
        this.setNext(newNode);
        newNode.setLast(this.nodeBefore(newNode));
    }

    /**
     * Inserts a new node before the cursor position
     * 
     * For the core part of the assignment.
     * 
     * Assumption: The cursor points to some node that is part of the list started by the successor of this node.
     * In other words, the cursor cannot point to this node, but will point to one of the successors of this node.
     * 
     * @param newNode the new node to be inserted
     * @param cursor  the position before which the node needs to be inserted
     * 
     * HINT: Make use of method 'nodeBefore' in order to find the node before the cursor.
     * HINT: Once you have the previous node, you can make use of method 'insertAfter'.
     *
     */
    public void insertBefore(ImageNodeDouble newNode, ImageNodeDouble cursor) {
        /*# YOUR CODE HERE */
        ImageNodeDouble nodeB = this.nodeBefore(cursor);
        nodeB.setNext(newNode);
        newNode.setNext(cursor);
        newNode.setLast(nodeB);
        cursor.setLast(newNode);
    } 

    /**
     * 
     * Returns the node before the provided node.
     * 
     * For the core part of the assignment.
     * 
     * Assumption: The provided node is one of the successors of this node.
     *
     * @param target the node whose predecessor is required 
     * @return the node before the provided node
     */
    public ImageNodeDouble nodeBefore(ImageNodeDouble target) {
        /*# YOUR CODE HERE */
        ImageNodeDouble nodeB;
        if (this.equals(target)){nodeB = null;}
        else{
            nodeB = null;
            ImageNodeDouble node = this;
            while(!node.equals(target)){
                nodeB = node;
                node = nodeB.getNext();
            }
        }
        return nodeB;
    }

    /**
     * Removes the node at the cursor, given a reference to the previous node
     * 
     * @param previous the node preceding this node
     * 
     */
    public void removeNodeUsingPrevious(ImageNodeDouble previous) {
        previous.setNext(this.getNext());
    }     
}
