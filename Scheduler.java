import java.util.*;
import java.util.TreeMap;
import java.util.Map;

public class Scheduler {

    TreeMap<String, ActiveCourse> schedule;
    String[][] week = new String[6][12];
    /**
     * Sets the schedule treemap to the courses treemap passed by registry in SRS
     * 
     * @param courses
     */
    public Scheduler(TreeMap<String, ActiveCourse> courses) {
        schedule = courses;
        String[] days = { "MON", "TUE", "WED", "THUR", "FRI" };// days is used for getting the correct day
        week[0][0] = "";// sets the top left corner of the 2d array to blank
        for (int i = 1; i < 6; i++) {// sets the rest of the top row to the days of the week
            week[i][0] = days[i - 1];// references the days array
        }
        for (int i = 1; i < 12; i++) {// sets the rest of the left column to the hours, starting with 800 and increasing by 100
            week[0][i] = String.valueOf(800 + (100 * (i - 1)));
        }
        for (int i = 0; i < 12; i++) {// this block of code sets anything that is null in the array to ""
            for (int j = 0; j < 6; j++) {
                if (week[j][i] == null)
                    week[j][i] = "";
            }
        }
    }

    /**
     * Sets the time slot for where the course should go, all parameters are heavily
     * checked to ensure there are no conflicts or misspelling
     * 
     * @param courseCode
     * @param day
     * @param startTime
     * @param duration
     * @throws UnknownCourseException
     * @throws InvalidDayException
     * @throws InvalidTimeException
     * @throws InvalidDurationException
     * @throws LectureTimeCollisionException
     */
    public void setDayAndTime(String courseCode, String day, int startTime, int duration) throws UnknownCourseException,
            InvalidDayException, InvalidTimeException, InvalidDurationException, LectureTimeCollisionException {
        String message = "";// message for any exception (if called)
        boolean invalidDay = true, invalidStart, invalidEnd;
        String[] days = { "mon", "tue", "wed", "thur", "fri" };
        if (!schedule.containsKey(courseCode.toLowerCase())) {// if the given course code is not in the map
            message = "The given course code could not be found: " + courseCode;
            throw new UnknownCourseException(message);// throw the UnknownCourseException
        }
        for (String tempDay : days) {// for each day in days
            if (tempDay.equalsIgnoreCase(day)) {// if the incoming day is equal to any of the days in the week
                invalidDay = false;// sets to false
            } else {
                message = "The day should be a work week day!";// sets this as the message
            }
        }
        if (invalidDay) {// only throw InvalidDayException if the day is not one of the work week days
            throw new InvalidDayException(message);
        }
        if ((startTime % 100 != 0) || (startTime < 800 || startTime > 1700)) {// checks if the start time is after 800 hrs and before 1700 hrs and also starts on the hour
            message = "The starting time must be between 800 hours to 1700 hours and must be at the hour";
            throw new InvalidTimeException(message);// throws InvalidTimeException if this is not true
        }
        if (!((duration == 1) || (duration == 2) || (duration == 3))) {// if the duration of the lecture is not 1 2 or 3 hours
            message = "The duration should only be between 1 to 3 hours";
            throw new InvalidDurationException(message);// throw InvalidDurationException
        }
        for (Map.Entry<String, ActiveCourse> entry : schedule.entrySet()) {// this block checks if the given course will conflict with any other course
            if (entry.getValue().getDay().equalsIgnoreCase(day)) {
                ActiveCourse a = entry.getValue();
                invalidStart = (startTime >= a.getStart()) && (startTime < (a.getStart() + (100 * a.getDuration())));
                // if the given course's start time is inside the slot of time that any other
                // course is taking up, set it to false
                invalidEnd = ((startTime + (100 * duration)) > a.getStart())
                        && ((startTime + (100 * duration)) <= (a.getStart() + (100 * a.getDuration())));
                // if the given course's end time is inside the slot of time that any other
                // course is taking up, set it to false
                if (invalidStart || invalidEnd) {// if either are false
                    message = "The course overlaps with course: " + a.getCode();
                    throw new LectureTimeCollisionException(message);// throw the LectureTimeCollisionException
                }
            }
        }
        schedule.get(courseCode.toLowerCase()).setDayAndTime(day, startTime, duration);
        // if none of these exceptions are executed, the time day and duration are
        // correct so it is ok to set them
    }
    public void multipleBlocks(String courseCode, String day1, int startTime1,
    int duration1, String day2, int startTime2, int duration2){

    }
    public void automatic(String courseCode, int duration) throws UnknownCourseException, InvalidDurationException{

    }
    /**
     * Clears the values associated with start time, duration and day
     * 
     * @param courseCode
     */
    public void clearSchedule(String courseCode) {
        if (schedule.containsKey(courseCode.toLowerCase())) {
            schedule.get(courseCode.toLowerCase()).setDayAndTime("", 0, 0);
        }
    }

