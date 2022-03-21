package interval;

import java.util.List;
import java.util.Set;
/**
 *  AF:
 *    period means the rotate
 *    times means the number of course in a day
 *    things contains all the events
 *  RI:
 *    collections all courses in a whole day to calculate whether it is periodic
 *  Safety from rep exposure:
 *    All fields are private final,
 *    The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
 */
public interface PeriodIntervalSet<L> {
    /**
     * Check if there are conflicts in interval set.
     *
     * @throws Exception if there are conflicts in interval set
     */
    public boolean checkNoConflict();

    /**
     * Insert an interval to this interval set.
     *
     * @param label the label of the interval
     * @param interval
     * @throw if interval is illegal
     */
    public void insert(L label, Interval<Integer> interval) throws Exception;

    /**
     * Insert an interval to this interval set.
     *
     * @param label the label of the interval
     * @param interval
     * @return true if there are no overlaps
     *         false otherwise
     * @throw if interval is illegal
     */
    public boolean unOverlapInsert(L label, Interval<Integer> interval) throws Exception;

    /**
     * Calculate the ratio of free of the interval set.
     *
     * @return the ratio of free of the interval set
     */
    public double ratioOfFree();

    /**
     * Calculate the ratio of overlap of the interval set.
     *
     * @return the ratio of overlap of the interval set
     */
    public double ratioOfOverlap();

    /**
     * Watch the list of events of a specific day.
     *
     * @param day the specific date
     * @return the list of events of a specific day
     */
    public List<Set<L>> eventsOfDay(int day);
}
