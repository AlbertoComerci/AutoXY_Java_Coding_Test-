package it.autoxy.autoxy_java_coding_test.services;

import java.security.Principal;
import java.util.List;

// Interfaccia generica per le operazioni CRUD
public interface CrudService<ReadDto, Model, Key> {

    List<ReadDto> readAll();
    ReadDto read(Key key);
    ReadDto create(Model model, Principal principal);
    ReadDto update(Key key, Model model);
    void delete(Key key);
    
}
