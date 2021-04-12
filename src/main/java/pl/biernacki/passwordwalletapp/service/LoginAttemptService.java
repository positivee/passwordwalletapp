package pl.biernacki.passwordwalletapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.biernacki.passwordwalletapp.entity.LoginAttempt;
import pl.biernacki.passwordwalletapp.entity.User;
import pl.biernacki.passwordwalletapp.repository.LoginAttemptRepository;
import pl.biernacki.passwordwalletapp.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final UserRepository userRepository;
    private final LoginAttemptRepository loginAttemptRepository;

    @Transactional
    public LoginAttempt addAttempts(User user){
        User tempUser = userRepository.findByLogin(user.getLogin());
        LocalDateTime now = LocalDateTime.now();
        tempUser.getLoginAttempt().setAttempts(tempUser.getLoginAttempt().getAttempts() + 1);
        tempUser.getLoginAttempt().setFailedLoginTime(now);

        switch(tempUser.getLoginAttempt().getAttempts()){
            case 2:
                tempUser.getLoginAttempt().setBlockedUntil(now.plusSeconds(5));
                break;
            case 3:
                tempUser.getLoginAttempt().setBlockedUntil(now.plusSeconds(10));
                break;
            case 4:
                tempUser.getLoginAttempt().setBlockedUntil(now.plusMinutes(2));
                break;
        }

        if(tempUser.getLoginAttempt().getAttempts() >= 5)
            tempUser.getLoginAttempt().setBlockedUntil(now.plusMinutes(2));

        return tempUser.getLoginAttempt();
    }

    @Transactional
    public LoginAttempt saveUserAttempt(User user){
        User tempUser = userRepository.findByLogin(user.getLogin());

        LocalDateTime now = LocalDateTime.now();
        LoginAttempt loginAttempt = new LoginAttempt();
        loginAttempt.setAttempts(0);
        loginAttempt.setLoginResult(true);
        loginAttempt.setSuccessfulLoginTime(now);
        loginAttempt.setUser(tempUser);
        loginAttempt.setFailedLoginTime(now);
        loginAttempt.setBlockedUntil(now);
        loginAttemptRepository.save(loginAttempt);
        tempUser.setLoginAttempt(loginAttempt);
        return loginAttempt;
    }

    @Transactional
    public LoginAttempt resetAttempts(User user){
        User tempUser = userRepository.findByLogin(user.getLogin());
        tempUser.getLoginAttempt().setSuccessfulLoginTime(LocalDateTime.now());
        tempUser.getLoginAttempt().setAttempts(0);
        return tempUser.getLoginAttempt();
    }

    @Transactional
    public boolean verifyLoginAttempts(User user){
        User tempUser = userRepository.findByLogin(user.getLogin());
        LocalDateTime blockedUntil = tempUser.getLoginAttempt().getBlockedUntil();

        switch (tempUser.getLoginAttempt().getAttempts()){
            case 2:
            case 3:
            case 4:
                if(LocalDateTime.now().isBefore(blockedUntil))
                    return false;
                break;
        }

        return true;
    }

    public LoginAttempt getLoginAttempt(String login){
        User user = userRepository.findByLogin(login);
        return user.getLoginAttempt();
    }

    public boolean existsByUserLogin(String login){
        return loginAttemptRepository.existsByUserLogin(login);
    }
}
