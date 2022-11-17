package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.model.ProfileState;

/**
 * Rozhrani pristukujici ke kolekci stavu profilu v databazi
 */
public interface ProfileStateRepository extends MongoRepository<ProfileState, Long> {
    
    @Query("{name:'?0'}")
    ProfileState findItemByName(EProfileState name);

}
