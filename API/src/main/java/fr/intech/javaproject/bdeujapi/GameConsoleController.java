package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/gameConsole")
public class GameConsoleController {

    @Autowired
    private IMapper mapper;
    @Autowired
    GameConsoleRepository gameConsoleRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveEquipment(@RequestBody String data) throws Exception {

        GameConsole gameConsole = new ObjectMapper().readValue(data, GameConsole.class);
        gameConsoleRepository.save(gameConsole);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<GameConsole> getAllEquipments() throws Exception {

        return gameConsoleRepository.findAll();
    }

    @GetMapping("/{id}")
    public GameConsole getEquipmentByID(@PathVariable int id) throws Exception {

        Optional<GameConsole> optionalGame = gameConsoleRepository.findById(id);

        if (optionalGame.isPresent()) {

            return optionalGame.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipment Not Found");
        }
    }

    @GetMapping("/validated")
    public Iterable<GameConsole> getEquipmentValidated() throws Exception {

        Iterable<GameConsole> equipments = gameConsoleRepository.findByAbleToBorrowLike("yes");

        return equipments;
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllEquipments() throws Exception {

        gameConsoleRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        gameConsoleRepository.deleteById(id);
    }
}
