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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IMapper mapper;
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public void saveUser(String path) throws Exception {

        User user = mapper.mapperUserRead(path);
        userRepository.save(user);
    }

    @GetMapping
    public Iterable<User> getAllUsers() throws Exception {

        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable int id) throws Exception {

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    public void deleteAllUsers() throws Exception {

        userRepository.deleteAll();
    }
    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        userRepository.deleteById(id);
    }

    @GetMapping("/updateLogin/{id}/{login}")
    public void updateLogin(@PathVariable int id, @PathVariable String login) throws Exception {

        userRepository.updateLogin(login, id);
    }

    @GetMapping("/updateSurname/{id}/{surname}")
    public void updateSurname(@PathVariable int id, @PathVariable String surname) throws Exception {

        userRepository.updateLogin(surname, id);
    }

    @GetMapping("/updatePassword/{id}/{password}")
    public void updatePassword(@PathVariable int id, @PathVariable String password) throws Exception {

        userRepository.updateLogin(password, id);
    }

    @GetMapping("/updateMail/{id}/{mail}")
    public void updateMail(@PathVariable int id, @PathVariable String mail) throws Exception {

        userRepository.updateLogin(mail, id);
    }
}
