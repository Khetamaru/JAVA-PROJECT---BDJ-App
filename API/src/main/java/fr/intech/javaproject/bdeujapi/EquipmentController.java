package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/game")
public class EquipmentController {

    @Autowired
    private IMapper mapper;
    @Autowired
    EquipmentRepository equipmentRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveGame(@RequestBody String data) throws Exception {

        Equipment equipment = new ObjectMapper().readValue(data, Equipment.class);
        equipmentRepository.save(equipment);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Equipment> getAllGames() throws Exception {

        return equipmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Equipment getGameByID(@PathVariable int id) throws Exception {

        Optional<Equipment> optionalGame = equipmentRepository.findById(id);

        if (optionalGame.isPresent()) {

            return optionalGame.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllGames() throws Exception {

        equipmentRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        equipmentRepository.deleteById(id);
    }

    /////////////////////////////// PATCH //////////////////////////////////

    @PatchMapping("/updateName/{id}/{name}")
    public void updateName(@PathVariable int id, @PathVariable String name) throws Exception {

        equipmentRepository.updateName(name, id);
    }

    @PatchMapping("/updateState/{id}/{state}")
    public void updateState(@PathVariable int id, @PathVariable String state) throws Exception {

        equipmentRepository.updateState(state, id);
    }
}
