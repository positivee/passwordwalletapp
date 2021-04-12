package pl.biernacki.passwordwalletapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import pl.biernacki.passwordwalletapp.Encryption.Hmac;
import pl.biernacki.passwordwalletapp.entity.Password;
import pl.biernacki.passwordwalletapp.entity.User;
import pl.biernacki.passwordwalletapp.repository.PasswordRepository;
import pl.biernacki.passwordwalletapp.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser(){
        User user = new User();
        user.setLogin("admin1234");
        user.setPasswordHash("admin1234");
     /*   user.setPasswordKeptAsHash(false);*/

        User savedUser = repo.save(user);
        User existUser = entityManager.find(User.class,savedUser.getId());

        assertThat(existUser.getLogin()).isEqualTo(user.getLogin());

    }
    @Test
    public void testCreatePassword(){

        User savedUser = repo.findByLogin("admin1234");

        Password password = new Password();
        password.setLogin("trutek");
        password.setWebAddress("www.pl.pl");
        password.setDescription("Moj ważny");
        password.setPasswordWeb("trutek123");
       /* password.setUsers(savedUser)*/;
        Password savedPassword = passwordRepository.save(password);

       /* Password passwordExist = entityManager.find(Password.class,savedPassword.getId());*/


       /* assertThat(passwordExist.getLogin()).isEqualTo(savedPassword.getLogin());*/

    }

  /*  @Test
    public void testFindUserByLogin(){
        String login = "admin123";

        User user = repo.findbyLogin(login);

        assertThat(user).isNotNull();

    }*/
    @Test
    public void shouldCreateUserWithHmac(){
        String sol = "sol";
        Hmac hmac = new Hmac();
        User user = new User();
        user.setLogin("admin12345");
        user.setPasswordHash("admin12345");



    }

}
