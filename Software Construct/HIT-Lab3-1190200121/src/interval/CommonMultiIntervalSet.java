package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 *  Abstract Function:
 *  Represents a map of interval, which has start time and end time. And
 * 	every interval has its own label to distinguish from each other
 * 	this is a mutable interval set with labeled intervals.
 *
 * 	Represent Invariant:
 *  Each interval has its own start time and end time.
 * Also guarantee the start>=0 and end>start
 *  And notice the label of each element is different
 * all Intervals have distinct labels of an immutable type.
 * and they must have a non-negative start and end at the same time end must later than start.
 *
 * 	Safety from rep exposure:
 *  Each one of fields is modified by key word final and private so they can't be
 *  accessed by outside of the class
 * 	And also do some defensive copying
 * @param <L> all types of labels in this interval set is immutable
 */
public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L>{
    //fields
    private final Set<MultiInterval<L>> multiIntervals = new HashSet<>();
    private final Set<L> labels = new HashSet<>();
    private IntervalSet<Integer> intervals = IntervalSet.empty();

    public void checkRep() {
        Set<L> tempLabels = new HashSet<>();
        Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
        while(iter.hasNext()) {
            MultiInterval<L> multiInterval = iter.next();
            if(tempLabels.contains(multiInterval.getLabel())) {
                assert false;
            }else {
                tempLabels.add(multiInterval.getLabel());
            }
            for(int i=0; i<multiInterval.size(); i++) {
                assert multiInterval.getStart().get(i) >= 0 && multiInterval.getEnd().get(i) >= 0;
            }
        }
        assert tempLabels.equals(labels);
    }

    //constructor
    public CommonMultiIntervalSet() {

    }

    public CommonMultiIntervalSet(IntervalSet<L> initial) {
        for(Interval<L> interval : initial.whole()) {
            MultiInterval<L> multiInterval = new MultiInterval<>(interval.getLabel());
            multiInterval.add(interval.getStart(), interval.getEnd());
            multiIntervals.add(multiInterval);
            labels.add(interval.getLabel());
        }
        checkRep();
    }
    /**
     * add a new interval to the interval set.
     *
     * @param start the start time of the interval(assume non-negative)
     * @param end the end time of the interval(assume non-negative) later than start time
     * @param label the label of the interval
     * @return true if this interval set has succeed added the interval;
     *         false otherwise
     */
    @Override
    public boolean insert(long start, long end, L label) {
        if(start < 0||end < start)return false;
        if(!labels.contains(label)) {
            MultiInterval<L> multiInterval = new MultiInterval<>(label);
            multiInterval.add(start, end);
            multiIntervals.add(multiInterval);
            labels.add(label);
            checkRep();
            return true;
        }else {
            for(MultiInterval<L> multiInterval : multiIntervals) {
                if(multiInterval.getLabel().equals(label)) {
                    multiInterval.add(start, end);
                    checkRep();
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * Get all intervals in this interval set.
     *
     * @return the set of intervals which in the interval set
     */
    @Override
    public Set<L> labels() {
        return Collections.unmodifiableSet(labels);
    }

    /**
     * Remove an interval from this interval set.
     *
     * @param label label of the interval to remove
     * @return true if this interval set included an interval with the given label;
     *         otherwise false (and this interval set is not modified)
     */
    @Override
    public boolean remove(L label) {
        Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
        while(iter.hasNext()) {
            MultiInterval<L> multiInterval = iter.next();
            if(multiInterval.getLabel().equals(label)) {
                iter.remove();
                labels.remove(label);
                checkRep();
                return true;
            }
        }
        checkRep();
        return false;
    }

    /**
     * Get the interval set whose label was given.
     *
     * @param label the label of the interval
     * @return the interval set whose label was given
     */
    @Override
    public IntervalSet<Integer> intervals(L label) {
        intervals = IntervalSet.empty();
        Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
        while(iter.hasNext()) {
            MultiInterval<L> multiInterval = iter.next();
            if(multiInterval.getLabel().equals(label)) {
                List<Long> starts = multiInterval.getStart();
                List<Long> ends = multiInterval.getEnd();
                for(int i=0; i<multiInterval.size(); i++) {
                    intervals.insert(starts.get(i), ends.get(i), i);
                }
                checkRep();
                return intervals;
            }
        }
        checkRep();
        return intervals;
    }

    /**
     * the goal is th get whole intervals in this interval set, sorted by startTime.
     *
     * @return a new list of whole intervals in the set
     */
    @Override
    public List<Interval<L>> whole()  {
        List<Interval<L>> temp = new ArrayList<>();
        Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
        while(iter.hasNext()) {
            MultiInterval<L> multiInterval = iter.next();
            L label = multiInterval.getLabel();
            List<Long> start = multiInterval.getStart();
            List<Long> end = multiInterval.getEnd();
            for(int i=0; i<start.size(); i++) {
                temp.add(new Interval<L>(start.get(i), end.get(i), label));
            }
        }
        temp.sort((o1, o2) -> (int) (o1.getStart()-o2.getStart()));
        checkRep();
        return Collections.unmodifiableList(temp);
    }

    @Override
    public String toString() {
        String str = "{";
        Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
        while(iter.hasNext()) {
            MultiInterval<L> multiInterval = iter.next();
            str += multiInterval.toString() + ",";
        }
        if(str.endsWith(",")) {
            str = str.substring(0, str.length()-1);
        }
        str += "}";
        checkRep();
        return str;
    }

}

