package application;
/**
 *      AF:
 *        ProcessTable is the arrangement
 *        processes is the schedule
 *        s is stdin
 *      RI:
 *        the time arrangement forbid overlap
 *      Safety from rep exposure:
 *        whole fields are private,
 *        The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
 */
public class Process {
    private final String id, name;
    private final int minTime, maxTime;

    public Process(String id, String name, int min, int max) {
        this.minTime = min;
        this.maxTime = max;
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinTime() {
        return minTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public String toString() {
        return "{"+id+","+name+","+minTime+","+maxTime+"}";
    }

}
