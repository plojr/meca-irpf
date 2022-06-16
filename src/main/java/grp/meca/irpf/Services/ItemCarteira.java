/*
 * Uma carteira é uma lista de objetos de ItemCarteira, isto é, esta classe.
 * Um item de carteira é algo do tipo: 
 * 	- 1000 cple3 a um custo total de R$ 6000.00.
 *  - 5000 itsa4 a um custo total de R$ 50000.00.
 *  - etc
 * A partir disso, é possível lançar este valor no programa da Receita Federal.
 * Esta classe será usada no arquivo JSP de relatório.
 * 
 * Por que não usar esta classe, para representar uma carteira, em todo o código?
 * A resposta está em eficiência. Para atualizar a quantidade e custo total de um ticker,
 * será necessário percorrer toda a lista de ItemCarteira para encontrar o ticker em questão.
 * Outra opção, mais eficiente, seria ter um Map de String (representando o ticker) 
 * para ItemCarteira. Isso acrescenta uma redundância de dados já que ItemCarteira já contém
 * uma String para o ticker. Com isso, vem a ideia de retirar o ticker de ItemCarteira para
 * usar esta ideia de usar o Map. Algo similar é feito no código, principalmente nas classes
 * do tipo Trade.
 * 
 * Com isso, eu mantenho uma estrutura de Map para fazer os cálculos, por questão de eficiência,
 * e uso esta classe para apresentação na view.
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
