package com.example.bookstore_ms.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para inserir um livro no sistema")
public class InserirLivroDTO {

    @Schema(description = "Título do livro", example = "Aprendendo Java")
    @NotEmpty
    private String titulo;

    @Schema(description = "Descrição do livro", example = "Um guia completo para iniciantes em Java.")
    @NotEmpty
    private String descricao;

    @Schema(description = "Categoria do livro", example = "Programação")
    @NotEmpty
    private String categoria;

    @Schema(description = "ID do autor")
    @NotNull
    private Long idAutor;

}
