package fr.intech.javaproject.bdeujapi;

import org.springframework.data.repository.CrudRepository;

public interface VideoGameRepository extends CrudRepository<VideoGame, Integer> {

    public Iterable<VideoGame> findByAbleToBorrowLike(String yes);
}
