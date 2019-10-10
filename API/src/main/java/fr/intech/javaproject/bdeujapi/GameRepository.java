package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends CrudRepository<Game, Integer> {

    @Query("UPDATE User SET name = :name WHERE idGame = :idGame")
    public void updateName( @Param("name") String name, @Param("idGame") int idGame);

    @Query("UPDATE User SET state = :state WHERE idGame = :idGame")
    public void updateState( @Param("state") String state, @Param("idGame") int idGame);
}
