package P2;

import java.util.*;

import P1.graph.ConcreteEdgesGraph;

public class FriendshipGraph extends ConcreteEdgesGraph<Person>{
    private final ConcreteEdgesGraph<Person> PersonGraph;

    /**
     * initialize
     */
    public FriendshipGraph() {
        PersonGraph = new ConcreteEdgesGraph<>();
    }

    /**
     * Add vertexes to graph or we can say add person into social network. If
     * person.name duplicated, it will exit this program with return -1.
     *
     * @param  person, an instance of class Person of "social network".
     */

    public void addVertex(Person person) {
        PersonGraph.add(person);
    }

    /**
     * Add edges to graph or we can say add interaction into social network. If
     * someone's name has not been added to the graph, it can not add the edge
     * into social network.
     *
     * @param person1 source vertex of the edge.
     * @param person2 target vertex of the edge.
     */

    public void addEdge(Person person1, Person person2) {
        if(person1.equals(person2))
        {
            System.out.println("Name has been duplicated!");
            return;
        }
        PersonGraph.set(person1, person2, 1);
    }

    public ConcreteEdgesGraph<Person> getAllPeople() {
        return PersonGraph;
    }
    /**
     * Get the shortest distance between two persons in the social network.
     *
     * @param  source Source of the two people that needed to know the
     *            distance between them.
     * @param  target Target of the two people that needed to know the
     *            distance between them.
     * @return the shortest distance between the two people.
     */

    public int getDistance(Person source, Person target) {
        if(source.equals(target)) {
            System.out.println("The same person!");
            return 0;
        }
        int res = -1;
        Map<Person, Integer> map = new HashMap<>();
        Queue<Map.Entry<Person, Integer> > que = new LinkedList<>();
        que.add(new AbstractMap.SimpleEntry<Person, Integer>(source, 0));

        while(!que.isEmpty()) {
            Person cur = que.peek().getKey();
            Integer dist = que.peek().getValue();
            que.poll();

            if(map.containsKey(cur)) continue;
            map.put(cur, dist);
            if(cur.equals(target)){
                res = dist;
                break;
            }
            for(Person nex : PersonGraph.targets(cur).keySet()) {
                que.add(new AbstractMap.SimpleEntry<Person, Integer>(nex, dist + 1));
            }
        }

        return res;
    }
}
