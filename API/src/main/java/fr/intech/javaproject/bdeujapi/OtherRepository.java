package fr.intech.javaproject.bdeujapi;

import org.springframework.data.repository.CrudRepository;

public interface OtherRepository extends CrudRepository<Other, Integer> {

    public Iterable<Other> findByAbleToBorrowLike(String yes);
}
