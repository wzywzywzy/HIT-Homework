package interval;

import java.util.List;
/**
 *  Abstraction function:
 *  multiIntervals represents the set of all of interval
 *  and every interval has it start time and end time
 *  the numbers represents the times of the interval shows
 *
 *  Representation invariant:
 *  There must not be overlap between the interval
 *
 *  Safety from rep exposure:
 *  All fields are private final, so the outside can't access them
 *  The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
 */
public interface NonOverlapIntervalSet<L> {
    /**
     * Check if there are overlaps in the interval set.
     *
     * @return true if there is no overlap
     *         otherwise false
     */
    public boolean checkNoOverlap(String opt);

    /**
     * the goal is th get whole intervals in this interval set, sorted by startTime.
     *
     * @return a new list of whole intervals in the set
     */
    public List<Interval<L>> whole();

    /**
     * add a new interval to the interval set.
     *
     * @param start the start time of the interval(assume non-negative)
     * @param end the end time of the interval(assume non-negative) later than start time
     * @param label the label of the interval
     * @param opt if overlaps between intervals accepted
     * @return true if this interval set has succeed added the interval;
     *         false otherwise
     */
    public boolean insert(long start, long end, L label, String opt);

    /**
     * Remove an interval from this interval set.
     *
     * @param label label of the interval to remove
     * @return true if this interval set included an interval with the given label;
     *         otherwise false (and this interval set is not modified)
     * @throws Exception
     */
    public boolean remove(L label) throws Exception;
}
