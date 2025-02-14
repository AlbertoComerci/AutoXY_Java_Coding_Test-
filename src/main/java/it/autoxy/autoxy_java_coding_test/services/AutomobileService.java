// package it.autoxy.autoxy_java_coding_test.services;

// import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
// import it.autoxy.autoxy_java_coding_test.models.Automobile;
// import it.autoxy.autoxy_java_coding_test.repositories.AutomobileRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.security.Principal;
// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class AutomobileService implements CrudService<AutomobileDto, Automobile, Long> {

//     @Autowired
//     private AutomobileRepository automobileRepository;

//     @Override
//     public List<AutomobileDto> readAll() {
//         return automobileRepository.findAll().stream()
//                 .map(this::convertToDto)
//                 .collect(Collectors.toList());
//     }

//     @Override
//     public AutomobileDto read(Long id) {
//         Automobile automobile = automobileRepository.findById(id).orElseThrow(() -> new RuntimeException("Automobile non trovata"));
//         return convertToDto(automobile);
//     }

//     @Override
//     public AutomobileDto create(Automobile automobile, Principal principal) {
//         Automobile savedAutomobile = automobileRepository.save(automobile);
//         return convertToDto(savedAutomobile);
//     }

//     @Override
//     public AutomobileDto update(Long id, Automobile updatedAutomobile) {
//         Automobile existingAutomobile = automobileRepository.findById(id).orElseThrow(() -> new RuntimeException("Automobile non trovata"));
//         existingAutomobile.setAnno(updatedAutomobile.getAnno());
//         existingAutomobile.setPrezzo(updatedAutomobile.getPrezzo());
//         existingAutomobile.setKm(updatedAutomobile.getKm());
//         existingAutomobile.setMarca(updatedAutomobile.getMarca());
//         existingAutomobile.setModello(updatedAutomobile.getModello());
//         existingAutomobile.setRegione(updatedAutomobile.getRegione());
//         existingAutomobile.setStato(updatedAutomobile.getStato());
//         existingAutomobile.setAlimentazione(updatedAutomobile.getAlimentazione());

//         return convertToDto(automobileRepository.save(existingAutomobile));
//     }

//     @Override
//     public void delete(Long id) {
//         automobileRepository.deleteById(id);
//     }

//     public List<AutomobileDto> searchByFilters(Long marcaId, Long modelloId, double prezzoMin, double prezzoMax, Long statoId, Long regioneId, Long alimentazioneId) {
//         return automobileRepository.findByFilters(marcaId, modelloId, prezzoMin, prezzoMax, statoId, regioneId, alimentazioneId).stream()
//                 .map(this::convertToDto)
//                 .collect(Collectors.toList());
//     }

//     private AutomobileDto convertToDto(Automobile automobile) {
//         return new AutomobileDto(
//                 automobile.getId(),
//                 automobile.getAnno(),
//                 automobile.getPrezzo(),
//                 automobile.getKm(),
//                 automobile.getMarca().getNome(),
//                 automobile.getModello().getNome(),
//                 automobile.getRegione().getNome(),
//                 automobile.getStato().getNome(),
//                 automobile.getAlimentazione().getNome()
//         );
//     }
// }

