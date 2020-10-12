package br.com.nogueira.cooperativismo.mappers;

import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.dtos.VotoDto;
import br.com.nogueira.cooperativismo.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper( TicketMapper.class );

    TicketDto map(VotoDto votoDto, Ticket ticket);

}
