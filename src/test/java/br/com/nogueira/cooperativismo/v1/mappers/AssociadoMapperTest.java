package br.com.nogueira.cooperativismo.v1.mappers;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AssociadoMapperTest {

    @Test
    void testaMap(){
        AssociadoForm associadoForm = getAssociadoForm();

        Associado associado = AssociadoMapper.INSTANCE.map(associadoForm);

        assertEquals(associadoForm.getNome(), associado.getNome());
        assertEquals(associadoForm.getCpf(), associado.getCpf());
    }

    private AssociadoForm getAssociadoForm(){
        AssociadoForm associadoForm = new AssociadoForm();

        associadoForm.setNome("Nome do associado");
        associadoForm.setCpf("11122233345");

        return associadoForm;
    }

}
