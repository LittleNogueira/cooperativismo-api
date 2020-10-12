package br.com.nogueira.cooperativismo.repository;

import br.com.nogueira.cooperativismo.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
