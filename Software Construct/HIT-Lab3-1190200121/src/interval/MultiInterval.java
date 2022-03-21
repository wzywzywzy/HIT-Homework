package interval;

import java.util.ArrayList;
import java.util.List;

/**
 * An immutable interval.
 *
 * @param <L> all type of the labels in this interval set is immutable
 */
public class MultiInterval<L> {
    //fields
    private final List<Long> starts = new ArrayList<>();
    private final List<Long> ends = new ArrayList<>();
    private final L label;

    /*
        AF:start end as list to contain the time
        lable names the name of intervals
        RI: all time must larger than zero
        and end bigger than start
        Safety from rep exposure:
         All of the fields are private final,
         and both L and long are immutable type
     */

    //constructor
    public MultiInterval(L label){
        this.label = label;
        checkRep();
    }

    //checkRep
    private void checkRep() {
        int length = starts.size();
        for(int i=0; i<length; i++) {
            assert (starts.get(i) >= 0 && ends.get(i) >= 0);
        }
    }

    //methods
    public boolean add(long start, long end) {
        if(start < 0 || end < start) {
            return false;
        }
        int length = starts.size();
        if(starts.isEmpty() || start > starts.get(length-1)) {
            starts.add(start);
            ends.add(end);
            checkRep();
            return true;
        }
        for(int i=0; i<length; i++) {
            if(start == starts.get(i) && end == ends.get(i)) {
                checkRep();
                return false;
            }
            else if(start < starts.get(i) || start == starts.get(i) && end < ends.get(i)) {
                starts.add(i, start);
                ends.add(i, end);
                checkRep();
                return true;
            }
        }
        checkRep();
        return true;
    }

    public List<Long> getStart() {return this.starts;}

    public List<Long> getEnd() {return this.ends;}

    public L getLabel() {return this.label;}

    public int size() {return this.starts.size();}

    @Override
    public String toString() {
        String str = label + "=[";
        int length = starts.size();;
        for(int i=0; i<length; i++) {
            str += "[" + starts.get(i) + "," + ends.get(i) + "],";
        }
        str = ((length>0)?str.substring(0, str.length()-1): str)+"]";
        checkRep();
        return str;
    }
}
