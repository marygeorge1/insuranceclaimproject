import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminLogin {

    // JDBC database URL, username, and password of MySQL server
    private static final String DB_URL = "jdbc:mysql://localhost/Aegon";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";


        Scanner scanner = new Scanner(System.in);

        System.out.println("Admin Login Page");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            // Check if the provided username and password exist in the database
            if (isValidAdmin(connection, username, password)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password. Login failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidAdmin(Connection connection, String username, String password) throws SQLException {
        String sql = "SELECT * FROM admin_table WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If a row is returned, the login is valid
            }
        }
    }
}