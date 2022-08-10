/*
 * Esta classe é para representar a cisão que pode acontecer por parte das empresas.
 * Exemplo de cisão de 2021 foi a abertura do capital da Getnet a partir da cisão
 * do Santander. A porcentagem cindida foi aproximadamente de 3,1422%, sendo que
 * a cada ação do Santander, o investidor recebeu 0,25 da mesma classe de Getnet.
 * Isto é, se o investidor tinha ON de Santander, ele recebeu ON de Getnet, e assim
 * por diante.
 * É também importante salvar a data da cisão no banco. No exemplo acima, a data da
 * cisão foi 18/10/2021.
 * Para deixar claro, na hora de cadastrar, é necessário adicionar a cisão de cada
 * tipo de ação. Tomando o exemplo anterior, será necessário adicionar uma cisão para
 * as ações ON, uma para as PN e uma para a UNIT.
 */

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
@Entity(name = "cisao")
public class Cisao extends EventoExtraordinario {
	
	public Cisao() {}
	
	public Cisao(Ticker ticker1, LocalDate dataEvento, Ticker ticker2, double parteCindida, double proporcaoDeAcoes) {
		super(ticker1, dataEvento);
		this.ticker2 = ticker2;
		this.parteCindida = parteCindida;
		this.proporcaoDeAcoes = proporcaoDeAcoes;
	}

	// Esta é a nova empresa.
	@OneToOne
	@JoinColumn(name = "ticker_id_2", nullable = false)
	private Ticker ticker2;
	
	/*
	 * Parte cindida é a porcentagem que será separada da empresa original.
	 */
	@Column(nullable = false)
	private double parteCindida;
	
	/*
	 * Só para ficar claro: proporção de ações significa quantas ações da empresa cindida o investidor vai receber 
	 * para cada ação da empresa original, e não quantas ações da empresa original o investidor precisa ter para 
	 * receber 1 da empresa cindida.
	 * No exemplo da cisão da Getnet, a proporção foi de 0,25, isto é, para cada ação de Santander, o investidor
	 * recebeu 0,25 ações da Getnet.
	 */
	@Column(nullable = false)
	private double proporcaoDeAcoes;
	
}
