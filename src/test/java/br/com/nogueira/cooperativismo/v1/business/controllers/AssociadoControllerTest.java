package br.com.nogueira.cooperativismo.v1.business.controllers;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.v1.business.AssociadoBusiness;
import br.com.nogueira.cooperativismo.v1.controllers.AssociadoController;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
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
public class AssociadoControllerTest {

    @InjectMocks
    private AssociadoController associadoController;

    @Mock
    private AssociadoBusiness associadoBusiness;

    @Test
    void testaCriarAssociado(){
        when(associadoBusiness.criarAssociado(any(AssociadoForm.class))).thenReturn(new Associado());

        ResponseEntity<Associado> associadoResponseEntity = associadoController.criarPauta(getAssociadoForm(), UriComponentsBuilder.newInstance());

        assertNotNull(associadoResponseEntity.getBody());
        assertEquals(HttpStatus.CREATED,associadoResponseEntity.getStatusCode());
        assertTrue(associadoResponseEntity.getHeaders().containsKey("Location"));
    }

    @Test
    void testaBuscarAssociadoPorId(){
        when(associadoBusiness.buscarAssociadoPorId(anyLong())).thenReturn(new Associado());

        ResponseEntity<Associado> associadoResponseEntity = associadoController.buscaAssociadoPorId(1l);

        assertNotNull(associadoResponseEntity.getBody());
        assertEquals(HttpStatus.OK,associadoResponseEntity.getStatusCode());
    }

    private AssociadoForm getAssociadoForm(){
        AssociadoForm associadoForm = new AssociadoForm();

        associadoForm.setNome("Nome do associado");
        associadoForm.setCpf("11122233345");

        return associadoForm;
    }

}
