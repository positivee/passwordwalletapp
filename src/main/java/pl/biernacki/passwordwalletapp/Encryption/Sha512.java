package pl.biernacki.passwordwalletapp.Encryption;

import pl.biernacki.passwordwalletapp.entity.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Sha512 {

    private final static String pepper = "test";

    public static String hashPassword(String passwordToHash, String pepper, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            md.update(pepper.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static boolean checkPassword(User user, User tempUser){
        String generatedHash = hashPassword(tempUser.getPasswordHash(), pepper, user.getSalt());
        return generatedHash.equals(user.getPasswordHash());
    }


    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
