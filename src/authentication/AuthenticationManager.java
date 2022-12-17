package authentication;

import db.DatabaseManager;
import models.AccountType;
import models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class AuthenticationManager {
    private DatabaseManager databaseManager;

    public AuthenticationManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public User authenticate(String username, String password) {
        User user = this.databaseManager.getUserByUsername(username);

        String encodedPassword = encodePassword(password);

        // Check if the encoded password stored in the database is different the encoded password just passed
        if (!user.getPassword().equals(encodedPassword)) {
            throw new InvalidUsernameOrPasswordException();
        }

        return user;
    }

    public User register(String username, String password, String firstName, String lastName) {
        String encodedPassword = encodePassword(password);

        AccountType accountType = this.databaseManager.getAccountTypeById(2);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountType(accountType);
        user.setCreatedOn(new Date());

        this.databaseManager.addUser(user);

        return user;
    }

    private String encodePassword(String password) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return javax.xml.bind.DatatypeConverter.printHexBinary(
                messageDigest.digest(password.getBytes())
        );
    }
}
