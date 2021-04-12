package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.Encryption.Aes
import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.Password
import pl.biernacki.passwordwalletapp.entity.SharePassword
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryPasswordRepository
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import spock.lang.Specification

class PasswordService_CreatePassword_Test extends Specification {
    private PasswordService passwordService = new PasswordService(new InMemoryPasswordRepository())
    private UserService userService = new UserService(new InMemoryUserRepository())

    def "shouldCreatePasswordWithAes"(){
        def sol = "sol"

        setup:
        User user = new User(1L,"admin", "admin", sol, false, new ArrayList<Password>(), new LoginAttempt())
        def savedUser = userService.saveUser(user, sol)
        Password password = new Password(1L, "login", "pass", "webAddress", "description", savedUser, new ArrayList<SharePassword>())

        when:
        def savedPassword = passwordService.savePassword(password, savedUser)

        then:
        savedPassword.login == password.login
        savedPassword.description == password.description
        savedPassword.webAddress == password.webAddress
        savedPassword.users == password.users

        savedPassword.passwordWeb == Aes.encrypt("pass", savedUser.getPasswordHash())

    }

}
