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
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    /**
     * <p>
     * Abstraction function:
     * </p>
     * f(G) = {v | v is the vertex in the graph G}
     *
     * <p>
     * Representation invariant:
     * </p>
     * All vertices are distinct, and the edges info exist both in sources and targets.
     *
     * <p>
     * Safety from rep exposure:
     * </p>
     * All fields are private, and method are also private if possible. Public
     * methods return copied variable(unabel to access to the private field).
     * Elements of vertices are immutable.
     */

    /**
     * Constructor
     */
    public ConcreteVerticesGraph() {
    }

    /**
     * Check representation.
     */
    protected void checkRep() {
        Map<L, Vertex<L>> map = new HashMap<>();
        for (Vertex<L> v : vertices) {
            if (map.containsKey(v.getLabel())) throw new AssertionError();
            map.put(v.getLabel(), v);
        }
        Integer weight;
        for (Vertex<L> v : vertices) {
            for (Map.Entry<L, Integer> e : v.getTargets().entrySet()) {
                Vertex<L> target = map.get(e.getKey());
                weight = target.getSourceEdgeWeight(v.getLabel());
                if (target == null) throw new AssertionError();
                assert weight != null;
                assert weight.equals(e.getValue());
            }
            for (Map.Entry<L, Integer> e : v.getSources().entrySet()) {
                Vertex<L> source = map.get(e.getKey());
                weight = source.getTargetEdgeWeight(v.getLabel());
                if (source == null) throw new AssertionError();
                assert weight != null;
                assert weight.equals(e.getValue());
            }
        }
    }

    private Vertex<L> findVertex(L vertex) {
        for (Vertex<L> v : vertices)
            if (v.getLabel().equals(vertex))return v;
        return null;
    }

    private Vertex<L> findVertexOrAdd(L vertex) {
        for (Vertex<L> vex : vertices)
            if (vex.getLabel().equals(vertex))return vex;
        Vertex<L> vex = new Vertex<L>(vertex);
        vertices.add(vex);
        return vex;
    }

    @Override
    public boolean add(L vertex) {
        boolean flag = false;
        for (Vertex<L> v : vertices)
            if (v.getLabel().equals(vertex))return flag;
        flag = true;
        Vertex<L> vex = new Vertex<L>(vertex);
        vertices.add(vex);
        checkRep();
        return flag;
    }

    @Override
    public int set(L source, L target, int weight) {
        if (weight < 0)
            throw new RuntimeException("The weight should be positive!");
        int prew;
        Vertex<L> sourceV = findVertexOrAdd(source);
        Vertex<L> targetV = findVertexOrAdd(target);

        prew = sourceV.setTargetEdge(target, weight);
        targetV.setSourceEdge(source, weight);

        checkRep();
        return prew;
    }

    @Override
    public boolean remove(L vertex) {
        boolean found = false;
        for (int i = 0; i < vertices.size(); ++i) {
            if (vertices.get(i).getLabel().equals(vertex))
            {

                vertices.remove(i);
                found = true;
                break;
            }
        }
        if (!found)
            return false;

        for (Vertex<L> vex : vertices) {
            vex.setTargetEdge(vertex, 0);
            vex.setSourceEdge(vertex, 0);
        }
        checkRep();
        return true;
    }

    @Override
    public Set<L> vertices() {
        Set<L> result = new HashSet<>();
        for (Vertex<L> vex : vertices)
            result.add(vex.getLabel());
        return result;
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Vertex<L> targetV = findVertex(target);
        if(targetV == null)
            return new HashMap<>();
        else return targetV.getSources();
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Vertex<L> sourceV = findVertex(source);
        if(sourceV == null)
            return new HashMap<>();
        else return sourceV.getTargets();
    }

    @Override
    public String toString() {
        StringBuilder ff = new StringBuilder();
        for (Vertex<L> vex : vertices) {
            ff.append(vex.toString());
            ff.append("-----\n");
        }
        return ff.toString();
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {

    private final L Label;
    private final Map<L, Integer> sources, targets;

    /**
     * <p>
     * Abstraction function:
     * </p>
     * f(V) = (label, {(source, weight) | edges to V}, {(target, weight) | edges
     * from V}), repersent a vertex
     *
     * <p>
     * Representation invariant:
     * </p>
     * Label is not in sources and targets, and all weights positive.
     *
     * <p>
     * Safety from rep exposure:
     * </p>
     * All fields are private and final.
     */

    /**
     * Vertex constructor
     *
     * @param label the label of the vertex
     */
    public Vertex(L label) {
        this.Label = label;
        this.sources = new HashMap<>();
        this.targets = new HashMap<>();
    }

    /**
     * Check representation.
     */
    protected void checkRep() {
        assert !sources.containsKey(Label);
        assert !targets.containsKey(Label);

        Set<L> x = sources.keySet();
        for (L key : x) {
            Integer value = sources.get(key);
            assert value > 0;
        }

        Set<L> y = targets.keySet();
        for (L key : y) {
            Integer value = targets.get(key);
            assert value > 0;
        }
    }

    /**
     * Get the label of the vertex.
     *
     * @return the label of the vertex
     */
    public L getLabel() {
        return Label;
    }

    /**
     * Get the edges from the vertex.
     *
     * @return the edges pair (source, weight) from the vertex
     */
    public Map<L, Integer> getTargets() {
        return new HashMap<>(targets);
    }

    /**
     * Get the edges to the vertex.
     *
     * @return the edges pair (source, weight) to the vertex
     */
    public Map<L, Integer> getSources() {
        return new HashMap<>(sources);
    }

    /**
     * Check whether there is an edge from vertex to target.
     *
     * @param target the target to check
     */
    public boolean connectTo(L target) {
        return targets.containsKey(target);
    }

    /**
     * Check whether there is an edge from source to vertex.
     *
     * @param source the target to check
     */
    public boolean connectFrom(L source) {
        return sources.containsKey(source);
    }

    /**
     * Get the weight of the edge from this vertex to target.
     *
     * @param target the edge's target
     * @return the weight of the edge (is zero if not exist)
     */
    public int getTargetEdgeWeight(L target) {
        if (targets.containsKey(target))
            return targets.get(target);
        return 0;
    }

    /**
     * Get the weight of the edge from source to this vertex.
     *
     * @param source the edge's source
     * @return the weight of the edge (is zero if not exist)
     */
    public int getSourceEdgeWeight(L source) {
        if (sources.containsKey(source))
            return sources.get(source);
        return 0;
    }

    /**
     * Add, change, or remove a weighted directed edge from this vertex to target.
     * If weight is nonzero, add an edge or update the weight of that edge; vertices
     * with the given labels are added to the graph if they do not already exist. If
     * weight is zero, remove the edge if it exists (the graph is not otherwise
     * modified).
     *
     * @param target the edge's target
     * @param weight the weight of the edge
     * @return the previous weight of the edge (zero if not exist)
     */
    public int setTargetEdge(L target, int weight) {
        int ans;
        if (targets.containsKey(target))
        {
            if (weight > 0)
                ans = targets.replace(target, weight);
            else
                ans = targets.remove(target);
            checkRep();
            return ans;
        }
        else {
            if (weight > 0)
                targets.put(target, weight);
            checkRep();
            return 0;
        }
    }

    /**
     * Add, change, or remove a weighted directed edge from source to this vertex.
     * If weight is nonzero, add an edge or update the weight of that edge; vertices
     * with the given labels are added to the graph if they do not already exist. If
     * weight is zero, remove the edge if it exists (the graph is not otherwise
     * modified).
     *
     * @param source the edge's source
     * @param weight the weight of the edge
     * @return the previous weight of the edge (zero if not exist)
     */
    public int setSourceEdge(L source, int weight) {
        int ans;
        if (sources.containsKey(source)) {
            if (weight > 0)
                ans = sources.replace(source, weight);
            else
                ans = sources.remove(source);
            checkRep();
            return ans;
        }
        else {
            if (weight > 0)
                sources.put(source, weight);
            checkRep();
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Label: " + Label.toString() + "\n");
        sb.append("Targets:\n");
        for (Map.Entry<?, ?> e : targets.entrySet()) {
            sb.append("<" + e.getKey().toString()+" , " + e.getValue().toString() + ">\n");
        }
        sb.append("Sources:\n");
        for (Map.Entry<?, ?> e : sources.entrySet()) {
            sb.append("<" + e.getKey().toString() +" , "+ e.getValue().toString() + ">\n");
        }
        return sb.toString();
    }
}