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
import java.util.Arrays;
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

      Long MAX_BORROWED_BOOKS = (Long) obj.get("MAX_BORROWED_BOOKS");
      if (MAX_BORROWED_BOOKS != null) {
        GlobalConfig.MAX_BORROWED_BOOKS = (long) MAX_BORROWED_BOOKS.longValue();
        logger.info("MAX_BORROWED_BOOKS set on: " + MAX_BORROWED_BOOKS);
      }

      Long MAX_ADMIN_COUNT = (Long) obj.get("MAX_ADMIN_COUNT");
      if (MAX_ADMIN_COUNT != null) {
        GlobalConfig.MAX_ADMIN_COUNT = (long) MAX_ADMIN_COUNT.longValue();
        if (GlobalConfig.MAX_ADMIN_COUNT < 1) {
          GlobalConfig.MAX_ADMIN_COUNT = 1;
        }
        logger.info("MAX_ADMIN_COUNT set on: " + MAX_ADMIN_COUNT);
      }

      Long BORROW_DAY_COUNT = (Long) obj.get("BORROW_DAY_COUNT");
      if (BORROW_DAY_COUNT != null) {
        GlobalConfig.BORROW_DAY_COUNT = (long) BORROW_DAY_COUNT.longValue();
        logger.info("BORROW_DAY_COUNT set on: " + BORROW_DAY_COUNT);
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
  public ApplicationRunner initRole(RoleRepository repository) {
    if (repository.count() == 0) {
      return args ->
        repository.saveAll(
          Arrays.asList(
            new Role(0, ERole.CUSTOMER),
            new Role(1, ERole.LIBRARIAN)
          )
        );
    } else {
      return null;
    }
  }

  @Bean
  public ApplicationRunner initProfileState(ProfileStateRepository repository) {
    if (repository.count() == 0) {
      return args ->
        repository.saveAll(
          Arrays.asList(
            new ProfileState(0, EProfileState.WAITING),
            new ProfileState(1, EProfileState.NOT_CONFIRMED),
            new ProfileState(2, EProfileState.CONFIRMED),
            new ProfileState(3, EProfileState.BANNED)
          )
        );
    } else {
      return null;
    }
  }

  @Bean
  public ApplicationRunner initUsers(
    UserRepository repository,
    RoleRepository roleRepository,
    ProfileStateRepository stateRepository
  ) {
    if (repository.count() == 0) {
      Role role = roleRepository.findItemByName(ERole.LIBRARIAN);
      ProfileState state = stateRepository.findItemByName(
        EProfileState.CONFIRMED
      );

      return args ->
        repository.save(
          new User(
            0,
            "Admin",
            "Admin",
            "000000/0000",
            "Not defined",
            "admin",
            "admin12345",
            state,
            role
          ).encodePassword()
        );
    } else {
      return null;
    }
  }
}
