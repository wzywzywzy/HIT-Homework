package P3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class FriendshipGraph {

    private Set<String> namNet = new HashSet<>();
    private List<Person> personList = new ArrayList<>();
    private int Num = 0;
    /**
     * Add vertex to the graph and each of vertex represents a person by using the name
     * to distinguish each of person. if the name is duplicated, then exit.
     *
     * @param Person
     * 			person, an instance of Class Person of "social network"
     * @return void
     */
    public void addVertex(Person person) {
        if(namNet.contains(person.getName())) {
            System.out.println("The name is duplicated!");
            System.exit(-1);
            return;
        }
        else{
            personList.add(person);
            namNet.add(person.getName());
            person.seq = Num++;
        }
        return;
    }

    /**
     * Add edge to the graph and each of edge represents there're two person have
     * interaction. If someone's name has been added to the graph, then it can't be
     * added again
     *
     * @param Person
     * 			person1 is one vertex of the edge
     * @param Person
     * 			person2 if another vertex of the edge
     */

    public void addEdge(Person person1, Person person2) {
        if(person1.getName().equals(person2.getName())) {
            System.out.println("The one has been added to the net!");
            return;
        }
        else {
            if(namNet.contains(person1.getName()) && namNet.contains(person2.getName())) {
                personList.get(person1.seq).friend.add(person2);
            }
        }
        return;
    }

    /**
     * To get the shortest distance between the two persons in the net.
     * @param Perosn
     * 			person1 is needed to know when getting distance information.
     * @param Person
     * 			person2 is needed to know when getting distance information.
     * @return
     * 			return the shortest distance between two persons
     */

    public int getDistance(Person person1, Person person2) {
        if(person2.getName().equals(person1.getName())) {return 0;}
        int ans = -1;
        for(int i=0; i<Num; i++) {
            personList.get(i).dis = 0;
            personList.get(i).vis = false;
        }

        person1.vis = true;
        Queue<Person> q = new LinkedList<>();
        q.offer(person1);
        boolean flag = false;
        Person person;

        while(!q.isEmpty()) {
            person = q.poll();
            Set<Person> net = person.friend;
            for(Person i: net) {
                if(!i.vis) {
                    q.offer(i);
                    i.vis = true;
                    i.dis = person.dis+1;
                    if(i.getName().equals(person2.getName())) {
                        ans = i.dis;
                        flag = true;
                        break;
                    }
                }
            }
            if(flag)
                return ans;
        }
        return -1;
    }

    public static void main(String argv[]) {
        FriendshipGraph graph = new FriendshipGraph();
        Person Tom = new Person("Tom");
        Person Jerry = new Person("Jerry");
        Person Cristi = new Person("Cristi");
        Person Messi = new Person("Messi");
        Person Me = new Person("Me");
        graph.addVertex(Jerry);
        graph.addVertex(Tom);
        graph.addVertex(Cristi);
        graph.addVertex(Messi);
        graph.addVertex(Me);

        graph.addEdge(Jerry, Tom);
        graph.addEdge(Tom, Jerry);
        graph.addEdge(Cristi, Messi);
        graph.addEdge(Messi, Cristi);
        graph.addEdge(Tom, Messi);
        graph.addEdge(Messi, Tom);
        System.out.println(graph.getDistance(Jerry, Tom));//print 1
        System.out.println(graph.getDistance(Jerry, Messi));//print 2
        System.out.println(graph.getDistance(Jerry, Jerry));//print 0
        System.out.println(graph.getDistance(Me, Tom));//print -1
    }
}
