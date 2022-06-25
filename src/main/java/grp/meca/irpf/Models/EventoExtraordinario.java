/*
 * Esta classe será a classe base para qualquer evento extraordinário.
 * Lista de eventos extraordinários: cisão, fusão, grupamento e desdobramento.
 * Os campos em comum a todos eles são: o id, a data do evento e a empresa,
 * representada pelo ticker, responsável pelo evento.
 * Quando o evento envolver outra empresa, como nos casos de cisão e fusão,
 * a variável responsável por ela estará na classe filha.
 */

package grp.meca.irpf.Models;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.util.Pair;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class EventoExtraordinario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id_1", nullable = false)
	private Ticker ticker1;
	
	@Column(nullable = false)
	private LocalDate dataEvento;
	
	public abstract void aplicarEvento(Map<String, Pair<Integer, Double>> carteira);
}
