package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.model.ProfileState;

public interface ProfileStateRepository extends MongoRepository<ProfileState, Long> {
    
    @Query("{name:'?0'}")
    ProfileState findItemByName(EProfileState name);

}
