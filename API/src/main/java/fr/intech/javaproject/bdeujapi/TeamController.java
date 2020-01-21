package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private IMapper mapper;
    @Autowired
    TeamRepository teamRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveTeam(@RequestBody String data) throws Exception {

        Team team = new ObjectMapper().readValue(data, Team.class);
        teamRepository.save(team);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<Team> getAllTeam() throws Exception {

        return teamRepository.findAll();
    }

    @GetMapping("/{id}")
    public Team getTeamByID(@PathVariable int id) throws Exception {

        Optional<Team> optionalTeam = teamRepository.findById(id);

        if (optionalTeam.isPresent()) {

            return optionalTeam.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Team Not Found");
        }
    }

    /////////////////////////////// POST //////////////////////////////////



    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllTeam() throws Exception {

        teamRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        teamRepository.deleteById(id);
    }
}