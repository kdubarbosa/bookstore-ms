package com.example.bookstore_ms.application.service;

import com.example.bookstore_ms.domain.dto.AtualizarLivroDTO;
import com.example.bookstore_ms.domain.dto.AutorSemLivrosDTO;
import com.example.bookstore_ms.domain.dto.InserirLivroDTO;
import com.example.bookstore_ms.domain.dto.LivroDTO;
import com.example.bookstore_ms.domain.entity.Livro;
import com.example.bookstore_ms.domain.repository.AutorRepository;
import com.example.bookstore_ms.domain.repository.LivroRepository;
import com.example.bookstore_ms.exception.AutorNaoEncontradoException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public Optional<LivroDTO> salvar(InserirLivroDTO inserirLivroDTO) {
        Long idAutor = inserirLivroDTO.getIdAutor();
        var autor = autorRepository.findById(idAutor)
                .orElseThrow(() -> new AutorNaoEncontradoException("Autor com ID " + idAutor + " não encontrado."));

        var livroEntity = objectMapper.convertValue(inserirLivroDTO, Livro.class);
        livroEntity.setAutor(autor);
        var livro = livroRepository.save(livroEntity);
        return Optional.ofNullable(objectMapper.convertValue(livro, LivroDTO.class));
    }

    @Transactional
    public Optional<LivroDTO> atualizar(Long id, AtualizarLivroDTO atualizarLivroDTO) {
        return livroRepository.findById(id).map(livro -> {
            // Atualiza os campos necessários da entidade existente
            try {
                objectMapper.updateValue(livro, atualizarLivroDTO);
                Livro livroAtualizado = livroRepository.save(livro);
                return objectMapper.convertValue(livroAtualizado, LivroDTO.class);
            } catch (IllegalArgumentException | JsonMappingException e) {
                throw new RuntimeException("Erro ao atualizar o livro: " + e.getMessage(), e);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> buscarTodos() {
        return livroRepository.findAll().stream()
                .map(livro -> objectMapper.convertValue(livro, LivroDTO.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<LivroDTO> buscarPorId(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if(livro.isPresent()) {
            var livroDTO = objectMapper.convertValue(livro.get(), LivroDTO.class);
            livroDTO.setAutor(AutorSemLivrosDTO.builder().id(livro.get().getAutor().getId()).nome(livro.get().getAutor().getNome()).build());
            return Optional.of(livroDTO);
        }
        return Optional.empty();
    }

}