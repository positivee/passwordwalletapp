package pl.biernacki.passwordwalletapp.Encryption;


import pl.biernacki.passwordwalletapp.entity.TempUser;
import pl.biernacki.passwordwalletapp.entity.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

public class Hmac {
    private static final String HMAC_SHA512 = "HmacSHA512";


    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public String calculateHMAC(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
        Mac mac = Mac.getInstance(HMAC_SHA512);
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    public boolean checkPassword(User user, User tempUser) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String generatedHash = calculateHMAC(tempUser.getPasswordHash(), user.getSalt());
        return generatedHash.equals(user.getPasswordHash());
    }



}
