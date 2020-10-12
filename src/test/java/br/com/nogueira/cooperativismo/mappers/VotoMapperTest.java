package br.com.nogueira.cooperativismo.mappers;

import br.com.nogueira.cooperativismo.dtos.VotoDto;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VotoMapperTest {

    @Test
    public void testaMap(){
        Associado associado = new Associado();

        VotoDto votoDto = new VotoDto();
        votoDto.setDataHoraVotacao(LocalDateTime.now());
        votoDto.setIdAssociado(1l);
        votoDto.setIdPauta(1l);
        votoDto.setVoto(VotoEnum.NAO);

        Voto voto = VotoMapper.INSTANCE.map(associado, votoDto);

        assertEquals(votoDto.getDataHoraVotacao(), voto.getDataHoraVotacao());
        assertEquals(votoDto.getVoto(), voto.getVoto());
        assertEquals(associado, voto.getAssociado());
    }

}
