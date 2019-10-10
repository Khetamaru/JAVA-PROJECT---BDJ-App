package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BorrowRepository extends CrudRepository<Borrow, Integer> {

    @Query("UPDATE User SET startDate = :startDate WHERE idBorrow = :idBorrow")
    public void updateStartDate(@Param("startDate") Date startDate, @Param("idBorrow") int idBorrow);

    @Query("UPDATE User SET endDate = :endDate WHERE idBorrow = :idBorrow")
    public void updateEndDate( @Param("endDate") Date endDate, @Param("idBorrow") int idBorrow);
}
