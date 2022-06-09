// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103, Assignment 9
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */

import java.util.Iterator;
import java.util.Queue;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/** ArrayQueueTest
 *  A JUnit class for testing an ArrayQueue
 */

public class ArrayQueueTest {

    /*# YOUR CODE HERE */
    private Queue<String> arrayQueue;

    @Before
    public void initialiseEmptyQueue() {
        arrayQueue = new ArrayQueue<>();
    }

    private void fillSet(int n) {
        for (int i = 1; i <= n; i++) {
            arrayQueue.offer("v" + i);
        }
    }

    @Test
    public void testEmptyQueueHasSizeZero() {
        assertEquals("An empty queue should have size zero", 0, arrayQueue.size());
    }

    @Test
    public void testEmptyQueueReturnsNull() {
        assertTrue("An empty queue should return null", arrayQueue.poll()==null);
    }

    @Test
    public void testOfferingToQueue() {
        for (int i = 1; i <= 20; i++) {
            assertTrue("Queue should successfully add item " + i, arrayQueue.offer("v" + i));
            assertEquals("Size should be " + i + " after " + i + " adds", i, arrayQueue.size());
        }
    }

    @Test
    public void testOfferingNull() {
        assertFalse("Adding null should return false", arrayQueue.offer(null));
    }

    @Test
    public void testPollingItems() {
        fillSet(2);
        assertTrue("Queue should successfully poll item v1", arrayQueue.poll().equals("v1"));
        assertEquals("Size should be " + 1 + " after " + 1 + " polls", 1, arrayQueue.size());
    }

    @Test
    public void testPollingAllItems() {
        fillSet(20);
        for (int i = 1; i <= 20; i++) {
            assertTrue("Queue should successfully poll item v" + i, arrayQueue.poll().equals("v"+i));
            assertEquals("Size should be " + (20-i) + " after " + i + " polls", 20-i, arrayQueue.size());
        }
    }

    @Test
    public void testPeek() {
        fillSet(2);
        assertTrue("Peek returns correct item", arrayQueue.peek().equals("v1"));
        assertFalse("Peek should not remove items", arrayQueue.peek().equals("v2"));
    }

    @Test
    public void testHasNextOnEmptyIterator() {
        Iterator<String> iterator = arrayQueue.iterator();
        assertFalse("An empty set does not have next item.", iterator.hasNext());
    }

    // The expected parameter in the Test annotation indicates
    // that this test case expects an IllegalArgumentException
    // The Unit test will fails if the given exception is not thrown.
    // Since JUnit 4.
    @Test (expected = java.util.NoSuchElementException.class)
    public void testNextOnEmptyIterator() {
        Iterator<String> iterator = arrayQueue.iterator();
        iterator.next();
    }

    @Test
    public void testIterator() {
        fillSet(20);
        Iterator <String> iterator = arrayQueue.iterator();

        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        assertEquals("Iterator should have returned 20 items", 20, count);
    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("ArrayQueueTest");
    }

}