package db;

import models.AccountType;
import models.User;
import models.UserDoesNotExistException;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    private String database;

    public boolean connect(String host, String user, String password, String database) {
        this.database = database;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + "/", user, password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByUsername(String username) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("USE " + database);

            ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM user INNER JOIN account_type " +
                   "ON user.account_type_id = account_type.account_type_id WHERE username = '" + username + "';"
            );

            resultSet.next();

            AccountType accountType = new AccountType();
            accountType.setDescription(resultSet.getString("description"));

            User user = new User();
            user.setUserId(resultSet.getInt("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setAccountType(accountType);
            user.setCreatedOn(resultSet.getDate("created_on"));

            return user;

        } catch (SQLException e) {
            // REMOVE FOR DEBUGGING:
            e.printStackTrace();
            throw new UserDoesNotExistException();
        }
    }
}
