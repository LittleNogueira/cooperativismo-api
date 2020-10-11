package br.com.nogueira.cooperativismo.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;

@Mapper
public interface PautaMapper {
    
   PautaMapper INSTANCE = Mappers.getMapper( PautaMapper.class );

   Pauta map (PautaForm form);

}

