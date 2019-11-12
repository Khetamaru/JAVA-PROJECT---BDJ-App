package fr.intech.javaproject.bdeujapi;

import java.io.IOException;

public interface IMapper {
    void mapperUserWrite(User user, String path) throws IOException;

    User mapperUserRead(String path) throws IOException;

    void mapperMemberWrite(Location location, String path) throws IOException;

    Location mapperMemberRead(String path) throws IOException;

    void mapperHistoricWrite(UserHistoric userHistoric, String path) throws IOException;

    UserHistoric mapperHistoricRead(String path) throws IOException;

    void mapperGameWrite(Equipment equipment, String path) throws IOException;

    Equipment mapperGameRead(String path) throws IOException;

    void mapperBorrowWrite(Loaning loaning, String path) throws IOException;

    Loaning mapperBorrowRead(String path) throws IOException;
}
