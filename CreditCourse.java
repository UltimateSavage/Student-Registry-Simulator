public class CreditCourse extends Course {

    private String semester;
    public double grade = 0;
    public boolean active = true;


    /**
     * Constructor which calls superclass to initialize inherited variables, as well
     * initializes some here as well
     *
     * @param name
     * @param code
     * @param descr
     * @param fmt
     * @param semester
     * @param grade
     */
    public CreditCourse(String name, String code, String descr, String fmt, String semester, double grade) {
        super(name, code, descr, fmt);
        this.semester = semester;
        this.grade = grade;
    }

    /**
     * @return boolean if the course is active or not
     */
    public boolean getActive() {
        return active;
    }

    /**
     * sets the course to active
     */
    public void setActive() {
        this.active = true;
    }

    /**
     * sets the course to inactive
     */
    public void setInactive() {
        this.active = false;
    }

    /**
     * @return inherited info + the semester and the grade student got in the course
     */
    public String displayGrade() {
        return getInfo() + " " + semester + " " + convertNumericGrade(Double.valueOf(getGrade()));
    }

    /**
     * Helps access the grades in files where it is not possible, like student
     *
     * @return returns the grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Helps access the semester in files where it is not possible, like student
     *
     * @return returns the semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Sets the grade based on a given grade
     *
     * @param grade
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }
}