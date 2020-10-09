package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta salvarPauta(Pauta pauta){
        return pautaRepository.save(pauta);
    }

    public Pauta buscaPautaPorId(Long id){
        Optional<Pauta> pauta = pautaRepository.findById(id);

        if(pauta.isEmpty()){
            throw new NotFoundException(MessageFormat.format("Pauta com id {0} nao existe.",id));
        }

        return pauta.get();
    }

    public Boolean existePautaComVotoDoAssociado(Long idPauta, Long idAssociado){
        return pautaRepository.existsByIdAndSessaoVotosAssociadoId(idPauta, idAssociado);
    }

}

