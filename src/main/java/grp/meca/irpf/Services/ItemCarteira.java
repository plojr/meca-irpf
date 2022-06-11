/*
 * Uma carteira é uma lista de objetos de ItemCarteira, isto é, esta classe.
 * Um item de carteira é algo do tipo: 
 * 	- 1000 cple3 a um custo total de R$ 6000.00.
 *  - 5000 itsa4 a um custo total de R$ 50000.00.
 *  - etc
 * A partir disso, é possível lançar este valor no programa da Receita Federal.
 */

package grp.meca.irpf.Models;

import lombok.Data;

@Data
public class ItemCarteira {

	private Ticker ticker;
	private int quantidade;
	private double custoTotal;
}
