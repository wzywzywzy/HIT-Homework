package interval;


import java.util.*;

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
public class NonOverlapIntervalSetImpl<L> implements NonOverlapIntervalSet<L>{

    //fields
    private MultiIntervalSet<L> multiIntervals = MultiIntervalSet.empty();
    private long[] times = new long[1000];
    private long left = 100;
    private long right = -100;

    //make an empty constructor
    public NonOverlapIntervalSetImpl() {

    }

    public NonOverlapIntervalSetImpl(IntervalSet<L> initial) throws Exception {
        multiIntervals = new CommonMultiIntervalSet<>(initial);
    }

    public NonOverlapIntervalSetImpl(MultiIntervalSet<L> initial) throws Exception {
        multiIntervals = initial;
    }

    @Override
    public boolean checkNoOverlap(String opt) {
        if(!opt.equals("ENABLE") && !opt.equals("DISABLE")) {
            return false;
        }
        if(opt.equals("DISABLE")) {
            for(L t: multiIntervals.labels()){
                List<Interval<Integer>> temp = multiIntervals.intervals(t).whole();
                for(int i=0; i<temp.size(); i++) {
                    for(long j=temp.get(i).getStart(); j<=temp.get(i).getEnd(); j++) {
                        times[(int)j]++;
                    }
                    right = Math.max(temp.get(i).getEnd(), right);
                    left = Math.min(temp.get(i).getStart(), left);
                }
            }
            for(long i=left;i<=right;++i)
                if(times[(int)i]>1){
                    return false;
                }
        }
        return true;
    }

    @Override
    public boolean insert(long start, long end, L label, String opt){
        multiIntervals.insert(start, end, label);
        return checkNoOverlap(opt);
    }

    @Override
    public boolean remove(L label) throws Exception {
        return multiIntervals.remove(label);
    }

    @Override
    public List<Interval<L>> whole() {
        return multiIntervals.whole();
    }

    @Override
    public String toString() {
        return multiIntervals.toString();
    }

}
