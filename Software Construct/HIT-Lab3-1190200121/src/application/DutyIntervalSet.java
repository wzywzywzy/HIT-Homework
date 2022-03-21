package application;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

import interval.Interval;
import interval.NonOverlapIntervalSet;
import interval.NonOverlapIntervalSetImpl;

/**
 *  AF:
 *    standard_date is the standard date for output
 *    startDay is the begin day
 *    endDay is the deadline
 *    period is the free time between two time
 *    TimeTable is the schedule
 *    EmployeeTable is the worker's table
 *    s是输入流
 *  RI:
 *    period is the interval between startDay ans endDay
 *    TimeTable forbid overlap
 *    Safety from rep exposure:
 *    All fields are private,
 *    The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
 */
public class DutyIntervalSet {

    //fields
    private final SimpleDateFormat standard_date = new SimpleDateFormat("yyyy-MM-dd");
    private Date startDay = new Date();
    private Date endDay = new Date();
    private final long period;
    private NonOverlapIntervalSet<Employee> TimeTable = new NonOverlapIntervalSetImpl<>();
    private Set<Employee> EmployeeTable = new HashSet<>();
    private Scanner s = new Scanner(System.in);


    public static void main(String[] args) throws Exception {

        DutyIntervalSet DutyRoster = new DutyIntervalSet("./txt/duty/test.txt");

        System.out.println(DutyRoster.toString());
    }

    public void checkRep() throws Exception {
        assert(period == (int) ((endDay.getTime() - startDay.getTime()) / (24*60*60*1000)));
        TimeTable.checkNoOverlap("DISABLE");
    }

    //constructor
    public DutyIntervalSet(String filename) throws Exception{

        File file = new File(filename);
        if(!file.exists()){
            throw new Exception("Error!");
        }
        FileInputStream inputStream = new FileInputStream(file);
        int length = inputStream.available();
        byte bytes[] = new byte[length];
        inputStream.read(bytes);
        inputStream.close();
        String str = new String(bytes, StandardCharsets.UTF_8);

        String pattern1 = "Period\\{.*\\}";
        String pattern2 = "Employee\\{[\\s\\S]*\\}";
        String pattern3 = "Roster\\{[\\s\\S]*\\}";

        Pattern r1 = Pattern.compile(pattern1);
        Pattern r2 = Pattern.compile(pattern2);
        Pattern r3 = Pattern.compile(pattern3);



        Matcher m = r1.matcher(str);

        if(!m.find()) {
            throw new Exception("Error!");
        }else {
            String tmp = m.group(0);
            String pattern_t = "\\d{4}-\\d{2}-\\d{2}";
            Pattern r_t = Pattern.compile(pattern_t);
            Matcher m_t = r_t.matcher(tmp);
            m_t.find();
            startDay = standard_date.parse(m_t.group(0));
            m_t.find();
            endDay = standard_date.parse(m_t.group(0));
            period = calctheperiod(startDay, endDay);
        }

        m = r2.matcher(str);

        if(!m.find()) {
            throw new Exception("Error！");
        }else {
            String tmp = m.group(0);
            String pattern_t = "[a-zA-Z]+\\{[a-zA-Z]+[a-zA-Z ]*,\\d{3}-\\d{4}-\\d{4}\\}";
            Pattern r_t = Pattern.compile(pattern_t);
            Matcher m_t = r_t.matcher(tmp);
            int pos1,pos2;
            while(m_t.find()) {
                String ss = m_t.group(0);
                pos1 = ss.indexOf("{");
                String name = ss.substring(0, pos1);
                pos2 = ss.indexOf(",");
                String position = ss.substring(pos1+1, pos2);
                pos1 = ss.indexOf("}");
                String tel = ss.substring(pos2+1, pos1);
                EmployeeTable.add(new Employee(name, position, tel));
            }
        }

        m = r3.matcher(str);

        if(!m.find()) {
	    	throw new Exception("Error！");
        }else {
            String tmp = m.group(0);
            String pattern_t = "[a-zA-Z]+\\{\\d{4}-\\d{2}-\\d{2},\\d{4}-\\d{2}-\\d{2}\\}";
            Pattern r_t = Pattern.compile(pattern_t);
            Matcher m_t = r_t.matcher(tmp);
            int pos1,pos2;
            while(m_t.find()) {
                String ss = m_t.group(0);
                pos1 = ss.indexOf("{");
                String name = ss.substring(0, pos1);
                pos2 = ss.indexOf(",");
                String start = ss.substring(pos1+1, pos2);
                pos1 = ss.indexOf("}");
                String end = ss.substring(pos2+1, pos1);
                set(name, start, end);
            }
        }
        checkRep();
    }

    /**
     * calculate the period
     *
     * @param startDay the date of start
     * @param endDay the date of end
     * @return the period of start and end
     */
    private int calctheperiod(Date startDay, Date endDay) {
        return (int) ((endDay.getTime() - startDay.getTime()) / (24*60*60*1000));
    }

    /**
     * 进行手动排班
     *
     * @param name the name of the employee
     * @param start the date of the start
     * @param end the date of the end
     * @throws ParseException
     * @throws Exception
     */
    private void set(String name, String start, String end) throws ParseException, Exception {

        for(Employee person : EmployeeTable){
            if(person.getName().equals(name)) {
                int period1 = calctheperiod(startDay, standard_date.parse(start));
                int period2 = calctheperiod(startDay, standard_date.parse(end));
                TimeTable.insert(period1, period2, person, "DISABLE");
                checkRep();
                return;
            }
        }
        checkRep();
    }


