package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.bussines.enums.ERole;
import cz.utb.fai.LibraryApp.model.Role;

/**
 * Rozhrani pristukujici ke kolekci uzivatelskych roli v databazi
 */
public interface RoleRepository extends MongoRepository<Role, Long> {
    
    @Query("{name:'?0'}")
    Role findItemByName(ERole name);

}
