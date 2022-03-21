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
public class CommonIntervalSet<L> implements IntervalSet<L>{
    //fields
    private final List<Interval<L>> intervals = new ArrayList<>();
    private final Set<L> labels = new HashSet<>();


    //checkRep
    private void checkRep() {
        Set<L> tempLabels = new HashSet<>();
        Iterator<Interval<L>> iter = intervals.iterator();
        while(iter.hasNext()) {
            Interval<L> interval = iter.next();
            assert interval.getStart() >= 0 && interval.getEnd() >= 0;
            if(tempLabels.contains(interval.getLabel())) {
                assert false;
            }else {
                tempLabels.add(interval.getLabel());
            }
        }
        assert tempLabels.equals(labels);
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
    public boolean insert(long start, long end, L label){
        boolean flag;
        if(start < 0|| end<start)return false;
        if(labels.contains(label)) {
            flag = false;
        }else {
            intervals.add(new Interval<>(start, end, label));
            labels.add(label);
            flag = true;
        }
        checkRep();
        return flag;
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
        Iterator<Interval<L>> iter = intervals.iterator();
        while(iter.hasNext()) {
            Interval<L> interval = iter.next();
            if(interval.getLabel().equals(label)) {
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
     * Get the start time of the interval whose label has been given.
     *
     * @param label the label of the interval
     * @return the start time
     * @throws Exception otherwise
     */
    @Override
    public long getStart(L label) throws Exception {
        Iterator<Interval<L>> iter = intervals.iterator();
        while(iter.hasNext()) {
            Interval<L> interval = iter.next();
            if(interval.getLabel().equals(label)) {
                checkRep();
                return interval.getStart();
            }
        }
        checkRep();
        throw new Exception("Label为"+label+"的interval不存在！");
    }
    /**
     * Get the end number of the interval with given label.
     *
     * @param label the label of an interval
     * @return the end time
     * @throws Exception otherwise
     */
    @Override
    public long getEnd(L label) throws Exception {
        Iterator<Interval<L>> iter = intervals.iterator();
        while(iter.hasNext()) {
            Interval<L> interval = iter.next();
            if(interval.getLabel().equals(label)) {
                checkRep();
                return interval.getEnd();
            }
        }
        checkRep();
        throw new Exception("Label为"+label+"的interval不存在！");
    }
    /**
     * the goal is th get whole intervals in this interval set, sorted by startTime.
     *
     * @return a new list of whole intervals in the set
     */
    @Override
    public List<Interval<L>> whole() {
        intervals.sort(new Comparator<Interval<L>>() {
            @Override
            public int compare(Interval<L> o1, Interval<L> o2) {
                return (int) (o1.getStart()-o2.getStart());
            }
        });
        checkRep();
        return Collections.unmodifiableList(intervals);
    }

    @Override
    public String toString() {
        String str = "{";
        intervals.sort(new Comparator<Interval<L>>() {
            @Override
            public int compare(Interval<L> o1, Interval<L> o2) {
                return (int) (o1.getStart()-o2.getStart());
            }
        });
        for(Interval<L> interval: intervals){
            str += interval.toString() + ",";
        }
        if(str.endsWith(",")) {
            str = str.substring(0, str.length()-1);
        }
        str += "}";
        checkRep();
        return str;
    }

}
