package pl.biernacki.passwordwalletapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.biernacki.passwordwalletapp.Encryption.Aes;
import pl.biernacki.passwordwalletapp.Encryption.Hmac;
import pl.biernacki.passwordwalletapp.Encryption.Sha512;
import pl.biernacki.passwordwalletapp.entity.Password;
import pl.biernacki.passwordwalletapp.entity.TempUser;
import pl.biernacki.passwordwalletapp.entity.User;
import pl.biernacki.passwordwalletapp.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final String pepper = "test";

    public boolean existByLogin(String login){
        return userRepository.existsByLogin(login);
    }

    public User getUser(Long id){
        return userRepository.getOne(id);
    }
    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }


    public Optional<User> checkPassword(User user) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        User tempUser = userRepository.findByLogin(user.getLogin());
        Hmac hmac = new Hmac();
        if(Sha512.checkPassword(tempUser, user) || hmac.checkPassword(tempUser, user))
            return Optional.of(tempUser);
        else
            return Optional.empty();
    }

    public User saveUser(User user, String salt) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Hmac hmac = new Hmac();
        user.setSalt(salt);
        //check what type of hash is password
        if(user.isPasswordKeptAsHash) {
            user.setPasswordHash(Sha512.hashPassword(user.getPasswordHash(), pepper, user.getSalt()));
        }else {
            user.setPasswordHash(hmac.calculateHMAC(user.getPasswordHash(), user.getSalt()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public User changePassword(TempUser tempUser, User user) throws Exception {
        Hmac hmac = new Hmac();
        String oldPass = user.getPasswordHash();
        //generate new salt
        String salt = Sha512.generateSalt().toString();
        if(user.isPasswordKeptAsHash) {  //check type of hash for user
            user.setPasswordHash(Sha512.hashPassword(tempUser.getNewPass(), pepper, salt));
        }
        else {
            user.setPasswordHash(hmac.calculateHMAC(tempUser.getNewPass(), salt));
        }
        user.setSalt(salt); //set new salt for user
        List<Password> passwordList = user.getPasswordList();
        for (Password password : passwordList) {
            password.setPasswordWeb(Aes.encrypt(Aes.decrypt(password.getPasswordWeb(), oldPass), user.getPasswordHash()));
        }

        return user;
    }
}
