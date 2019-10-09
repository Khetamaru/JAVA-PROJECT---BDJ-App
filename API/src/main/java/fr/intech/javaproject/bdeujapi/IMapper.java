package fr.intech.javaproject.bdeujapi;

import java.io.IOException;

public interface IMapper {
    void mapperUserWrite(User user, String path) throws IOException;

    User mapperUserRead(String path) throws IOException;

    void mapperMemberWrite(Member member, String path) throws IOException;

    Member mapperMemberRead(String path) throws IOException;

    void mapperHistoricWrite(Historic historic, String path) throws IOException;

    Historic mapperHistoricRead(String path) throws IOException;

    void mapperGameWrite(Game game, String path) throws IOException;

    Game mapperGameRead(String path) throws IOException;

    void mapperBorrowWrite(Borrow borrow, String path) throws IOException;

    Borrow mapperBorrowRead(String path) throws IOException;
}
