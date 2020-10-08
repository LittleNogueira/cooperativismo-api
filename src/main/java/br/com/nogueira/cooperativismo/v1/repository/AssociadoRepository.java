package br.com.nogueira.cooperativismo.v1.repository;

import br.com.nogueira.cooperativismo.v1.entities.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
}