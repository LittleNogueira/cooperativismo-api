package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.dtos.VotoDto;
import br.com.nogueira.cooperativismo.entities.*;
import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.mappers.TicketMapper;
import br.com.nogueira.cooperativismo.services.KafkaService;
import br.com.nogueira.cooperativismo.services.TicketService;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import br.com.nogueira.cooperativismo.v1.mappers.PautaMapper;
import br.com.nogueira.cooperativismo.v1.mappers.SessaoMapper;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import br.com.nogueira.cooperativismo.services.PautaService;
import br.com.nogueira.cooperativismo.v1.mappers.VotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;


@Component
public class PautaBusiness {

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private KafkaService<TicketDto> kafkaService;

    @Value("${topic.ticket.name}")
    private String topico;

    private static Logger Logger = LoggerFactory.getLogger(PautaBusiness.class);

    public Pauta criarPauta(PautaForm pautaForm){
        Logger.info("Formulario recebido na camanda de negócio {}", pautaForm);

        Logger.info("Inicia mapeamento do formulario para entidade {}",pautaForm);

        Pauta pauta = PautaMapper.INSTANCE.map(pautaForm);

        Logger.info("Mapeamento realizado com sucesso {}",pauta);

        return pautaService.salvarPauta(pauta);
    }

    public Pauta criarSessao(Long idPauta, SessaoForm sessaoForm){
        Logger.info("Formulario recebido na camanda de negócio {}", sessaoForm);

        Pauta pauta = pautaService.buscarPautaPorId(idPauta);

        Logger.info("Inicia mapeamento do formulario para entidade {}", sessaoForm);

        Sessao sessao = SessaoMapper.INSTANCE.map(sessaoForm);

        Logger.info("Mapeamento realizado com sucesso {}", sessao);

        if(Objects.nonNull(pauta.getSessao())){
            Logger.info("Está pauta já possui uma sessão {}",pauta);
            throw new NotAcceptable("Está pauta já possui uma sessão.");
        }

        if(Objects.isNull(sessao.getDataHoraFinalizacao())){
            LocalDateTime dataHora = sessao.getDataHoraCriacao().plusMinutes(1);

            Logger.info("Adiciona data e hora padrão para finalização da sessão {}",dataHora);

            sessao.setDataHoraFinalizacao(dataHora);
        }

        pauta.setSessao(sessao);

        Logger.info("Adiciona sessão para a pauta {}",pauta);

        pautaService.salvarPauta(pauta);

        return pauta;
    }

    public Ticket criarVoto(Long idPauta, VotoForm votoForm){
        Logger.info("Formulario recebido na camanda de negócio {}", votoForm);

        Pauta pauta = pautaService.buscarPautaPorId(idPauta);
        pautaService.validaSePautaEstaAptaParaVotacao(pauta, votoForm.getDataHoraVotacao());

        associadoService.buscarAssociadoPorId(votoForm.getIdAssociado());

        Logger.info("Inicia mapeamento do formulario {} para dto", votoForm);

        Ticket ticket = ticketService.salvarTicket(new Ticket());

        VotoDto votoDto = VotoMapper.INSTANCE.map(idPauta,votoForm);
        TicketDto ticketDto =  TicketMapper.INSTANCE.map(votoDto, ticket);

        kafkaService.send(topico,ticketDto).addCallback(
                success -> Logger.info("Mensagem enviada para o topico '{}' com sucesso {}",topico,success),
                fail -> Logger.info("Falha ao enviar mensagem para o topico {} {}",topico, fail));

        return ticket;
    }

    public Pauta buscarPautaPorId(Long id){
        Logger.info("Id {} recebido na camanda de negócio ", id);

        return pautaService.buscarPautaPorId(id);
    }

}
