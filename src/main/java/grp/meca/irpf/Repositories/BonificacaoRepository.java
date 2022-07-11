package grp.meca.irpf.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.Eventos.Bonificacao;

@Repository
public interface BonificacaoRepository extends JpaRepository<Bonificacao, Integer> {
	public List<Bonificacao> findAllByOrderByDataEvento();
}
