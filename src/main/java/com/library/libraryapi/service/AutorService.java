package com.library.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.libraryapi.model.Autor;
import com.library.libraryapi.repository.AutorRepository;
import com.library.libraryapi.validator.AutorValidator;

@Service
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;

    public AutorService(AutorRepository repository, AutorValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Autor salvar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("O id do autor não pode ser nulo.");

        }
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("O id do autor não pode ser nulo.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID idAutor) {
        return repository.findById(idAutor);
    }

    public Optional<Autor> deletarPorId(UUID idAutor) {
        Optional<Autor> autor = repository.findById(idAutor);
        if (autor.isPresent()) {
            repository.deleteById(idAutor);
        }
        return autor;

    }

    public List<Autor> pesquisar(@RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);

        }
        if (nome != null) {
            return repository.findByNome(nome);
        }

        if (nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        }
        return repository.findAll();
    }

}
