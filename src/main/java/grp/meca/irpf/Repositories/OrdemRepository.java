package grp.meca.irpf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.Ordem;

@Repository
public interface OrdemRepository extends JpaRepository<Ordem, Integer> {

}
