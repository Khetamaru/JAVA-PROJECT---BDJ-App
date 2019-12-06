package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IMapper mapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserHistoricRepository userHistoricRepository;
    @Autowired
    LoaningRepository loaningRepository;
    @Autowired
    LocationRepository locationRepository;

    /////////////////////////////// PUT //////////////////////////////////

    @PutMapping
    public void saveUser(@RequestBody String data) throws Exception {

        User user = new ObjectMapper().readValue(data, User.class);
        userRepository.save(user);

        Date date = new Date();
        UserHistoric userHistoric = new UserHistoric(user.getIdUser(), user.getLevel(), user.getSurname(), user.getLogin(), user.getPassword(), user.getMail(), date);
        userHistoricRepository.save(userHistoric);
    }

    /////////////////////////////// GET //////////////////////////////////

    @GetMapping
    public Iterable<User> getAllUsers() throws Exception {

        return userRepository.findAll();
    }

    @GetMapping("/allExept/{id}")
    public Iterable<User> getAllUsersExept(@PathVariable int id) throws Exception {

        return userRepository.findAllByIdUserNotLike(id);
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

    /////////////////////////////// PATCH //////////////////////////////////

    @PatchMapping("/levelUp/{id}")
    public void levelUp(@PathVariable int id) throws Exception {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            String userString = "{" +
                                    "\"idUser\" : \"" + user.getIdUser() + "\"," +
                                    "\"surname\" : \"" + user.getSurname() + "\"," +
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

    /////////////////////////////// DELETE //////////////////////////////////

    @DeleteMapping
    public void deleteAllUsers() throws Exception {

        userRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();

        Date date = new Date();
        UserHistoric userHistoric = new UserHistoric(id, "", user.getSurname(), "", "", "", date);
        userHistoricRepository.save(userHistoric);

        loaningRepository.deleteByUser(user);
        locationRepository.deleteByUser(user);

        userRepository.deleteById(id);
    }

    /////////////////////////////// POST //////////////////////////////////

    @PostMapping("/login")
    public User login(@RequestBody String data) throws Exception {

        Login login = new ObjectMapper().readValue(data, Login.class);
        
        Optional<User> user = userRepository.findByLoginAndPassword(login.getLog(), login.getPassword());


        if (user.isPresent()) {

            System.out.println("User is ok");
            return user.get();
        }
        else {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Bad login or Password");
        }
    }
}
