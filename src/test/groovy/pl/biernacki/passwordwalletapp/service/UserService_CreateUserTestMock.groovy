package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.Encryption.Hmac
import pl.biernacki.passwordwalletapp.Encryption.Sha512
import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.UserRepository
import spock.lang.Specification

class UserService_CreateUserTestMock extends Specification {
    private UserRepository userRepository = Mock()
    private UserService userService = new UserService(userRepository)

    def "shouldCreateUserWithSha512"(){
        def salt = "sol"

        setup:
        User user = new User(1L,"admin", "admin", salt, true, Collections.emptyList(), new LoginAttempt())

        and:
        userRepository.save(_) >> user

        when:
        def savedUser = userService.saveUser(user, salt)

        then:
        savedUser.login == user.login
        savedUser.isPasswordKeptAsHash
        savedUser.salt == salt
        savedUser.passwordList == user.passwordList
        savedUser.passwordHash == Sha512.hashPassword("admin", "test", salt)

    }

    def "shouldCreateUserWithHmac"(){
        def salt = "sol"
        def hmac = new Hmac()
        setup:
        User user = new User(1L,"admin", "admin", salt, false, Collections.emptyList(), new LoginAttempt())

        and:
        userRepository.save(_) >> user

        when:
        def savedUser = userService.saveUser(user, salt)

        then:
        savedUser.login == user.login
        !savedUser.isPasswordKeptAsHash
        savedUser.salt == salt
        savedUser.passwordList == user.passwordList

        savedUser.passwordHash == hmac.calculateHMAC("admin", salt)
    }
}
