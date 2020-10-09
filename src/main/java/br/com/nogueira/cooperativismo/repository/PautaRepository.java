package br.com.nogueira.cooperativismo.repository;

import br.com.nogueira.cooperativismo.entities.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    Boolean existsByIdAndSessaoVotosAssociadoId(Long idPauta, Long idAssociado);

}
