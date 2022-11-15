package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.model.User;

public interface UserRepository extends MongoRepository<User, String> {   

    @Query("{name:'?0'}")
    User findItemByName(String name);

    @Query("{surname:'?0'}")
    User findItemBySurname(String surname);

    @Query("{role:'?0'}")
    User findItemByRoleID(long roleID);

    public long count();
}
