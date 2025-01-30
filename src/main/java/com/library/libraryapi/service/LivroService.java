package com.library.libraryapi.service;

import org.springframework.stereotype.Service;

import com.library.libraryapi.model.Livro;
import com.library.libraryapi.repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

}
