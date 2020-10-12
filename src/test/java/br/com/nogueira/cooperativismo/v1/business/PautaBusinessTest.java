package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.dtos.UserDto;
import br.com.nogueira.cooperativismo.entities.*;
import br.com.nogueira.cooperativismo.enums.StatusEnum;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import br.com.nogueira.cooperativismo.services.KafkaService;
import br.com.nogueira.cooperativismo.services.PautaService;
import br.com.nogueira.cooperativismo.services.TicketService;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PautaBusinessTest {

    @InjectMocks
    private PautaBusiness pautaBusiness;

    @Mock
    private PautaService pautaService;

    @Mock
    private AssociadoService associadoService;

    @Mock
    private TicketService ticketService;

    @Mock
    private KafkaService<TicketDto> kafkaService;

    @Test
    public void testaCriarPauta(){
        when(pautaService.salvarPauta(any(Pauta.class))).thenReturn(new Pauta());

        Pauta pauta = pautaBusiness.criarPauta(getPautaForm());

        assertNotNull(pauta);
        verify(pautaService,times(1)).salvarPauta(any(Pauta.class));
    }

    @Test
    public void testaBuscarPautaPorId(){
        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(new Pauta());

        Pauta pauta = pautaService.buscarPautaPorId(1l);

        assertNotNull(pauta);
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
    }

    @Test
    public void testaCriarSessaoComDataHoraFinalizacaoPadrao(){
        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(new Pauta());

        Pauta pauta = pautaBusiness.criarSessao(1l,new SessaoForm());

        assertNotNull(pauta);
        verify(pautaService,times(1)).salvarPauta(any(Pauta.class));
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
    }

    @Test
    public void testaCriarSessaoComDataHoraFinalizacaoInformada(){
        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(new Pauta());

        SessaoForm sessaoForm = new SessaoForm();
        sessaoForm.setDataHoraFinalizacao(LocalDateTime.now());

        Pauta pauta = pautaBusiness.criarSessao(1l,sessaoForm);

        assertNotNull(pauta);
        assertEquals(sessaoForm.getDataHoraFinalizacao(), pauta.getSessao().getDataHoraFinalizacao());
        verify(pautaService,times(1)).salvarPauta(any(Pauta.class));
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
    }

    @Test
    public void testaCriarVoto(){
        ReflectionTestUtils.setField(pautaBusiness,"topico","topico-ticket");

        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(getPauta());
        when(associadoService.buscarAssociadoPorId(anyLong())).thenReturn(getAssociado());
        when(pautaService.existePautaComVotoDoAssociado(any(), any())).thenReturn(Boolean.FALSE);
        when(ticketService.salvarTicket(any(Ticket.class))).thenReturn(new Ticket());
        when(kafkaService.send(anyString(),any(TicketDto.class))).thenReturn(new SettableListenableFuture<>());

        VotoForm votoForm = new VotoForm();
        votoForm.setVoto(VotoEnum.SIM);
        votoForm.setIdAssociado(1l);

        Ticket ticket = pautaBusiness.criarVoto(1l, votoForm);

        assertNotNull(ticket);

        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
        verify(associadoService,times(1)).buscarAssociadoPorId(anyLong());
        verify(ticketService, times(1)).salvarTicket(any(Ticket.class));

    }

    private PautaForm getPautaForm(){
        PautaForm pautaForm = new PautaForm();

        pautaForm.setTitulo("Titulo da pauta");
        pautaForm.setDescricao("Descricao da pauta");

        return pautaForm;
    }

    private Pauta getPauta(){
        Pauta pauta = new Pauta();

        Sessao sessao = new Sessao();
        sessao.setDataHoraFinalizacao(LocalDateTime.now().plusMinutes(1));

        pauta.setTitulo("Titulo da pauta");
        pauta.setDescricao("Descricao da pauta apta para receber votacao");
        pauta.setSessao(sessao);

        return pauta;
    }

    private Associado getAssociado(){
        Associado associado = new Associado();

        associado.setCpf("11122233345");
        associado.setNome("Nome do associado");

        return associado;
    }

    private UserDto getUserDto(){
        UserDto userDto = new UserDto();

        userDto.setStatus(StatusEnum.ABLE_TO_VOTE);

        return userDto;
    }

}
