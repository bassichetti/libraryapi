package com.library.libraryapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.library.libraryapi.controller.dto.ErroResposta;
import com.library.libraryapi.controller.dto.LivroDTO;
import com.library.libraryapi.exception.RegistroDuplicadoException;
import com.library.libraryapi.model.Livro;
import com.library.libraryapi.service.LivroService;

import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping()
    public ResponseEntity<Object> salvar(@RequestBody LivroDTO dto) {
        try {

            Livro livro = dto.mapearParaLivro();

            service.salvar(livro);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(livro.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }

    }

}
