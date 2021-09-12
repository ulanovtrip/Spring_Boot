package ru.itis.site;

import freemarker.template.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.freemarker.SpringTemplateLoader;

@SpringBootApplication
public class SiteApplication {

    // для отправки сообщения на почту при регистрации
    @Bean
    public Configuration forMailsFreemarkerConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        // кодировка письма
        configuration.setDefaultEncoding("UTF-8");
        // укажем, где искать шаблоны для загрузки
        configuration.setTemplateLoader(
                // грузим с помощью спринга
                new SpringTemplateLoader(
                        // на основе путей из приложения, которые прописаны, "/" - означает пакет resources
                        // this.getClass() - означает текущий класс
                        new ClassRelativeResourceLoader(this.getClass()), "/"));
        return configuration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

}
