package br.com.nogueira.cooperativismo.schedules;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.*;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.services.KafkaService;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ResultadoScheduleTest {

    @InjectMocks
    private ResultadoSchedule resultadoSchedule;

    @Mock
    private PautaService pautaService;

    @Mock
    private KafkaService<ResultadoDto> kafkaService;

    @Test
    void testaApuraResultado(){
        when(pautaService.buscarTodasAsPautasFinalizadasSemResultado()).thenReturn(Arrays.asList(getPauta(),getPauta()));
        when(pautaService.apurarResultado(any(Pauta.class))).thenReturn(new ResultadoDto(1l,1,1));
        when(kafkaService.send(anyString(),any(ResultadoDto.class))).thenReturn(new SettableListenableFuture<>());

        resultadoSchedule.apuraResultado();

        verify(kafkaService,times(2)).send(anyString(),any(ResultadoDto.class));
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
