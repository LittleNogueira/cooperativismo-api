package br.com.nogueira.cooperativismo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.repository.AssociadoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AssociadoServiceTest {

    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private AssociadoRepository associadoRepository;

    @Test
    void testaCriarAssociadoComSucesso(){
        when(associadoRepository.save(any(Associado.class))).thenReturn(new Associado());

        Associado associado = associadoService.salvarAssociado(new Associado());

        assertNotNull(associado);
        verify(associadoRepository,times(1)).save(any(Associado.class));
    }

    @Test
    void testaBuscaAssociadoPorIdComIdQueNaoExiste() {
        when(associadoRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            associadoService.buscaAssociadoPorId(1l);
        });

        assertEquals("Associado com id 1 não existe.", exception.getMessage());
    }

    @Test
    void testaBuscaAssociadoPorIdComSucesso() {
        when(associadoRepository.findById(1l)).thenReturn(Optional.of(new Associado()));

        Associado associado = associadoService.buscaAssociadoPorId(1l);

        assertNotNull(associado);
    }

}
