package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.clients.UserClient;
import br.com.nogueira.cooperativismo.dtos.UserDto;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Sessao;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.enums.StatusEnum;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import br.com.nogueira.cooperativismo.services.PautaService;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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
    private UserClient userClient;

    @Test
    void testaCriarPauta(){
        when(pautaService.salvarPauta(any(Pauta.class))).thenReturn(new Pauta());

        Pauta pauta = pautaBusiness.criarPauta(getPautaForm());

        assertNotNull(pauta);
        verify(pautaService,times(1)).salvarPauta(any(Pauta.class));
    }

    @Test
    void testaBuscarPautaPorId(){
        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(new Pauta());

        Pauta pauta = pautaService.buscarPautaPorId(1l);

        assertNotNull(pauta);
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
    }

    @Test
    void testaCriarSessaoComDataHoraFinalizacaoPadrao(){
        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(new Pauta());

        Pauta pauta = pautaBusiness.criarSessao(1l,new SessaoForm());

        assertNotNull(pauta);
        verify(pautaService,times(1)).salvarPauta(any(Pauta.class));
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
    }

    @Test
    void testaCriarSessaoComDataHoraFinalizacaoInformada(){
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
    void testaCriarVoto(){
        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(getPauta());
        when(associadoService.buscarAssociadoPorId(anyLong())).thenReturn(getAssociado());
        when(userClient.buscarUsuarioPorCpf(anyString())).thenReturn(getUserDto());
        when(pautaService.existePautaComVotoDoAssociado(any(), any())).thenReturn(Boolean.FALSE);

        VotoForm votoForm = new VotoForm();
        votoForm.setVoto(VotoEnum.SIM);
        votoForm.setIdAssociado(1l);

        Voto voto = pautaBusiness.criarVoto(1l, votoForm);

        assertNotNull(voto);

        verify(pautaService,times(1)).salvarPauta(any(Pauta.class));
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
        verify(userClient,times(1)).buscarUsuarioPorCpf(anyString());
        verify(associadoService,times(1)).buscarAssociadoPorId(anyLong());
        verify(pautaService,times(1)).existePautaComVotoDoAssociado(any(), any());
    }

    @Test
    void testaCriarVotoEmUmaPautaSemSessao(){
        Pauta pauta = getPauta();
        pauta.setSessao(null);

        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(pauta);

        VotoForm votoForm = new VotoForm();
        votoForm.setVoto(VotoEnum.SIM);
        votoForm.setIdAssociado(1l);

        NotAcceptable exception = assertThrows(NotAcceptable.class, () -> {
            pautaBusiness.criarVoto(1l, votoForm);
        });

        assertEquals("Está pauta não tem uma sessão aberta.", exception.getMessage());
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
    }

    @Test
    void testaCriarVotoEmUmaPautaComSessaoFechada(){
        VotoForm votoForm = new VotoForm();
        votoForm.setVoto(VotoEnum.SIM);
        votoForm.setIdAssociado(1l);

        Pauta pauta = getPauta();
        pauta.getSessao().setDataHoraFinalizacao(votoForm.getDataHoraVotacao().minusMinutes(1));

        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(pauta);

        NotAcceptable exception = assertThrows(NotAcceptable.class, () -> {
            pautaBusiness.criarVoto(1l, votoForm);
        });

        assertEquals("A sessão desta pauta já foi fechada.", exception.getMessage());
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
    }

    @Test
    void testaCriarVotoEmUmaPautaOndeOAssociadoJaVotou(){
        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(getPauta());
        when(associadoService.buscarAssociadoPorId(anyLong())).thenReturn(getAssociado());
        when(pautaService.existePautaComVotoDoAssociado(any(), any())).thenReturn(Boolean.TRUE);

        VotoForm votoForm = new VotoForm();
        votoForm.setVoto(VotoEnum.SIM);
        votoForm.setIdAssociado(1l);

        NotAcceptable exception = assertThrows(NotAcceptable.class, () -> {
            pautaBusiness.criarVoto(1l, votoForm);
        });

        assertEquals("Um associado não pode votar duas vezes na mesma pauta.", exception.getMessage());

        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
        verify(associadoService,times(1)).buscarAssociadoPorId(anyLong());
        verify(pautaService,times(1)).existePautaComVotoDoAssociado(any(), any());
    }

    @Test
    void testaCriarVotoComAssociadNaoAptoParaVotar(){
        UserDto userDto = getUserDto();
        userDto.setStatus(StatusEnum.UNABLE_TO_VOTE);

        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(getPauta());
        when(associadoService.buscarAssociadoPorId(anyLong())).thenReturn(getAssociado());
        when(userClient.buscarUsuarioPorCpf(anyString())).thenReturn(userDto);
        when(pautaService.existePautaComVotoDoAssociado(any(), any())).thenReturn(Boolean.FALSE);

        VotoForm votoForm = new VotoForm();
        votoForm.setVoto(VotoEnum.SIM);
        votoForm.setIdAssociado(1l);

        NotAcceptable exception = assertThrows(NotAcceptable.class, () -> {
            pautaBusiness.criarVoto(1l, votoForm);
        });

        assertEquals("O associado não está apto para votar.", exception.getMessage());

        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
        verify(userClient,times(1)).buscarUsuarioPorCpf(anyString());
        verify(associadoService,times(1)).buscarAssociadoPorId(anyLong());
        verify(pautaService,times(1)).existePautaComVotoDoAssociado(any(), any());
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
