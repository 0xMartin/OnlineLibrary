package cz.utb.fai.LibraryApp.bussines.services;

import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.utb.fai.LibraryApp.model.Image;
import cz.utb.fai.LibraryApp.repository.BookRepository;
import cz.utb.fai.LibraryApp.repository.BorrowHistoryRepository;
import cz.utb.fai.LibraryApp.repository.BorrowRepository;
import cz.utb.fai.LibraryApp.repository.ImageRepository;
import cz.utb.fai.LibraryApp.repository.ProfileStateRepository;
import cz.utb.fai.LibraryApp.repository.RoleRepository;
import cz.utb.fai.LibraryApp.repository.UserRepository;

/**
 * Servis urceny pro export a import databaze
 */
@Service
public class DBExportImportService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected ProfileStateRepository profileStateRepository;

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected BorrowRepository borrowRepository;

    @Autowired
    protected BorrowHistoryRepository borrowHistoryRepository;

    @Autowired
    protected ImageRepository imageRepository;

    /**
     * Exportuje vsechny kolekce v databazi do souboru ve json formatu a vsechny
     * soubory vlozi do zipu
     * 
     * @param outputStream Output stream
     * @throws Exception
     */
    public void exportDatabase(OutputStream outputStream) throws Exception {
        if (outputStream == null) {
            throw new Exception("OutputStream is null");
        }

        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        ObjectMapper objectMapper = new ObjectMapper();

        // role
        zipOutputStream.putNextEntry(new ZipEntry("roles.json"));
        String roleList = objectMapper.writeValueAsString(roleRepository.findAll());
        zipOutputStream.write(roleList.getBytes());
        zipOutputStream.closeEntry();

        // stavy profilu
        zipOutputStream.putNextEntry(new ZipEntry("profile_states.json"));
        String profileStateList = objectMapper.writeValueAsString(profileStateRepository.findAll());
        zipOutputStream.write(profileStateList.getBytes());
        zipOutputStream.closeEntry();

        // uzivatele
        zipOutputStream.putNextEntry(new ZipEntry("users.json"));
        String userList = objectMapper.writeValueAsString(userRepository.findAll());
        zipOutputStream.write(userList.getBytes());
        zipOutputStream.closeEntry();

        // knihy
        zipOutputStream.putNextEntry(new ZipEntry("books.json"));
        String bookList = objectMapper.writeValueAsString(bookRepository.findAll());
        zipOutputStream.write(bookList.getBytes());
        zipOutputStream.closeEntry();

        // vypujcky knih
        zipOutputStream.putNextEntry(new ZipEntry("borrows.json"));
        String borrowList = objectMapper.writeValueAsString(borrowRepository.findAll());
        zipOutputStream.write(borrowList.getBytes());
        zipOutputStream.closeEntry();

        // historie vypujcek knih
        zipOutputStream.putNextEntry(new ZipEntry("borrows_history.json"));
        String borrowHistoryList = objectMapper.writeValueAsString(borrowHistoryRepository.findAll());
        zipOutputStream.write(borrowHistoryList.getBytes());
        zipOutputStream.closeEntry();

        // obrazky
        zipOutputStream.putNextEntry(new ZipEntry("images.json"));
        for (Image img : imageRepository.findAll()) {
            String imageList = objectMapper.writeValueAsString(new Image.Export(img));
            zipOutputStream.write(imageList.getBytes());
        }
        zipOutputStream.closeEntry();

        // ukonceni zip streamu
        zipOutputStream.flush();
        zipOutputStream.close();
    }

    /**
     * Imporutje databazovy soubor. Jde o ZIP ktery obsahuje presne pojmenovane
     * soubor ve json formatu
     * 
     * @param file  MultipartFile
     * @param clear True -> Pred importem dat smaze vsechny data ve vsech kolekcich
     *              databaze
     * @throws Exception
     */
    public void importDatabase(MultipartFile file, boolean clear) throws Exception {
        if (file == null) {
            throw new Exception("ZIP is not defined");
        }

        // unzip files

        // drop database

        // import data from files
    }

}
