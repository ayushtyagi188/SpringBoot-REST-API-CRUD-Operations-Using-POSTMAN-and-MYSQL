package com.ayush;


import com.ayush.model.User;
import com.ayush.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RestApiApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

    @Autowired
	private UserRepo employeeRepository;

	@Override
	public void run(String... args) throws Exception {
    User u=new User();
    /*u.setFirstName("Ayush");
	u.setLastName("Tyagi");
	u.setEmailId("a.t@email.com");
	employeeRepository.save(u);*/


	}
}
