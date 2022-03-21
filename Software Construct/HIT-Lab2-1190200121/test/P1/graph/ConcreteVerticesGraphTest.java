/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 *
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 *
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */

    /**
     * tests for ConcreteVerticesGraph.toString()
     *
     * Testing strategy for ConcreteVerticesGraph.toString()
     *  strategy:
     *   create an empty graph
     *   create graph with vertex
     *   create graph with edge
     */
    @Test
    public void testConcreteVerticesGraphToString() {
        final Graph<String> graph = emptyInstance();
        assertEquals("", graph.toString());
        graph.add("A");
        graph.add("B");
        assertEquals("Label: A\nTargets:\nSources:\n-----\nLabel: B\nTargets:\nSources:\n-----\n",
                graph.toString());
        graph.set("A", "B", 1);
        assertEquals(
                "Label: A\nTargets:\n<B , 1>\nSources:\n-----\nLabel: B\nTargets:\nSources:\n<A , 1>\n-----\n",
                graph.toString());
    }


    /*
     * Testing Vertex...
     */

    @Test
    public void testVertexGetAndSetMethods() {
        final Vertex<String> A = new Vertex<String>("A"), B = new Vertex<String>("B");
        assertEquals("A", A.getLabel());
        assertEquals(Collections.emptyMap(), A.getTargets());
        assertEquals(Collections.emptyMap(), A.getSources());

        B.setTargetEdge("A", 1);A.setSourceEdge("B", 1);

        assertTrue(A.connectFrom("B"));
        assertEquals(1, A.getSourceEdgeWeight("B"));
        assertEquals(1, B.getTargetEdgeWeight("A"));
        assertEquals(Stream.of(new AbstractMap.SimpleEntry<>("B", 1))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), A.getSources());

        A.setTargetEdge("B", 0);
        assertFalse(A.connectTo("B"));
    }

    @Test
    public void testVertexToString() {
        final Vertex<String> vertex = new Vertex<String>("vertex");
        vertex.setTargetEdge("target", 1);
        assertEquals("Label: vertex\nTargets:\n<target , 1>\nSources:\n", vertex.toString());
    }
}