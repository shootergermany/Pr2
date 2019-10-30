package pr2.collections.iterator.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pr2.collections.iterator.StringStack;

/**
 * Test für den Stack.
 */
public class TestStack {

    /**
     * Testet den Stack an sich.
     */
    @Test
    public void testStack() {
        final var stack = new StringStack(10);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");
        assertEquals("D", stack.peek());
        assertEquals("D", stack.pop());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        stack.push("A2");
        stack.push("A3");
        assertEquals("A3", stack.peek());
        assertEquals("A3", stack.pop());
        assertEquals("A2", stack.pop());
        assertEquals("A", stack.pop());
    }

    /**
     * Testet den Iterator.
     */
    @Test
    public void testIterator() {
        final var stack = new StringStack(10);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");

        final var result = new String[5];
        var count1 = 0;

        for (final var string : stack) {
            result[count1++] = string;
        }

        assertEquals("D", stack.peek());

        assertEquals("D", result[0]);
        assertEquals("C", result[1]);
        assertEquals("B", result[2]);
        assertEquals("A", result[3]);

        stack.push("E");

        final var it1 = stack.iterator();

        var count2 = 0;
        while (it1.hasNext()) {
            result[count2++] = it1.next();
        }

        assertEquals("E", result[0]);
        assertEquals("D", result[1]);
        assertEquals("C", result[2]);
        assertEquals("B", result[3]);
        assertEquals("A", result[4]);

        // nun ist der Iterator am Ende:
        assertFalse(it1.hasNext());

        // ein neuer Iterator ist aber frisch und am Anfang:
        final var it2 = stack.iterator();
        assertTrue(it2.hasNext());
    }
}
