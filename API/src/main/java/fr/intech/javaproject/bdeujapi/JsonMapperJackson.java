package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class JsonMapperJackson {

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        //INSERT MULTIPLES

        //User user = new User(0, "default", "default", "default", "default@gmail.com");
        //mapper.writeValue(new File("toto.json"), user);

        mapper.readValue("toto.json", User);
    }
}
