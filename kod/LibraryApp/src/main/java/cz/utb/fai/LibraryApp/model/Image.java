package cz.utb.fai.LibraryApp.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Trida reprezentujici obrazek
 */
@Document("Images")
@Data
public class Image {

    @Id
    private Long id;

    /**
     * Data obrazku (ignorovane ve json kvuli velikosti)
     */
    @JsonIgnore
    @Field("image")
    private Binary image;

    public Image(Long id, Binary image) {
        this.id = id;
        this.image = image;
    }

    /**
     * Obrazek urceny pro export
     */
    public static class Export {
        public Long id;
        public Binary image;

        public Export(Image img) {
            this.id = img.getId();
            this.image = img.getImage();
        }
    }

}
