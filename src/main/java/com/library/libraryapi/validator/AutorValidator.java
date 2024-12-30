package com.library.libraryapi.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.library.libraryapi.exception.RegistroDuplicadoException;
import com.library.libraryapi.model.Autor;
import com.library.libraryapi.repository.AutorRepository;

@Component
public class AutorValidator {

    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Já existe um autor cadastrado com o mesmo nome e nacionalidade.");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());
        if (autor.getId() == null) {
            return autorEncontrado.isPresent();

        }
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }

}
