package application;

/**
 *  AF:
 *    standard_date is the standard date for output
 *    startDay is the begin day
 *    endDay is the deadline
 *    period is the free time between two time
 *    TimeTable is the schedule
 *    EmployeeTable is the worker's table
 *    s « ‰»Î¡˜
 *  RI:
 *    period is the interval between startDay ans endDay
 *    TimeTable forbid overlap
 *    Safety from rep exposure:
 *    All fields are private,
 *    The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
 */
public class Employee {

    private final String name, duty, tel;

    public Employee(String name, String duty, String tel) {
        this.name = name;
        this.duty = duty;
        this.tel = tel;
    }
    public Employee() {
        name = "";
        duty = "";
        tel = "";
    }



    public String getName() {
        return this.name;
    }

    public String toString() {
        return "{"+ name + ","+ duty +","+ tel +"}";
    }

}
