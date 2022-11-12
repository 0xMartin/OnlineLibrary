package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.models.ProfileState;

public interface ProfileStateRepository extends MongoRepository<ProfileState, Long> {
    
    @Query("{name:'?0'}")
    ProfileState findItemByName(String name);

}
