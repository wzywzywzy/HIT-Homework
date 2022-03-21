/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    /**
     * Testing strategy
     *
     * For {@link P1.poet.GraphPoet#poem(String)}
     * cases:
     *  the word exists/doesn't exist in the graph
     *  there is/isn't two-edge-long path between given words
     */

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    /**
     * test for {@link P1.poet.GraphPoet#poem(String)}
     */
    @Test
    public void testPoem() {
        File testFile = new File("test/P1/poet/test.txt");
        GraphPoet poet = null;
        try {
            poet = new GraphPoet(testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("", poet.poem(""));
        assertEquals("I night", poet.poem("I night"));
        assertEquals("alice is in day", poet.poem("alice is day"));
        assertEquals("bob is in day", poet.poem("bob in day"));
        assertEquals("he is catching night", poet.poem("he is night"));
    }
}
