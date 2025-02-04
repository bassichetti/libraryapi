package com.library.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record LivroDTO(UUID id,

        String isbn,
        @NotBlank(message = "O título é obrigatório") @Size(max = 100, min = 2, message = "O título deve ter no máximo 100 caracteres e no minimo 2 caracteres") String titulo,
        @NotNull(message = "A data de publicação é obrigatória") @Past(message = "Data de publicação não deve ser uma data futura") LocalDate dataPublicacao,
        @NotBlank(message = "O gênero é obrigatório") @Size(max = 50, min = 2, message = "O gênero deve ter no máximo 50 caracteres e no minimo 2 caracteres") String genero,
        @NotNull(message = "O preço é obrigatório") BigDecimal preco,
        @NotNull(message = "O autor é obrigatório") UUID idAutor) {



}
