package authentication;

import db.DatabaseManager;
import models.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationManager {
    private DatabaseManager databaseManager;

    public AuthenticationManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public User authenticate(String username, String password) {
        User user = this.databaseManager.getUserByUsername(username);

        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String encodedPassword = javax.xml.bind.DatatypeConverter.printHexBinary(
            messageDigest.digest(password.getBytes())
        );

        // Check if the encoded password stored in the database is different the encoded password just passed
        if (!user.getPassword().equals(encodedPassword)) {
            throw new InvalidUsernameOrPasswordException();
        }

        return user;
    }
}
