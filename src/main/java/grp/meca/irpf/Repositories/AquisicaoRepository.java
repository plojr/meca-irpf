package grp.meca.irpf.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.Eventos.Aquisicao;

@Repository
public interface AquisicaoRepository extends JpaRepository<Aquisicao, Integer> {
	public List<Aquisicao> findAllByOrderByDataEventoAsc();
}
