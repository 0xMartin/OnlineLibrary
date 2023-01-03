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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Zajistuje inicializaci aplikace
 */
@Configuration
public class Init {

  private static final Logger logger = LoggerFactory.getLogger(Init.class);

  @Autowired
  private AbstractApplicationContext context;

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
    if (userRepository.count() == 0 && roleRepository.count() == 0 && stateRepository.count() == 0) {
      return args -> {

        Role role = new Role(1L, ERole.LIBRARIAN);
        roleRepository.saveAll(
            Arrays.asList(
                new Role(0L, ERole.CUSTOMER),
                role));

        ProfileState state = new ProfileState(2, EProfileState.CONFIRMED);
        stateRepository.saveAll(
            Arrays.asList(
                new ProfileState(0, EProfileState.WAITING),
                new ProfileState(1, EProfileState.NOT_CONFIRMED),
                state,
                new ProfileState(3, EProfileState.BANNED)));

        userRepository.save(
            new User(
                "admin",
                "admin12345",
                "Admin",
                "Admin",
                "000000/0000",
                "Not defined",
                state,
                role).encodePassword());

      };
    } else {
      return null;
    }
  }

  @Bean
  public void registerListeners() {
    //BorrowExpirationListener listener = BeanFactory.createBean(BorrowExpirationListener.class);
    //context.addApplicationListener(listener);
  }

}
