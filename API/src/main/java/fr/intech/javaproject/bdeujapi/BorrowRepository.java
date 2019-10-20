package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface BorrowRepository extends CrudRepository<Borrow, Integer> {

    @Query("SELECT idBorrow FROM Borrow WHERE (startDate < :startDate AND endDate > :startDate)")
    public Optional<Integer> goodStartDate(@Param("startDate") Date date);

    public Optional<Iterable<Borrow>> findAllByUser(User user);
}
