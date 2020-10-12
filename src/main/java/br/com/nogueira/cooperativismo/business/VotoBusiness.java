package br.com.nogueira.cooperativismo.business;

import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.mappers.VotoMapper;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotoBusiness {

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    private static Logger Logger = LoggerFactory.getLogger(VotoBusiness.class);

    public Voto criarVoto(TicketDto ticketDto){
        Logger.info("Dto recebido na camanda de negócio {}", ticketDto);

        Pauta pauta = pautaService.buscarPautaPorId(ticketDto.getVotoDto().getIdPauta());
        pautaService.validaSePautaEstaAptaParaVotacao(pauta,ticketDto.getVotoDto().getDataHoraVotacao());

        Associado associado = associadoService.buscarAssociadoPorId(ticketDto.getVotoDto().getIdAssociado());
        pautaService.validaSeAssociadoEstaAptoParaVotarNaPauta(associado, pauta);

        Logger.info("Inicia mapeamento do dto {} e associado {} para entidade", ticketDto.getVotoDto(), associado);

        Voto voto = VotoMapper.INSTANCE.map(associado, ticketDto.getVotoDto());

        Logger.info("Mapeamento realizado com sucesso {}", voto);

        pauta.getSessao().getVotos().add(voto);

        Logger.info("Adiciona voto do associado na sessão {}",voto);

        pautaService.salvarPauta(pauta);

        return pauta.getSessao().getVotos().get(pauta.getSessao().getVotos().size() -1);
    }

}

