import java.util.ArrayList;

public class Student implements Comparable<Student> {

    private String name;
    private String id;
    public ArrayList<CreditCourse> courses;

    /**
     * Initializes variables
     *
     * @param name
     * @param id
     */
    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        courses = new ArrayList<CreditCourse>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Add a credit course to list of courses for this student
     */
    public void addCourse(String courseName, String courseCode, String descr, String format, String sem, double grade) {
        CreditCourse newCreditCourse = new CreditCourse(courseName, courseCode, descr, format, sem, grade);// creates a
        // CreditCourse
        // object
        newCreditCourse.setActive();// sets course active
        courses.add(newCreditCourse);// adds to courses arraylist
    }

    /**
     * Prints a student's transcript
     */
    public void printTranscript() {
        for (int i = 0; i <= courses.size() - 1; i++) {
            if (!courses.get(i).getActive())// only print if it is inactive
            {
                System.out.println(courses.get(i).displayGrade());// Prints all completed (i.e. non active) courses for
                                                                  // this
            } // student (course code, course name,semester, letter grade)
        }
    }

    // gets grade a student got in a course based on a given index (my own method)
    public double getGrade(int index) {
        return courses.get(index).getGrade();
    }

    // gets number of courses (my own method)
    public int numberCourses() {
        return courses.size();
    }

    // gets course code based on a given index (my own method)
    public String courseCode(int index) {
        return courses.get(index).getCode();
    }

    /**
     * Sets a course inactive
     *
     * @param courseCode
     * @param grade
     */
    public void setInactiveGrade(String courseCode, double grade) {
        for (int i = 0; i <= courses.size() - 1; i++) {// find the course
            CreditCourse c = courses.get(i);
            if (c.getCode().equalsIgnoreCase(courseCode)) {// if found
                c.setInactive();// set it inactive
                c.setGrade(grade);// set the grade to the given grade
            }
        }
    }

    /**
     * Prints all active courses this student is enrolled in
     */
    public void printActiveCourses() {
        for (int i = 0; i <= courses.size(); i++) {// go through courses
            if (courses.get(i).getActive()) {// if it is an active course
                System.out.println(courses.get(i));// print the course
            }
        }
    }

    /**
     * Drop a course (given by courseCode)
     */
    public void removeActiveCourse(String courseCode) {
        for (int i = 0; i <= courses.size() - 1; i++) {// Find the credit course in courses arraylist
            CreditCourse c = courses.get(i);
            if (c.getActive() && c.getCode().equalsIgnoreCase(courseCode)) {// if it is active
                courses.remove(i);// remove it
                break;// prevent looping again
            }
        }
    }

    @Override
    /**
     * Overrides the built-in toString()
     */
    public String toString() {
        return "Student ID: " + getId() + " Name: " + getName();
    }

    /**
     * Override equals method inherited from superclass Object
     *
     * @return if the student is equal to any other student
     */
    @Override
    public boolean equals(Object other) {
        // Cast (if necessary)
        Student otherStudent = Student.class.cast(other);// makes a new student object based on the object passed in
        return (this.name.equalsIgnoreCase(otherStudent.name) && this.id.equals(otherStudent.id));// checks if the
        // student name and
        // id is equal to
        // another student
    }

    /**
     * Comparable method compareTo used to sort the list of students
     *
     * @return if the student's name is the same as any other student
     */
    public int compareTo(Student other) {
        return this.getName().compareToIgnoreCase(other.getName());
    }
}
