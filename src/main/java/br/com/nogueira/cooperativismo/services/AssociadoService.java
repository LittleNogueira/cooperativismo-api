package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.repository.AssociadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    private static Logger Logger = LoggerFactory.getLogger(AssociadoService.class);

    public Associado salvarAssociado(Associado associado){
        Logger.info("Entidade recebida na camanda de serviço {}", associado);

        associado = associadoRepository.save(associado);

        Logger.info("Entidade persistida com sucesso {}", associado);

        return associado;
    }

    public Associado buscarAssociadoPorId(Long id){
        Logger.info("Id {} recebido na camanda de serviço para realizar busca", id);

        Optional<Associado> associado = associadoRepository.findById(id);

        Logger.info("Busca realizada com sucesso {}", associado);

        if(associado.isEmpty()){
            Logger.info("Não existe associado com id {}", id);
            throw new NotFoundException(MessageFormat.format("Associado com id {0} não existe.",id));
        }

        Logger.info("Associado encontrada com sucesso {}", associado.get());
        return associado.get();
    }
}
