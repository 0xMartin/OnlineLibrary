package cz.utb.fai.LibraryApp.bussines.services;

import java.io.File;
import java.io.IOException;
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
   * Nazev adresare do ktereho se budou uploadovat obrazky (cesta k adresari se
   * bere relativne k .jar webove app)
   */
  public static final String UPLOAD_DIRECTORY = "images";

  /**
   * Uploaduje obrazek na disk do predem urceneho adresare
   * "GlobalConfig.UPLOAD_DIRECTORY"
   * 
   * @param image Obrazek ve forme "MultipartFile"
   * @return Nazev obrazku na disku
   * @throws Exception
   */
  public String uploadImage(MultipartFile image) throws Exception, IOException {
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
    Path path = Paths.get(ImageService.UPLOAD_DIRECTORY, nameOfImage);

    Files.write(path, image.getBytes());
    return nameOfImage;
  }

  /**
   * Odstrani obrazek z disku
   * 
   * @param imgID ID obrazku (jeho jmeno)
   * @throws IOException
   */
  public void deletaImage(String imgID) throws IOException {
    Path path = Paths.get(ImageService.UPLOAD_DIRECTORY, imgID);
    Files.deleteIfExists(path);
  }

  /**
   * Nacte obrazek z disku
   * 
   * @param imgID - ID obrazku (jeho nazev)
   * @return byte[] (JPG))
   * @throws IOException
   */
  public byte[] readImage(String imgID) throws IOException {
    if (dirTraversalCheck(imgID)) {
      return null;
    }
    Path path = Paths.get(ImageService.UPLOAD_DIRECTORY, imgID);
    return Files.readAllBytes(path);
  }

  /**
   * Kontrola dir travelsar
   * 
   * @param id - ID obrazku (jeho nazev)
   * @return True -> jde o dir travelsar
   * @throws IOException
   */
  private boolean dirTraversalCheck(String path) throws IOException {
    String normalized_path = Paths.get(path).normalize().toString();
    File file = new File(ImageService.UPLOAD_DIRECTORY, normalized_path);
    File uploadDir = new File(ImageService.UPLOAD_DIRECTORY);
    return !file.getCanonicalPath().startsWith(uploadDir.getCanonicalPath());
  }

}
