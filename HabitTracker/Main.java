import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HabitTracker tracker = new HabitTracker();
        
        while(true){
            System.out.println("\nHabit Tracker Menu:");
            System.out.println("1. Add a New Habit");
            System.out.println("2. View Habits");
            System.out.println("3. Mark Habit as Completed");
            System.out.println("4. Delete a Habit");
            System.out.println("5. Reset Progress");
            System.out.println("6. Exit");
            System.out.println("Choose an Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter habit name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter goal (daily/weekly/monthly)");
                    int goal = scanner.nextInt();
                    tracker.addHabit(name, goal);
                }
                case 2 -> tracker.viewHabits();
                case 3 -> {
                    tracker.viewHabits();
                    System.out.println("Enter the index number of the habit to mark as completed: ");
                    int habitNumber = scanner.nextInt();
                    tracker.markHabitAsCompleted(habitNumber);
                }
                case 4 -> {
                    tracker.viewHabits();
                    int deleteNumber = scanner.nextInt();
                    tracker.deleteHabit(deleteNumber);
                }
                case 5 -> {
                    tracker.viewHabits();
                    System.out.println("Enter the number of the habit to reset progress (0 to reset all): ");
                    int resetIndex = scanner.nextInt();
                    tracker.resetProgress(resetIndex);
                }
                case 6 -> {
                    System.out.println("Exiting Habit Tracker. Bye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid Option. Please try again.");
            }
        }
    }
}
