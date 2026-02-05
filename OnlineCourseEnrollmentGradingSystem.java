

 
 import java.util.*;


public class OnlineCourseEnrollmentGradingSystem {

    static class Student {
        private final int id;
        private String name;
       
        private final Map<Integer, Double> courseMarks = new HashMap<>(); // no understand 

        Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        int getId() {
		   return id;
		}
        String getName() { 
		   return name; 
		}
        void setName(String name) {
			this.name = name; 
			}

        void enroll(int courseId){
            courseMarks.putIfAbsent(courseId, null);
        }

        boolean isEnrolled(int courseId) {
            return courseMarks.containsKey(courseId);
        }

        void setMarks(int courseId, double marks) {
            if (!courseMarks.containsKey(courseId)) throw new IllegalStateException("Not enrolled in course " + courseId);
            courseMarks.put(courseId, marks);
        }

        Double getMarks(int courseId) {
            return courseMarks.get(courseId);
        }

        Map<Integer, Double> getCourseMarks() {
            return Collections.unmodifiableMap(courseMarks);
        }

        @Override
        public String toString() {
            return id + " - " + name;
        }
    }

    static class Course {
        private final int id;
        private final String name;
        private final double maxMarks; // usually 100
    
        Course(int id, String name, double maxMarks) {
            this.id = id;
            this.name = name;
            this.maxMarks = maxMarks;
        }

        int getId() { 
		return id; 
		}
        String getName() { 
		return name; 
		}
        double getMaxMarks() {
			return maxMarks;
			}

       @Override
        public String toString() {
            return id + " - " + name + " (Max: " + maxMarks + ")";
        }
    }

    // Manager data stores
    private final Map<Integer, Student> students = new HashMap<>();
    private final Map<Integer, Course> courses = new HashMap<>();
    private final Scanner sc = new Scanner(System.in);

    // ID generators (simple incremental)
    private int nextStudentId = 1;
    private int nextCourseId = 1;

    public static void main(String[] args) {
        OnlineCourseEnrollmentGradingSystem app = new OnlineCourseEnrollmentGradingSystem();
        app.seedDemoData(); // optional: seeds sample students/courses
        app.run();
    }

    private void run() {
        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": registerStudent(); break;
                case "2": addCourse(); break;
                case "3": enrollStudentInCourse(); break;
                case "4": recordOrUpdateMarks(); break;
                case "5": generateGradeReport(); break;
                case "6": listStudents(); break;
                case "7": listCourses(); break;
                case "8": System.out.println("Exiting. Goodbye!"); return;
                default: System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Online Course Enrollment & Grading System ===");
        System.out.println("1. Register Student");
        System.out.println("2. Add Course");
        System.out.println("3. Enroll Student in Course");
        System.out.println("4. Record / Update Marks");
        System.out.println("5. Generate Grade Report for Student");
        System.out.println("6. List Students");
        System.out.println("7. List Courses");
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
    }

