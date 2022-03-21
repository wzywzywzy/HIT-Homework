/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 *
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 *
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<>();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    /**
     * tests for {@link P1.graph.ConcreteEdgesGraph#toString}
     *
     * Testing strategy for ConcreteEdgesGraph.toString()
     *  strategy:
     *   create empty graph
     *   create graph with vertex
     *   create graph with edge
     */
    @Test
    public void testConcreteEdgesGraphToString() {
        Graph<String> graph = emptyInstance();
        assertEquals("Vertices:[]\nEdges:[]", graph.toString());
        graph.add("A");
        graph.add("B");
        assertEquals("Vertices:[A, B]\nEdges:[]", graph.toString());
        graph.set("A", "B", 1);
        assertEquals("Vertices:[A, B]\nEdges:[[A->B:1]]", graph.toString());
    }


    /*
     * Testing Edge...
     */

    @Test
    public void testEdgeGetMethods() {
        Edge<String> edge = new Edge<>("A", "B", 1);
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getTarget());
        assertEquals(1, edge.getWeight());
    }

    @Test
    public void testEdgeToString() {
        Edge<String> edge = new Edge<>("A", "B", 1);
        assertEquals("[A->B:1]", edge.toString());
    }
}