package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private IMapper mapper;
    @Autowired
    EventRepository eventRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveEvent(@RequestBody String data) throws Exception {

        Event event = new ObjectMapper().readValue(data, Event.class);
        eventRepository.save(event);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Event> getAllEvents() throws Exception {

        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable int id) throws Exception {

        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isPresent()) {

            return optionalEvent.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Event Not Found");
        }
    }

    /////////////////////////////// PATCH //////////////////////////////////



    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllEvents() throws Exception {

        eventRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        Optional<Event> optionalEvent = eventRepository.findById(id);
        Event event = optionalEvent.get();

        eventRepository.deleteById(id);
    }

    /////////////////////////////// POST //////////////////////////////////


}
