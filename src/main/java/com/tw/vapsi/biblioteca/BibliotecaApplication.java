package com.tw.vapsi.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {

		try {
			SpringApplication.run(BibliotecaApplication.class, args);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
