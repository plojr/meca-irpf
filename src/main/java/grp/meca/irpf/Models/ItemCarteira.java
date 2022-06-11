/*
 * Uma carteira é uma lista de objetos de ItemCarteira, isto é, esta classe.
 * Um item de carteira é algo do tipo: 
 * 	- 1000 cple3 a um custo total de R$ 6000.00.
 *  - 5000 itsa4 a um custo total de R$ 50000.00.
 *  - etc
 * A partir disso, é possível lançar este valor no programa da Receita Federal.
 */

package grp.meca.irpf.Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
(name = "items_carteira")
public class ItemCarteira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id", nullable = false)
	private Ticker ticker;
	
	@Column(nullable = false)
	private int quantidade;
	
	@Column(nullable = false)
	private double custoTotal;
}
