package interval;

/**
 *  Abstraction function:
 *  multiIntervals represents the set of all of interval
 *  and every interval has it start time and end time
 *  the numbers represents the times of the interval shows
 *
 *  Representation invariant:
 *  There must be no blank space between any intervals
 *
 *  Safety from rep exposure:
 *  All fields are private final, so the outside can't access them
 *  The Set and Map objects in the rep are made immutable by unmodifiable wrappers.

 */
public interface NoBlankIntervalSet<L>{
    /**
     * Check whether there are blanks in the interval set
     */
    public boolean checkNoBlank();

    /**
     * add a new interval to the interval set.
     *
     * @param start the start time of the interval(assume non-negative)
     * @param end the end time of the interval(assume non-negative) later than start time
     * @param label the label of the interval
     * @throws Exception
     */
    public void insert(long start, long end, L label) throws Exception;

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