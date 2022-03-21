package interval;

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
public interface IntervalSet<L> {
    /**
     * every time Create an empty interval set.
     *
     * @param <L> as the type of labels in this interval set which is immutable
     * @return create a new empty interval set
     */
    public static <L> IntervalSet<L> empty(){
        return new CommonIntervalSet<>();
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
    public boolean insert(long start, long end, L label);


    /**
     * the goal is th get whole intervals in this interval set, sorted by startTime.
     *
     * @return a new list of whole intervals in the set
     */
    public List<Interval<L>> whole();

    /**
     * Get all intervals in this interval set.
     *
     * @return the set of intervals which in the interval set
     */

    public Set<L> labels();

    /**
     * Remove an interval from this interval set.
     *
     * @param label label of the interval to remove
     * @return true if this interval set included an interval with the given label;
     *         otherwise false (and this interval set is not modified)
     */
    public boolean remove(L label);

    /**
     * Get the start time of the interval whose label has been given.
     *
     * @param label the label of the interval
     * @return the start time
     * @throws Exception otherwise
     */
    public long getStart(L label) throws Exception;

    /**
     * Get the end number of the interval with given label.
     *
     * @param label the label of an interval
     * @return the end time
     * @throws Exception otherwise
     */
    public long getEnd(L label) throws Exception;

}
