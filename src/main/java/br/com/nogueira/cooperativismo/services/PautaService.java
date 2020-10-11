package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.CooperativismoApplication;
import br.com.nogueira.cooperativismo.entities.Resultado;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.enums.StatusEnum;
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

    public Pauta buscaPautaPorId(Long id){
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

}
