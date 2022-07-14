package grp.meca.irpf.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.Eventos.Fusao;

@Repository
public interface FusaoRepository extends JpaRepository<Fusao, Integer> {
	public List<Fusao> findAllByOrderByDataEvento();
}
