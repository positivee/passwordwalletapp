package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import spock.lang.Specification

class UserService_CheckPassword_Test extends Specification {
    private UserService userService = new UserService(new InMemoryUserRepository())

    def "shouldCheckPasswordAllTypes"(){
        def salt = "salt"

        setup:
        User user1 = new User(1L,"admin", passwordHash, salt, isHashed, Collections.emptyList(), new LoginAttempt())
        User userSha = new User(1L,"admin", "admin", salt, true, Collections.emptyList(), new LoginAttempt())
        User userHmac = new User(1L,"admin", "admin", salt, false, Collections.emptyList(), new LoginAttempt())
        userService.saveUser(userSha, salt)
        userService.saveUser(userHmac, salt)

        expect:
        userService.checkPassword(user1).isPresent() == c

        where:
        isHashed | passwordHash || c
        true | "admin" || true
        true | "admin123" || false
        false | "admin" || true
        false |  "admin123" || false
    }
}
