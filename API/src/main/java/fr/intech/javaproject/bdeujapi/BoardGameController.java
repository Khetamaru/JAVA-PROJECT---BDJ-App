package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/boardGame")
public class BoardGameController {

    @Autowired
    private IMapper mapper;
    @Autowired
    BoardGameRepository boardGameRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveEquipment(@RequestBody String data) throws Exception {

        BoardGame boardGame = new ObjectMapper().readValue(data, BoardGame.class);
        boardGameRepository.save(boardGame);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<BoardGame> getAllEquipments() throws Exception {

        return boardGameRepository.findAll();
    }

    @GetMapping("/{id}")
    public BoardGame getEquipmentByID(@PathVariable int id) throws Exception {

        Optional<BoardGame> optionalBoardGame = boardGameRepository.findById(id);

        if (optionalBoardGame.isPresent()) {

            return optionalBoardGame.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipment Not Found");
        }
    }

    @GetMapping("/validated")
    public Iterable<BoardGame> getEquipmentValidated() throws Exception {

        Iterable<BoardGame> equipments = boardGameRepository.findByAbleToBorrowLike("yes");

        return equipments;
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllEquipments() throws Exception {

        boardGameRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        boardGameRepository.deleteById(id);
    }
}
