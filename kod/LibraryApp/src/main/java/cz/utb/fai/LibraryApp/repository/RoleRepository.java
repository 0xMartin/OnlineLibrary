package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.model.Role;

public interface RoleRepository extends MongoRepository<Role, Long> {
    
    @Query("{name:'?0'}")
    Role findItemByName(String name);

}
