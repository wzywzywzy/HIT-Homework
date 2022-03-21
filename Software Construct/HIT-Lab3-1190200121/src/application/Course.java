package application;

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
public class Course {

    private final String id, name, teacher, location;
    private final int hours;

    public Course(String id, String name, String teacher, String location, int hours) {
        this.id = id;
        this.name = name;

        this.hours = hours;
        this.teacher = teacher;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public String toString() {
        return "{ID: "+id+",subject:"+name+",teacher:"+teacher+",classroom:"+location+",times:"+hours+"}";
    }
}
