package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.Password
import pl.biernacki.passwordwalletapp.entity.SharePassword
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryPasswordRepository
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import spock.lang.Specification

class PasswordService_GetPassword_Test extends Specification {
    private PasswordService passwordService = new PasswordService(new InMemoryPasswordRepository())
    private UserService userService = new UserService(new InMemoryUserRepository())

    def "shouldGetPassword"() {
        def sol = "sol"

        setup:
        User user = new User(1L,"admin", "admin", sol, false, new ArrayList<Password>(), new LoginAttempt())
        def savedUser = userService.saveUser(user, sol)
        Password password = new Password(1L, "login", "pass", "webAddress", "description", savedUser, new ArrayList<SharePassword>())
        def savedPassword = passwordService.savePassword(password, savedUser)

        when:
        def getedPassword = passwordService.getPassword(savedPassword.id) //getting passwrd form database
        def notGeted = passwordService.getPassword(55L)

        then:
        getedPassword == savedPassword
        notGeted != savedPassword
    }
}
