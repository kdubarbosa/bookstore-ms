package com.example.bookstore_ms.application.service;

import com.example.bookstore_ms.domain.dto.AtualizarLivroDTO;
import com.example.bookstore_ms.domain.dto.LivroDTO;
import com.example.bookstore_ms.domain.dto.InserirLivroDTO;
import com.example.bookstore_ms.domain.entity.Livro;
import com.example.bookstore_ms.domain.entity.Autor;
import com.example.bookstore_ms.domain.repository.AutorRepository;
import com.example.bookstore_ms.domain.repository.LivroRepository;
import com.example.bookstore_ms.exception.AutorNaoEncontradoException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LivroService livroService;

    private Livro livro;
    private LivroDTO livroDTO;
    private InserirLivroDTO inserirLivroDTO;
    private AtualizarLivroDTO atualizarLivroDTO;
    private Autor autor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        autor = new Autor();
        autor.setId(1L);
        autor.setNome("Autor Teste");

        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Livro Teste");
        livro.setAutor(autor);

        livroDTO = new LivroDTO();
        livroDTO.setId(1L);
        livroDTO.setTitulo("Livro Teste");

        inserirLivroDTO = new InserirLivroDTO();
        inserirLivroDTO.setTitulo("Livro Teste");
        inserirLivroDTO.setIdAutor(1L);

        atualizarLivroDTO = new AtualizarLivroDTO();
        atualizarLivroDTO.setTitulo("Livro Atualizado");

        ReflectionTestUtils.setField(livroService, "livroRepository", livroRepository);
        ReflectionTestUtils.setField(livroService, "autorRepository", autorRepository);
        ReflectionTestUtils.setField(livroService, "objectMapper", objectMapper);
    }

    @Test
    void salvar_DeveSalvarLivroComSucesso() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(objectMapper.convertValue(inserirLivroDTO, Livro.class)).thenReturn(livro);
        when(livroRepository.save(livro)).thenReturn(livro);
        when(objectMapper.convertValue(livro, LivroDTO.class)).thenReturn(livroDTO);

        Optional<LivroDTO> result = livroService.salvar(inserirLivroDTO);

        assertTrue(result.isPresent());
        assertEquals("Livro Teste", result.get().getTitulo());
        verify(livroRepository, times(1)).save(livro);
    }

    @Test
    void salvar_DeveLancarExcecaoQuandoAutorNaoEncontrado() {
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AutorNaoEncontradoException.class, () -> livroService.salvar(inserirLivroDTO));
    }

    @Test
    void atualizar_DeveAtualizarLivroComSucesso() throws JsonMappingException {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(objectMapper.updateValue(livro, atualizarLivroDTO)).thenReturn(livro);
        when(livroRepository.save(livro)).thenReturn(livro);
        when(objectMapper.convertValue(livro, LivroDTO.class)).thenReturn(livroDTO);

        Optional<LivroDTO> result = livroService.atualizar(1L, atualizarLivroDTO);

        assertTrue(result.isPresent());
        assertEquals("Livro Teste", result.get().getTitulo());
        verify(livroRepository, times(1)).save(livro);
    }

    @Test
    void buscarTodos_DeveRetornarListaDeLivros() {
        when(livroRepository.findAll()).thenReturn(List.of(livro));
        when(objectMapper.convertValue(livro, LivroDTO.class)).thenReturn(livroDTO);

        var result = livroService.buscarTodos();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Livro Teste", result.get(0).getTitulo());
    }

    @Test
    void buscarPorId_DeveRetornarLivroQuandoEncontrado() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(objectMapper.convertValue(livro, LivroDTO.class)).thenReturn(livroDTO);

        Optional<LivroDTO> result = livroService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Livro Teste", result.get().getTitulo());
        assertEquals(1L, result.get().getAutor().getId());
    }

    @Test
    void buscarPorId_DeveRetornarEmptyQuandoNaoEncontrado() {
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<LivroDTO> result = livroService.buscarPorId(1L);

        assertTrue(result.isEmpty());
    }
}
