package P2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



public class FriendshipGraphTest {
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    /**
     * Test empty graph
     */
    @Test
    public void testEmptyGraph() {
        FriendshipGraph graph = new FriendshipGraph();
        Person person1 = new Person("person1");
        Person person2 = new Person("person2");
        assertEquals(-1, graph.getDistance(person1, person2));
    }

    /**
     * Test only vertices graph
     */
    @Test
    public void testOnlyVerticesGraph() {
        FriendshipGraph graph = new FriendshipGraph();
        Person person1 = new Person("person1");
        Person person2 = new Person("person2");
        graph.addVertex(person1);
        graph.addVertex(person2);
        assertEquals(-1, graph.getDistance(person1, person2));
    }

    /**
     * Test sample tests.
     */
    @Test
    public void testSampleTests() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);

        assertEquals(1, graph.getDistance(rachel, ross));
        assertEquals(2, graph.getDistance(rachel, ben));
        assertEquals(0, graph.getDistance(rachel, rachel));
        assertEquals(-1, graph.getDistance(rachel, kramer));
    }

    /**
     * Test by using myself test cases.
     */
    @Test
    public void testMyselfCases() {
        FriendshipGraph graph = new FriendshipGraph();
        Person aPerson = new Person("A");
        Person bPerson = new Person("B");
        Person cPerson = new Person("C");
        Person dPerson = new Person("D");
        Person ePerson = new Person("E");
        Person fPerson = new Person("F");
        Person gPerson = new Person("G");

        graph.addVertex(aPerson);
        graph.addVertex(bPerson);
        graph.addVertex(cPerson);
        graph.addVertex(dPerson);
        graph.addVertex(ePerson);
        graph.addVertex(fPerson);
        graph.addVertex(gPerson);

        graph.addEdge(aPerson, bPerson);
        graph.addEdge(aPerson, cPerson);
        graph.addEdge(bPerson, cPerson);
        graph.addEdge(bPerson, dPerson);
        graph.addEdge(dPerson, fPerson);
        graph.addEdge(ePerson, cPerson);
        graph.addEdge(bPerson, ePerson);
        graph.addEdge(aPerson, dPerson);
        graph.addEdge(cPerson, bPerson);
        graph.addEdge(fPerson, dPerson);
        graph.addEdge(dPerson, bPerson);


        assertEquals(2, graph.getDistance(aPerson, ePerson));
        assertEquals(1, graph.getDistance(aPerson, cPerson));
        assertEquals(2, graph.getDistance(aPerson, fPerson));
        assertEquals(1, graph.getDistance(aPerson, dPerson));
        assertEquals(-1,graph.getDistance(dPerson, gPerson));
        assertEquals(3, graph.getDistance(cPerson, fPerson));
        assertEquals(2, graph.getDistance(cPerson, dPerson));
        assertEquals(2, graph.getDistance(fPerson, bPerson));
    }
}
