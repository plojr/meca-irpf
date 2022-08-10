package grp.meca.irpf.Models.Eventos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import grp.meca.irpf.Models.Basico.Ticker;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class Aquisicao extends EventoExtraordinario {

	public Aquisicao() {}
	
	public Aquisicao(Ticker ticker1, LocalDate dataEvento, Ticker ticker2, Double proporcaoDeAcoes, Double precoPorAcao) {
		super(ticker1, dataEvento);
		this.ticker2 = ticker2;
		this.proporcaoDeAcoes = proporcaoDeAcoes;
		this.precoPorAcao = precoPorAcao;
	}
	
	// Esta é a empresa que será adquirida.
	@OneToOne
	@JoinColumn(name = "ticker_id_2", nullable = false)
	private Ticker ticker2;
	
	@Column(nullable = false)
	private Double proporcaoDeAcoes;
	
	@Column(nullable = false)
	private Double precoPorAcao;
}
