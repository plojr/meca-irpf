package grp.meca.irpf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.Eventos.Desdobramento;

@Repository
public interface DesdobramentoRepository extends JpaRepository<Desdobramento, Integer> {

}
