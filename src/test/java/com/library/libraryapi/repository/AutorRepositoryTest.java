package com.library.libraryapi.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.library.libraryapi.model.Autor;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("LEANDRO BASSICHETTI");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1983, 8, 5));
        autorRepository.save(autor);
        System.out.println(autor);
    }

    @Test
    public void buscarPorIdTest() {
        var id = UUID.fromString("44172df0-d2b8-4f7a-ab82-ac55f9f81e5f");
        Optional<Autor> autor = autorRepository.findById(id);
        if (autor.isPresent()) {

            Autor autorEncontrado = autor.get();
            System.out.println("Dados do autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1979, 8, 5));
            autorRepository.save(autorEncontrado);
        }
        System.out.println(autor);
    }

    @Test
    public void listarTodosTest() {
        Iterable<Autor> autores = autorRepository.findAll();
        autores.forEach(autor -> System.out.println(autor));
    }

    @Test
    public void deletarPorIdTest() {
        var id = UUID.fromString("44172df0-d2b8-4f7a-ab82-ac55f9f81e5f");
        autorRepository.deleteById(id);
    }

}
