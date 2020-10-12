package br.com.nogueira.cooperativismo.business;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Sessao;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ResultadoBusinessTest {

    @InjectMocks
    private ResultadoBusiness resultadoBusiness;

    @Mock
    private PautaService pautaService;

    @Test
    public void testaCriarResultado(){
        when(pautaService.buscarPautaPorId(anyLong())).thenReturn(getPauta());

        resultadoBusiness.criarResultado(new ResultadoDto(1l,1,1));

        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
        verify(pautaService,times(1)).salvarPauta(any(Pauta.class));
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
