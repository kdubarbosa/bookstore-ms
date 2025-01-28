package com.example.bookstore_ms.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para atualizar um livro no sistema")
public class AtualizarLivroDTO {

    @Schema(description = "Título do livro", example = "Aprendendo Java")
    private String titulo;

    @Schema(description = "Descrição do livro", example = "Um guia completo para iniciantes em Java.")
    private String descricao;

    @Schema(description = "Categoria do livro", example = "Programação")
    private String categoria;

}
