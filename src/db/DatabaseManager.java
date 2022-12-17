package db;

import models.AccountType;
import models.Operation;
import models.User;
import models.UserDoesNotExistException;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;
    private String database;

    public boolean connect(String host, String user, String password, String database) {
        this.database = database;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + "/", user, password);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public AccountType getAccountTypeById(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("USE " + database);

            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM account_type WHERE account_type_id = " + id + ";"
            );

            resultSet.next();

            AccountType accountType = new AccountType();
            accountType.setAccountId(resultSet.getInt("account_type_id"));
            accountType.setDescription(resultSet.getString("description"));

            return accountType;

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            accountType.setAccountId(resultSet.getInt("account_type_id"));
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
            throw new UserDoesNotExistException();
        }
    }

    public List<User> getAllUsers() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("USE " + database);

            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM user INNER JOIN account_type ON user.account_type_id = account_type.account_type_id;"
            );

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                AccountType accountType = new AccountType();
                accountType.setAccountId(resultSet.getInt("account_type_id"));
                accountType.setDescription(resultSet.getString("description"));

                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAccountType(accountType);
                user.setCreatedOn(resultSet.getDate("created_on"));

                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("USE " + database);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            statement.execute(
                    "INSERT INTO user(username, password, first_name, last_name, account_type_id, created_on)" +
                            "VALUES(" +
                            "'" + user.getUsername() + "'," +
                            "'" + user.getPassword() + "'," +
                            "'" + user.getFirstName() + "'," +
                            "'" + user.getLastName() + "'," +
                            user.getAccountType().getAccountId() + "," +
                            "'" + dateFormat.format(user.getCreatedOn()) + "'" +
                            ");"
            );

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("USE " + database);

            statement.execute(
                    "UPDATE user SET " +
                            "first_name = '" + user.getFirstName() + "', " +
                            "last_name = '" + user.getLastName() + "' " +
                            "WHERE user_id = " + user.getUserId() + ";"
            );

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("USE " + database);

            statement.execute(
                    "DELETE FROM user WHERE user_id = " + user.getUserId() + ";"
            );

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Operation> getOperationsByUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("USE " + database);

            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM operation INNER JOIN user ON operation.user_id = user.user_id " +
                            "WHERE user.user_id = " + user.getUserId() + ";"
            );

            List<Operation> operations = new ArrayList<>();

            while (resultSet.next()) {
                Operation operation = new Operation();
                operation.setOperationId(resultSet.getInt("operation_id"));
                operation.setUser(user);
                operation.setFirstEquation(resultSet.getString("first_equation"));
                operation.setSecondEquation(resultSet.getString("second_equation"));
                operation.setThirdEquation(resultSet.getString("third_equation"));
                operation.setXValue(resultSet.getDouble("x_value"));
                operation.setYValue(resultSet.getDouble("y_value"));
                operation.setZValue(resultSet.getDouble("z_value"));
                operation.setCreatedOn(resultSet.getDate("created_on"));

                operations.add(operation);
            }

            return operations;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addOperation(Operation operation) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("USE " + database);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            statement.execute(
                    "INSERT INTO operation(user_id, first_equation, second_equation, third_equation, x_value, y_value, z_value, created_on)" +
                            "VALUES(" +
                            operation.getUser().getUserId() + "," +
                            "'" + operation.getFirstEquation() + "'," +
                            "'" + operation.getSecondEquation() + "'," +
                            "'" + operation.getThirdEquation() + "'," +
                            operation.getXValue() + "," +
                            operation.getYValue() + "," +
                            operation.getZValue() + "," +
                            "'" + dateFormat.format(operation.getCreatedOn()) + "'" +
                            ");"
            );

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
