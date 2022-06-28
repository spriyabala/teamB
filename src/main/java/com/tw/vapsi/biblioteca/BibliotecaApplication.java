package com.tw.vapsi.biblioteca;

import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {

		try {
			SpringApplication.run(BibliotecaApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	/*@Bean
	public CommandLineRunner setup(BookRepository repository) {
		return (args) -> {
			repository.save(new Book("ABC", "DEF"));
			repository.save(new Book("A1B1C1", "D1E1F!"));

		};
*/
	//}
}