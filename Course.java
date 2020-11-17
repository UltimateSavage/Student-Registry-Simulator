public class Course {

    private String code;
    private String name;
    private String description;
    private String format;

    /**
     * Default Constructor for Course, Credit and Active Course
     */
    public Course() {
        this.code = "";
        this.name = "";
        this.description = "";
        this.format = "";
    }

    /**
     * Sets the param according to the conditions
     *
     * @param name
     * @param code
     * @param descr
     * @param fmt
     */
    public Course(String name, String code, String descr, String fmt) {
        this.code = code;
        this.name = name;
        this.description = descr;
        this.format = fmt;
    }

    /**
     * @return course code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return course name
     */
    public String getName() {
        return name;
    }

    /**
     * @return course format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @return course description
     */
    public String getDescription() {
        return code + " - " + name + "\n" + description + "\n" + format;
    }

    /**
     * @return course info
     */
    public String getInfo() {
        return code + " - " + name;
    }

    /**
     * @return only the description
     */
    public String getDesricpt() {
        return description;
    }

    /**
     * Converts number grade to letter grade
     *
     * @param score
     * @return letter grade associated with numeric grade
     */
    public static String convertNumericGrade(double score) {
        if (score >= 90) return "A+";
        if (score >= 85) return "A";
        if (score >= 80) return "A-";
        if (score >= 77) return "B+";
        if (score >= 73) return "B";
        if (score >= 70) return "B-";
        if (score >= 67) return "C+";
        if (score >= 63) return "C";
        if (score >= 60) return "C-";
        if (score >= 57) return "D+";
        if (score >= 53) return "D";
        if (score >= 50) return "D-";
        return "F";
    }
}