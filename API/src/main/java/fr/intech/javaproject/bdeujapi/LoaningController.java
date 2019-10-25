package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/borrow")
public class LoaningController {

    @Autowired
    private IMapper mapper;
    @Autowired
    LoaningRepository loaningRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveBorrow(@RequestBody String data) throws Exception {

        Loaning loaning = new ObjectMapper().readValue(data, Loaning.class);
        loaningRepository.save(loaning);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Loaning> getAllBorrows() throws Exception {

        return loaningRepository.findAll();
    }

    @GetMapping("/{id}")
    public Loaning getBorrowByID(@PathVariable int id) throws Exception {

        Optional<Loaning> optionalBorrow = loaningRepository.findById(id);

        if (optionalBorrow.isPresent()) {

            return optionalBorrow.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    /////////////////////////////// POST //////////////////////////////////

    /*@PostMapping("/startDate")
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
    }*/

    @PostMapping("/all")
    public Iterable<Loaning> getBorrowsByIDUser(@RequestBody String data) throws Exception {

        User user = new ObjectMapper().readValue(data, User.class);

        Optional<Iterable<Loaning>> optionalBorrow = loaningRepository.findAllByUser(user);

        if (optionalBorrow.isPresent()) {

            return optionalBorrow.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    @PostMapping
    public Loaning update(@RequestBody Loaning loaning) throws Exception {
        //Borrow borrow = mapper.mapperBorrowRead(data);

        //borrowRepository.save(borrow);
        return loaning;
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllBorrows() throws Exception {

        loaningRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        loaningRepository.deleteById(id);
    }
}