package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private IMapper mapper;
    @Autowired
    GameRepository gameRepository;

    @PutMapping
    public void saveGame(Game game) throws Exception {

        gameRepository.save(game);
    }

    @GetMapping
    public Iterable<Game> getAllGames() throws Exception {

        return gameRepository.findAll();
    }

    @GetMapping("/{id}")
    public Game getGameByID(@PathVariable int id) throws Exception {

        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            return optionalGame.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    public void deleteAllGames() throws Exception {

        gameRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        gameRepository.deleteById(id);
    }

    @PatchMapping("/updateName/{id}/{name}")
    public void updateName(@PathVariable int id, @PathVariable String name) throws Exception {

        gameRepository.updateName(name, id);
    }

    @PatchMapping("/updateState/{id}/{state}")
    public void updateState(@PathVariable int id, @PathVariable String state) throws Exception {

        gameRepository.updateState(state, id);
    }
}
