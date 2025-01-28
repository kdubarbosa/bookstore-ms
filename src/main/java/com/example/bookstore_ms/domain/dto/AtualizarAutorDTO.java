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
@Schema(description = "DTO para atualiar um autor no sistema")
public class AtualizarAutorDTO {

    @Schema(description = "Nome do autor", example = "Fula de Tal")
    private String nome;

}
