package cz.utb.fai.LibraryApp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.utb.fai.LibraryApp.model.Sequence;

/**
 * Repositar pro prace se sequence objektem (vyuzivan jen pro autoincrement ID vsech entit v databazi)
 */
public interface SequenceRepository extends MongoRepository<Sequence, String> {

    Optional<Sequence> findById(String id);

}
