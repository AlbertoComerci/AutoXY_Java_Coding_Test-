package it.autoxy.autoxy_java_coding_test.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
import it.autoxy.autoxy_java_coding_test.models.Automobile;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper instanceModelMapper() {
        ModelMapper mapper = new ModelMapper();
        // Usa createTypeMap per evitare errori di mapping duplicato
        if (mapper.getTypeMap(Automobile.class, AutomobileDto.class) == null) {
            mapper.createTypeMap(Automobile.class, AutomobileDto.class)
                .setPostConverter(context -> {
                    Automobile source = context.getSource();
                    AutomobileDto dest = context.getDestination();
                    dest.setUtente(source.getUtente() != null ? source.getUtente().getNome() + " " + source.getUtente().getCognome() : null);
                    dest.setMarca(source.getMarca() != null ? source.getMarca().getNome() : null);
                    dest.setModello(source.getModello() != null ? source.getModello().getNome() : null);
                    dest.setRegione(source.getRegione() != null ? source.getRegione().getNome() : null);
                    dest.setStato(source.getStato() != null ? source.getStato().getNome() : null);
                    dest.setAlimentazione(source.getAlimentazione() != null ? source.getAlimentazione().getNome() : null);
                    return dest;
                });
        }
        return mapper;
    }

}
