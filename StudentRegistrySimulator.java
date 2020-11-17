import java.util.Scanner;
import java.io.*;


public class StudentRegistrySimulator {
    public static void main(String[] args) {
        try {
            Registry registry = new Registry();// makes a new reigstry object
            Scheduler scheduler = new Scheduler(registry.getCourses());
            File file = new File("inputs.txt");
            try {
                // Scanner scanner = new Scanner(System.in);
                Scanner scanner = new Scanner(file);// makes a new scanner object
                System.out.print(">");
                String name = null, code = null, id = null, grade = null, day = null, start = null, duration = null;// sets these strings to null
                while (scanner.hasNextLine()) {// loops until the user quits
                    String inputLine = scanner.nextLine();
                    // this whole block of code checks if the first word
                    // of the input is a keyword such as l or q or reg etc
                    if (inputLine == null || inputLine.equals("")) {
                        continue;
                    }
                    System.out.print(inputLine + "\n");
                    Scanner commandLine = new Scanner(inputLine);
                    String command = commandLine.next();
                    if (command == null || command.equals("")) {
                        continue;
                    } else if (command.equalsIgnoreCase("L") || command.equalsIgnoreCase("LIST")) {
                        registry.printAllStudents();// prints all students
                    } else if (command.equalsIgnoreCase("Q") || command.equalsIgnoreCase("QUIT")) {
                        commandLine.close();// closes the commandLine
                        return;// quits the program
                    } else if (command.equalsIgnoreCase("REG")) {
                        /**
                         * Register a new student in the registry gets the name and student id using
                         * commandLine.next() also checks if strings are as they should be (alphabetical
                         * or numerical)
                         */
                        if (commandLine.hasNext()) {
                            name = commandLine.next();
                        }
                        if (commandLine.hasNext()) {
                            id = commandLine.next();
                            if (!isStringOnlyAlphabet(name)) {
                                System.out.println("Invalid Characters in name " + name);
                            }
                            if (!isNumeric(id)) {
                                System.out.println("Invalid Characters in id " + id);
                            }
                            if (isStringOnlyAlphabet(name) && isNumeric(id)) {
                                if (!registry.addNewStudent(name, id))// if the student is already registered
                                {
                                    System.out.println("Student " + name + " already registered");// print this
                                }
                            }
                        }
                    } else if (command.equalsIgnoreCase("DEL")) {
                        /**
                         * delete a student from registry get student id ensure numeric remove student
                         * from registry
                         */
                        if (commandLine.hasNext()) {
                            id = commandLine.next();
                            if (isNumeric(id)) {
                                registry.removeStudent(id);
                            } else {
                                System.out.println("Invalid Characters in id " + id);
                            }
                        }
                    } else if (command.equalsIgnoreCase("ADDC")) {
                        /**
                         * add a student to an active course get student id and course code strings
                         * ensure they are numeric and alphanumeric add student to course
                         */
                        if (commandLine.hasNext()) {
                            id = commandLine.next();
                        }
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (isNumeric(id) && isAlphaNumeric(code)) {
                                registry.addCourse(id, code);
                            } else if (!isNumeric(id)) {
                                System.out.println("Invalid Characters in id " + id);
                            } else if (!isAlphaNumeric(code)) {
                                System.out.println("Invalid Characters in code " + code);
                            }
                        }
                    } else if (command.equalsIgnoreCase("DROPC")) {
                        /**
                         * get student id and course code strings (ensure they are correct) drop student
                         * from course
                         */
                        if (commandLine.hasNext()) {
                            id = commandLine.next();
                        }
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (isNumeric(id) && isAlphaNumeric(code)) {
                                registry.dropCourse(id, code);
                            } else if (!isNumeric(id)) {
                                System.out.println("Invalid Characters in id " + id);
                            } else if (!isAlphaNumeric(code)) {
                                System.out.println("Invalid Characters in code " + code);
                            }
                        }
                    } else if (command.equalsIgnoreCase("PAC")) {
                        // print all active courses
                        registry.printActiveCourses();
                    } else if (command.equalsIgnoreCase("PCL")) {
                        /**
                         * get course code string (ensure they are correct) print class list
                         */
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (isAlphaNumeric(code)) {
                                registry.printClassList(code);
                            } else {
                                System.out.println("Invalid Characters in code " + code);
                            }
                        }
                    } else if (command.equalsIgnoreCase("PGR")) {
                        /**
                         * get course code string (ensure they are correct) print name, id and grade of
                         * all students in active course
                         */
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (isAlphaNumeric(code)) {
                                registry.printGrades(code);
                            } else {
                                System.out.println("Invalid Characters in code " + code);
                            }
                        }
                    } else if (command.equalsIgnoreCase("PSC")) {
                        /**
                         * get student id string (ensure they are correct) print all credit courses of
                         * student
                         */
                        if (commandLine.hasNext()) {
                            id = commandLine.next();
                            if (isNumeric(id)) {
                                registry.printStudentCourses(id);
                            } else {
                                System.out.println("Invalid Characters in id " + id);
                            }
                        }
                    } else if (command.equalsIgnoreCase("PST")) {
                        /**
                         * get student id string (ensure they are correct) print student transcript
                         */
                        if (commandLine.hasNext()) {
                            id = commandLine.next();
                            if (isNumeric(id)) {
                                registry.printStudentTranscript(id);
                            } else {
                                System.out.println("Invalid Characters in id " + id);
                            }
                        }
                    } else if (command.equalsIgnoreCase("SFG")) {
                        /**
                         * set final grade of student get course code, student id, numeric grade (ensure
                         * they are correct)
                         */
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (!isAlphaNumeric(code)) {
                                System.out.println("Invalid Characters in code " + code);
                            }
                        }
                        if (commandLine.hasNext()) {
                            id = commandLine.next();
                            if (!isNumeric(id)) {
                                System.out.println("Invalid Characters in id " + id);
                            }
                        }
                        if (commandLine.hasNext()) {
                            grade = commandLine.next();
                            if (isNumeric(id) && isNumeric(grade)) {
                                registry.setFinalGrade(code, id, Double.valueOf(grade));
                            } else if (!isNumeric(grade)) {
                                System.out.println("Invalid Characters in grade " + grade);
                            }
                        }
                    } else if (command.equalsIgnoreCase("SCN")) {
                        /**
                         * get course code (ensure it is correct) sort list of students in course by
                         * name (i.e. alphabetically)
                         */
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (isAlphaNumeric(code)) {
                                registry.sortCourseByName(code);
                            } else {
                                System.out.println("Invalid Characters in code " + code);
                            }
                        }

                    } else if (command.equalsIgnoreCase("SCI")) {
                        /**
                         * get course code sort list of students in course by student id
                         */
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (isAlphaNumeric(code)) {
                                registry.sortCourseById(code);
                            } else {
                                System.out.println("Invalid Characters in code " + code);
                            }
                        }
                    } else if (command.equalsIgnoreCase("SCH")) {
                        /**
                         * get course code
                         * get day
                         * get start time
                         * get duration
                         * ensure they are all correct
                         * otherwise catch the corresponding exception
                         */
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (!isAlphaNumeric(code)) {
                                System.out.println("Invalid characters in code " + code);
                            }
                        }
                        if (commandLine.hasNext()) {
                            day = commandLine.next();
                            if (!isStringOnlyAlphabet(day)) {
                                System.out.println("Invalid characters in day " + day);
                            }
                        }
                        if (commandLine.hasNext()) {
                            start = commandLine.next();
                            if (!isNumeric(start)) {
                                System.out.println("Invalid characters in start time " + start);
                            }
                        }
                        if (commandLine.hasNext()) {
                            duration = commandLine.next();
                            if (isAlphaNumeric(code) && isStringOnlyAlphabet(day) && isNumeric(start)) {
                                try {
                                    scheduler.setDayAndTime(code, day, Integer.valueOf(start),
                                            Integer.valueOf(duration));
                                } catch (UnknownCourseException ex) {
                                    ex.printStackTrace();
                                } catch (InvalidDayException ex) {
                                    ex.printStackTrace();
                                } catch (InvalidTimeException ex) {
                                    ex.printStackTrace();
                                } catch (InvalidDurationException ex) {
                                    ex.printStackTrace();
                                } catch (LectureTimeCollisionException ex) {
                                    ex.printStackTrace();
                                }
                            } else if (!isNumeric(duration)) {
                                System.out.println("Invalid characters in duration " + duration);
                            }
                        }
                    } else if (command.equalsIgnoreCase("CSCH")) {
                        /**
                         * get course code
                         * ensure it is correct
                         * if so clear it
                         */
                        if (commandLine.hasNext()) {
                            code = commandLine.next();
                            if (!isAlphaNumeric(code)) {
                                System.out.println("Invalid characters in code " + code);
                            } else
                                scheduler.clearSchedule(code);
                        }
                    } else if (command.equalsIgnoreCase("PSCH")) {
                        //print the schedule
                        scheduler.printSchedule();
                    }
                    System.out.print("\n>");
                }
                scanner.close();// closes scanner
            } catch (FileNotFoundException f) {//catch for inputs from file
                f.printStackTrace();
            }
        } catch (IOException e) {//catch for registry
            e.printStackTrace();
        }
    }

    /**
     * Checks if the string only contains letters
     *
     * @param str
     * @return if the string is only letters
     */
    private static boolean isStringOnlyAlphabet(String str) {
        String regex = "[a-zA-Z]+";
        return str.matches(regex);
    }

    // same as above but for digits
    public static boolean isNumeric(String str) {
        String regex = "\\d+";
        return str.matches(regex);
    }

    // same as above, but both letters and numbers
    public static boolean isAlphaNumeric(String str) {
        String regex = "^[a-zA-Z0-9]+$";
        return str.matches(regex);
    }
}
