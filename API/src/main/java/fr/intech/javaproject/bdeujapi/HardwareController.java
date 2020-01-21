package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/hardware")
public class HardwareController {

    @Autowired
    private IMapper mapper;
    @Autowired
    HardwareRepository hardwareRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveEquipment(@RequestBody String data) throws Exception {

        Hardware hardware = new ObjectMapper().readValue(data, Hardware.class);
        hardwareRepository.save(hardware);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Hardware> getAllEquipments() throws Exception {

        return hardwareRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hardware getEquipmentByID(@PathVariable int id) throws Exception {

        Optional<Hardware> optionalGame = hardwareRepository.findById(id);

        if (optionalGame.isPresent()) {

            return optionalGame.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipment Not Found");
        }
    }

    @GetMapping("/validated")
    public Iterable<Hardware> getEquipmentValidated() throws Exception {

        Iterable<Hardware> equipments = hardwareRepository.findByAbleToBorrowLike("yes");

        return equipments;
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllEquipments() throws Exception {

        hardwareRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        hardwareRepository.deleteById(id);
    }
}
