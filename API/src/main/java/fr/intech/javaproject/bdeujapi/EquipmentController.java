package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private IMapper mapper;
    @Autowired
    EquipmentRepository equipmentRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveEquipment(@RequestBody String data) throws Exception {

        Equipment equipment = new ObjectMapper().readValue(data, Equipment.class);
        equipmentRepository.save(equipment);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Equipment> getAllEquipments() throws Exception {

        return equipmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Equipment getEquipmentByID(@PathVariable int id) throws Exception {

        Optional<Equipment> optionalGame = equipmentRepository.findById(id);

        if (optionalGame.isPresent()) {

            return optionalGame.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipment Not Found");
        }
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllEquipments() throws Exception {

        equipmentRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        equipmentRepository.deleteById(id);
    }
}
