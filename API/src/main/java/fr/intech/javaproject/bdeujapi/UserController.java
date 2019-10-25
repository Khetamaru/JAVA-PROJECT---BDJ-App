package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IMapper mapper;
    @Autowired
    UserRepository userRepository;

    @PutMapping
    public void saveUser(@RequestBody String data) throws Exception {

        User user = new ObjectMapper().readValue(data, User.class);
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

    @GetMapping("/unable")
    public User getUserById(int id) throws Exception {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {

            return optionalUser.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    @PatchMapping("/levelUp/{id}")
    public void levelUp(@PathVariable int id) throws Exception {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            String userString = "{" +
                                    "\"idUser\" : \"" + user.getIdUser() + "\"," +
                                    "\"surname\" : \"" + user.getPseudo() + "\"," +
                                    "\"login\" : \"" + user.getLogin() + "\"," +
                                    "\"password\" : \"" + user.getPassword() + "\"," +
                                    "\"mail\" : \"" + user.getMail() + "\"," +
                                    "\"level\" : \"admin\""  +
                                "}";

            saveUser(userString);
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    @GetMapping("/byLogin/{login}")
    public User getUserByLogin(@PathVariable String login) throws Exception {

        Optional<User> optionalUser = userRepository.findByLogin(login);

        if (optionalUser.isPresent()) {

            return optionalUser.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    @DeleteMapping
    public void deleteAllUsers() throws Exception {

        userRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        userRepository.deleteById(id);
    }

    /*@PatchMapping("/updateLogin/{id}/{login}")
    public void updateLogin(@PathVariable int id, @PathVariable String login) throws Exception {

        userRepository.updateLogin(login, id);
    }

    @PatchMapping("/updateSurname/{id}/{surname}")
    public void updateSurname(@PathVariable int id, @PathVariable String surname) throws Exception {

        userRepository.updateLogin(surname, id);
    }

    @PatchMapping("/updatePassword/{id}/{password}")
    public void updatePassword(@PathVariable int id, @PathVariable String password) throws Exception {

        userRepository.updateLogin(password, id);
    }

    @PatchMapping("/updateMail/{id}/{mail}")
    public void updateMail(@PathVariable int id, @PathVariable String mail) throws Exception {

        userRepository.updateLogin(mail, id);
    }*/

    @PostMapping("/login")
    public User login(@RequestBody String data) throws Exception {

        Login login = new ObjectMapper().readValue(data, Login.class);
        
        Optional<User> user = userRepository.findByLoginAndPassword(login.getLog(), login.getPassword());


        if (user.isPresent()) {

            System.out.println(user.get().getLogin());
            System.out.println(user.get().getPassword());
            return user.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Bad login or Password");
        }
    }
}
