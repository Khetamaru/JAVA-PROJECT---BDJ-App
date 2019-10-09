package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
    public void mapperMemberWrite(Member member, String path) throws IOException {

        mapper.writeValue(new File(path), member);
    }
    @Override
    public Member mapperMemberRead(String path) throws IOException {

        Member member = mapper.readValue(new File(path), Member.class);

        return member;
    }

    @Override
    public void mapperHistoricWrite(Historic historic, String path) throws IOException {

        mapper.writeValue(new File(path), historic);
    }
    @Override
    public Historic mapperHistoricRead(String path) throws IOException {

        Historic historic = mapper.readValue(new File(path), Historic.class);

        return historic;
    }

    @Override
    public void mapperGameWrite(Game game, String path) throws IOException {

        mapper.writeValue(new File(path), game);
    }
    @Override
    public Game mapperGameRead(String path) throws IOException {

        Game game = mapper.readValue(new File(path), Game.class);

        return game;
    }

    @Override
    public void mapperBorrowWrite(Borrow borrow, String path) throws IOException {

        mapper.writeValue(new File(path), borrow);
    }
    @Override
    public Borrow mapperBorrowRead(String path) throws IOException {

        Borrow borrow = mapper.readValue(new File(path), Borrow.class);

        return borrow;
    }
}