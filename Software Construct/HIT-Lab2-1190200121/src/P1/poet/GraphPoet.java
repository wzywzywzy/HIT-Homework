/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 *
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 *
 * <p>
 * For example, given this corpus:
 *
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 *
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 *
 * <p>
 * For example, given this corpus:
 *
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 *
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 *
 * <pre>
 *     Test of the system.
 * </pre>
 *
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   TODO
    //GraphPoet represents a word affinity graph  which is generated with a
    //corpus and vertices represent case-insensitive. the weight of edge is
    //in-order adjacency counts

    // Representation invariant:
    //   TODO
    //graph is a null-graph

    // Safety from rep exposure:
    //   TODO
    // All fields are modified by private and final.
    // Clients can not access the graph reference outside the class

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        //throw new RuntimeException("not implemented");
        BufferedReader input;
        List<String> list = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        input = new BufferedReader(new FileReader(corpus));


        String str;
        while((str = input.readLine()) != null)
        {
            list.addAll(Arrays.asList(str.split(" ")));
        }
        input.close();

        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext())
        {
            if(iterator.next().length() == 0)
                iterator.remove();
        }
        int w = 0;
        for(int i=0; i<list.size()-1; i++)
        {
            String target = list.get(i+1).toLowerCase();
            String source = list.get(i).toLowerCase();
            if(map.containsKey(source + target))
            {
                w = map.get(source + target);
            }
            map.put(source+target, w + 1);
            graph.set(source, target, w+1);
        }

        checkRep();
    }

    // TODO checkRep
    public void checkRep() {
        assert graph != null;
    }

    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        //throw new RuntimeException("not implemented");
        StringBuilder tmp = new StringBuilder();
        List<String> list = new ArrayList<>(Arrays.asList(input.split(" ")));
        Map<String, Integer> sourceM;
        Map<String, Integer> targetM;

        for(int i=0; i<list.size()-1; i++)
        {
            String target = list.get(i+1).toLowerCase();
            String source = list.get(i).toLowerCase();

            sourceM = graph.sources(target);
            targetM = graph.targets(source);
            tmp.append(list.get(i)).append(" ");
            int Max = 0;
            String bridgeWord = "";
            boolean flag = true;
            for(String str: targetM.keySet())
            {
                if(sourceM.containsKey(str) && sourceM.get(str) + targetM.get(str) > Max)
                {
                    bridgeWord = str;
                    Max = sourceM.get(str) + targetM.get(str);

                }
            }

            if(flag && Max > 0)
            {
                tmp.append(bridgeWord + " ");
            }
        }

        tmp.append(list.get(list.size()-1));
        return tmp.toString();
    }

    // TODO toString()
    @Override
    public String toString() {
        return graph.toString();
    }
}