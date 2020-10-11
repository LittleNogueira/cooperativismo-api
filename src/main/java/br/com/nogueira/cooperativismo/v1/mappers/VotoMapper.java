package br.com.nogueira.cooperativismo.v1.mappers;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VotoMapper {

    VotoMapper INSTANCE = Mappers.getMapper( VotoMapper.class );

    Voto map(Associado associado, VotoForm form);

}
