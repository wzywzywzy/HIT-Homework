package application;

public class CourseScheduleApp {

    public static void main(String[] args) throws Exception {

        CourseIntervalSet CourseSchedule = new CourseIntervalSet("./txt/course/test.txt");

        CourseSchedule.process();

    }
}
