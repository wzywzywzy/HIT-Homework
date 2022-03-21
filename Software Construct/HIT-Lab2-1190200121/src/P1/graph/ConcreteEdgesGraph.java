/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    /**
     * <p>
     * Abstraction function:
     * </p>
     * f(G) = {e | e is the edge in the graph G}
     *
     * <p>
     * Representation invariant:
     * </p>
     * For all the edge in the graph, target and source are vertices in the graph,
     * the target and source are not same, and weight is positive.
     *
     * <p>
     * Safety from rep exposure:
     * <p>
     * All fields are private, and method are also private if possible. Public
     * methods return copied variable(unabel to access to the private field).
     * Elements of vertices and edges are immutable.
     */

    public ConcreteEdgesGraph() {
    }

    /**
     * Check representation.
     */
    protected void checkRep() {
        for(Edge<L> edge : edges) {
            assert vertices.contains(edge.getSource());
            assert vertices.contains(edge.getTarget());
            assert edge.getWeight() > 0;
        }
    }

    /**
     * Add a vertex to this graph.
     *
     * @param vertex label for the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    @Override
    public boolean add(L vertex) {
        boolean tmp = vertices.add(vertex);
        checkRep();
        return tmp;
    }

    /**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     *
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    @Override
    public int set(L source, L target, int weight) {
        if (weight < 0)
            throw new RuntimeException("weight should be positive");

        boolean vertexflag = true;
        int prew = 0;
        if (!vertices.contains(target)) {
            vertices.add(target);
            vertexflag = false;
        }
        if (!vertices.contains(source)) {
            vertices.add(source);
            vertexflag = false;
        }

        if (vertexflag) {
            int idx = -1;
            for(int i = 0; i < edges.size(); i++) {
                Edge<L> e = edges.get(i);
                if(e.getSource().equals(source) && e.getTarget().equals(target)) {
                    idx = i;
                    break;
                }
            }
            if(idx != -1) {
                Edge<L> e = edges.get(idx);
                prew = e.getWeight();
                if(weight > 0) {
                    edges.set(idx, new Edge<L>(source, target, weight));
                } else {
                    edges.remove(idx);
                }
            }
            if (idx == -1 && weight > 0)
                edges.add(new Edge<L>(source, target, weight));
        }
        else if (weight > 0) {
            edges.add(new Edge<L>(source, target, weight));
        }

        checkRep();
        return prew;
    }

    /**
     * Remove a vertex from this graph; any edges to or from the vertex are
     * also removed.
     *
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)
     */
    @Override
    public boolean remove(L vertex) {
        if (!vertices.contains(vertex))
            return false;
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return true;
    }

    /**
     * Get all the vertices in this graph.
     *
     * @return the set of labels of vertices in this graph
     */
    @Override
    public Set<L> vertices() {
        return new HashSet<>(vertices);
    }

    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     *
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> result = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getTarget().equals(target)) {
                result.put(edge.getSource(), edge.getWeight());
            }
        }
        return result;
    }

    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     *
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key
     */
    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> result = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source)) {
                result.put(edge.getTarget(), edge.getWeight());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Vertices:" + vertices.toString() + "\nEdges:" + edges.toString();
    }
}

/**
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {


    private final L source, target;
    private final int weight;

    /**
     * <p>
     * Abstraction function:
     * </p>
     * f(E) = (source, target, weight), repersent a directed edge
     *
     * <p>
     * Representation invariant:
     * </P>
     * source != target and weight is positive
     *
     * <p>
     * Safety from rep exposure:
     * </p>
     * All fields are private and final.
     */

    /**
     * The constructor of Edge
     *
     * @param source the source of the edge
     * @param target the target of the edge
     * @param weight the weight of the edge
     */
    public Edge(L source, L target, int weight) {
        this.weight = weight;
        this.source = source;
        this.target = target;

        checkRep();
    }

    /**
     * Check representation
     */
    private void checkRep() {
        assert !source.equals(target);
        assert weight > 0;
        assert source != null;
        assert target != null;
    }

    /**
     * Get the source of the edge.
     *
     * @return the source of the edge
     */
    public L getSource() {
        return this.source;
    }

    /**
     * Get the target of the edge.
     *
     * @return the target of the edge
     */
    public L getTarget() {
        return this.target;
    }

    /**
     * Get the weight of the edge.
     *
     * @return the weight of the edge
     */
    public int getWeight() {
        return this.weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Edge<?>) {
            return ((Edge<?>) obj).getSource().equals(source) &&
                    ((Edge<?>) obj).getTarget().equals(target)
                    && ((Edge<?>) obj).getWeight() == weight;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + source.toString() + "->" + target.toString() + ":" + weight + "]";
    }
}