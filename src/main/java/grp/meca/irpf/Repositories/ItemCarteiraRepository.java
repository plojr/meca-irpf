package grp.meca.irpf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.ItemCarteira;

@Repository
public interface ItemCarteiraRepository extends JpaRepository<ItemCarteira, Integer> {

}
