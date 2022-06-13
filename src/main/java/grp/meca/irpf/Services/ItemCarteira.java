/*
 * Uma carteira é uma lista de objetos de ItemCarteira, isto é, esta classe.
 * Um item de carteira é algo do tipo: 
 * 	- 1000 cple3 a um custo total de R$ 6000.00.
 *  - 5000 itsa4 a um custo total de R$ 50000.00.
 *  - etc
 * A partir disso, é possível lançar este valor no programa da Receita Federal.
 * Esta classe será usada no arquivo JSP de relatório.
 */

package grp.meca.irpf.Services;

import lombok.Data;

@Data
public class ItemCarteira {

	private String ticker;
	private String cnpj;
	private int quantidade;
	private double custoTotal;
	
	public ItemCarteira(String ticker, String cnpj, int quantidade, double custoTotal) {
		super();
		this.ticker = ticker;
		this.cnpj = cnpj;
		this.quantidade = quantidade;
		this.custoTotal = custoTotal;
	}
}
