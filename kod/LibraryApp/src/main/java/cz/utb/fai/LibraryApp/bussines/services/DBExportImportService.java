package cz.utb.fai.LibraryApp.bussines.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.bussines.enums.ERole;
import cz.utb.fai.LibraryApp.model.Book;
import cz.utb.fai.LibraryApp.model.Borrow;
import cz.utb.fai.LibraryApp.model.BorrowHistory;
import cz.utb.fai.LibraryApp.model.Image;
import cz.utb.fai.LibraryApp.model.ProfileState;
import cz.utb.fai.LibraryApp.model.Role;
import cz.utb.fai.LibraryApp.model.User;
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

    public static final String ROLE_FILE = "roles.json";
    public static final String PROFILE_STATE_FILE = "profile_states.json";
    public static final String USER_FILE = "users.json";
    public static final String BOOK_FILE = "books.json";
    public static final String BORROW_FILE = "borrows.json";
    public static final String BORROW_HISTORY_FILE = "borrows_history.json";
    public static final String IMAGE_FILE = "images.json";

    public static final String DB_IMPORT_DIR = "dbimport";

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

        StringBuilder buffer = new StringBuilder();

        // role
        zipOutputStream.putNextEntry(new ZipEntry(DBExportImportService.ROLE_FILE));
        roleRepository.findAll().forEach((e) -> {
            try {
                buffer.append(objectMapper.writeValueAsString(e));
            } catch (JsonProcessingException e1) {
            }
            buffer.append('\n');
        });
        zipOutputStream.write(buffer.toString().getBytes());
        buffer.delete(0, buffer.length());
        zipOutputStream.closeEntry();

        // stavy profilu
        zipOutputStream.putNextEntry(new ZipEntry(DBExportImportService.PROFILE_STATE_FILE));
        profileStateRepository.findAll().forEach((e) -> {
            try {
                buffer.append(objectMapper.writeValueAsString(e));
            } catch (JsonProcessingException e1) {
            }
            buffer.append('\n');
        });
        zipOutputStream.write(buffer.toString().getBytes());
        buffer.delete(0, buffer.length());
        zipOutputStream.closeEntry();

        // uzivatele
        zipOutputStream.putNextEntry(new ZipEntry(DBExportImportService.USER_FILE));
        userRepository.findAll().forEach((e) -> {
            try {
                buffer.append(objectMapper.writeValueAsString(e));
            } catch (JsonProcessingException e1) {
            }
            buffer.append('\n');
        });
        zipOutputStream.write(buffer.toString().getBytes());
        buffer.delete(0, buffer.length());
        zipOutputStream.closeEntry();

        // knihy
        zipOutputStream.putNextEntry(new ZipEntry(DBExportImportService.BOOK_FILE));
        bookRepository.findAll().forEach((e) -> {
            try {
                buffer.append(objectMapper.writeValueAsString(e));
            } catch (JsonProcessingException e1) {
            }
            buffer.append('\n');
        });
        zipOutputStream.write(buffer.toString().getBytes());
        buffer.delete(0, buffer.length());
        zipOutputStream.closeEntry();

        // vypujcky knih
        zipOutputStream.putNextEntry(new ZipEntry(DBExportImportService.BORROW_FILE));
        borrowRepository.findAll().forEach((e) -> {
            try {
                buffer.append(objectMapper.writeValueAsString(e));
            } catch (JsonProcessingException e1) {
            }
            buffer.append('\n');
        });
        zipOutputStream.write(buffer.toString().getBytes());
        buffer.delete(0, buffer.length());
        zipOutputStream.closeEntry();

        // historie vypujcek knih
        zipOutputStream.putNextEntry(new ZipEntry(DBExportImportService.BORROW_HISTORY_FILE));
        borrowHistoryRepository.findAll().forEach((e) -> {
            try {
                buffer.append(objectMapper.writeValueAsString(e));
            } catch (JsonProcessingException e1) {
            }
            buffer.append('\n');
        });
        zipOutputStream.write(buffer.toString().getBytes());
        buffer.delete(0, buffer.length());
        zipOutputStream.closeEntry();

        // obrazky
        zipOutputStream.putNextEntry(new ZipEntry(DBExportImportService.IMAGE_FILE));
        for (Image img : imageRepository.findAll()) {
            try {
                buffer.append(objectMapper.writeValueAsString(new Image.Export(img)));
            } catch (JsonProcessingException e1) {
            }
            buffer.append('\n');
        }
        zipOutputStream.write(buffer.toString().getBytes());
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

        byte[] buffer = new byte[1024];

        // create dir
        Files.createDirectories(Paths.get(DBExportImportService.DB_IMPORT_DIR));

        // unzip files
        ZipInputStream zis = new ZipInputStream(file.getInputStream());
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File f = new File(DBExportImportService.DB_IMPORT_DIR, zipEntry.getName());
            FileOutputStream fos = new FileOutputStream(f);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

        // drop database
        if (clear) {
            this.userRepository.deleteAll(this.userRepository.findAll());
            this.profileStateRepository.deleteAll(this.profileStateRepository.findAll());
            this.roleRepository.deleteAll(this.roleRepository.findAll());
            this.borrowHistoryRepository.deleteAll(this.borrowHistoryRepository.findAll());
            this.borrowRepository.deleteAll(this.borrowRepository.findAll());
            this.bookRepository.deleteAll(this.bookRepository.findAll());
            this.imageRepository.deleteAll(this.imageRepository.findAll());
        }

        // import data from files
        JSONParser parser = new JSONParser();
        JSONObject obj = null, obj2 = null, obj3 = null;
        File[] listOfFiles = new File[] {
                new File(DBExportImportService.DB_IMPORT_DIR, ROLE_FILE),
                new File(DBExportImportService.DB_IMPORT_DIR, PROFILE_STATE_FILE),
                new File(DBExportImportService.DB_IMPORT_DIR, IMAGE_FILE),
                new File(DBExportImportService.DB_IMPORT_DIR, BOOK_FILE),
                new File(DBExportImportService.DB_IMPORT_DIR, USER_FILE),
                new File(DBExportImportService.DB_IMPORT_DIR, BORROW_HISTORY_FILE),
                new File(DBExportImportService.DB_IMPORT_DIR, BORROW_FILE)
        };
        for (File f : listOfFiles) {
            if (!f.exists())
                continue;
            if (f.isFile()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.length() > 0) {
                            obj = (JSONObject) parser.parse(line);
                            if (obj == null)
                                continue;
                            switch (f.getName()) {
                                case DBExportImportService.BOOK_FILE:

                                    /************************************************************************************/
                                    // BOOK
                                    obj2 = (JSONObject) obj.get("image");
                                    Book e1 = new Book(
                                            (Long) obj.get("id"),
                                            (String) obj.get("name"),
                                            (String) obj.get("author"),
                                            (Long) obj.get("pageCount"),
                                            (Long) obj.get("yearOfPublication"),
                                            (String) obj.get("description"),
                                            this.imageRepository.findById((Long) obj2.get("id")).get(),
                                            (Long) obj.get("available"));
                                    this.bookRepository.insert(e1);
                                    // BOOK
                                    /************************************************************************************/

                                    break;
                                case DBExportImportService.BORROW_FILE:

                                    /************************************************************************************/
                                    // BORROW
                                    obj2 = (JSONObject) obj.get("book");
                                    Borrow e2 = new Borrow(
                                            (Long) obj.get("id"),
                                            new java.util.Date((Long) obj.get("date")),
                                            ((Long) obj.get("expireAt") - (Long) obj.get("date")),
                                            (String) obj.get("user_id"),
                                            this.bookRepository.findById((Long) obj2.get("id")).get());
                                    this.borrowRepository.insert(e2);
                                    // BORROW
                                    /************************************************************************************/

                                    break;
                                case DBExportImportService.BORROW_HISTORY_FILE:

                                    /************************************************************************************/
                                    // BORROW HISTORY
                                    BorrowHistory e3 = new BorrowHistory(
                                            (Long) obj.get("id"),
                                            new java.util.Date((Long) obj.get("date")),
                                            (String) obj.get("user_id"),
                                            (Long) obj.get("book_id"),
                                            (String) obj.get("book_name"),
                                            (String) obj.get("book_author"));
                                    this.borrowHistoryRepository.insert(e3);
                                    // BORROW HISTORY
                                    /************************************************************************************/

                                    break;
                                case DBExportImportService.IMAGE_FILE:

                                    /************************************************************************************/
                                    // IMAGE
                                    Image e4 = new Image(
                                            (Long) obj.get("id"),
                                            new org.bson.types.Binary(obj.get("image").toString().getBytes()));
                                    this.imageRepository.insert(e4);
                                    // IMAGE
                                    /************************************************************************************/

                                    break;
                                case DBExportImportService.PROFILE_STATE_FILE:

                                    /************************************************************************************/
                                    // PROFILE STATE
                                    ProfileState e5 = new ProfileState(
                                            (Long) obj.get("id"),
                                            EProfileState.fromString((String) obj.get("name")));
                                    this.profileStateRepository.insert(e5);
                                    // PROFILE STATE
                                    /************************************************************************************/

                                    break;
                                case DBExportImportService.ROLE_FILE:

                                    /************************************************************************************/
                                    // ROLE
                                    Role e6 = new Role(
                                            (Long) obj.get("id"),
                                            ERole.fromString((String) obj.get("name")));
                                    this.roleRepository.insert(e6);
                                    // ROLE
                                    /************************************************************************************/

                                    break;
                                case DBExportImportService.USER_FILE:

                                    /************************************************************************************/
                                    // USER
                                    obj2 = (JSONObject) obj.get("state");
                                    obj3 = (JSONObject) obj.get("role");
                                    User e7 = new User(
                                            (String) obj.get("username"),
                                            (String) obj.get("password"),
                                            (String) obj.get("name"),
                                            (String) obj.get("surname"),
                                            (String) obj.get("personalID"),
                                            (String) obj.get("address"),
                                            this.profileStateRepository.findById((Long) obj2.get("id")).get(),
                                            this.roleRepository.findById((Long) obj3.get("id")).get());
                                    this.userRepository.insert(e7);
                                    // USER
                                    /************************************************************************************/

                                    break;
                            }
                        }
                    }
                    reader.close();
                }
            }
        }
    }

}
