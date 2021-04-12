package pl.biernacki.passwordwalletapp.Encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

public class Aes {
    private static final String ALGO = "AES";

    //encrypts string and returns encrypted string
    public static String encrypt(String data, String key) throws Exception {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, generateKey(key));
        byte[] encVal = c.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    //decrypts string and returns plain text
    public static String decrypt(String encryptedData, String key) throws Exception {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, generateKey(key));
        byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    // Generate a new encryption key.
    private static Key generateKey(String keyValue) throws Exception {
        byte[] bytesOfMessage = keyValue.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] b = md.digest(bytesOfMessage);
        return new SecretKeySpec(b, ALGO);
    }
}