    private void registerStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name can't be empty.");
            return;
        }
        Student s = new Student(nextStudentId++, name);
        students.put(s.getId(), s);
        System.out.println("Registered student: " + s);
    }

    private void addCourse() {
        System.out.print("Enter course name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter maximum marks (e.g., 100): ");
        String mmStr = sc.nextLine().trim();
        double maxMarks;
        try {
            maxMarks = Double.parseDouble(mmStr);
        }
		catch (NumberFormatException e) {
            System.out.println("Invalid number for max marks.");
            return;
        }
        Course c = new Course(nextCourseId++, name, maxMarks);
        courses.put(c.getId(), c);
        System.out.println("Added course: " + c);
    }

    private void enrollStudentInCourse() {
        Student s = selectStudent();
        if (s == null) return;
        Course c = selectCourse();
        if (c == null) return;
        if (s.isEnrolled(c.getId())) {
            System.out.println("Student already enrolled in this course.");
            return;
        }
        s.enroll(c.getId());
        System.out.println("Enrolled " + s.getName() + " in " + c.getName());
    }

    private void recordOrUpdateMarks() {
        Student s = selectStudent();
        if (s == null) return;
        Course c = selectCourse();
        if (c == null) return;
        if (!s.isEnrolled(c.getId())) {
            System.out.println("Student is not enrolled in selected course. Enroll first.");
            return;
        }
        System.out.print("Enter marks obtained (max " + c.getMaxMarks() + "): ");
        String marksStr = sc.nextLine().trim();
        double marks;
        try {
            marks = Double.parseDouble(marksStr);
        } 
		catch (NumberFormatException e) {
            System.out.println("Invalid number for marks.");
            return;
        }
        if (marks < 0 || marks > c.getMaxMarks()) {
            System.out.println("Marks must be between 0 and " + c.getMaxMarks());
            return;
        }
        s.setMarks(c.getId(), marks);
        System.out.println("Recorded marks: " + marks + " for " + s.getName() + " in " + c.getName());
    }

    private void generateGradeReport() {
        Student s = selectStudent();
        if (s == null) return;
        Map<Integer, Double> cm = s.getCourseMarks();
        if (cm.isEmpty()) {
            System.out.println("No courses enrolled for student.");
            return;
        }
        System.out.println("\n--- Grade Report for " + s.getName() + " (ID: " + s.getId() + ") ---");
        double totalPercentSum = 0;
        int countedCourses = 0;
        for (Map.Entry<Integer, Double> e : cm.entrySet()) {
            Course course = courses.get(e.getKey());
            if (course == null) continue; // skip if course removed
            Double marks = e.getValue();
            String marksStr = (marks == null) ? "Not entered" : String.format("%.2f", marks);
            String percentStr = "-";
            String grade = "-";
            if (marks != null) {
                double percent = (marks / course.getMaxMarks()) * 100.0;
                percentStr = String.format("%.2f%%", percent);
                grade = letterGrade(percent);
                totalPercentSum += percent;
                countedCourses++;
            }
            System.out.printf("%s | Marks: %s | %s | Grade: %s\n", course.getName(), marksStr, percentStr, grade);
        }
        if (countedCourses > 0) {
            double avgPercent = totalPercentSum / countedCourses;
            System.out.printf("Average Percentage: %.2f%% | Overall Grade: %s\n", avgPercent, letterGrade(avgPercent));
        } 
		else {
            System.out.println("No marks recorded yet for any course.");
        }
        System.out.println("--- End of Report ---");
    }

    private void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("Students:");
        students.values().stream()
                .sorted(Comparator.comparingInt(Student::getId))
                .forEach(s -> System.out.println(s));
    }

    private void listCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses added.");
            return;
        }
        System.out.println("Courses:");
        courses.values().stream()
                .sorted(Comparator.comparingInt(Course::getId))
                .forEach(c -> System.out.println(c));
    }

    // Helper: choose a student by id or show list
    private Student selectStudent() {
        if (students.isEmpty()) {
            System.out.println("No students registered. Register first.");
            return null;
        }
        listStudents();
        System.out.print("Enter student ID: ");
        String idStr = sc.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } 
		catch (NumberFormatException e) {
            System.out.println("Invalid student ID.");
            return null;
        }
        Student s = students.get(id);
        if (s == null) {
            System.out.println("Student with ID " + id + " not found.");
            return null;
        }
        return s;
    }

    // Helper: choose a course by id or show list
    private Course selectCourse() {
        if (courses.isEmpty()) {
            System.out.println("No courses added. Add course first.");
            return null;
        }
        listCourses();
        System.out.print("Enter course ID: ");
        String idStr = sc.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } 
		catch (NumberFormatException e) {
            System.out.println("Invalid course ID.");
            return null;
        }
        Course c = courses.get(id);
        if (c == null) {
            System.out.println("Course with ID " + id + " not found.");
            return null;
        }
        return c;
    }

    // Grading scale (you can modify)
    private static String letterGrade(double percent) {
        if (percent >= 90) return "A";
        if (percent >= 80) return "B";
        if (percent >= 70) return "C";
        if (percent >= 60) return "D";
        return "F";
    }

    // Seed sample data for quick testing (optional)
    private void seedDemoData() {
        Course c1 = new Course(nextCourseId++, "Introduction to Java", 100);
        Course c2 = new Course(nextCourseId++, "Database Systems", 100);
        Course c3 = new Course(nextCourseId++, "Web Development", 100);
        courses.put(c1.getId(), c1);
        courses.put(c2.getId(), c2);
        courses.put(c3.getId(), c3);

        Student s1 = new Student(nextStudentId++, "Navshal Mathakiya");
        Student s2 = new Student(nextStudentId++, "Aisha Sharma");
        students.put(s1.getId(), s1);
        students.put(s2.getId(), s2);

 
        s1.enroll(c1.getId());
        s1.enroll(c2.getId());
        s1.setMarks(c1.getId(), 85);
        s1.setMarks(c2.getId(), 78);


        s2.enroll(c3.getId());

    }
}
