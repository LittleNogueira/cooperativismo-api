package br.com.nogueira.cooperativismo.v1.mappers;

import br.com.nogueira.cooperativismo.entities.Sessao;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessaoMapper {

    SessaoMapper INSTANCE = Mappers.getMapper( SessaoMapper.class );

    Sessao fomularioParaEntidade (SessaoForm form);

}
