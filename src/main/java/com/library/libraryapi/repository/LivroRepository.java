package com.library.libraryapi.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.library.libraryapi.model.Autor;
import com.library.libraryapi.model.GeneroLivro;
import com.library.libraryapi.model.Livro;

import jakarta.transaction.Transactional;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    @Query("SELECT l FROM Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query("SELECT l FROM Livro as l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro genero, @Param("paramOrdenacao") String paramOrdenacao);

    @Modifying
    @Transactional
    @Query("DELETE FROM Livro as l where l.titulo = :titulo")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("UPDATE Livro as l set l.titulo = :titulo where l.id = :id")
    void updateTituloById(UUID id);

}
