package fr.intech.javaproject.bdeujapi;

import org.springframework.data.repository.CrudRepository;

public interface GameConsoleRepository extends CrudRepository<GameConsole, Integer> {

    public Iterable<GameConsole> findByAbleToBorrowLike(String yes);
}
