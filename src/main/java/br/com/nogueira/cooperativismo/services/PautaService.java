package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.clients.UserClient;
import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.dtos.UserDto;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.enums.StatusEnum;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private UserClient userClient;

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

    public void validaSePautaEstaAptaParaVotacao(Pauta pauta, LocalDateTime dataHoraVotacao){
        Logger.info("Inicia a validação para verificar se a pauta pode receber votos {}", pauta);

        if(Objects.isNull(pauta.getSessao())){
            Logger.info("Está pauta não tem uma sessão aberta {}", pauta);
            throw new NotAcceptable("Está pauta não tem uma sessão aberta.");
        }

        Logger.info("Existe uma sessão para está pauta {}", pauta);

        if(dataHoraVotacao.isAfter(pauta.getSessao().getDataHoraFinalizacao())){
            Logger.info("A sessão desta pauta já foi fechada {}", pauta);
            throw new NotAcceptable("A sessão desta pauta já foi fechada.");
        }

        Logger.info("Pauta está apta para receber votos {}", pauta);
    }

    public void validaSeAssociadoEstaAptoParaVotarNaPauta(Associado associado, Pauta pauta){
        Logger.info("Inicia a validação para verificar se a o associado {} pode votar na pauta {}", associado, pauta);

        if(existePautaComVotoDoAssociado(pauta.getId(), associado.getId())){
            Logger.info("Um associado {} não pode votar duas vezes na mesma pauta {}", associado, pauta);
            throw new NotAcceptable("Um associado não pode votar duas vezes na mesma pauta.");
        }

        Logger.info("O associado {} ainda não votou nesta pauta {}", associado, pauta);

        Logger.info("Inicia busca de usuario por cpf {} ", associado.getCpf());

        UserDto userDto = userClient.buscarUsuarioPorCpf(associado.getCpf());

        Logger.info("Busca por usuario realizada com sucesso {} ", userDto);

        if(userDto.getStatus().equals(StatusEnum.UNABLE_TO_VOTE)){
            Logger.info("O associado {} não está apto para votar", userDto);
            throw new NotAcceptable("O associado não está apto para votar.");
        }

        Logger.info("O associado está apto para votar {}", associado);
    }

}
