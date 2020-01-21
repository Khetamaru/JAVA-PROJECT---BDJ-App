package fr.intech.javaproject.bdeujapi;

import org.springframework.data.repository.CrudRepository;

public interface HardwareRepository extends CrudRepository<Hardware, Integer> {

    public Iterable<Hardware> findByAbleToBorrowLike(String yes);
}
