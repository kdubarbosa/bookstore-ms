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
@Schema(description = "DTO para representar um autor")
public class AutorSemLivrosDTO {

    @Schema(description = "ID do autor", example = "1")
    private Long id;

    @Schema(description = "Nome do autor", example = "Fula de Tal")
    private String nome;

}
