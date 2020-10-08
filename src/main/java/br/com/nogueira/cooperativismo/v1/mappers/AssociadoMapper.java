package br.com.nogueira.cooperativismo.v1.mappers;

import br.com.nogueira.cooperativismo.v1.entities.Associado;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssociadoMapper {

    AssociadoMapper INSTANCE = Mappers.getMapper( AssociadoMapper.class );

    Associado fomularioParaEntidade (AssociadoForm form);

}
