package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import spock.lang.Specification

class UserService_Exists_Test extends Specification {
    private UserService userService = new UserService(new InMemoryUserRepository())

    def "shouldExistsByLogin"(){
        def salt = "sol"

        setup:
        User user = new User(1L,"admin", "admin", salt, true, Collections.emptyList(), new LoginAttempt())
        userService.saveUser(user, salt)

        expect:
        userService.existByLogin(login) == c

        where:
        login || c
        "admin" || true
        "admin123" || false

    }
}
