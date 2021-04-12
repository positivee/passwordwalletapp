package pl.biernacki.passwordwalletapp.service

import org.apache.tomcat.util.net.openssl.ciphers.Encryption
import pl.biernacki.passwordwalletapp.Encryption.Hmac
import pl.biernacki.passwordwalletapp.Encryption.Sha512
import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import spock.lang.Specification

class UserService_CreateUser_Test extends Specification {
    private UserService userService = new UserService(new InMemoryUserRepository())


    def "shouldCreateUserWithSha512"(){
        def salt = "salt"

        setup:
        def user = new User(1L,"admin", "admin", salt, true, Collections.emptyList(), new LoginAttempt())

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
        def salt = "salt"
        def hmac = new Hmac()

        setup:
        User user = new User(1L,"admin", "admin", salt, false, Collections.emptyList(), new LoginAttempt())

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
