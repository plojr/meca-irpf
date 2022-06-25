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

package grp.meca.irpf.Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity(name = "cisao")
public class Cisao extends EventoExtraordinario {
	
	// Esta é a empresa que foi cindida.
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id_2", nullable = false)
	private Ticker ticker2;
	
	@Column(nullable = false)
	private double porcentagemCindida;
	
	@Column(nullable = false)
	private double proporcao;
}
