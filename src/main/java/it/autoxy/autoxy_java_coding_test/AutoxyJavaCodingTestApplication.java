package it.autoxy.autoxy_java_coding_test;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
import it.autoxy.autoxy_java_coding_test.models.Automobile;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AutoxyJavaCodingTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoxyJavaCodingTestApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper instanceModelMapper() {
		ModelMapper mapper = new ModelMapper();

		mapper.addMappings(new PropertyMap<Automobile, AutomobileDto>() {
            @Override
            protected void configure() {
                // Mappa i campi nidificati
                map().setUtente(source.getUtente().getUsername());
                map().setMarca(source.getMarca().getNome());
                map().setModello(source.getModello().getNome());
                map().setRegione(source.getRegione().getNome());
                map().setStato(source.getStato().getNome());
                map().setAlimentazione(source.getAlimentazione().getNome());
            }
        });

		return mapper;
	}

}
