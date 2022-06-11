package grp.meca.irpf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.Ticker;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Integer> {
	
	Ticker findByCodigo(String codigo);
}
