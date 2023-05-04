package cz.utb.fai.LibraryApp.bussines.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cz.utb.fai.LibraryApp.model.Book;
import lombok.Data;

/**
 * Servis urceny pro vyhledavani stranek obsahujicich specifikovany text
 */
@Service
public class SearchService {

    @Data
    public static class SearchEntry {
        private String title;
        private String url;

        public SearchEntry(String title, String url) {
            this.title = title;
            this.url = url;
        }
    }

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * Adresa na ktere bezi tato aplikace
     */
    public static String HOST = "http://localhost:8080";

    /**
     * Seznam vsech volne dostupnych stranek a jejich URL
     */
    public static SearchEntry[] PAGES = new SearchEntry[] {
            new SearchEntry("Home page", "/home"),
            new SearchEntry("About page", "/home/about"),
            new SearchEntry("Login page", "/auth/login"),
            new SearchEntry("Register page", "/auth/register")
    };

    /**
     * Prohleda vsechen volne dostupny obsah a pokusi se najit text
     * 
     * @param text - Hledany text
     * @return Seznam vsech URL stranek kde se tento text nachazi
     */
    public List<SearchService.SearchEntry> search(String text) throws Exception {
        if (text.length() < 3) {
            throw new Exception("The search text is too short. The minimal length is 3 characters");
        }

        List<SearchEntry> URLs = new ArrayList<SearchEntry>();

        // vyhledavani v databazi knih
        List<Criteria> criterias = new LinkedList<>();
        criterias.add(Criteria.where("name").regex(text, "i"));
        criterias.add(Criteria.where("author").regex(text, "i"));
        try {
            criterias.add(Criteria.where("yearOfPublication").is(Long.parseLong(text)));
        } catch (Exception ex) {
        }
        criterias.add(Criteria.where("description").regex(text, "i"));
        Query query = Query.query(new Criteria().orOperator(criterias));
        List<Book> books = mongoTemplate.find(query, Book.class);

        books.stream().forEach((b) -> {
            URLs.add(new SearchEntry(
                    String.format("Book Info Page \"%s\"", b.getName()),
                    String.format("/home/info?id=%d", b.getId())));
        });

        // vyhledavani v textu normalnich stranek
        for (SearchEntry entry : PAGES) {
            try {
                String body = getHTMLContent(entry.getUrl()).toLowerCase();
                if (body != null) {
                    if (body.contains(text.toLowerCase())) {
                        URLs.add(entry);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return URLs;
    }

    public String getHTMLContent(String url) throws Exception {
        URLConnection connection = new URL(HOST + url).openConnection();
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                Charset.forName("UTF-8")));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

}
