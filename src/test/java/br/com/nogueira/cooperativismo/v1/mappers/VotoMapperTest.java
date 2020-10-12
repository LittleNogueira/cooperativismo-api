package br.com.nogueira.cooperativismo.v1.mappers;

import br.com.nogueira.cooperativismo.dtos.VotoDto;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VotoMapperTest {

    @Test
    public void testaMapParaEntidade(){
        VotoForm votoForm = getVotoForm();
        Associado associado = new Associado();

        Voto voto = VotoMapper.INSTANCE.map(associado, votoForm);

        assertEquals(votoForm.getDataHoraVotacao(), voto.getDataHoraVotacao());
        assertEquals(votoForm.getVoto(), voto.getVoto());
        assertEquals(associado, voto.getAssociado());
    }

    @Test
    public void testaMapParaDto(){
        VotoForm votoForm = getVotoForm();
        Long idPauta = 1l;

        VotoDto voto = VotoMapper.INSTANCE.map(idPauta, votoForm);

        assertEquals(votoForm.getDataHoraVotacao(), voto.getDataHoraVotacao());
        assertEquals(votoForm.getVoto(), voto.getVoto());
        assertEquals(idPauta, voto.getIdPauta());
    }

    private VotoForm getVotoForm(){
        VotoForm votoForm = new VotoForm();

        votoForm.setIdAssociado(1l);
        votoForm.setVoto(VotoEnum.NAO);

        return votoForm;
    }

}
