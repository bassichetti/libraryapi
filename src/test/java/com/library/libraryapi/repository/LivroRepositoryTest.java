package com.library.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.library.libraryapi.model.Autor;
import com.library.libraryapi.model.GeneroLivro;
import com.library.libraryapi.model.Livro;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest() {
        var id = UUID.fromString("7de3408d-dc62-483e-b85f-95f8269ece89");
        Autor autor = autorRepository.findById(id).orElse(null);

        Livro livro = new Livro();
        livro.setTitulo("Clean Code");
        livro.setIsbn("9780132350884");
        livro.setAutor(autor);
        livro.setGenero(GeneroLivro.DRAMA);
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setDataPublicacao(LocalDate.of(2008, 8, 11));
        livroRepository.save(livro);
        System.out.println(livro);
    }

    @Test
    public void salvarCascadeTest() {

        Autor autor = new Autor();
        autor.setNome("JOAO BASSICHETTI");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1983, 8, 5));
        // autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setTitulo("Clean Code");
        livro.setIsbn("9780132350884");
        livro.setAutor(autor);
        livro.setGenero(GeneroLivro.DRAMA);
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setDataPublicacao(LocalDate.of(2008, 8, 11));
        livroRepository.save(livro);
        System.out.println(livro);
    }

    @Test
    public void buscarPorIdTest() {
        var id = UUID.fromString("71a51660-014b-49d4-a2b3-3142e65fb3d2");
        var livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            System.out.println("Dados do livro:");
            System.out.println(livro.toString());
        }
    }

    @Test
    public void listarLivrosAutorTest() {
        var id = UUID.fromString("7de3408d-dc62-483e-b85f-95f8269ece89");
        Autor autor = autorRepository.findById(id).orElse(null);
        List<Livro> livros = livroRepository.findByAutor(autor);
        autor.setLivros(livros);
        autor.getLivros().forEach(System.out::println);
    }

    @Test
    public void listarLivrosTituloTest() {
        List<Livro> livros = livroRepository.findByTitulo("Clean Code");
        livros.forEach(System.out::println);
    }

    @Test
    public void listarLivrosTituloPrecoTest() {
        List<Livro> livros = livroRepository.findByTituloAndPreco("Clean Code", BigDecimal.valueOf(100));
        livros.forEach(System.out::println);
    }

    @Test
    public void listarLivrosComQueryJPQL() {
        var livros = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        livros.forEach(System.out::println);
    }

    @Test
    public void listarPorGeneroQueryParamTest() {
        var genero = GeneroLivro.DRAMA;
        var livros = livroRepository.findByGenero(genero, "dataPublicacao");
        livros.forEach(System.out::println);
    }

    @Test
    public void deletarTest() {
        var id = UUID.fromString("71a51660-014b-49d4-a2b3-3142e65fb3d2");
        livroRepository.deleteById(id);
    }

}
