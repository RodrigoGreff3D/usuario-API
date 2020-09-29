package br.com.cadastrousuarioapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = { "br.com.cadastrousuarioapi.model" })
@ComponentScan(basePackages = { "br.**" })
@EnableJpaRepositories(basePackages = { "br.com.cadastrousuarioapi.repository" })
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
@EnableCaching
public class CadastroUsuarioApiApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CadastroUsuarioApiApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/usuario/**").allowedMethods("*").allowedOrigins("*");
	}
}
