package br.com.nogueira.cooperativismo.v1.business.controllers;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.enums.VotoEnum;
import br.com.nogueira.cooperativismo.v1.business.PautaBusiness;
import br.com.nogueira.cooperativismo.v1.controllers.PautaController;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PautaControllerTest {

    @InjectMocks
    private PautaController pautaController;

    @Mock
    private PautaBusiness pautaBusiness;

    @Test
    void testaBuscarPautaPorId(){
        when(pautaBusiness.buscarPautaPorId(anyLong())).thenReturn(new Pauta());

        ResponseEntity<Pauta> associadoResponseEntity = pautaController.buscarPautaPorId(1l);

        assertNotNull(associadoResponseEntity.getBody());
        assertEquals(HttpStatus.OK,associadoResponseEntity.getStatusCode());
    }

    @Test
    void testaCriarPauta(){
        when(pautaBusiness.criarPauta(any(PautaForm.class))).thenReturn(new Pauta());

        ResponseEntity<Pauta> responseEntity = pautaController.criarPauta(getPautaForm(), UriComponentsBuilder.newInstance());

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }

    @Test
    void testaCriarSessao(){
        when(pautaBusiness.criarSessao(anyLong(),any(SessaoForm.class))).thenReturn(new Pauta());

        ResponseEntity<Pauta> responseEntity = pautaController.criarSessao(1l, new SessaoForm(), UriComponentsBuilder.newInstance());

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }

    @Test
    void testaCriarVoto(){
        when(pautaBusiness.criarVoto(anyLong(),any(VotoForm.class))).thenReturn(new Voto());

        ResponseEntity<Voto> responseEntity = pautaController.criarVoto(1l, getVotoForm(), UriComponentsBuilder.newInstance());

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }

    private PautaForm getPautaForm(){
        PautaForm pautaForm = new PautaForm();

        pautaForm.setTitulo("Titulo da pauta");
        pautaForm.setDescricao("Descricao da pauta");

        return pautaForm;
    }

    private VotoForm getVotoForm(){
        VotoForm votoForm = new VotoForm();

        votoForm.setIdAssociado(1l);
        votoForm.setVoto(VotoEnum.NAO);

        return votoForm;
    }

}
