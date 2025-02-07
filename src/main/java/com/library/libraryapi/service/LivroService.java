package com.library.libraryapi.service;

import com.library.libraryapi.model.Autor;
import org.springframework.stereotype.Service;

import com.library.libraryapi.model.Livro;
import com.library.libraryapi.repository.LivroRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }



    public Optional<Livro> obterPorId(UUID idLivro) {
        return repository.findById(idLivro);
    }

}
