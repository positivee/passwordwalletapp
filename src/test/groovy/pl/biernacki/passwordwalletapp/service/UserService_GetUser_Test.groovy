package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import spock.lang.Specification

class UserService_GetUser_Test extends Specification {
    private UserService userService = new UserService(new InMemoryUserRepository())

    def "shouldGetUser"(){
        def sol = "sol"

        setup:
        User user = new User(1L,"admin", "admin", sol, true, Collections.emptyList(), new LoginAttempt())
        def savedUser = userService.saveUser(user, sol)

        when:
        def getedUser = userService.getUser(savedUser.id)
        def notGeted = userService.getUser(1L)

        then:
        getedUser == savedUser
        notGeted != savedUser

    }
}
