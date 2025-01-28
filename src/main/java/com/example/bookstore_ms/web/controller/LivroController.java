package com.example.bookstore_ms.web.controller;

import com.example.bookstore_ms.application.service.LivroService;
import com.example.bookstore_ms.domain.dto.AtualizarLivroDTO;
import com.example.bookstore_ms.domain.dto.InserirLivroDTO;
import com.example.bookstore_ms.domain.dto.LivroDTO;
import com.example.bookstore_ms.exception.AutorNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ApiResponse(responseCode = "400", description = "Bad Request")
@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    @Operation(summary = "Salvar um livro.", description = "livro")
    public ResponseEntity<LivroDTO> salvar(@RequestBody @Valid InserirLivroDTO inserirLivroDTO) {
        try {
            Optional<LivroDTO> livroDTO = livroService.salvar(inserirLivroDTO);
            return livroDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        } catch (AutorNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar um livro.", description = "livro")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Long id, @RequestBody AtualizarLivroDTO atualizarLivroDTO) {
        Optional<LivroDTO> livroDTO = livroService.atualizar(id, atualizarLivroDTO);
        return livroDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    @Operation(summary = "Buscar uma lista de todos os livros.", description = "livro")
    public ResponseEntity<List<LivroDTO>> buscarTodos() {
        List<LivroDTO> livros = livroService.buscarTodos();
        return livros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um livro pelo seu ID.", description = "livro")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        Optional<LivroDTO> livroDTO = livroService.buscarPorId(id);
        return livroDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}