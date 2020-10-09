package br.com.nogueira.cooperativismo.schedules;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.services.KafkaService;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResultadoSchedule{

    @Autowired
    private PautaService pautaService;

    @Autowired
    private KafkaService<ResultadoDto> kafkaService;

    @Scheduled(fixedRate = 5000)
    public void apuraResultado() {
        List<Pauta> pautas = pautaService.buscarTodasAsPautasFinalizadasSemResultado();
        pautas.forEach(pauta -> kafkaService.send("topico",apurarResultado(pauta)));
    }

    private ResultadoDto apurarResultado(Pauta pauta){
        int votosSim = pauta.getSessao().getVotos().stream()
                .filter(voto -> voto.getVoto().equals(VotoEnum.SIM))
                .collect(Collectors.toList()).size();

        int votosNao = pauta.getSessao().getVotos().size() - votosSim;

        return new ResultadoDto(pauta.getId(),votosSim, votosNao);
    }

}
