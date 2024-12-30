package com.library.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.library.libraryapi.controller.dto.AutorDTO;
import com.library.libraryapi.controller.dto.ErroResposta;
import com.library.libraryapi.exception.RegistroDuplicadoException;
import com.library.libraryapi.model.Autor;
import com.library.libraryapi.service.AutorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/autores")
// http://localhost:8080/autores
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor) {
        try {
            Autor autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = service.obterPorId(idAutor);
        if (autor.isPresent()) {
            Autor autorEncontrado = autor.get();
            AutorDTO autorDTO = new AutorDTO(autorEncontrado.getNome(), autorEncontrado.getDataNascimento(),
                    autorEncontrado.getNacionalidade());
            return ResponseEntity.ok(autorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = service.deletarPorId(idAutor);
        if (autor.isEmpty()) {
            return ResponseEntity.notFound().build();

        }
        service.deletarPorId(autor.get().getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> lista = service.pesquisar(nome, nacionalidade);
        return ResponseEntity.ok(lista.stream().map(autor -> new AutorDTO(autor.getNome(), autor.getDataNascimento(),
                autor.getNacionalidade())).toList());
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO entity) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = service.obterPorId(idAutor);
            if (autor.isEmpty()) {
                return ResponseEntity.notFound().build();

            }

            Autor autorEncontrado = autor.get();
            autorEncontrado.setNome(entity.nome());
            autorEncontrado.setDataNascimento(entity.dataNascimento());
            autorEncontrado.setNacionalidade(entity.nacionalidade());
            service.atualizar(autorEncontrado);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

}
