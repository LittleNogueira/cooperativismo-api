package br.com.nogueira.cooperativismo.v1.mappers;

import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PautaMapperTest {

    @Test
    void testaMap(){
        PautaForm pautaForm = getPautaForm();
        Pauta pauta = PautaMapper.INSTANCE.map(pautaForm);

        assertEquals(pautaForm.getTitulo(), pauta.getTitulo());
        assertEquals(pautaForm.getDescricao(), pauta.getDescricao());
    }

    private PautaForm getPautaForm(){
        PautaForm pautaForm = new PautaForm();

        pautaForm.setTitulo("Titulo da pauta");
        pautaForm.setDescricao("Descricao da pauta");

        return pautaForm;
    }

}
