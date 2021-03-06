package br.com.nogueira.cooperativismo.schedules;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.services.KafkaService;
import br.com.nogueira.cooperativismo.services.PautaService;
import br.com.nogueira.cooperativismo.v1.business.AssociadoBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResultadoSchedule{

    @Autowired
    private PautaService pautaService;

    @Autowired
    private KafkaService<ResultadoDto> kafkaService;

    @Value("${topic.result.name}")
    private String topico;

    private static Logger Logger = LoggerFactory.getLogger(AssociadoBusiness.class);

    @Scheduled(fixedRate = 5000)
    public void apuraResultado() {
        Logger.info("Inicia schedule para apurar resultado");

        List<Pauta> pautas = pautaService.buscarTodasAsPautasFinalizadasSemResultado();

        Logger.info("Inicia processo de apuração das pautas {}", pautas);

        pautas.forEach(pauta -> {
            kafkaService.send(topico,pautaService.apurarResultado(pauta)).addCallback(
                    sucesso -> Logger.info("Mensagem enviada para o topico '{}' com sucesso {}",topico,sucesso),
                    falha -> Logger.info("Falha ao enviar mensagem para o topico {} {}",topico, falha));
        });
    }

}
