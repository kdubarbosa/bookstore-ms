package com.example.bookstore_ms.web.controller;

import com.example.bookstore_ms.application.service.AutorService;
import com.example.bookstore_ms.domain.dto.AtualizarAutorDTO;
import com.example.bookstore_ms.domain.dto.AutorDTO;
import com.example.bookstore_ms.domain.dto.InserirAutorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ApiResponse(responseCode = "400", description = "Bad Request")
@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    @Operation(summary = "Salvar um novo autor", description = "autor")
    public ResponseEntity<AutorDTO> salvar(@RequestBody InserirAutorDTO autorInsertDTO) {
        Optional<AutorDTO> autorDTO = autorService.salvar(autorInsertDTO);

        // Se o autor for salvo com sucesso, retorna 201 (Created)
        return autorDTO.map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza um autor", description = "autor")
    public ResponseEntity<AutorDTO> atualizar(@PathVariable Long id, @RequestBody AtualizarAutorDTO atualizarAutorDTO) {
        Optional<AutorDTO> autorDTO = autorService.atualizar(id, atualizarAutorDTO);

        // Se o autor for encontrado e atualizado, retorna 200 (OK)
        return autorDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um autor do BD.", description = "autor")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean excluido = autorService.excluir(id);

        return excluido ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Busca uma lista com todos os autores com seus livros.", description = "autor")
    public ResponseEntity<List<AutorDTO>> buscarTodos() {
        return autorService.buscarTodos()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um autor pelo seu ID.", description = "autor")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Long id) {
        Optional<AutorDTO> autorDTO = autorService.buscarPorId(id);

        return autorDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
