package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    @Query("UPDATE User SET place = :place WHERE idMember = :idMember")
    public void updatePlace( @Param("place") String place, @Param("idMember") int idMember);

    @Query("UPDATE User SET startDate = :startDate WHERE idMember = :idMember")
    public void updateStartDate(@Param("startDate") Date startDate, @Param("idMember") int idMember);

    @Query("UPDATE User SET endDate = :endDate WHERE idMember = :idMember")
    public void updateEndDate( @Param("endDate") Date endDate, @Param("idMember") int idMember);
}
