package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("UPDATE User SET login = :login WHERE idUser = :idUser")
    public void updateLogin( @Param("login") String login, @Param("idUser") int idUser);

    @Query("UPDATE User SET surname = :surname WHERE idUser = :idUser")
    public void updateSurname( @Param("surname") String surname, @Param("idUser") int idUser);

    @Query("UPDATE User SET password = :password WHERE idUser = :idUser")
    public void updatePassword( @Param("password") String password, @Param("idUser") int idUser);

    @Query("UPDATE User SET mail = :mail WHERE idUser = :idUser")
    public void updateMail( @Param("mail") String mail, @Param("idUser") int idUser);

    public Optional<User> findByLoginAndPassword(String login, String password);

    public Optional<User> findByLogin(String login);
}