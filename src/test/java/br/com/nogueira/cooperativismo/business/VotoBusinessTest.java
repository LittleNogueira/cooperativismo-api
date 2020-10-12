package br.com.nogueira.cooperativismo.business;

import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.dtos.VotoDto;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Sessao;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VotoBusinessTest {

    @InjectMocks
    private VotoBusiness votoBusiness;

    @Mock
    private PautaService pautaService;

    @Mock
    private AssociadoService associadoService;

    @Test
    public void testaCriarVoto(){
        TicketDto ticketDto = new TicketDto();
        ticketDto.setVotoDto(new VotoDto());

        when(pautaService.buscarPautaPorId(any())).thenReturn(getPauta());
        when(associadoService.buscarAssociadoPorId(any())).thenReturn(new Associado());

        votoBusiness.criarVoto(ticketDto);

        verify(pautaService, times(1)).salvarPauta(any(Pauta.class));
        verify(pautaService, times(1)).buscarPautaPorId(any());
        verify(associadoService, times(1)).buscarAssociadoPorId(any());
    }

    private Pauta getPauta(){
        Pauta pauta = new Pauta();

        Sessao sessao = new Sessao();
        sessao.setDataHoraFinalizacao(sessao.getDataHoraCriacao().plusSeconds(1));

        pauta.setTitulo("Titulo da pauta");
        pauta.setDescricao("Descricao da pauta");
        pauta.setSessao(sessao);

        return pauta;
    }

}

