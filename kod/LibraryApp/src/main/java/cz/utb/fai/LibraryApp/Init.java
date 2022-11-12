package cz.utb.fai.LibraryApp;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cz.utb.fai.LibraryApp.repository.RoleRepository;
import cz.utb.fai.LibraryApp.repository.UserRepository;

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public ApplicationRunner initRole(RoleRepository repository) {
        if (repository.count() == 0) {
            return null;
        } else {
            return null;
        }
    }

    @Bean
    public ApplicationRunner initUsers(UserRepository repository) {
        if (repository.count() == 0) {
            return null;
        } else {
            return null;
        }
    }

}
