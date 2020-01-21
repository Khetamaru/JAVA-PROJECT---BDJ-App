package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/other")
public class OtherController {

    @Autowired
    private IMapper mapper;
    @Autowired
    OtherRepository otherRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveEquipment(@RequestBody String data) throws Exception {

        Other other = new ObjectMapper().readValue(data, Other.class);
        otherRepository.save(other);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Other> getAllEquipments() throws Exception {

        return otherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Other getEquipmentByID(@PathVariable int id) throws Exception {

        Optional<Other> optionalGame = otherRepository.findById(id);

        if (optionalGame.isPresent()) {

            return optionalGame.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipment Not Found");
        }
    }

    @GetMapping("/validated")
    public Iterable<Other> getEquipmentValidated() throws Exception {

        Iterable<Other> equipments = otherRepository.findByAbleToBorrowLike("yes");

        return equipments;
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllEquipments() throws Exception {

        otherRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        otherRepository.deleteById(id);
    }
}
