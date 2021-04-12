package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.Password
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryLoginAttemptRepository
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import spock.lang.Specification

import java.time.LocalDateTime

class LoginAttemptService_addAttempts_Test extends Specification {

    InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
    private UserService userService = new UserService(inMemoryUserRepository)
    private  LoginAttemptService LoginAttemptService = new LoginAttemptService(inMemoryUserRepository, new InMemoryLoginAttemptRepository());



    def "shouldSaveAddLoginAttempt"(){
        def date = LocalDateTime.now();
        def salt = "salt"
        def  attempt =0;
        setup:

        User user = new User(1L,"admin", "admin", salt, false, new ArrayList<Password>(),new LoginAttempt())
        def savedUser = userService.saveUser(user, salt)
        LoginAttempt loginAttempt = new LoginAttempt(1L,true,attempt,date,date,date,savedUser)
        def savedloginAttempt = LoginAttemptService.saveUserAttempt(savedUser)


        when:
        def addedNewLoginAttempt = LoginAttemptService.addAttempts(savedUser)

        then:
        addedNewLoginAttempt.loginResult == loginAttempt.loginResult
        addedNewLoginAttempt.attempts == loginAttempt.attempts+1
        addedNewLoginAttempt.user == loginAttempt.user

    }
}
