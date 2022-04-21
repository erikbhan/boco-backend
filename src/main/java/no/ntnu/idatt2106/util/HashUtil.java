package no.ntnu.idatt2106.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {

    /**
     * Method to generate and return a salt
     * @return a random 64-byte array; a salt 
     */
    public byte[] getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);
        return salt;
    }

    public byte[] getHashedPassword(String password, byte[] salt) {
        byte[] hashedPassword = new byte[0];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(hashedPassword.length == 0) return null;
        return hashedPassword;
    }
}
