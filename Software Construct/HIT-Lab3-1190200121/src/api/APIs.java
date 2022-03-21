package api;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import interval.CommonMultiIntervalSet;
import interval.Interval;
import interval.IntervalSet;
import interval.MultiIntervalSet;

public class APIs<L> {

    double calcConflictRatio(IntervalSet<L> set) throws Exception {
        MultiIntervalSet<L> multiset = new CommonMultiIntervalSet<>(set);
        return calcConflictRatio(multiset);
    }

    double calcFreeTimeRatio(IntervalSet<L> set) throws Exception {
        MultiIntervalSet<L> multiset = new CommonMultiIntervalSet<>(set);
        return calcFreeTimeRatio(multiset);
    }
    public double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) throws Exception {

        long length = 0;
        long length1 = 0;
        long length2 = 0;
        long left = 100;
        long right = -100;
        Iterator<L> iter1 = s1.labels().iterator();
        while(iter1.hasNext()) {
            L s = iter1.next();
            IntervalSet<Integer> iS1 = s1.intervals(s);
            Set<Integer> iN1 = iS1.labels();

            if(iN1 == null)continue;
            for(Integer i: iN1)
                if(iS1.getEnd(i) > length1) {
                    length1 = iS1.getEnd(i);
                }
        }

        Iterator<L> iter2 = s2.labels().iterator();
        while(iter2.hasNext()) {
            L r = iter2.next();
            IntervalSet<Integer> iS2 = s2.intervals(r);
            Set<Integer> iN2 = iS2.labels();
            if(iN2 == null)continue;
            for(Integer j: iN2)
                if(iS2.getEnd(j) > length2) {
                    length2 = iS2.getEnd(j);
                }
        }

        length = Math.max(length1, length2);

        long overlap = 0;
        Iterator<L> iterr = s1.labels().iterator();
        while(iterr.hasNext()) {
            L s = iterr.next();
            if(s2.labels().contains(s)) {
                IntervalSet<Integer> intervalSet1 = s1.intervals(s);
                IntervalSet<Integer> intervalSet2 = s2.intervals(s);

                Set<Integer> inter1 = intervalSet1.labels();
                Set<Integer> inter2 = intervalSet2.labels();
                if(inter1 == null)continue;
                if(inter2 == null)continue;
                for(Integer i: inter1) {
                    for(Integer j: inter2) {
                        left = Math.min(left,Math.max(intervalSet1.getStart(i), intervalSet2.getStart(j)));
                        right = Math.max(right ,Math.min(intervalSet1.getEnd(i), intervalSet2.getEnd(j)));
                    }
                }
                overlap = right-left;
            }
        }
        return 1.0*overlap/length;
    }

    double calcConflictRatio(MultiIntervalSet<L> s1) throws Exception {

        long length1 = 0;
        Iterator<L> iter1 = s1.labels().iterator();
        while(iter1.hasNext()) {
            L s = iter1.next();
            IntervalSet<Integer> iS1 = s1.intervals(s);
            Set<Integer> iN1 = iS1.labels();

            if(iN1 == null)continue;
            for(Integer i: iN1)
                if(iS1.getEnd(i) > length1) {
                    length1 = iS1.getEnd(i);
                }
        }

        int[] cnt = new int[(int) length1];
        long offset = s1.whole().get(0).getStart();
        long overlap = 0;

        List<Interval<L>> intervals = s1.whole();
        for(int i=0; i<intervals.size(); i++) {
            for(long j=intervals.get(i).getStart()-offset; j<intervals.get(i).getEnd()-offset; j++) {
                cnt[(int)j]++;
            }
        }
        for(int i=0; i<length1; i++) {
            if(cnt[i] > 1) {
                overlap++;
            }
        }
        return 1.0*overlap/length1;
    }


    double calcFreeTimeRatio(MultiIntervalSet<L> s1) throws Exception {
        long length1 = 0;
        Iterator<L> iter1 = s1.labels().iterator();
        while(iter1.hasNext()) {
            L s = iter1.next();
            IntervalSet<Integer> iS1 = s1.intervals(s);
            Set<Integer> iN1 = iS1.labels();

            if(iN1 == null)continue;
            for(Integer i: iN1)
                if(iS1.getEnd(i) > length1) {
                    length1 = iS1.getEnd(i);
                }
        }

        int[] cnt = new int[(int) length1];
        long offset = s1.whole().get(0).getStart();
        long overlap = 0;

        List<Interval<L>> intervals = s1.whole();
        for(int i=0; i<intervals.size(); i++) {
            for(long j=intervals.get(i).getStart()-offset; j<intervals.get(i).getEnd()-offset; j++) {
                cnt[(int)j]++;
            }
        }
        for(int i=0; i<length1; i++) {
            if(cnt[i] == 0) {
                overlap++;
            }
        }
        return 1.0*overlap/length1;
    }

}