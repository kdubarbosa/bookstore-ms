package com.example.bookstore_ms.application.service;

import com.example.bookstore_ms.domain.dto.AtualizarAutorDTO;
import com.example.bookstore_ms.domain.dto.AutorDTO;
import com.example.bookstore_ms.domain.dto.InserirAutorDTO;
import com.example.bookstore_ms.domain.dto.LivroSemAutorDTO;
import com.example.bookstore_ms.domain.entity.Autor;
import com.example.bookstore_ms.domain.repository.AutorRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public Optional<AutorDTO> salvar(InserirAutorDTO inserirAutorDTO) {
        var entity = objectMapper.convertValue(inserirAutorDTO, Autor.class);
        var autor = autorRepository.save(entity);
        return Optional.ofNullable(objectMapper.convertValue(autor, AutorDTO.class));
    }

    @Transactional
    public Optional<AutorDTO> atualizar(Long id, AtualizarAutorDTO atualizarAutorDTO) {
        return autorRepository.findById(id).map(autor -> {
            try {
                objectMapper.updateValue(autor, atualizarAutorDTO);
                autorRepository.save(autor);
                return objectMapper.convertValue(autor, AutorDTO.class);
            } catch (IllegalArgumentException | JsonMappingException e) {
                throw new RuntimeException("Erro ao atualizar o autor: " + e.getMessage(), e);
            }
        });
    }

    @Transactional
    public boolean excluir(Long id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Optional<AutorDTO> buscarPorId(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.map(a -> objectMapper.convertValue(a, AutorDTO.class));
    }

    @Transactional(readOnly = true)
    public Optional<List<AutorDTO>> buscarTodos() {
        List<Autor> autores = autorRepository.findAll();

        List<AutorDTO> autoresComLivros = autores.stream()
                .map(autor -> {
                    AutorDTO autorDTO = objectMapper.convertValue(autor, AutorDTO.class);
                    List<LivroSemAutorDTO> livrosDTO = autor.getLivros().stream()
                            .map(livro -> objectMapper.convertValue(livro, LivroSemAutorDTO.class))
                            .collect(Collectors.toList());
                    autorDTO.setLivros(livrosDTO);
                    return autorDTO;
                })
                .collect(Collectors.toList());

        return Optional.ofNullable(autoresComLivros.isEmpty() ? null : autoresComLivros);
    }

}