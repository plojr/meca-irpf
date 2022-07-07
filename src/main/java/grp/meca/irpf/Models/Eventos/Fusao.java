package grp.meca.irpf.Models.Eventos;

import java.time.LocalDate;

import javax.persistence.CascadeType;
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
public class Fusao extends EventoExtraordinario {

	public Fusao() {}
	
	public Fusao(Ticker ticker1, LocalDate dataEvento, Ticker ticker2, Ticker tickerNovaEmpresa, 
			double proporcaoDeAcoesEmpresa1, double proporcaoDeAcoesEmpresa2) {
		super(ticker1, dataEvento);
		this.ticker2 = ticker2;
		this.tickerNovaEmpresa = tickerNovaEmpresa;
		this.proporcaoDeAcoesEmpresa1 = proporcaoDeAcoesEmpresa1;
		this.proporcaoDeAcoesEmpresa2 = proporcaoDeAcoesEmpresa2;
	}
	
	// Esta é a empresa com a qual haverá a fusão.
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id_2", nullable = false)
	private Ticker ticker2;
	
	// Esta é a empresa que vai surgir da fusão.
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id_nova_empresa", nullable = false)
	private Ticker tickerNovaEmpresa;
	
	/*
	 * Esta é a proporção de ações da empresa 1 que o investidor precisa ter
	 * para que ele ganhe 1 ação da nova empresa.
	 */
	@Column(nullable = false)
	private double proporcaoDeAcoesEmpresa1;
	
	/*
	 * Esta é a proporção de ações da empresa 2 que o investidor precisa ter
	 * para que ele ganhe 1 ação da nova empresa.
	 */
	@Column(nullable = false)
	private double proporcaoDeAcoesEmpresa2;
}
