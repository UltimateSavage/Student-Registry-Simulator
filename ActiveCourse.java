import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Active University Course
public class ActiveCourse extends Course {

    private ArrayList<Student> students;
    private String semester;
    private int lectureStart;
    private int lectureDuration;
    private String lectureDay;

    /**
     * Sets the param according to the conditions
     *
     * @param name            passed to super
     * @param code            same
     * @param descr           same
     * @param fmt             same
     * @param semester        initializes semester
     * @param students        initializes a copy of student list
     * @param lectureStart    sets the start time of active course
     * @param lectureDuration same
     * @param lectureDay      same
     */
    public ActiveCourse(String name, String code, String descr, String fmt, String semester,
            ArrayList<Student> students) {
        super(name, code, descr, fmt);
        this.semester = semester;
        this.students = (ArrayList<Student>) students.clone();
        lectureStart = 0;
        lectureDuration = 0;
        lectureDay = "";
    }

    /**
     * Sets the day, start time and duration of the course
     * @param day
     * @param startTime
     * @param duration
     */
    public void setDayAndTime(String day, int startTime, int duration) {
        this.lectureDay = day;
        this.lectureStart = startTime;
        this.lectureDuration = duration;
    }

    //gets day
    public String getDay() {
        return lectureDay;
    }

    //gets start time
    public int getStart() {
        return lectureStart;
    }

    //gets duration
    public int getDuration() {
        return lectureDuration;
    }

    // gets semsester
    public String getSemester() {
        return semester;
    }

    // Prints the students in this course (name and student id)
    public void printClassList() {
        for (int i = 0; i <= students.size() - 1; i++) {
            System.out.println(students.get(i).toString());
        }
    }

    // Prints the grade of each student in this course (along with name and student
    // id)
    //
    public void printGrades() {
        String format = "%-15s%-5s%-2s%-4s%n";
        for (int i = 0; i <= students.size() - 1; i++) {// this for checks get each student
            Student s = students.get(i);
            for (int j = 0; j <= s.courses.size() - 1; j++) {// this for gets the courses for a given student (s)
                CreditCourse c = s.courses.get(j);
                if (c.getCode().equals(super.getCode())) {
                    System.out.format(format, s.getName(), s.getId(), "  ", Double.toString(c.getGrade()));
                    // print grade of student s
                }
            }
        }
    }

    /**
     * Returns the numeric grade in this course for this student
     *
     * @param studentId
     * @return grade, if not found returns 0
     */
    public double getGrade(String studentId) {
        for (int i = 0; i <= students.size() - 1; i++) {// goes through student list
            Student s = students.get(i);
            if (s.getId().equals(studentId)) {// if student found
                for (int j = 0; j <= s.numberCourses() - 1; j++) {// goes through student courses
                    if (super.getCode().equals(s.courseCode(j))) {// if course found, return grade
                        return s.getGrade(j);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Adds a student to the student arraylist
     */
    public void addStudent(Student s) {
        students.add(s);
    }

    /**
     * @return size of students arraylist
     */
    public int numberOfStudents() {
        return students.size();
    }

    /**
     * @return the student based on a given index
     */
    public Student getStudent(int index) {
        return students.get(index);
    }

    /**
     * removes a student from the registry based on their student id
     *
     * @param studentId
     */
    public void removeStudent(String studentId) {
        for (int i = 0; i <= students.size() - 1; i++) {// checks if student in the list
            Student s = students.get(i);
            if (s.getId().equals(studentId)) {// if they are
                students.remove(i);// remove them
                break;// breaks to stop looping
            }
        }
    }

    /**
     * @return a String containing the course information as well as the semester
     *         and the number of students enrolled in the course
     */
    @Override
    public String getDescription() {
        return super.getDescription() + "\t" + semester + "\t" + students.size();// overrides the superclass method
    }

    /**
     * Sorts the student in the course by name using Collections.sort()
     */
    public void sortByName() {
        Collections.sort(students, new NameComparator());
    }

    /**
     * Implements comparator with type Student
     */
    private class NameComparator implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {
            return s1.getName().compareToIgnoreCase(s2.getName());
        }
    }

    // Same as above but with id
    public void sortById() {
        Collections.sort(students, new IdComparator());
    }

    // Same as above but with id
    private class IdComparator implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {
            if (Double.valueOf(s1.getId()) < Double.valueOf(s2.getId())) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}