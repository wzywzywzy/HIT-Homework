package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interval.Interval;
import interval.NonOverlapIntervalSet;
import interval.NonOverlapIntervalSetImpl;

/**
 *      AF:
 *        ProcessTable is the arrangement
 *        processes is the schedule
 *        s is stdin
 *      RI:
 *        the time arrangement forbid overlap
 *      Safety from rep exposure:
 *        whole fields are private,
 *        The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
 */
public class ProcessIntervalSet {

    //fields
    private Scanner s = new Scanner(System.in);
    private List<Process> processes = new ArrayList<>();
    private NonOverlapIntervalSet<String> ProcessTable = new NonOverlapIntervalSetImpl<>();


    public static void main(String[] args) throws Exception {

        ProcessIntervalSet ProcessSchedule = new ProcessIntervalSet("./txt/process/test.txt");
//此处可选择优先排班还是随机排班
//		ProcessSchedule.randomSet();
        ProcessSchedule.bestSet();

        System.out.println(ProcessSchedule.toString());
        ProcessSchedule.show(100);
    }

    //checkRep
    public void checkRep() throws Exception {
        ProcessTable.checkNoOverlap("DISABLE");
    }

    /**
     * output the meau
     *
     * @return user's choice(0-3)
     */
    private int printMeau() {
        System.out.println("the meau :1:random module 2:best module 3:check the situation before the input time 0:Exit");
        int flag = s.nextInt();
        return flag;
    }

    /**
     * change the content into string
     *
     * @param filename filename
     * @return the information of the file (string)
     * @throws IOException
     */ 
    public static String fileToString(String filename) throws IOException {
        File file = new File(filename);
        if(!file.exists()){return null;}
        FileInputStream inputStream = new FileInputStream(file);
        int length = inputStream.available();
        byte bytes[] = new byte[length];
        inputStream.read(bytes);
        inputStream.close();
        String str = new String(bytes, StandardCharsets.UTF_8);
        return str ;
    }

    /**
     * 格式化输出进程调度情况
     */
    public String toString() {
        List<Interval<String>> list = ProcessTable.whole();

        String str = "";
        long time1,time2;
        for(int i=0; i<list.size(); i++) {
            time1 = list.get(i).getStart();
            time2 = list.get(i).getEnd();
            str += "(" + time1 + "," + time2 + ") id" + list.get(i).getLabel() + '\n' ;
        }

        return str;

    }
    
    /**
     * according the user's choice to choose the module
     * the meau :1:random module 2:best module 3:check the situation before the input time 0:Exit
     *
     * @throws Exception
     */
    public void process() throws Exception {
        int flag = printMeau();
        while(flag != 0) {
            switch(flag) {
                case 1:
                    ProcessTable = new NonOverlapIntervalSetImpl<>();
                    randomSet();
                    flag = printMeau();
                    break;
                case 2:
                    ProcessTable = new NonOverlapIntervalSetImpl<>();
                    bestSet();
                    flag = printMeau();
                    break;
                case 3:
                    System.out.println("Please input the current time");
                    int time = s.nextInt();
                    show(time);
                    flag = printMeau();
                    break;
                default:
                    flag = printMeau();
                    break;
            }
        }
        s.close();
        checkRep();
        System.out.println("Exit!");

    }

    //constructor
    public ProcessIntervalSet(String filename) throws Exception {
        String str = fileToString(filename);

        String pattern1 = "Process\\{[\\s\\S]*\\}";
        Pattern r1 = Pattern.compile(pattern1);

        Matcher m = r1.matcher(str);

        if(!m.find()) {
            throw new Exception("Error！");
        }else {
            String temp = m.group(0);
            String pattern_t = "\\d{8}-[\\w]+\\{[\\d]+,[\\d]+\\}";
            Pattern reads = Pattern.compile(pattern_t);
            Matcher ss = reads.matcher(temp);
            int pos1 = 0, pos2 = 0;
            while(ss.find()) {
                String string = ss.group(0);
                pos1 = string.indexOf("-");
                pos2 = string.indexOf("{");
                String id = string.substring(0, pos1);
                String name = string.substring(pos1+1, pos2);
                
                pos1 = string.indexOf(",");
                String min = string.substring(pos2+1, pos1);
                
                pos2 = string.indexOf("}");
                String max = string.substring(pos1+1, pos2);
                processes.add(new Process(id, name, Integer.parseInt(min), Integer.parseInt(max)));
            }
        }
        checkRep();
    }

    /**
     * arrange randomly
     *
     * @throws Exception
     */
    public void randomSet() throws Exception {
        List<Process> tmp = new ArrayList<>();
        for(Process t: processes){
            tmp.add(t);
        }
        int size = tmp.size();
        int start = 0, end = 0, length;
        while(size > 0) {
            int index = (int) (Math.random() * (size + 1));

            if(index == size) {//empty
                length = 5;
                end = start + length;
            }
            else {
                Process process = tmp.get(index);
                length = (int) (Math.random() * (process.getMaxTime() + 1));
                end = start + length;

                ProcessTable.insert(start, end, process.getId(), "DISABLE");
                tmp.remove(index);
                if(length < process.getMinTime()) {
                    tmp.add(new Process(process.getId(), process.getName(), process.getMinTime() - length, process.getMaxTime() - length));
                }
                else {
                    size--;
                }
            }
            start = end + 1;
        }
        checkRep();
    }

    /**
     * 最短进程优先
     *
     * @throws Exception
     */
    public void bestSet() throws Exception {
        List<Process> tmp = new ArrayList<>();
        for(Process t: processes){
            tmp.add(t);
        }
        int size = tmp.size();
        int start = 0, end = 0, length;
        while(size > 0) {
            int index=0, minTime=Integer.MAX_VALUE;
            for(int i=0; i<size; i++) {
                if(tmp.get(i).getMaxTime() < minTime) {
                    minTime = tmp.get(i).getMaxTime();
                    index = i;
                }
            }
                Process process = tmp.get(index);
                length = (int) (Math.random() * (process.getMaxTime() + 1));
                end = start + length;

                ProcessTable.insert(start, end, process.getId(), "DISABLE");
                tmp.remove(index);
                if(length < process.getMinTime()) {
                    tmp.add(new Process(process.getId(), process.getName(), process.getMinTime() - length, process.getMaxTime() - length));
                }
                else {
                    size--;
                }

            start = end + 1;
        }
        checkRep();
    }

    /**
     * 可视化当前时刻之前的进程调度结果
     *
     * @param time current（non-negative）
     */
    public void show(int time) {
        List<Interval<String>> list = new ArrayList<>();

        list = ProcessTable.whole();

        String str = "";
        long start, end;
        for(int i=0; i<list.size(); i++) {
            if(time < list.get(i).getStart()) {
                str += "current: nothing";
                break;
            }
            start = list.get(i).getStart();
            end = list.get(i).getStart();
            str += "("+ start + ",";
            if(time <= end) {
                str += time + ") id:" + list.get(i).getLabel() + "\n" +"current: " + list.get(i).getLabel();
                break;
            }
            else {
                str += end +") id:" + list.get(i).getLabel() + "\n";
            }

        }

        if (list.isEmpty() || !list.isEmpty() && time > list.get(list.size() - 1).getEnd()) {
            str += "current: nothing";
        }

        System.out.println(str);
    }
}

