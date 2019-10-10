package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historic")
public class HistoricController {

    @Autowired
    private IMapper mapper;
    @Autowired
    HistoricRepository historicRepository;

    @GetMapping
    public void saveHistoric(Historic historic) throws Exception {

        historicRepository.save(historic);
    }

    @GetMapping
    public Iterable<Historic> getAllHistorics() throws Exception {

        return historicRepository.findAll();
    }

    @GetMapping("/{id}")
    public Historic getHistoricByID(@PathVariable int id) throws Exception {

        Optional<Historic> optionalHistoric = historicRepository.findById(id);
        if (optionalHistoric.isPresent()) {
            return optionalHistoric.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    public void deleteAllHistories() throws Exception {

        historicRepository.deleteAll();
    }
    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        historicRepository.deleteById(id);
    }

    @GetMapping("/updateOrigin/{id}/{origin}")
    public void updateOrigin(@PathVariable int id, @PathVariable String origin) throws Exception {

        historicRepository.updateOrigin(origin, id);
    }

    @GetMapping("/updateAction/{id}/{action}")
    public void updateAction(@PathVariable int id, @PathVariable String action) throws Exception {

        historicRepository.updateAction(action, id);
    }

    @GetMapping("/updateDate/{id}/{date}")
    public void updateDate(@PathVariable int id, @PathVariable String date) throws Exception {

        historicRepository.updateDate(date, id);
    }
}
