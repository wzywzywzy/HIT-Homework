package application;

public class ProcessScheduleApp {

    public static void main(String[] args) throws Exception {

        ProcessIntervalSet ProcessSchedule = new ProcessIntervalSet("./txt/process/test.txt");

        ProcessSchedule.process();

    }
}
