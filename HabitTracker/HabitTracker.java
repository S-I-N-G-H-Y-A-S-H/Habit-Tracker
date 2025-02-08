import java.sql.*;
import java.util.ArrayList;

public class HabitTracker {
    private final ArrayList<Habit> habits;
    private static final String DB_URL = "jdbc:derby://localhost:1527/HabitTrackerDB;user=APP;password=APP";
    private static final String USER = "APP";  // Default Derby username
    private static final String PASSWORD = "APP";  // Default Derby password
    
    // Constructor
    public HabitTracker() {
        habits = new ArrayList<>();
        loadHabitsFromDatabase();
    }

    // Add a new habit to the database
    public void addHabit(String name, int goal) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String query = "INSERT INTO habits (name, goal) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setInt(2, goal);
                stmt.executeUpdate();
            }
            System.out.println("Habit added successfully!");
            loadHabitsFromDatabase();  // Reload the habits from DB to reflect changes
        } catch (SQLException e) {
            System.out.println("Error adding habit: " + e.getMessage());
        }
    }

    // View all habits from the database
    public void viewHabits() {
        if (habits.isEmpty()) {
            System.out.println("No habits added yet.");
        } else {
            System.out.println("Your Habits:");
            for (int i = 0; i < habits.size(); i++) {
                System.out.println((i + 1) + ". " + habits.get(i).toString());
            }
        }
    }

    // Mark a habit as completed and update the progress in the database
    public void markHabitAsCompleted(int index) {
        if (index >= 1 && index <= habits.size()) {
            Habit habit = habits.get(index - 1);
            habit.markAsCompleted();
            updateHabitProgress(habit);  // Update progress in the DB
            System.out.println("Progress updated!");
        } else {
            System.out.println("Invalid habit number!");
        }
    }

    // Delete a habit from the database
    public void deleteHabit(int index) {
        if (index >= 1 && index <= habits.size()) {
            Habit habit = habits.get(index - 1);
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                String query = "DELETE FROM habits WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, habit.getId());
                    stmt.executeUpdate();
                }
                System.out.println("Habit \"" + habit.getName() + "\" deleted successfully!");
                habits.remove(index - 1);  // Remove habit from the list
            } catch (SQLException e) {
                System.out.println("Error deleting habit: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid habit number!");
        }
    }

    // Reset progress for a specific habit or all habits
    public void resetProgress(int index) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (index == 0) {
                // Reset progress for all habits
                String query = "UPDATE habits SET progress = 0";
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(query);
                }
                System.out.println("All habits progress reset successfully!");
                habits.forEach(habit -> habit.reset());
            } else if (index >= 1 && index <= habits.size()) {
                Habit habit = habits.get(index - 1);
                habit.reset();
                String query = "UPDATE habits SET progress = 0 WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, habit.getId());
                    stmt.executeUpdate();
                }
                System.out.println("Progress for \"" + habit.getName() + "\" reset successfully!");
            } else {
                System.out.println("Invalid habit number!");
            }
        } catch (SQLException e) {
            System.out.println("Error resetting progress: " + e.getMessage());
        }
    }

    // Load habits from the database
    private void loadHabitsFromDatabase() {
        habits.clear();  // Clear the existing list
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String query = "SELECT * FROM habits";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int goal = rs.getInt("goal");
                    int progress = rs.getInt("progress");
                    habits.add(new Habit(id, name, goal, progress));  // Assuming you modified Habit to have an id
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading habits: " + e.getMessage());
        }
    }

    // Update habit progress in the database
    private void updateHabitProgress(Habit habit) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String query = "UPDATE habits SET progress = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, habit.getProgress());
                stmt.setInt(2, habit.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error updating habit progress: " + e.getMessage());
        }
    }
}
