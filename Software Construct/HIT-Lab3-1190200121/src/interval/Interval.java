package interval;

public class Interval<L> {
    private final MultiInterval<L> intervals;

    public Interval(long start, long end, L label) {
        intervals = new MultiInterval<>(label);
        intervals.add(start, end);
    }

    public long getStart() {
        return intervals.getStart().get(0);
    }

    public long getEnd() {
        return intervals.getEnd().get(0);
    }

    public L getLabel() {
        return intervals.getLabel();
    }

    public String toString() {
        return this.getLabel() + "=[" + this.getStart() + "," + this.getEnd() + "]";
    }
}
