package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.clients.UserClient;
import br.com.nogueira.cooperativismo.dtos.UserDto;
import br.com.nogueira.cooperativismo.enums.StatusEnum;
import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Sessao;
import br.com.nogueira.cooperativismo.entities.Voto;
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
    private UserClient userClient;

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

            sessao.setDataHoraFinalizacao(sessao.getDataHoraCriacao().plusMinutes(1));
        }

        pauta.setSessao(sessao);

        Logger.info("Adiciona sessão para a pauta {}",pauta);

        pautaService.salvarPauta(pauta);

        return pauta;
    }

    public Voto criarVoto(Long idPauta, VotoForm votoForm){
        Logger.info("Formulario recebido na camanda de negócio {}", votoForm);

        Pauta pauta = pautaService.buscarPautaPorId(idPauta);
        validaSePautaEstaAptaParaVotacao(pauta,votoForm.getDataHoraVotacao());

        Associado associado = associadoService.buscarAssociadoPorId(votoForm.getIdAssociado());
        validaSeAssociadoEstaAptoParaVotarNaPauta(associado, pauta);

        Logger.info("Inicia mapeamento do formulario {} e associado {} para entidade", votoForm, associado);

        Voto voto = VotoMapper.INSTANCE.map(associado, votoForm);

        Logger.info("Mapeamento realizado com sucesso {}", voto);

        pauta.getSessao().getVotos().add(voto);

        Logger.info("Adiciona voto do associado na sessão {}",voto);

        pautaService.salvarPauta(pauta);

        return pauta.getSessao().getVotos().get(pauta.getSessao().getVotos().size() -1);
    }

    public Pauta buscarPautaPorId(Long id){
        Logger.info("Id {} recebido na camanda de negócio ", id);

        return pautaService.buscarPautaPorId(id);
    }

    private void validaSePautaEstaAptaParaVotacao(Pauta pauta, LocalDateTime dataHoraVotacao){
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

    private void validaSeAssociadoEstaAptoParaVotarNaPauta(Associado associado, Pauta pauta){
        Logger.info("Inicia a validação para verificar se a o associado {} pode votar na pauta {}", associado, pauta);

        if(pautaService.existePautaComVotoDoAssociado(pauta.getId(), associado.getId())){
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
