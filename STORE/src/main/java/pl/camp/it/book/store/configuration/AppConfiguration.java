package pl.camp.it.book.store.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("pl.camp.it.book.store")
@EnableScheduling
public class AppConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    /*@Bean
    public Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookstore16",
                    "root",
                    "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/
    /*@Bean
    public IBookDAO bookDAO(IBookIdSequence bookIdSequence) {
        return new BookDB(bookIdSequence);
    }

    @Bean
    public IBookIdSequence bookIdSequence() {
        return new BookIdSequence();
    }*/

    /*@Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select().build();
    }*/
}
