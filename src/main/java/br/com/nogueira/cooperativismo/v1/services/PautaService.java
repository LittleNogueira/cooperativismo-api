package br.com.nogueira.cooperativismo.v1.services;

import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.v1.entities.Pauta;
import br.com.nogueira.cooperativismo.v1.entities.Sessao;
import br.com.nogueira.cooperativismo.v1.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta criarPauta(Pauta pauta){
        return pautaRepository.save(pauta);
    }

    public Pauta buscaPautaPorId(Long id){
        Optional<Pauta> pauta = pautaRepository.findById(id);

        if(pauta.isEmpty()){
            throw new NotFoundException(MessageFormat.format("Pauta com id {0} nao existe.",id));
        }

        return pauta.get();
    }

    public Pauta criarSessao(Long idPauta, Sessao sessao){
        Pauta pauta = buscaPautaPorId(idPauta);

        if(Objects.nonNull(pauta.getSessao())){
            throw new NotAcceptable("Esta pauta ja tem uma sessao.");
        }

        if(Objects.isNull(sessao.getDataHoraFinalizacao())){
            sessao.setDataHoraFinalizacao(sessao.getDataHoraCriacao().plusMinutes(1));
        }

        pauta.setSessao(sessao);

        return pautaRepository.save(pauta);
    }

}

