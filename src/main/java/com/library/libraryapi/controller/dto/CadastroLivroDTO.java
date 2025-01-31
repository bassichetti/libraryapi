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
        @NotBlank(message = "O ISBN é obrigatório") @ISBN String isbn,
        @NotBlank(message = "O título é obrigatório") String titulo,
        @Past(message = "Data de publicação não deve ser uma data futura") LocalDate dataPublicacao, GeneroLivro genero,
        BigDecimal preco, @NotNull(message = "O autor é obrigatório") UUID idAutor) {

}
