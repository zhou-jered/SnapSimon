package me.local.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MergeInterval {


    static class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public static Interval of(int s, int e) {
            return new Interval(s, e);
        }

        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }

    ArrayList<Interval> solve(ArrayList<Interval> intervals) {
        ArrayList<Interval> mergedIntervals = new ArrayList();
        intervals.sort((a, b) -> {
            return Integer.compare(a.start, b.start);
        });

        System.out.println(intervals);
        Interval lastInterval = new Interval(intervals.get(0).start, intervals.get(0).end);
        int lastEnd = intervals.get(0).end;
        for (int i = 1; i < intervals.size(); i++) {
            Interval current = intervals.get(i);
            if (current.start <= lastEnd) {
                lastEnd = Math.max(lastEnd, current.end);
            } else {
                lastInterval.end = lastEnd;
                mergedIntervals.add(lastInterval);
                lastInterval = current;
                lastEnd = current.end;
            }
            if (i == intervals.size() - 1) {
                mergedIntervals.add(lastInterval);
            }
        }
        return mergedIntervals;
    }

    ArrayList<Interval> solve1(ArrayList<Interval> intervals) {
        intervals.sort((a,b)->{
            if(a.start == b.start) {
                return -Integer.compare(a.end, b.end);
            }
            return Integer.compare(a.start, b.start);
        });

        intervals.sort((a,b)->{
            if(a.start == b.start) {
                return -Integer.compare(a.end, b.end);
            }
            return Integer.compare(a.start, b.start);
        });

        ArrayList<Interval> mergedIntervals = new ArrayList();
        Interval lastInterval = intervals.get(0);
        for(int i =1;i<intervals.size();i++) {
            Interval current = intervals.get(i);
            if(current.start == lastInterval.start){
                continue;
            }
            if(current.start<=lastInterval.end) {
                lastInterval.end = Math.max(current.end, lastInterval.end);
            } else {
                mergedIntervals.add(lastInterval);
                lastInterval = current;
            }
            if(i==intervals.size()-1) {
                mergedIntervals.add(lastInterval);

            }
        }
        return mergedIntervals;
    }

    public static void main(String[] args) {
        ArrayList<Interval> input = getInput();
        ArrayList<Interval> result = new MergeInterval().solve1(input);
        System.out.println(result);
    }

    static ArrayList<Interval> getInput() {
        ArrayList<Interval> intervals = new ArrayList<>();
        intervals.add(Interval.of(1, 2));
        intervals.add(Interval.of(1, 4));
        intervals.add(Interval.of(1, 6));
        intervals.add(Interval.of(2, 3));
        intervals.add(Interval.of(2, 3));
        intervals.add(Interval.of(8, 10));
        intervals.add(Interval.of(11, 13));
        intervals.add(Interval.of(12, 15));
        return intervals;
    }

}
