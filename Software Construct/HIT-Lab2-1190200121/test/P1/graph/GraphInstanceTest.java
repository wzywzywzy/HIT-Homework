/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 *
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

    /**
     * Testing strategy
     *
     * For {@link P1.graph.Graph#add(Object)}
     * covers:.
     *  vertex in the graph
     * 	vertex not in the graph graph
     *
     * For {@link P1.graph.Graph#set(Object, Object, int)}
     * covers:
     *  set edge exist with weigth zero
     *  set edge existing with positive weigth
     *  set edge not existing with weigth zero
     *  set edge not existing with positive weigth
     *
     * For {@link P1.graph.Graph#remove(Object)}
     * covers:
     *  remove a vertex existing with edges
     *  remove a vertex existing without edges
     *  remove a vertex not existing
     *
     * For {@link P1.graph.Graph#vertices()}
     * covers:
     *  an empty graph
     *  a graph with vertices
     *
     * For {@link P1.graph.Graph#sources(Object)}
     * covers:
     *  no edge connected to the target
     *  positive number of edges connected to the target
     *
     * For {@link P1.graph.Graph#targets(Object)}
     * covers:
     *  no edge connected to the source
     *  positive number of edges connected to the source
     */

    /**
     * Overridden by implementation-specific test classes.
     *
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    /**
     * Test for {@link P1.graph.Graph#add(Object)}
     * covers:
     *  vertex A in the graph
     *  vertex A not in the graph
     */
    @Test
    public void testAdd() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add("A"));
        assertFalse(graph.add("A"));
    }

    /**
     * Test for {@link P1.graph.Graph#set(Object, Object, int)}
     * covers:
     *  set edge existing with the weight of one
     *  change set edge existing with the weight of two
     *  remove set edge not existing with weigth zero
     *  set edge existing with the weight of zero
     */
    @Test
    public void testSet() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");

        assertEquals(0, graph.set("A", "C", 0));
        assertEquals(0, graph.set("A", "B", 1));
        assertEquals(1, graph.set("A", "B", 2));
        assertEquals(2, graph.set("A", "B", 0));
        assertEquals(0, graph.set("A", "B", 0));

    }

    /**
     * Test for {@link P1.graph.Graph#remove(Object)}
     * covers:
     *  remove a vertex not existing
     *  remove a vertex existing with edges
     *  remove a vertex existing without edges
     */
    @Test
    public void testRemove() {
        Graph<String> graph = emptyInstance();
        assertEquals(false,graph.remove("A"));
        graph.add("A");
        graph.add("C");
        graph.add("B");
        assertEquals(true,graph.remove("A"));

        graph.set("B", "C", 1);
        assertEquals(true,graph.remove("B"));
    }

    /**
     * Test for {@link P1.graph.Graph#vertices()}
     * covers:
     *  create an empty graph
     *  create a graph with two vertices
     */
    @Test
    public void testVertices() {
        Graph<String> graph = emptyInstance();
        assertEquals(Collections.emptySet(), graph.vertices());
        graph.add("A");
        graph.add("B");
        assertEquals(new HashSet<>(Arrays.asList("A","B")), graph.vertices());
    }

    /**
     * Test for {@link P1.graph.Graph#sources(Object)}
     * covers:
     *  make a graph
     *  positive number of edges connected to the target
     *  test the graph
     */
    @Test
    public void testSources() {
        Graph<String> graph = emptyInstance();
        String vertexA="A";
        String vertexB="B";
        String vertexC="C";

        graph.add(vertexA);
        graph.add(vertexB);
        graph.add(vertexC);

        graph.set(vertexA, vertexC, 1);
        graph.set(vertexB, vertexC, 2);

        Map<String,Integer> map=new HashMap<String,Integer>();
        map.put(vertexA,1);
        map.put(vertexB,2);
        assertEquals(map,graph.sources(vertexC));
    }

    /**
     * Test for  {@link P1.graph.Graph#targets(Object)}
     * covers:
     *  make a graph
     *  positive number of edges connected to the source
     *  test the graph
     */
    @Test
    public void testTargerts() {
        Graph<String> graph = emptyInstance();
        String vertexA="A";
        String vertexB="B";
        String vertexC="C";

        graph.add(vertexA);
        graph.add(vertexB);
        graph.add(vertexC);

        graph.set(vertexC, vertexA, 1);
        graph.set(vertexC, vertexB, 2);

        Map<String,Integer> map=new HashMap<String,Integer>();
        map.put(vertexA,1);
        map.put(vertexB,2);
        assertEquals(map,graph.targets(vertexC));
    }
}