    /**
     * Prints the schedule
     */
    public void printSchedule() {
        // see assignment doc
        String[][] schoolWeek = new String[6][12];// this is the 2d array that will be printed to console
        String[] days = { "MON", "TUE", "WED", "THUR", "FRI" };// days is used for getting the correct day
        int column, row;// column and row for indexing
        String format = "%-6s%-6s%-6s%-6s%-6s%-6s%n";// formatting string so it formats it properly, essentially 6 blocks of space for 6 characters followed by a newline character
        schoolWeek[0][0] = "";// sets the top left corner of the 2d array to blank
        for (int i = 1; i < 6; i++) {// sets the rest of the top row to the days of the week
            schoolWeek[i][0] = days[i - 1];// references the days array
        }
        for (int i = 1; i < 12; i++) {// sets the rest of the left column to the hours, starting with 800 and
                                      // increasing by 100
            schoolWeek[0][i] = String.valueOf(800 + (100 * (i - 1)));
        }
        for (Map.Entry<String, ActiveCourse> entry : schedule.entrySet()) {// this block of code actually puts the course where it needs to go in the week calendar
            ActiveCourse a = entry.getValue();// temp active course
            for (String day : days) {// for each day in days
                if (a.getDay().equalsIgnoreCase(day)) {// if the day is the same as the scheduled day of the course
                    column = (Arrays.asList(days).indexOf(day)) + 1;// the column is the index of the day+1
                    row = Math.floorDiv((a.getStart() - 800), 100) + 1;// the row is the floor division of the ((start time - start of day) and 100) + 1
                    for (int i = 0; i < a.getDuration(); i++) {// this loops for however long the duration of the course is
                        if (a.getCode().equalsIgnoreCase("CPS209")) {// if course code is CPS209 (same for others)
                            // 2d array at column and row+1 is the string below, this process is repeated for the duration course each time
                            schoolWeek[column][row + i] = "\u001B[41m" + a.getCode() + "\u001B[0m";// sets cps209 to a red highlight using ansi escape
                        }
                        if (a.getCode().equalsIgnoreCase("CPS511")) {
                            schoolWeek[column][row + i] = "\u001B[42m" + a.getCode() + "\u001B[0m";// sets cps511 to green
                        }
                        if (a.getCode().equalsIgnoreCase("CPS643")) {
                            schoolWeek[column][row + i] = "\u001B[44m" + a.getCode() + "\u001B[0m";// sets cps643 to blue
                        }
                        if (a.getCode().equalsIgnoreCase("CPS706")) {
                            schoolWeek[column][row + i] = "\u001B[43m" + a.getCode() + "\u001B[0m";// sets cps706 to yellow
                        }
                        if (a.getCode().equalsIgnoreCase("CPS616")) {
                            schoolWeek[column][row + i] = "\u001B[45m" + a.getCode() + "\u001B[0m";// sets cps616 to purple
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 12; i++) {// this block of code sets anything that is null in the array to ""
            for (int j = 0; j < 6; j++) {
                if (schoolWeek[j][i] == null)
                    schoolWeek[j][i] = "";
            }
        }
        for (int k = 0; k < 12; k++) {// this block of code formats the 2d array line by line
            System.out.format(format, schoolWeek[0][k], schoolWeek[1][k], schoolWeek[2][k], schoolWeek[3][k],
                    schoolWeek[4][k], schoolWeek[5][k]);
            // .format takes the schoolWeek[][] and formats it so that it is correct
        }
    }

}

class UnknownCourseException extends Exception {// these next classes are used for the method setDayAndTime
    public UnknownCourseException(String message) {// their constructors pass in the message to the super method
        super(message);
    }
}

class InvalidDayException extends Exception {
    public InvalidDayException(String message) {
        super(message);
    }
}

class InvalidTimeException extends Exception {
    public InvalidTimeException(String message) {
        super(message);
    }
}

class InvalidDurationException extends Exception {
    public InvalidDurationException(String message) {
        super(message);
    }
}

class LectureTimeCollisionException extends Exception {
    public LectureTimeCollisionException(String message) {
        super(message);
    }
}