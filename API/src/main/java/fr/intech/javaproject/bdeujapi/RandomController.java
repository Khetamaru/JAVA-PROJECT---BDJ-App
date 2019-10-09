package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class RandomController {

    @Autowired
    private IMapper mapper;
    @Autowired
    UserRepository repo;




    @GetMapping("/toto")
    public void helloSayer() throws IOException {

        User user = mapper.mapperUserRead("toto.json");
        repo.save(user);

        System.out.println(user.getLogin());
        System.out.println("hello world");
    }
}
