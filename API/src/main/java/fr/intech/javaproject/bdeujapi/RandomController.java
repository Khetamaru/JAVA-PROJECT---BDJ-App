package fr.intech.javaproject.bdeujapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RandomController {

    @Autowired
    private IMapper mapper;


    @GetMapping("/toto")
    public void helloSayer() throws IOException {

        User user = mapper.mapperUserRead("toto.json");
        System.out.println(user.getLogin());
        System.out.println("hello world");
    }
}
