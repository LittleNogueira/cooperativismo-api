package br.com.nogueira.cooperativismo.schedules;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.services.KafkaService;
import br.com.nogueira.cooperativismo.services.PautaService;
import br.com.nogueira.cooperativismo.v1.business.AssociadoBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger Logger = LoggerFactory.getLogger(AssociadoBusiness.class);

    @Scheduled(fixedRate = 5000)
    public void apuraResultado() {
        Logger.info("Inicia schedule para apurar resultado");

        List<Pauta> pautas = pautaService.buscarTodasAsPautasFinalizadasSemResultado();

        Logger.info("Inicia processo de apuração das pautas {}", pautas);

        pautas.forEach(pauta -> {
            kafkaService.send("topico",apurarResultado(pauta)).addCallback(
                    success -> Logger.info("Mensagem enviada para o topico {} com sucesso {}","topico",success),
                    fail -> Logger.info("Falha ao enviar mensagem para o topico {} {}", "topico", fail));
        });
    }

    private ResultadoDto apurarResultado(Pauta pauta){

        Logger.info("Apura votação da pauta {}", pauta);

        int votosSim = pauta.getSessao().getVotos().stream()
                .filter(voto -> voto.getVoto().equals(VotoEnum.SIM))
                .collect(Collectors.toList()).size();

        int votosNao = pauta.getSessao().getVotos().size() - votosSim;

        ResultadoDto resultadoDto = new ResultadoDto(pauta.getId(),votosSim, votosNao);

        Logger.info("Finaliza apuração de votos {}", resultadoDto);

        return resultadoDto;
    }

}
