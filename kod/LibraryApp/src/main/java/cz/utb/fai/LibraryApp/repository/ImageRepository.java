package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.utb.fai.LibraryApp.model.Image;

/**
 * Rozhrani pristukujici ke kolekci obrazku
 */
public interface ImageRepository extends MongoRepository<Image, Long> {

}
