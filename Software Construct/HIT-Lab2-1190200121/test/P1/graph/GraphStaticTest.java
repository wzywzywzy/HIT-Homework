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
 * Tests for static methods of Graph.
 *
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {

    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }


    /**
     * Test for {@link P1.graph.Graph#add(Object)}
     * strategy:
     *  vertex a in the graph
     *  vertex a not in the graph
     */
    @Test
    public void testAdd() {
        Graph<Integer> graph = Graph.empty();
        assertTrue(graph.add(1));
        assertFalse(graph.add(1));
    }

    /**
     * Test for {@link P1.graph.Graph#set(Object, Object, int)}
     * strategy:
     *  set edge existing with the weight of one
     *  change set edge existing with the weight of two
     *  remove set edge not existing with weigth zero
     *  set edge existing with the weight of zero
     */
    @Test
    public void testSet() {
        Graph<Integer> graph = Graph.empty();
        graph.add(1);
        graph.add(2);
        graph.add(3);

        assertEquals(0, graph.set(1, 3, 0));
        assertEquals(0, graph.set(1, 2, 1));
        assertEquals(1, graph.set(1, 2, 2));
        assertEquals(2, graph.set(1, 2, 0));
        assertEquals(0, graph.set(1, 2, 0));

    }

    /**
     * Test for {@link P1.graph.Graph#remove(Object)}
     * strategy:
     *  remove a vertex not existing
     *  remove a vertex existing with edges
     *  remove a vertex existing without edges
     */
    @Test
    public void testRemove() {
        Graph<Integer> graph = Graph.empty();
        assertEquals(false,graph.remove(1));
        graph.add(1);
        graph.add(2);
        graph.add(3);
        assertEquals(true,graph.remove(1));

        graph.set(2, 3, 1);
        assertEquals(true,graph.remove(2));
    }



    /**
     * Test for {@link P1.graph.Graph#vertices()}
     * strategy:
     *  creat an empty graph
     *  creat a graph with two vertices
     */
    @Test
    public void testVertices() {
        Graph<Integer> graph = Graph.empty();
        assertEquals(Collections.emptySet(), graph.vertices());
        graph.add(1);
        graph.add(2);
        assertEquals(new HashSet<>(Arrays.asList(1,2)), graph.vertices());
    }


    /**
     * Test for {@link P1.graph.Graph#sources(Object)}
     * strategy:
     *  make a graph
     *  positive number of edges connected to the target
     *  test the graph
     */
    @Test
    public void testSources() {
        Graph<Integer> graph = Graph.empty();
        Integer vertexA=1;
        Integer vertexB=2;
        Integer vertexC=3;

        graph.add(vertexA);
        graph.add(vertexB);
        graph.add(vertexC);

        graph.set(vertexA, vertexC, 1);
        graph.set(vertexB, vertexC, 2);

        Map<Integer,Integer> map=new HashMap<Integer,Integer>();
        map.put(vertexA,1);
        map.put(vertexB,2);
        assertEquals(map,graph.sources(vertexC));
    }


    /**
     * Test for  {@link P1.graph.Graph#targets(Object)}
     * strategy:
     *  make a graph
     *  positive number of edges connected to the source
     *  test the graph
     */
    @Test
    public void testTargerts() {
        Graph<Integer> graph = Graph.empty();
        Integer vertexA=1;
        Integer vertexB=2;
        Integer vertexC=3;

        graph.add(vertexA);
        graph.add(vertexB);
        graph.add(vertexC);

        graph.set(vertexC, vertexA, 1);
        graph.set(vertexC, vertexB, 2);

        Map<Integer,Integer> map=new HashMap<Integer,Integer>();
        map.put(vertexA,1);
        map.put(vertexB,2);
        assertEquals(map,graph.targets(vertexC));
    }
}