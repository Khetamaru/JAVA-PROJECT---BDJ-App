package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class LocationController {

    @Autowired
    private IMapper mapper;
    @Autowired
    LocationRepository locationRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveMember(@RequestBody String data) throws Exception {

        Location location = new ObjectMapper().readValue(data, Location.class);
        locationRepository.save(location);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Location> getAllMembers() throws Exception {

        return locationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Location getMemberByID(@PathVariable int id) throws Exception {

        Optional<Location> optionalMember = locationRepository.findById(id);
        if (optionalMember.isPresent()) {
            return optionalMember.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllMembers() throws Exception {

        locationRepository.deleteAll();
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        locationRepository.deleteById(id);
    }

    /////////////////////////////// PATCH //////////////////////////////////

    @PatchMapping("/updatePlace/{id}/{place}")
    public void updatePlace(@PathVariable int id, @PathVariable String place) throws Exception {

        locationRepository.updatePlace(place, id);
    }

    @PatchMapping("/updateStartDate/{id}/{startDate}")
    public void updateStartDate(@PathVariable int id, @PathVariable Date startDate) throws Exception {

        locationRepository.updateStartDate(startDate, id);
    }

    @PatchMapping("/updateEndDate/{id}/{endDate}")
    public void updateEndDate(@PathVariable int id, @PathVariable Date endDate) throws Exception {

        locationRepository.updateEndDate(endDate, id);
    }
}
