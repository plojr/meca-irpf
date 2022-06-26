package grp.meca.irpf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.Grupamento;

@Repository
public interface GrupamentoRepository extends JpaRepository<Grupamento, Integer> {

}
