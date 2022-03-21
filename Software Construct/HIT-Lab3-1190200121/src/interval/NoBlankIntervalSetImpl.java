package interval;

import java.util.*;
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
public class NoBlankIntervalSetImpl<L> implements NoBlankIntervalSet<L> {

    //fields
    private MultiIntervalSet<L> multiIntervals = MultiIntervalSet.empty();
    private long[] times = new long[200];
    private long left = 100;
    private long right = -100;

    //make an empty constructor
    public NoBlankIntervalSetImpl() {

    }

    public NoBlankIntervalSetImpl(IntervalSet<L> initial)  {
        multiIntervals = new CommonMultiIntervalSet<>(initial);
        for(L t: multiIntervals.labels()){
            List<Interval<Integer>> tempIntervals = multiIntervals.intervals(t).whole();
            for(int i=0; i<tempIntervals.size(); i++) {
                for(long j=tempIntervals.get(i).getStart(); j<=tempIntervals.get(i).getEnd(); j++) {
                    times[(int)j]++;
                }
            }
        }
    }

    public NoBlankIntervalSetImpl(MultiIntervalSet<L> initial)  {
        multiIntervals = initial;
        for(L t: multiIntervals.labels()){
            List<Interval<Integer>> tempIntervals = multiIntervals.intervals(t).whole();
            for(int i=0; i<tempIntervals.size(); i++) {
                for(long j=tempIntervals.get(i).getStart(); j<=tempIntervals.get(i).getEnd(); j++) {
                    times[(int)j]++;
                }
            }
        }
    }

    public void insert(long start, long end, L label) throws Exception {
        multiIntervals.insert(start, end, label);
        for(long i=start; i<=end; i++) {
            times[(int)i]++;
        }
    }

    public boolean remove(L label) throws Exception {
        boolean flag = multiIntervals.remove(label);
        for(int i=0;i<200;++i)times[i]++;

        for(L t: multiIntervals.labels()){
            List<Interval<Integer>> tempIntervals = multiIntervals.intervals(t).whole();
            for(int i=0; i<tempIntervals.size(); i++) {
                for(long j=tempIntervals.get(i).getStart(); j<=tempIntervals.get(i).getEnd(); j++) {
                    times[(int)j]++;
                }
            }
        }
        return flag;
    }

    @Override
    public boolean checkNoBlank() {

        int cnt=0;
        for(long i=left;i<=right;++i)
        {
            if(times[(int)i]>0)cnt++;
            else return false;
        }
        if(cnt == 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return multiIntervals.toString();
    }
}
