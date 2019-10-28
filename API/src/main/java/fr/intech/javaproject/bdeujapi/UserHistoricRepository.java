package fr.intech.javaproject.bdeujapi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserHistoricRepository extends CrudRepository<UserHistoric, Integer> {

    public Optional<Iterable<Loaning>> findAllByUser(User user);
}
