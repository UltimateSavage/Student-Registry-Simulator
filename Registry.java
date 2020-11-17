import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class Registry {

    private TreeMap<String, Student> students = new TreeMap<String, Student>();
    private TreeMap<String, ActiveCourse> courses = new TreeMap<String, ActiveCourse>();

    /**
     * Initializes the registry
     *
     * @throws java.io.IOException
     */
    public Registry() throws IOException {
        // Add some students
        File studentsFile = new File("students.txt");// finds students file
        if (!studentsFile.exists()) {
            throw new IOException("students.txt not found");// throws exception if not found
        }
        File coursesFile = new File("courses.txt");// finds courses file
        if (!coursesFile.exists()) {
            throw new IOException("courses.txt not found");// throws exception if not found
        }
        Scanner scannerStudents = new Scanner(studentsFile);// scanner for students file
        String tempName = "", tempId = "";// temporary variables for the name and id
        ArrayList<String> studentIds = new ArrayList<String>();// arraylist of student ids, useful below
        while (scannerStudents.hasNextLine()) {// iterates through file
            tempName = scannerStudents.next();// sets name to the first element of a line
            if (scannerStudents.hasNext()) {// checks if there is a second
                tempId = scannerStudents.next();// sets id to the second
                studentIds.add(tempId);// adds the id to the student id list
                students.put(tempId, new Student(tempName, tempId));// puts the student in the student manp
            } else {
                throw new IOException("Bad File Format, students.txt");// throws exception if there is not 2 elements in a line
            }
        }
        ArrayList<Student> listMap = new ArrayList<Student>();// list for which students go in which class
        String[] classes = { "1 2 3", "0 4 5", "0 1 3 5", "0 1 3 5", "0 1 3 5" };// the numbers represent the index of
                                                                                 // which students go in which class
        String courseName = "", courseCode = "", descr = "", format = "";// temp variables

        Scanner scannerCourses = new Scanner(coursesFile);
        while (scannerCourses.hasNextLine()) {// iterates through courses file
            for (int i = 0; i < classes.length; i++) {
                Scanner classScanner = new Scanner(classes[i]);// scanner for each string in classes
                courseName = scannerCourses.nextLine();// sets
                courseCode = scannerCourses.nextLine();// sets
                descr = scannerCourses.nextLine();// sets
                format = scannerCourses.nextLine();// sets
                while (classScanner.hasNextInt()) {
                    int index = classScanner.nextInt();
                    listMap.add(students.get(studentIds.get(index)));// prepares a student list based on the index
                    students.get(studentIds.get(index)).addCourse(courseName, courseCode, descr, format, "W2020", 0);
                    // adds the course to the student's list of courses
                }
                courses.put(courseCode.toLowerCase(),
                        new ActiveCourse(courseName, courseCode, descr, format, "W2020", listMap));
                // puts the course in the course map with the list of students in the course
                if (i != 4) {
                    listMap.clear();// clears the list
                }
            }
        }
    }

    /**
     * Add new student to the registry (students arraylist above)
     *
     * @return if student is added or not
     */
    public boolean addNewStudent(String name, String id) {
        boolean add = false;
        if (!students.containsKey(id)) {// checks if the student with the id is not in the students map
            Student student = new Student(name, id);// Creates a new Student object with the given name and id
            students.put(id, student);
            add = true;
        }
        return add;
    }

    /**
     * Remove student from registry
     *
     * @return if the student is removed or not
     */
    public boolean removeStudent(String studentId) {
        if (students.containsKey(studentId)) {
            students.remove(studentId);
            return true;
        }
        return false;
    }

    // Print all registered students name and id
    public void printAllStudents() {
        for (Map.Entry<String, Student> entry : students.entrySet()) {// iterates through each entry in the map
            System.out.println("ID: " + entry.getValue().getId() + " Name: " + entry.getValue().getName());
            // prints all the students
        }
    }

    /**
     * adds a course to a students list of courses adds student to the course's list
     * of students
     *
     * @param studentId
     * @param courseCode
     */
    public void addCourse(String studentId, String courseCode) {
        if (students.containsKey(studentId)) {// if the student is in the treemap
            Student s = students.get(studentId);
            for (int j = 0; j <= s.courses.size() - 1; j++) {// checks to see if they have taken the course before
                CreditCourse c = s.courses.get(j);
                if ((c.getCode().equalsIgnoreCase(courseCode))) {// if they did, do nothing hence the return with
                    // do nothing
                    return;
                }
            }
            if (courses.containsKey(courseCode)) {// if they have not taken it before see if the courses has the code
                ActiveCourse a = courses.get(courseCode.toLowerCase());
                a.addStudent(s);// add the student to the course
                s.addCourse(a.getName(), a.getCode(), a.getDesricpt(), a.getFormat(), a.getSemester(), 0.0);
                // add the course to the student's list of courses
            }
            // makes a new credit course object with the parameter of the active course and
            // grade of 0
        }
    }

    /**
     * Given a student id and a course, drop the student from the course and remove
     * the course from the student's list of courses
     */
    public void dropCourse(String studentId, String courseCode) {
        // Find the active course
        // Find the student in the list of students for this course
        // If student found:
        // remove the student from the active course
        // remove the credit course from the student's list of credit courses
        if (students.containsKey(studentId) && courses.containsKey(courseCode)) {
            Student s = students.get(studentId);
            ActiveCourse a = courses.get(courseCode);
            a.removeStudent(studentId);
            s.removeActiveCourse(courseCode);
        }
    }

    /**
     * Prints all active courses
     */
    public void printActiveCourses() {
        for (Map.Entry<String, ActiveCourse> entry : courses.entrySet()) {// prints all active courses in the registry
            System.out.println(entry.getValue().getDescription() + "\n");
        }
    }

    /**
     * Print the list of students in an active course
     */
    public void printClassList(String courseCode) {
        if (courses.containsKey(courseCode)) {
            ActiveCourse a = courses.get(courseCode);
            a.printClassList();// prints the class list
        }
    }

    /**
     * Given a course code, find course and sort class list by student name
     */
    public void sortCourseByName(String courseCode) {
        if (courses.containsKey(courseCode)) {
            ActiveCourse a = courses.get(courseCode);
            a.sortByName();// sorts the active course class list by name
        }
    }

    // same as above but with id
    public void sortCourseById(String courseCode) {
        if (courses.containsKey(courseCode)) {
            ActiveCourse a = courses.get(courseCode);
            a.sortById();
        }
    }

    /**
     * Given a course code, find course and print student names and grades
     */
    public void printGrades(String courseCode) {
        if (courses.containsKey(courseCode)) {
            ActiveCourse a = courses.get(courseCode);
            a.printGrades();// prints the grades of all students in the active course
        }
    }

    /**
     * Given a studentId, print all active courses of student
     */
    public void printStudentCourses(String studentId) {
        if (students.containsKey(studentId)) {
            Student s = students.get(studentId);
            for (int j = 0; j <= s.courses.size() - 1; j++) {// if student found, go through the student's courses
                CreditCourse c = s.courses.get(j);
                if (c.getActive()) {// if course is active
                    System.out.println(c.getDescription() + "\n");// get its description
                }
            }
        }
    }

    /**
     * Given a studentId, print all completed courses and grades of student
     */
    public void printStudentTranscript(String studentId) {
        if (students.containsKey(studentId)) {
            Student s = students.get(studentId);
            s.printTranscript();// print its transcript
        }
    }

    /**
     * Given a course code, student id and numeric grade set the final grade of the
     * student
     *
     * @param courseCode
     * @param studentId
     * @param grade
     */
    public void setFinalGrade(String courseCode, String studentId, double grade) {
        if (courses.containsKey(courseCode)) {
            ActiveCourse a = courses.get(courseCode);
            for (int j = 0; j <= a.numberOfStudents() - 1; j++) {// then search student credit course list in
                // student object and find course
                Student s = a.getStudent(j);
                if (s.getId().equals(studentId)) {
                    s.setInactiveGrade(courseCode, grade);// set the grade in credit course and set credit course
                    // inactive
                }
            }
        }
    }

    /**
     * @return the course map (useful for scheduler)
     */
    public TreeMap<String, ActiveCourse> getCourses() {
        return courses;
    }
}