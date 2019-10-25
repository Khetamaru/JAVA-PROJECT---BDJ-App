package fr.intech.javaproject.bdeujapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/historic")
public class UserHistoricController {

    @Autowired
    private IMapper mapper;
    @Autowired
    UserHistoricRepository userHistoricRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveHistoric(UserHistoric userHistoric) throws Exception {

        userHistoricRepository.save(userHistoric);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<UserHistoric> getAllHistorics() throws Exception {

        return userHistoricRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserHistoric getHistoricByID(@PathVariable int id) throws Exception {

        Optional<UserHistoric> optionalHistoric = userHistoricRepository.findById(id);
        if (optionalHistoric.isPresent()) {
            return optionalHistoric.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllHistories() throws Exception {

        userHistoricRepository.deleteAll();
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        userHistoricRepository.deleteById(id);
    }

    /////////////////////////////// PATCH //////////////////////////////////

    @PatchMapping("/updateOrigin/{id}/{origin}")
    public void updateOrigin(@PathVariable int id, @PathVariable String origin) throws Exception {

        userHistoricRepository.updateOrigin(origin, id);
    }

    @PatchMapping("/updateAction/{id}/{action}")
    public void updateAction(@PathVariable int id, @PathVariable String action) throws Exception {

        userHistoricRepository.updateAction(action, id);
    }

    @PatchMapping("/updateDate/{id}/{date}")
    public void updateDate(@PathVariable int id, @PathVariable String date) throws Exception {

        userHistoricRepository.updateDate(date, id);
    }
}
