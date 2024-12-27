package com.library.libraryapi.service;

import org.springframework.stereotype.Service;

import com.library.libraryapi.model.Autor;
import com.library.libraryapi.repository.AutorRepository;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public Autor salvar(Autor autor) {
        return repository.save(autor);
    }

}
