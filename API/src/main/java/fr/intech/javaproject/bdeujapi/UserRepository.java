package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("UPDATE User SET level = ':level' WHERE idUser = :idUser")
    public void levelUp( @Param("idUser") int idUser, @Param("level") String level);

    public Optional<User> findByLoginAndPassword(String login, String password);

    public Optional<User> findByLogin(String login);

    public Iterable<User> findAllByIdUserNotLike(int id);
}