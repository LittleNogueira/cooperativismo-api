package br.com.nogueira.cooperativismo.v1.mappers;

import br.com.nogueira.cooperativismo.entities.Sessao;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SessaoMapperTest {

    @Test
    public void testaMap(){
        SessaoForm sessaoForm = new SessaoForm();
        sessaoForm.setDataHoraFinalizacao(LocalDateTime.now());

        Sessao sessao = SessaoMapper.INSTANCE.map(sessaoForm);

        assertEquals(sessaoForm.getDataHoraFinalizacao(), sessao.getDataHoraFinalizacao());
    }

}
