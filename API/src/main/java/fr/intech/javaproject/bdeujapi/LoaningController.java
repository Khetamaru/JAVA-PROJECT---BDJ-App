package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/loaning")
public class LoaningController {

    @Autowired
    private IMapper mapper;
    @Autowired
    LoaningRepository loaningRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveLoaning(@RequestBody String data) throws Exception {

        Loaning loaning = new ObjectMapper().readValue(data, Loaning.class);
        loaningRepository.save(loaning);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Loaning> getAllLoaning() throws Exception {

        return loaningRepository.findAll();
    }

    @GetMapping("/{id}")
    public Loaning getLoaningByID(@PathVariable int id) throws Exception {

        Optional<Loaning> optionalBorrow = loaningRepository.findById(id);

        if (optionalBorrow.isPresent()) {

            return optionalBorrow.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Loaning Not Found");
        }
    }

    /////////////////////////////// POST //////////////////////////////////

    @PostMapping("/startDate")
    public int getValidStartDate(@RequestBody String data) throws Exception {

        Date date = new ObjectMapper().readValue(data, Date.class);

        Optional<Integer> optionalBorrow = loaningRepository.goodStartDate(date);

        if (optionalBorrow.isPresent()) {

            return optionalBorrow.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No date found");
        }
    }

    @PostMapping("/all")
    public Iterable<Loaning> getLoaningByIDUser(@RequestBody String data) throws Exception {

        User user = new ObjectMapper().readValue(data, User.class);

        Optional<Iterable<Loaning>> optionalBorrow = loaningRepository.findAllByUser(user);

        if (optionalBorrow.isPresent()) {

            return optionalBorrow.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Loaning Not Found");
        }
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllLoaning() throws Exception {

        loaningRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        loaningRepository.deleteById(id);
    }
}