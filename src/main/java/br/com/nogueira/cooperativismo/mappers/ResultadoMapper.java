package br.com.nogueira.cooperativismo.mappers;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Resultado;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResultadoMapper {

    ResultadoMapper INSTANCE = Mappers.getMapper( ResultadoMapper.class );

    Resultado dtoParaEntidade(ResultadoDto dto);
}
