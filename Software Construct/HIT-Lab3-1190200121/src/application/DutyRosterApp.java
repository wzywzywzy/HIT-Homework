package application;

public class DutyRosterApp {

    public static void main(String[] args) throws Exception {

        DutyIntervalSet DutyRoster = new DutyIntervalSet("./txt/duty/test.txt");

        DutyRoster.process();

    }
}
