package cz.utb.fai.LibraryApp;

import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.bussines.enums.ERole;
import cz.utb.fai.LibraryApp.model.ProfileState;
import cz.utb.fai.LibraryApp.model.Role;
import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.ProfileStateRepository;
import cz.utb.fai.LibraryApp.repository.RoleRepository;
import cz.utb.fai.LibraryApp.repository.UserRepository;
import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Zajistuje inicializaci aplikace
 */
@Configuration
public class Init {

  private static final Logger logger = LoggerFactory.getLogger(Init.class);

  @Bean
  public static void loadConfigFromFile() {
    try {
      JSONParser parser = new JSONParser();
      JSONObject obj = null;

      obj = (JSONObject) parser.parse(new FileReader("config.json"));

      Long BORROW_DAY_COUNT = (Long) obj.get("BORROW_DAY_COUNT");
      if (BORROW_DAY_COUNT != null) {
        GlobalConfig.MAX_BORROWED_BOOKS = (long) BORROW_DAY_COUNT.longValue();
        logger.info("BORROW_DAY_COUNT set on: " + BORROW_DAY_COUNT);
      }

      Long MAX_BORROWED_BOOKS = (Long) obj.get("MAX_BORROWED_BOOKS");
      if (MAX_BORROWED_BOOKS != null) {
        GlobalConfig.MAX_BORROWED_BOOKS = (long) MAX_BORROWED_BOOKS.longValue();
        logger.info("MAX_BORROWED_BOOKS set on: " + MAX_BORROWED_BOOKS);
      }

      Long MIN_PASSWORD_LENGTH = (Long) obj.get("MIN_PASSWORD_LENGTH");
      if (MIN_PASSWORD_LENGTH != null) {
        GlobalConfig.MIN_PASSWORD_LENGTH = (long) MIN_PASSWORD_LENGTH.longValue();
        logger.info("MIN_PASSWORD_LENGTH set on: " + MIN_PASSWORD_LENGTH);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Bean
  public ApplicationRunner initDatabase(
      UserRepository userRepository,
      RoleRepository roleRepository,
      ProfileStateRepository stateRepository) {
    return args -> {

      // role
      if (!roleRepository.findItemByName(ERole.CUSTOMER).isPresent()) {
        roleRepository.insert(new Role(0L, ERole.CUSTOMER));
      }
      if (!roleRepository.findItemByName(ERole.LIBRARIAN).isPresent()) {
        roleRepository.insert(new Role(1L, ERole.LIBRARIAN));
      }

      // stavy porfilu
      if (!stateRepository.findItemByName(EProfileState.WAITING).isPresent()) {
        stateRepository.insert(new ProfileState(0L, EProfileState.WAITING));
      }
      if (!stateRepository.findItemByName(EProfileState.NOT_CONFIRMED).isPresent()) {
        stateRepository.insert(new ProfileState(1L, EProfileState.NOT_CONFIRMED));
      }
      if (!stateRepository.findItemByName(EProfileState.CONFIRMED).isPresent()) {
        stateRepository.insert(new ProfileState(2L, EProfileState.CONFIRMED));
      }
      if (!stateRepository.findItemByName(EProfileState.BANNED).isPresent()) {
        stateRepository.insert(new ProfileState(3L, EProfileState.BANNED));
      }

      // admin uzivatel
      if (!userRepository.findById("admin").isPresent()) {
        userRepository.save(
            new User(
                "admin",
                "admin12345",
                "Admin",
                "Admin",
                "000000/0000",
                "Not defined",
                stateRepository.findItemByName(EProfileState.CONFIRMED).get(),
                roleRepository.findItemByName(ERole.LIBRARIAN).get()).encodePassword());
      }
    };
  }

}
