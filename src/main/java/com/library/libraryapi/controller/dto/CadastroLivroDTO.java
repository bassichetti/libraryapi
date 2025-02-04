package com.library.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.library.libraryapi.model.GeneroLivro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record CadastroLivroDTO(
      String isbn,
      String titulo,
      LocalDate dataPublicacao,
      GeneroLivro genero,
      BigDecimal preco,
      UUID idAutor) {

}
