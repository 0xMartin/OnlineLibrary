package cz.utb.fai.LibraryApp.bussines.services;

import java.io.IOException;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cz.utb.fai.LibraryApp.model.Image;
import cz.utb.fai.LibraryApp.repository.ImageRepository;

/**
 * Servis, ktery umoznuje pracovat s obrazky na disku
 */
@Service
public class ImageService {

  @Autowired
  private ImageRepository imageRepository;

  /**
   * Vyhleda obrazek podle jeho ID
   * 
   * @param id ID obrazku
   * @return Obrazek
   * @throws Exception
   */
  public Image findImage(long id) throws Exception {
    Optional<Image> img = this.imageRepository.findById(id);
    if (!img.isPresent()) {
      throw new Exception(String.format("Image with ID [%d] not exists", id));
    }
    return img.get();
  }

  /**
   * Uploaduje obrazek do databaze
   * 
   * @param image Obrazek ve forme "MultipartFile"
   * @return Obrazek
   * @throws Exception
   */
  public Image uploadImage(MultipartFile file) throws Exception {
    if (file == null) {
      throw new Exception("Image is not defined");
    }

    Image image = new Image(
        this.imageRepository.count(),
        new Binary(BsonBinarySubType.BINARY, file.getBytes()));
    image = this.imageRepository.insert(image);

    return image;
  }

  /**
   * Odstrani obrazek z disku
   * 
   * @param imgID ID obrazku (jeho jmeno)
   * @throws IOException
   */
  public void deletaImage(Long imgID) throws Exception {
    Optional<Image> img = imageRepository.findById(imgID);
    if (img.isPresent()) {
      this.imageRepository.delete(img.get());
    } else {
      throw new Exception(String.format("Image [%d] not found", imgID));
    }
  }

  /**
   * Nacte obrazek z disku
   * 
   * @param imgID - ID obrazku
   * @return byte[] (JPG))
   * @throws IOException
   */
  public byte[] readImage(Image image) throws Exception {
    if (image == null) {
      throw new Exception("Image is not defined");
    }
    if (image.getImage() == null) {
      throw new Exception("Image data is null");
    }
    return image.getImage().getData();
  }

}
