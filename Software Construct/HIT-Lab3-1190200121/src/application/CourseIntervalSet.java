package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interval.Interval;
import interval.PeriodIntervalSet;
import interval.PeriodIntervalSetImpl;

/**
 *      AF:
 *        standard_date为表示格式化日期
 *        begin是开学日weeks是学期周数
 *        CourseTable表示课程安排表
 *        s isstdin
 *      RI:
 *       模拟四种操作1：arrange courses 2：check the free courses 3：check the ratio 4：check the schedule of one day
 *      Safety from rep exposure:
 *        All fields are private,
 *        The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
 */
public class CourseIntervalSet {

    private Date begin = new Date();
    private int weeks;
    private Set<Course> courses = new HashSet<>();
    private PeriodIntervalSet<Course> CourseTable = new PeriodIntervalSetImpl<>(7, 5);
    private Scanner s = new Scanner(System.in);
    private final SimpleDateFormat standard_date = new SimpleDateFormat("yyyy-MM-dd");


    public static void main(String[] args) throws Exception {

        CourseIntervalSet CourseSchedule = new CourseIntervalSet("./txt/course/test.txt");

        CourseSchedule.process();
    }

    //constructor
    public CourseIntervalSet(String filename) throws Exception{
        String str = fileToString(filename);

        String pattern1 = "Period\\{\\d{4}-\\d{2}-\\d{2},[\\d]+\\}";
        Pattern r1 = Pattern.compile(pattern1);

        String pattern2 = "Course\\{[\\s\\S]*\\}";
        Pattern r2 = Pattern.compile(pattern2);
//处理学期信息
        Matcher m = r1.matcher(str);
        if(!m.find()) {
            throw new Exception("Error!");
        }else {
            String tmp1 = m.group(0);
            int pos1 = tmp1.indexOf("{") + 1;
            int pos2 = tmp1.indexOf(",");
            int pos3 = tmp1.indexOf("}");

            String begin = tmp1.substring(pos1, pos2);
            String week = tmp1.substring(pos2 + 1, pos3);
            this.begin = standard_date.parse(begin);
            this.weeks = Integer.parseInt(week);
        }
//处理课程信息
        m = r2.matcher(str);

        if(!m.find()) {
            throw new Exception("Error!");
        }else {
            String tmp2 = m.group(0);
            String t = "[\\d]{8}\\{[a-zA-Z]+[a-zA-Z ]*,[a-zA-Z]+[a-zA-Z ]*,[\\w ]+,[\\d]+\\}";
            Pattern r_t = Pattern.compile(t);
            Matcher m_t = r_t.matcher(tmp2);
            int pos = 0;
            while (m_t.find()) {
                String ss = m_t.group(0);
                pos = ss.indexOf("{");
                String id = ss.substring(0, pos);
                ss = ss.substring(pos + 1);

                pos = ss.indexOf(",");
                String name = ss.substring(0, pos);
                ss = ss.substring(pos + 1);

                pos = ss.indexOf(",");
                String teacher = ss.substring(0, pos);
                ss = ss.substring(pos + 1);

                pos = ss.indexOf(",");
                String location = ss.substring(0, pos);
                ss = ss.substring(pos + 1);

                pos = ss.indexOf("}");
                String hours = ss.substring(0, pos);
                courses.add(new Course(id, name, teacher, location, Integer.parseInt(hours)));
            }
        }
        
    }
    /**
     * judicial whether the course has been managed
     *
     * @param name the course
     * @return true if the schedule contains
     *         false otherwise
     */
    private boolean existCourse(String name) {
        for(Course t: courses){
            if(t.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * get the course from the keyboard
     *
     * @param name the course
     * @return the string of the course
     * @throws Exception
     */
    
    
    private Course getCourse(String name) throws Exception {
        Iterator<Course> iter = courses.iterator();
        while(iter.hasNext()) {
            Course temp = iter.next();
            if(temp.getName().equals(name)){
                return temp;
            }
        }
        throw new Exception();
    }

    /**
     * change the content into string
     *
     * @param filename filename
     * @return the information of the file (string)
     * @throws IOException
     */
    private String fileToString(String filename) throws IOException {
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
     * output the meau
     *
     * @return the choice（0-4）
     */
    private int printMeau() {
        System.out.println("The Meau is：1：arrange courses 2：check the free courses 3：check the ratio 4：check the schedule of one day 0：exit");
        int flag = s.nextInt();
        return flag;
    }
    /**
     * The Meau is：1：arrange courses 2：check the free courses 3：check the ratio 4：check the schedule of one day 0：exit
     *
     * @throws Exception
     */
    public void process() throws Exception {
        Set<Course> copy = new HashSet<>();
        for(Course t:courses) {
            copy.add(t);
        }
        System.out.print("Please input how many weeks do you want,now is");
        System.out.println(this.weeks);
        this.weeks = s.nextInt();
        int flag = printMeau();
        while(flag != 0) {
            switch(flag) {
                case 1:
                    System.out.println("Please input the subject");
                    String name = s.next();
                    while(!existCourse(name)) {
                        System.out.println("Please input the subject");
                        name = s.next();
                    }
                    System.out.println(name);

                    int total = getCourse(name).getHours();
                    Course p = null;
                        int day, times;
                        do {
                            if(total<=0)break;
                            System.out.println("the rest hours(every time two hours)："+total);
                            System.out.println("Please input which day you prefer(1-7)");
                            day = s.nextInt();
                            System.out.println("Please input when do you want to study(1-5)");
                            times = s.nextInt();
                            p = getCourse(name);
                            total -= 2;
                        }while(CourseTable.unOverlapInsert(p, new Interval<Integer>(times, times, day)));

                    copy.remove(p);
                    flag = printMeau();
                    break;
                case 2:
                    System.out.println("Please out put the rest subjects");
                    for(Course tt: copy){
                        System.out.println(tt.toString());
                    }
                    flag = printMeau();
                    break;
                case 3:
                    System.out.println("the current ratio of free:"+CourseTable.ratioOfFree());
                    System.out.println("the current ratio of overlap:"+CourseTable.ratioOfOverlap());
                    flag = printMeau();
                    break;
                case 4:
                    System.out.println("which day do you want to figure(example 2021-03-01)");
                    String input = s.next();
                    Date date = standard_date.parse(input);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    List<Set<Course>> result = CourseTable.eventsOfDay(cal.get(Calendar.DAY_OF_WEEK)-1);
                    System.out.println("08-10:"+result.get(0).toString());
                    System.out.println("10-12:"+result.get(1).toString());
                    System.out.println("13-15:"+result.get(2).toString());
                    System.out.println("15-17:"+result.get(3).toString());
                    System.out.println("19-21:"+result.get(4).toString());
                    flag = printMeau();
                    break;
                default:
                    flag = printMeau();
                    break;
            }
        }
        System.out.println("Exit！");
        s.close();
    }
}

