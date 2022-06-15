package grp.meca.irpf.Services;

import java.util.Map;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.Ordem;
import grp.meca.irpf.Models.Ticker;

public abstract class Trade {
	
	// Retornar o imposto a ser pago.
	public abstract double getImposto();
	
	// Retornar a taxa a ser paga de acodo com o Trade.
	public abstract double getTaxaIR();
	
	/*
	 * Uma nota consolidada, variável encontrada dentro da função, é uma nota onde todas as negociações 
	 * do mesmo tipo de um ticker são agrupadas em um único item do Map.
	 * Por exemplo: se houver 3 ordens de compra de xpto, então será consolidada uma única 
	 * ordem de compra de xpto com o preço médio ponderado e a quantidade igual à soma da quantidade 
	 * das três ordens.
	 */
	protected static void atualizarNotaConsolidada(Map<String, Pair<Integer, Double>> notaConsolidada, String ticker,
			Ordem ordem) {
		if(notaConsolidada.containsKey(ticker)) {
			int quantidadeAtual = notaConsolidada.get(ticker).getFirst();
			double custoAtual = notaConsolidada.get(ticker).getSecond();
			notaConsolidada.put(ticker, Pair.of(ordem.getQuantidade() + quantidadeAtual, ordem.getPreco()*ordem.getQuantidade() + custoAtual));
		}
		else
			notaConsolidada.put(ticker, Pair.of(ordem.getQuantidade(), ordem.getPreco()*ordem.getQuantidade()));
	}
	
	protected static Ordem getOrdemTrade(Map<String, Pair<Integer, Double>> notaConsolidadaCompras,
			Map<String, Pair<Integer, Double>> notaConsolidadaVendas, String ticker) {
		Ordem ordem = null;
		/*
		 * Suponha que houve compra de 100 xpto3 e venda de 30 xpto3. O maior valor é a ordem de compra.
		 * Com isso, é considerado que, desses 100 xpto3, 30 são relativas ao day trade, já que a venda 
		 * foi de 30 xpto3, e as 70 xpto3 formam uma compra para o swing trade.
		 * Se essas ações, tanto a compra de 100 xpto quanto a venda de 30,
		 * foram negociadas em mais de um trade, então é considerado o preço médio de compra e preço 
		 * médio de venda para cada uma delas, respectivamente. 
		 */
		if(notaConsolidadaCompras.containsKey(ticker) && notaConsolidadaVendas.containsKey(ticker)) {
			// Significa que houve mais compra do que venda.
			if(notaConsolidadaCompras.get(ticker).getFirst() > notaConsolidadaVendas.get(ticker).getFirst())
				ordem = getOrdemTrade(notaConsolidadaCompras, ticker, 'c', 
							notaConsolidadaCompras.get(ticker).getFirst() - notaConsolidadaVendas.get(ticker).getFirst());
			// Significa que houve mais venda do que compra.
			else if(notaConsolidadaVendas.get(ticker).getFirst() > notaConsolidadaCompras.get(ticker).getFirst())
				ordem = getOrdemTrade(notaConsolidadaVendas, ticker, 'v',
							notaConsolidadaVendas.get(ticker).getFirst() - notaConsolidadaCompras.get(ticker).getFirst());
			/*
			 *  Se houver o mesmo número de venda e compra, houve somente day trade para este ticker.
			 *  Com isso, é para retornar null, já que esta função retorna a ordem do tipo swing trade. 
			 */
		}
		else if(notaConsolidadaCompras.containsKey(ticker))
			ordem = getOrdemTrade(notaConsolidadaCompras, ticker, 'c', notaConsolidadaCompras.get(ticker).getFirst());
		else
			ordem = getOrdemTrade(notaConsolidadaVendas, ticker, 'v', notaConsolidadaVendas.get(ticker).getFirst());
		return ordem;
	}

	/*
	 *  Para desmembrar um pouco a função anterior, é que esta foi criada. Ela só calcula o novo custo total. 
	 */
	protected static Ordem getOrdemTrade(Map<String, Pair<Integer, Double>> notaConsolidada, String ticker, 
											char tipo, int quantidade) {
		Ordem ordem = null;
		double custoTrade = quantidade*notaConsolidada.get(ticker).getSecond()/notaConsolidada.get(ticker).getFirst();
		try {
			ordem = new Ordem(tipo, quantidade, new Ticker(ticker), custoTrade/quantidade, null);
		} catch(Exception e) {
			System.out.println("A função SwingTrade.getOrdemSwingTrade() lançou exceção ao instanciar uma Ordem."
					+ "É possível que haja algum erro lançamento de nota de corretagem e/ou ordem. A mensagem de "
					+ "exceção foi: " + e.getMessage());
		}
		return ordem;
	}
}
