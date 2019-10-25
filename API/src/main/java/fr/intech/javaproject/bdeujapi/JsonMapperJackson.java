package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonMapperJackson implements IMapper {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void mapperUserWrite(User user, String path) throws IOException {

        mapper.writeValue(new File(path), user);
    }
    @Override
    public User mapperUserRead(String path) throws IOException {

        User user = mapper.readValue(new File(path), User.class);

        return user;
    }

    @Override
    public void mapperMemberWrite(Location location, String path) throws IOException {

        mapper.writeValue(new File(path), location);
    }
    @Override
    public Location mapperMemberRead(String path) throws IOException {

        Location location = mapper.readValue(new File(path), Location.class);

        return location;
    }

    @Override
    public void mapperHistoricWrite(UserHistoric userHistoric, String path) throws IOException {

        mapper.writeValue(new File(path), userHistoric);
    }
    @Override
    public UserHistoric mapperHistoricRead(String path) throws IOException {

        UserHistoric userHistoric = mapper.readValue(new File(path), UserHistoric.class);

        return userHistoric;
    }

    @Override
    public void mapperGameWrite(Equipment equipment, String path) throws IOException {

        mapper.writeValue(new File(path), equipment);
    }
    @Override
    public Equipment mapperGameRead(String path) throws IOException {

        Equipment equipment = mapper.readValue(new File(path), Equipment.class);

        return equipment;
    }

    @Override
    public void mapperBorrowWrite(Loaning loaning, String path) throws IOException {

        mapper.writeValue(new File(path), loaning);
    }
    @Override
    public Loaning mapperBorrowRead(String path) throws IOException {

        Loaning loaning = mapper.readValue(new File(path), Loaning.class);

        return loaning;
    }
}