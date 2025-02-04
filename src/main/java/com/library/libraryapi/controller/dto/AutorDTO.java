package com.library.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.library.libraryapi.model.Autor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "O nome é obrigatório") @Size(max = 100, min = 2, message = "O nome deve ter no máximo 100 caracteres e no minimo 2 caracteres") String nome,
        @NotNull(message = "A data de nascimento é obrigatória") @Past(message = "Data de nascimento não deve ser uma data futura") LocalDate dataNascimento,
        @NotBlank(message = "A nacionalidade é obrigatória") @Size(max = 50, min = 2, message = "A nacionalidade deve ter no máximo 50 caracteres e no minimo 2 caracteres") String nacionalidade) {



}
