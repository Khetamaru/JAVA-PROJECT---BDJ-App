package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private IMapper mapper;
    @Autowired
    BorrowRepository borrowRepository;

    @GetMapping
    public void saveBorrow(Borrow borrow) throws Exception {

        borrowRepository.save(borrow);
    }

    @GetMapping
    public Iterable<Borrow> getAllBorrows() throws Exception {

        return borrowRepository.findAll();
    }

    @GetMapping("/{id}")
    public Borrow getBorrowByID(@PathVariable int id) throws Exception {

        Optional<Borrow> optionalBorrow = borrowRepository.findById(id);
        if (optionalBorrow.isPresent()) {
            return optionalBorrow.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    public void deleteAllBorrows() throws Exception {

        borrowRepository.deleteAll();
    }
    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        borrowRepository.deleteById(id);
    }

    @GetMapping("/updateStartDate/{id}/{startDate}")
    public void updateStartDate(@PathVariable int id, @PathVariable Date startDate) throws Exception {

        borrowRepository.updateStartDate(startDate, id);
    }

    @GetMapping("/updateEndDate/{id}/{endDate}")
    public void updateEndDate(@PathVariable int id, @PathVariable Date endDate) throws Exception {

        borrowRepository.updateEndDate(endDate, id);
    }
}