    /**
     * print the meau
     *
     * @return the choice
     * @throws Exception
     */
    private int printMeau() throws Exception {
        System.out.println("The meau:  1：manage the schedule by personal 2：manage the schedule randomly 3：check whether it is full 4：check the surrent situation 0：exit");
        int ans = s.nextInt();
        checkRep();
        return ans;
    }

//    /**
//     * check whether the table contains the employee
//     *
//     * @param name the name of the employee
//     * @return true the table contains the employee
//     *         otherwise false
//     */
//    private boolean existEmployee(String name) {
//        for(Employee person: EmployeeTable){
//            if(person.getName().equals(name)) {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * output the current situation
     *
     */
    public String toString() {
        List<Interval<Employee>> list = new ArrayList<>();

        list = TimeTable.whole();

        Calendar c = Calendar.getInstance();
        String string = "";

        for(int i=0; i<list.size(); i++) {
            string += "(";
            c.setTime(startDay);
            c.add(Calendar.DATE, (int)list.get(i).getStart());
            string += standard_date.format(c.getTime()) + ",";

            c.setTime(startDay);
            c.add(Calendar.DATE, (int)list.get(i).getEnd());
            string += standard_date.format(c.getTime()) + "): " + list.get(i).getLabel().toString() + "\n";
        }
        return string;
    }

    /**
     * according to the input to choose the module(must 0-4)
     * 1：manage the schedule by personal 2：manage the schedule randomly 3：check whether it is full 4：check the surrent situation 0：exit
     *
     * @throws Exception
     */
    public void process() throws ParseException, Exception {
        int choice = printMeau();
        while(choice != 0) {
            switch(choice) {
                case 1:
                    System.out.println("Please input the employee's name:");
                    String name = s.next();
//                    while(!existEmployee(name)) {
//                        System.out.println("Please input the employee's name:");
//                        name = s.next();
//                    }
                    System.out.println("Please input the start time:");
                    String startTime = s.next();
                    System.out.println("Please input the end time:");
                    String endTime = s.next();
                    set(name, startTime, endTime);
                    choice = printMeau();
                    break;
                case 2:
                    List<Employee> tmp = new ArrayList<>();
                    TimeTable = new NonOverlapIntervalSetImpl<>();

                    for(Employee t: EmployeeTable){
                        tmp.add(t);
                    }
                    long start = 0;
                    while(start < period) {
                        long end,ddl;
                        end = (tmp.size() == 1)? period : (long) (Math.random() * (period - start + 1) + start);
                        ddl = end+1;
                        Employee employee_t = tmp.get(0);
                        tmp.remove(0);
                        Calendar ci = Calendar.getInstance();
                        ci.setTime(startDay);ci.add(Calendar.DATE, (int) start);
                        String startDate = standard_date.format(ci.getTime());

                        ci.setTime(startDay);ci.add(Calendar.DATE, (int) end);
                        String endDate = standard_date.format(ci.getTime());

                        set(employee_t.getName(), startDate, endDate);
                        start = Math.min(ddl, period);
                        if(ddl == period) {
                            ci.setTime(startDay);ci.add(Calendar.DATE, (int) period);

                            set(tmp.get(0).getName(), standard_date.format(ci.getTime()), standard_date.format(ci.getTime()));
                        }
                    }
                    choice = printMeau();
                    break;
                case 3:
                    List<Interval<Employee>> list = TimeTable.whole();
                    List<Interval<Integer>> freelist = new ArrayList<>();
                    int cnt = 0;

                    
                    for(int i=0; i<list.size(); i++) {
                        if(i == 0){
                            if(list.get(i).getStart() > 0) {
                                freelist.add(new Interval<Integer>(0, list.get(i).getStart()-1, cnt++));
                            }
                            continue;
                        }
                        

                        if(list.get(i).getStart() - list.get(i-1).getEnd() > 1) {
                            freelist.add(new Interval<Integer>(list.get(i-1).getEnd()+1, list.get(i).getStart()-1, cnt++));
                        }
                        
                        if(i == list.size()-1)
                        {
                            if(list.get(i).getEnd() < period) {
                                freelist.add(new Interval<Integer>(list.get(i).getEnd()+1, period, cnt++));
                            }
                        }
                    }


                    if(!freelist.isEmpty()){
                        Calendar ci = Calendar.getInstance();
                        int length = freelist.size();
                        System.out.println("The free time is:");
                        for(int i=0; i<length; i++) {
                            String str = i + ":(";
                            ci.setTime(startDay);
                            ci.add(Calendar.DATE, (int)freelist.get(i).getStart());
                            str += standard_date.format(ci.getTime())+","; 
                            
                            ci.setTime(startDay);
                            ci.add(Calendar.DATE, (int)freelist.get(i).getEnd());
                            str += standard_date.format(ci.getTime())+")";
                            System.out.println(str);
                        }
                    }
                    else{
                    System.out.println("Full!");
                    }
                checkRep();
                    choice = printMeau();
                    break;
                case 4:
                    System.out.println("the current situation :");
                    System.out.println(toString());
                    choice = printMeau();
                    break;
                default:
                    choice = printMeau();
                    break;
            }
        }
        System.out.println("Exit!");
        s.close();
        checkRep();
    }
}

