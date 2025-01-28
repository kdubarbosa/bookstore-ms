package com.example.bookstore_ms.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para inserir um autor no sistema")
public class InserirAutorDTO {

    @Schema(description = "Nome do autor", example = "Fula de Tal")
    private String nome;

}
