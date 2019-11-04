package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface LoaningRepository extends CrudRepository<Loaning, Integer> {

    @Query("SELECT idBorrow FROM Loaning WHERE (startDate < :startDate AND endDate > :startDate)")
    public Optional<Integer> goodStartDate(@Param("startDate") Date date);

    public Optional<Iterable<Loaning>> findAllByUser(User user);
}
