package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping
    public void saveBorrow(@RequestBody String data) throws Exception {

        Borrow borrow = new ObjectMapper().readValue(data, Borrow.class);
        borrowRepository.save(borrow);
    }

    @GetMapping
    public Iterable<Borrow> getAllBorrows() throws Exception {

        return borrowRepository.findAll();
    }

    @PostMapping("/startDate")
    public int getValidStartDate(@RequestBody String data) throws Exception {

        DateStartEnd date = new ObjectMapper().readValue(data, DateStartEnd.class);

        Optional<Integer> optionalBorrow = borrowRepository.goodStartDate(date.getDate());
        if (optionalBorrow.isPresent()) {
            return optionalBorrow.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
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

    @PostMapping("/all")
    public Iterable<Borrow> getBorrowsByIDUser(@RequestBody String data) throws Exception {

        User user = new ObjectMapper().readValue(data, User.class);

        Optional<Iterable<Borrow>> optionalBorrow = borrowRepository.findAllByUser(user);
        if (optionalBorrow.isPresent()) {
            return optionalBorrow.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    @DeleteMapping
    public void deleteAllBorrows() throws Exception {

        borrowRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        borrowRepository.deleteById(id);
    }

    @PostMapping
    public Borrow update(@RequestBody Borrow borrow) throws Exception {
        //Borrow borrow = mapper.mapperBorrowRead(data);

        //borrowRepository.save(borrow);
        return borrow;
    }
}