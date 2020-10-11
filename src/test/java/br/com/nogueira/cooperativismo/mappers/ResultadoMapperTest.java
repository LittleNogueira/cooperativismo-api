package br.com.nogueira.cooperativismo.mappers;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Resultado;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResultadoMapperTest {

    @Test
    void testaMap(){
        ResultadoDto resultadoDto = new ResultadoDto(1l,2,1);

        Resultado resultado = ResultadoMapper.INSTANCE.dtoParaEntidade(resultadoDto);

        assertEquals(resultadoDto.getVotosSim(), resultado.getVotosSim());
        assertEquals(resultadoDto.getVotosNao(), resultado.getVotosNao());
        assertEquals(resultadoDto.getResultado(), resultado.getResultado());
    }
}
