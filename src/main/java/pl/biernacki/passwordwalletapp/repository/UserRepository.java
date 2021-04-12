package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.biernacki.passwordwalletapp.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
 /*  @Query("SELECT u FROM User u WHERE u.login =?1")
    User findbyLogin(String login);*/
     @Query("SELECT u FROM User u where u.login = :login")
     User findByLogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);
}
