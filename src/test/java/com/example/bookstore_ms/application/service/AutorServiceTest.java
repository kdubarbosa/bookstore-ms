package com.example.bookstore_ms.application.service;

import com.example.bookstore_ms.domain.dto.AtualizarAutorDTO;
import com.example.bookstore_ms.domain.dto.AutorDTO;
import com.example.bookstore_ms.domain.dto.InserirAutorDTO;
import com.example.bookstore_ms.domain.entity.Autor;
import com.example.bookstore_ms.domain.repository.AutorRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AutorService autorService;

    private Autor autor;
    private AutorDTO autorDTO;
    private InserirAutorDTO inserirAutorDTO;
    private AtualizarAutorDTO atualizarAutorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        autor = new Autor();
        autor.setId(1L);
        autor.setNome("Autor Teste");

        autorDTO = new AutorDTO();
        autorDTO.setId(1L);
        autorDTO.setNome("Autor Teste");

        inserirAutorDTO = new InserirAutorDTO();
        inserirAutorDTO.setNome("Autor Teste");

        atualizarAutorDTO = new AtualizarAutorDTO();
        atualizarAutorDTO.setNome("Autor Atualizado");

        ReflectionTestUtils.setField(autorService, "autorRepository", autorRepository);
        ReflectionTestUtils.setField(autorService, "objectMapper", objectMapper);
    }

    @Test
    void salvar_DeveSalvarAutorComSucesso() {
        when(objectMapper.convertValue(inserirAutorDTO, Autor.class)).thenReturn(autor);
        when(autorRepository.save(autor)).thenReturn(autor);
        when(objectMapper.convertValue(autor, AutorDTO.class)).thenReturn(autorDTO);

        Optional<AutorDTO> result = autorService.salvar(inserirAutorDTO);

        assertTrue(result.isPresent());
        assertEquals("Autor Teste", result.get().getNome());
        verify(autorRepository, times(1)).save(autor);
    }

    @Test
    void atualizar_DeveAtualizarAutorComSucesso() throws JsonMappingException {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(objectMapper.updateValue(autor, atualizarAutorDTO)).thenReturn(autor);
        when(autorRepository.save(autor)).thenReturn(autor);
        when(objectMapper.convertValue(autor, AutorDTO.class)).thenReturn(autorDTO);

        Optional<AutorDTO> result = autorService.atualizar(1L, atualizarAutorDTO);

        assertTrue(result.isPresent());
        assertEquals("Autor Teste", result.get().getNome());
        verify(autorRepository, times(1)).save(autor);
    }

    @Test
    void excluir_DeveExcluirAutorComSucesso() {
        when(autorRepository.existsById(1L)).thenReturn(true);

        boolean result = autorService.excluir(1L);

        assertTrue(result);
        verify(autorRepository, times(1)).deleteById(1L);
    }

    @Test
    void excluir_DeveRetornarFalseQuandoAutorNaoExistir() {
        when(autorRepository.existsById(1L)).thenReturn(false);

        boolean result = autorService.excluir(1L);

        assertFalse(result);
        verify(autorRepository, never()).deleteById(1L);
    }

    @Test
    void buscarPorId_DeveRetornarAutorQuandoEncontrado() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(objectMapper.convertValue(autor, AutorDTO.class)).thenReturn(autorDTO);

        Optional<AutorDTO> result = autorService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Autor Teste", result.get().getNome());
    }

    @Test
    void buscarPorId_DeveRetornarEmptyQuandoNaoEncontrado() {
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<AutorDTO> result = autorService.buscarPorId(1L);

        assertTrue(result.isEmpty());
    }

}
