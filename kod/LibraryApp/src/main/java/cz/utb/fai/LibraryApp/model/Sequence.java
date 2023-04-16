package cz.utb.fai.LibraryApp.model;

import java.util.Optional;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import cz.utb.fai.LibraryApp.repository.SequenceRepository;
import lombok.Data;

/**
 * Entita je vyuzivana ciste jen pro pridelovani unikatnich ID vsech entit v databazi (nahrada za autoincrement jako je u mysql)
 */
@Document(collection = "sequence")
@Data
public class Sequence {
    @Id
    private String id;

    @Field("seq")
    private long seq;

    public static long generateUniqueID(SequenceRepository sequenceRepository, String repositoryID) {
        Optional<Sequence> opt = sequenceRepository.findById(repositoryID);
        if(opt.isPresent()) {
            // ziska hodnotu sequence entity pro specifikovene ID "repositar" a inkrementuje jeho hodnotu
            Sequence sequence = opt.get();
            long id = sequence.getSeq() + 1;
            sequence.setSeq(id);
            sequenceRepository.save(sequence);
            return id;
        } else {
            // sequence enita neexistuje, vytovri v databazi novou
            Sequence sequence = new Sequence();
            long id = 0;
            sequence.setSeq(id);
            sequence.setId(repositoryID);
            sequenceRepository.save(sequence);
            return id;
        }
    }
}
