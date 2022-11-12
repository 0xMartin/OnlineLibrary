package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.models.User;

public interface UserRepository extends MongoRepository<User, Long> {
    
    @Query("{name:'?0'}")
    User findItemByName(String name);

}
