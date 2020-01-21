package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/videoGame")
public class VideoGameController {

    @Autowired
    private IMapper mapper;
    @Autowired
    VideoGameRepository videoGameRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveEquipment(@RequestBody String data) throws Exception {

        VideoGame videoGame = new ObjectMapper().readValue(data, VideoGame.class);
        videoGameRepository.save(videoGame);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<VideoGame> getAllEquipments() throws Exception {

        return videoGameRepository.findAll();
    }

    @GetMapping("/{id}")
    public VideoGame getEquipmentByID(@PathVariable int id) throws Exception {

        Optional<VideoGame> optionalGame = videoGameRepository.findById(id);

        if (optionalGame.isPresent()) {

            return optionalGame.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipment Not Found");
        }
    }

    @GetMapping("/validated")
    public Iterable<VideoGame> getEquipmentValidated() throws Exception {

        Iterable<VideoGame> equipments = videoGameRepository.findByAbleToBorrowLike("yes");

        return equipments;
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllEquipments() throws Exception {

        videoGameRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        videoGameRepository.deleteById(id);
    }
}
