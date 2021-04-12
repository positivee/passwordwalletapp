package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.Encryption.Aes
import pl.biernacki.passwordwalletapp.Encryption.Hmac
import pl.biernacki.passwordwalletapp.Encryption.Sha512
import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.Password
import pl.biernacki.passwordwalletapp.entity.SharePassword
import pl.biernacki.passwordwalletapp.entity.TempUser
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryPasswordRepository
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import spock.lang.Specification

import javax.servlet.http.HttpSession

class UserService_ChangePassword_Test extends Specification {
    private PasswordService passwordService = new PasswordService(new InMemoryPasswordRepository())


    private UserService userService = new UserService(new InMemoryUserRepository())

    def "shouldChangePasswordWithHmac"(){
        def sol = "sol"
        Hmac hmac = new Hmac()

        setup:
        User user = new User(1L,"admin", "admin", sol, false, new ArrayList<Password>(), new LoginAttempt())
        def savedUser = userService.saveUser(user, sol)
        Password password = new Password(1L, "login", "pass", "webAddress", "description", savedUser, new ArrayList<SharePassword>())
        def savedPassword = passwordService.savePassword(password, savedUser)
        TempUser tempUser = new TempUser("login","admin", "nowehaslo")

        when:
        def changedUser = userService.changePassword(tempUser, savedUser)

        then:
        changedUser.login == savedUser.login
        changedUser.isPasswordKeptAsHash == savedUser.isPasswordKeptAsHash
        changedUser.passwordHash == hmac.calculateHMAC(tempUser.newPass, changedUser.salt)
        for(Password pass : changedUser.passwordList){
            pass.login == savedPassword.login
            pass.users == savedPassword.users
            pass.passwordWeb == Aes.encrypt(Aes.decrypt(savedPassword.passwordWeb, savedUser.passwordHash), changedUser.passwordHash)
        }
    }

    def "shouldChangePasswordWithSha512"(){
        def sol = "sol"

        setup:
        User user = new User(1L,"admin", "admin", sol, true, new ArrayList<Password>(), new LoginAttempt())
        def savedUser = userService.saveUser(user, sol)
        Password password = new Password(1L, "login", "pass", "webAddress", "description", savedUser, new ArrayList<SharePassword>())
        def savedPassword = passwordService.savePassword(password, savedUser)
        TempUser tempUser = new TempUser("login","admin", "nowehaslo")

        when:
        def changedUser = userService.changePassword(tempUser, savedUser)

        then:
        changedUser.login == savedUser.login
        changedUser.isPasswordKeptAsHash == savedUser.isPasswordKeptAsHash
        changedUser.passwordHash == Sha512.hashPassword(tempUser.newPass, "test", changedUser.salt)
        for(Password pass : changedUser.passwordList){
            pass.login == savedPassword.login
            pass.users == savedPassword.users
            pass.passwordWeb == Aes.encrypt(Aes.decrypt(savedPassword.passwordWeb, savedUser.passwordHash), changedUser.passwordHash)
        }
    }
}
