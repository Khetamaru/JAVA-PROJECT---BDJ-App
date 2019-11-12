package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private IMapper mapper;
    @Autowired
    LocationRepository locationRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveLocation(@RequestBody String data) throws Exception {

        Location location = new ObjectMapper().readValue(data, Location.class);
        locationRepository.save(location);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Location> getAllLocations() throws Exception {

        return locationRepository.findAll();
    }

    @GetMapping("/today")
    public Iterable<Location> getAllLocationsToday() throws Exception {

        Date date = Calendar.getInstance().getTime();

        return locationRepository.findByDateGreaterThan(date);
    }

    @GetMapping("/{id}")
    public Location getLocationByID(@PathVariable int id) throws Exception {

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
    public void deleteAllLocations() throws Exception {

        locationRepository.deleteAll();
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        locationRepository.deleteById(id);
    }
}
