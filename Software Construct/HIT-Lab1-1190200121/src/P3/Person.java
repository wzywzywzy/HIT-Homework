package P3;

import java.util.HashSet;
import java.util.Set;

public class Person {

    private String name;

    public Set<Person> friend;
    public int dis;
    public boolean vis;
    public int seq;
    public Person(String name) {
        this.name = name;
        this.friend = new HashSet<>();
        this.dis = 0;
        this.vis = false;
    }

    public String getName() {
        return this.name;
    }
}
