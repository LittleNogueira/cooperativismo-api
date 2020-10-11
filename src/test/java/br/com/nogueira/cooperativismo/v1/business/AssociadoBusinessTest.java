package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AssociadoBusinessTest {

    @InjectMocks
    private AssociadoBusiness associadoBusiness;

    @Mock
    private AssociadoService associadoService;

    @Test
    void testaCriarAssociado(){
        when(associadoService.salvarAssociado(any(Associado.class))).thenReturn(new Associado());

        Associado associado = associadoBusiness.criarAssociado(getAssociadoForm());

        assertNotNull(associado);
        verify(associadoService,times(1)).salvarAssociado(any(Associado.class));
    }

    @Test
    void testaBuscarAssociadoPorId(){
        when(associadoService.buscarAssociadoPorId(anyLong())).thenReturn(new Associado());

        Associado associado = associadoBusiness.buscarAssociadoPorId(1l);

        assertNotNull(associado);
        verify(associadoService,times(1)).buscarAssociadoPorId(anyLong());
    }

    private AssociadoForm getAssociadoForm(){
        AssociadoForm associadoForm = new AssociadoForm();

        associadoForm.setCpf("11122233345");
        associadoForm.setNome("Nome do associado");

        return associadoForm;
    }
}
