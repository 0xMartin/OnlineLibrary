package cz.utb.fai.LibraryApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class ThymeleafConfig {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

}