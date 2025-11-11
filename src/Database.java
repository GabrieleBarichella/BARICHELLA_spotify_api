import java.sql.*;

public class Database {

    private Connection connection = null;
    public static Database instance = null;

    private Database() throws SQLException {
        String url = "jdbc:sqlite:database/spotify.db";
        connection = DriverManager.getConnection(url);
        System.out.println("Database connection established");
    }

    public static Database getInstance() throws SQLException {
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public boolean insert(Artist artist) {

        try {
            if(connection == null || !connection.isValid(5)) {
                System.err.println("Connection error");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Connection error");
            return false;
        }

        String query = "INSERT INTO spotify(id, name, genre, country) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, artist.id);
            statement.setString(2, artist.name);
            statement.setString(3, artist.genre);
            statement.setString(4, artist.country);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println("Query error");
            return false;
        }

        return true;
    }
}
