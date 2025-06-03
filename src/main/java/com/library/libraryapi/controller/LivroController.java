package com.library.libraryapi.controller;

import com.library.libraryapi.controller.dto.CadastroLivroDTO;
import com.library.libraryapi.controller.mapper.LivroMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.library.libraryapi.controller.dto.ErroResposta;
import com.library.libraryapi.controller.dto.LivroDTO;
import com.library.libraryapi.exception.RegistroDuplicadoException;
import com.library.libraryapi.model.Livro;
import com.library.libraryapi.service.LivroService;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @Qualifier("livroMapper")
    private final LivroMapper livroMapper;

    @PostMapping()
    public ResponseEntity<Object> salvar(@RequestBody CadastroLivroDTO dto) {
        try {
            Livro livro = livroMapper.toEntity(dto);
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

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody CadastroLivroDTO dto) {
        try {
            var idlivro = UUID.fromString(id);
            Optional<Livro> livroOptional = service.obterPorId(idlivro);
            if (livroOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Livro livroEncontrado = livroOptional.get();
            livroEncontrado = livroMapper.toEntity(dto);
            service.salvar(livroEncontrado);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(livroEncontrado.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<LivroDTO> obterDetalhes(@PathVariable String id) {
        var idLivro = UUID.fromString(id);
        Optional<Livro> livro = service.obterPorId(idLivro);
        if (livro.isPresent()) {
            Livro livroDetalhe = livro.get();
            LivroDTO livroDTO = livroMapper.toDTO(livroDetalhe);
            return ResponseEntity.ok(livroDTO);
        }
        return ResponseEntity.notFound().build();
    }

}
