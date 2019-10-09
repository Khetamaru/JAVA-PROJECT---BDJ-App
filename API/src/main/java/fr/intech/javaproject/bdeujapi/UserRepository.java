package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("UPDATE User SET login = :login WHERE idUser = :idUser")
    public void updateLogin( @Param("login") String login, @Param("idUser") int idUser);
}
