package br.com.nogueira.cooperativismo.mappers;

import br.com.nogueira.cooperativismo.dtos.VotoDto;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Voto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VotoMapper {

    VotoMapper INSTANCE = Mappers.getMapper( VotoMapper.class );

    Voto map(Associado associado, VotoDto customer);

}
