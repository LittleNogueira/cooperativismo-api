package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.repository.PautaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Test
    void testaCriarAssociado(){
        when(pautaRepository.save(any(Pauta.class))).thenReturn(new Pauta());

        Pauta associado = pautaService.salvarPauta(new Pauta());

        assertNotNull(associado);
        verify(pautaRepository,times(1)).save(any(Pauta.class));
    }

    @Test
    void testaBuscaPautaPorIdComIdQueNaoExiste() {
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            pautaService.buscaPautaPorId(1l);
        });

        assertEquals("Pauta com id 1 não existe.", exception.getMessage());
    }

    @Test
    void testaBuscaPautaPorIdComSucesso() {
        when(pautaRepository.findById(1l)).thenReturn(Optional.of(new Pauta()));

        Pauta pauta = pautaService.buscaPautaPorId(1l);

        assertNotNull(pauta);
    }

    @Test
    void testaExistePautaComVotoDoAssociado() {
        when(pautaRepository.existsByIdAndSessaoVotosAssociadoId(anyLong(),anyLong())).thenReturn(Boolean.TRUE);

        Boolean aBoolean = pautaService.existePautaComVotoDoAssociado(1l, 1l);

        assertTrue(aBoolean);
        verify(pautaRepository,times(1)).existsByIdAndSessaoVotosAssociadoId(anyLong(),anyLong());
    }

    @Test
    void testaBuscarTodasAsPautasFinalizadasSemResultado() {
        when(pautaRepository.findByResultadoIsNullAndSessaoDataHoraFinalizacaoBefore(any(LocalDateTime.class))).thenReturn(Arrays.asList(new Pauta()));

        List<Pauta> pautas = pautaService.buscarTodasAsPautasFinalizadasSemResultado();

        assertNotNull(pautas);
        assertEquals(1,pautas.size());

        verify(pautaRepository,times(1)).findByResultadoIsNullAndSessaoDataHoraFinalizacaoBefore(any(LocalDateTime.class));
    }

}

