package fr.intech.javaproject.bdeujapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomController {

    @GetMapping("/toto")
    public void helloSayer() {
        System.out.println("hello world");
    }
}
