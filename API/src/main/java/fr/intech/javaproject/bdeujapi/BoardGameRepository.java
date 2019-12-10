package fr.intech.javaproject.bdeujapi;

import org.springframework.data.repository.CrudRepository;

public interface BoardGameRepository extends CrudRepository<BoardGame, Integer> {

    public Iterable<BoardGame> findByAbleToBorrowLike(String yes);
}
