package com.example.bookstore_ms.domain.dto;

import com.example.bookstore_ms.domain.entity.Livro;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representar um autor")
public class AutorDTO {

    @Schema(description = "ID do autor", example = "1")
    private Long id;

    @Schema(description = "Nome do autor", example = "Fula de Tal")
    private String nome;

    @Schema(description = "Lista de livros associados ao autor")
    private List<LivroSemAutorDTO> livros;

}
