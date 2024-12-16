package com.library.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.libraryapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

}
