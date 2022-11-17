package cz.utb.fai.LibraryApp.bussines.services;

import cz.utb.fai.LibraryApp.GlobalConfig;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Servis, ktery umoznuje pracovat s obrazky na disku
 */
@Service
public class ImageService {

  /**
   * Uploaduje obrazek na disk do predem urceneho adresare "GlobalConfig.UPLOAD_DIRECTORY"
   * @param image Obrazek ve forme "MultipartFile"
   * @return Nazev obrazku na disku
   * @throws Exception
   */
  public String uploadImage(MultipartFile image) throws Exception {
    if (image == null) {
      throw new Exception("Image is not defined");
    }

    String extension = "";
    String originalFilename = image.getOriginalFilename();
    if (originalFilename == null) {
      throw new Exception("Wrong image extension");
    }
    int i = originalFilename.lastIndexOf('.');
    if (i > 0) {
      extension = originalFilename.substring(i + 1);
    }

    UUID uuid = UUID.randomUUID();
    String nameOfImage = uuid.toString() + "." + extension;
    Path path = Paths.get(GlobalConfig.UPLOAD_DIRECTORY, originalFilename);

    Files.write(path, image.getBytes());
    return nameOfImage;
  }

  public void deletaImage(String imgUrl) {}
}
