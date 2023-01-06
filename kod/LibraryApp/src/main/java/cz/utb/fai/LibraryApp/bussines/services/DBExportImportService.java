package cz.utb.fai.LibraryApp.bussines.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.utb.fai.LibraryApp.repository.BookRepository;
import cz.utb.fai.LibraryApp.repository.BorrowHistoryRepository;
import cz.utb.fai.LibraryApp.repository.BorrowRepository;
import cz.utb.fai.LibraryApp.repository.ProfileStateRepository;
import cz.utb.fai.LibraryApp.repository.RoleRepository;
import cz.utb.fai.LibraryApp.repository.UserRepository;

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

    public void exportDatabase(OutputStream outputStream) throws IOException {
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

        // ukonceni zip streamu
        zipOutputStream.flush();
        zipOutputStream.close();
    }

    public void importDatabase() {

    }

}
