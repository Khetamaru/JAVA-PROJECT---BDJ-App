package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserHistoricRepository extends CrudRepository<UserHistoric, Integer> {

    @Query("UPDATE User SET origin = :origin WHERE idHistoric = :idHistoric")
    public void updateOrigin( @Param("origin") String origin, @Param("idHistoric") int idHistoric);

    @Query("UPDATE User SET action = :action WHERE idHistoric = :idHistoric")
    public void updateAction( @Param("action") String action, @Param("idHistoric") int idHistoric);

    @Query("UPDATE User SET date = :date WHERE idHistoric = :idHistoric")
    public void updateDate( @Param("date") String date, @Param("idHistoric") int idHistoric);
}
