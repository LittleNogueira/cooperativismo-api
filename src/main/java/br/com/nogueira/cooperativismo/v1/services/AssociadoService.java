package br.com.nogueira.cooperativismo.v1.services;

import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.v1.entities.Associado;
import br.com.nogueira.cooperativismo.enums.StatusEnum;
import br.com.nogueira.cooperativismo.v1.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    public Associado criarAssociado(Associado associado){
        return associadoRepository.save(associado);
    }

    public Associado buscaAssociadoPorId(Long id){
        Optional<Associado> associado = associadoRepository.findById(id);

        if(associado.isEmpty()){
            throw new NotFoundException(MessageFormat.format("Associado com id {0} nao existe.",id));
        }

        return associado.get();
    }
}
