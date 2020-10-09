package br.com.nogueira.cooperativismo.mappers;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Resultado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResultdoMapper {

    ResultdoMapper INSTANCE = Mappers.getMapper( ResultdoMapper.class );

    Resultado dtoParaEntidade(ResultadoDto dto);
}
