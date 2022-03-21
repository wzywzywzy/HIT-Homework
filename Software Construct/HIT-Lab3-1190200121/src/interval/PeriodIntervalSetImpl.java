package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
public class PeriodIntervalSetImpl<L> implements PeriodIntervalSet<L>{

    //fields
    private final int period;
    private final int times;
    private List<Set<L>> things = new ArrayList<>();


    //constructor
    public PeriodIntervalSetImpl(int period, int times) {
        this.period = period;
        this.times = times;
        for(int i=0; i<=this.period*this.times; i++) {
            things.add(new HashSet<>());
        }
    }

    @Override
    public void insert(L label, Interval<Integer> interval) throws Exception {
        things.get((interval.getLabel()-1)*times+(int)interval.getStart()).add(label);
    }

    @Override
    public boolean unOverlapInsert(L label, Interval<Integer> interval){
        int index = (interval.getLabel()-1)*times+(int)interval.getStart();
        if(things.get(index).isEmpty()) {
            things.get(index).add(label);
            return true;
        }
        return false;
    }

    @Override
    public double ratioOfFree() {
        int cnt = 0;
        int length = things.size();
        for(int i=1; i<length; i++) {
            if(things.get(i).isEmpty()) {
                cnt++;
            }
        }
        return 1.0*cnt/(length-1);
    }

    public PeriodIntervalSetImpl() {
        this.period = 7;
        this.times = 5;
        for(int i=0; i<=this.period*this.times; i++) {
            things.add(new HashSet<>());
        }
    }

    @Override
    public boolean checkNoConflict(){
        for(int i=0; i<things.size(); i++) {
            if(things.get(i).size() > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double ratioOfOverlap() {
        int cnt = 0;
        int length = things.size();
        for(int i=1; i<length; i++) {
            if(things.get(i).size()>1) {
                cnt++;
            }
        }
        return 1.0*cnt/(length-1);
    }

    @Override
    public List<Set<L>> eventsOfDay(int day) {
        List<Set<L>> sum = new ArrayList<>();
        int index = (day-1) % period + 1;
        int pre = (index-1)*times+1;
        int end = index*times;
        for(int i=pre; i<=end; i++) {
            sum.add(things.get(i));
        }
        return Collections.unmodifiableList(sum);
    }

}
