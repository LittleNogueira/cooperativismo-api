package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.repository.PautaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    private static Logger Logger = LoggerFactory.getLogger(PautaService.class);

    public Pauta salvarPauta(Pauta pauta){
        Logger.info("Entidade recebida na camanda de serviço {}", pauta);

        pauta = pautaRepository.save(pauta);

        Logger.info("Entidade persistida com sucesso {}", pauta);

        return pauta;
    }

    public Pauta buscarPautaPorId(Long id){
        Logger.info("Id {} recebido na camanda de serviço para realizar busca", id);

        Optional<Pauta> pauta = pautaRepository.findById(id);

        Logger.info("Busca realizada com sucesso {}", pauta);

        if(pauta.isEmpty()){
            Logger.info("Não existe pauta com id {}", id);
            throw new NotFoundException(MessageFormat.format("Pauta com id {0} não existe.",id));
        }

        Logger.info("Pauta encontrada com sucesso {}", pauta.get());
        return pauta.get();
    }

    public Boolean existePautaComVotoDoAssociado(Long idPauta, Long idAssociado){
        Logger.info("Verifica se existe voto do id associado {} no id pauta {}", idAssociado, idPauta);

        Boolean existeVotoDoAssociadoNaPauta = pautaRepository.existsByIdAndSessaoVotosAssociadoId(idPauta, idAssociado);

        Logger.info("Verificação realizada com sucesso");

        return existeVotoDoAssociadoNaPauta;
    }

    public List<Pauta> buscarTodasAsPautasFinalizadasSemResultado(){
        Logger.info("Inicia busca de pautas finalizadas sem resultado");

        List<Pauta> pautas = pautaRepository.findByResultadoIsNullAndSessaoDataHoraFinalizacaoBefore(LocalDateTime.now());

        Logger.info("Foram encontradas {} pautas finalizdas sem resultado {}", pautas.size(), pautas);

        return pautas;
    }

    public ResultadoDto apurarResultado(Pauta pauta){
        Logger.info("Entidade recebida na camanda de serviço {}", pauta);

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
