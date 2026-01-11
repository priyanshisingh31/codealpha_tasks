import java.util.ArrayList;
import java.util.Scanner;

// Student class
class Student {
    String name;
    int marks;

    // Constructor
    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

// Main class
public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine(); // clear buffer

        // Input student details
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details of student " + (i + 1));

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Marks: ");
            int marks = sc.nextInt();
            sc.nextLine();

            students.add(new Student(name, marks));
        }

        // Initialize calculations
        int total = 0;
        int highest = students.get(0).marks;
        int lowest = students.get(0).marks;

        // Calculate average, highest and lowest
        for (Student s : students) {
            total += s.marks;

            if (s.marks > highest)
                highest = s.marks;

            if (s.marks < lowest)
                lowest = s.marks;
        }

        double average = (double) total / students.size();

        // Summary Report
        System.out.println("\n================ STUDENT SUMMARY REPORT ================");
        System.out.println("Name\t\tMarks");
        System.out.println("--------------------------------------------------------");

        for (Student s : students) {
            System.out.println(s.name + "\t\t" + s.marks);
        }

        System.out.println("--------------------------------------------------------");
        System.out.println("Average Marks : " + average);
        System.out.println("Highest Marks : " + highest);
        System.out.println("Lowest Marks  : " + lowest);
        System.out.println("========================================================");

        sc.close();
    }
}
