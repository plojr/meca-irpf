package grp.meca.irpf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.Bonificacao;

@Repository
public interface BonificacaoRepository extends JpaRepository<Bonificacao, Integer> {

}
