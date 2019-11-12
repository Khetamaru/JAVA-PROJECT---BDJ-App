package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    public Iterable<Location> findByDateGreaterThan(Date date);
}
