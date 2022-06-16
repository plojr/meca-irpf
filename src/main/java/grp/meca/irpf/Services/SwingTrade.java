package grp.meca.irpf.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import grp.meca.irpf.Models.Ticker;

public class SwingTrade extends Trade {

	@Override
	public double getImposto() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTaxaIR() {
		return 0.15;
	}
	/*
	 * A função getOrdensSwingTrade elimina todas as ordens de day trades, retornando apenas as 
	 * ordens relativas ao swing trade.
	 * A nota consolidada está explicada na classe Trade.
	 */
	public static List<Ordem> getOrdensSwingTrade(NotaDeCorretagem corretagem) {
		double taxas = corretagem.getTaxas(), valorBrutoDaCorretagem = corretagem.getValorBruto();
		/*
		 * Uma nota de corretagem pode conter várias ordens do mesmo ticker.
		 * Para simplificar, as ordens de compra foram consolidadas na variável notaConsolidadaCompras
		 * e as ordens de venda, em notaConsolidadaVendas.
		 * Isso vai facilitar para separar as ordens relativas ao day trade e ao swing trade.
		 */
		Map<String, Pair<Integer, Double>> notaConsolidadaCompras = new HashMap<>(), 
				notaConsolidadaVendas = new HashMap<>();
		// Set para guardar os tickers de forma única e poder acessá-los posteriormente.
		Set<String> tickers = new LinkedHashSet<>();
		atualizarNotasDeCorretagem(notaConsolidadaCompras, notaConsolidadaVendas, tickers, corretagem);
		List<Ordem> ordens = new ArrayList<>();
		// Para cada ticker, pegar a ordem (seja de compra ou de venda) consolidada relativa ao swing trade.
		for(String ticker: tickers) {
			Ordem ordem = getOrdemSwingTrade(notaConsolidadaCompras, notaConsolidadaVendas, ticker);
			/*
			 * Será null somente no caso de haver somente o day trade para o ticker.
			 * Com isso, se ordem for null, é para ignorar.
			 */
			if(ordem != null) {
				ordem.setTaxas(taxas*(ordem.getPreco()*ordem.getQuantidade()/Math.abs(valorBrutoDaCorretagem)));
				ordens.add(ordem);
			}
		}
		return ordens;
	}

	private static Ordem getOrdemSwingTrade(Map<String, Pair<Integer, Double>> notaConsolidadaCompras,
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
				ordem = getOrdemComNovoCustoTotal(notaConsolidadaCompras, ticker, 'c', 
							notaConsolidadaCompras.get(ticker).getFirst() - notaConsolidadaVendas.get(ticker).getFirst());
			// Significa que houve mais venda do que compra.
			else if(notaConsolidadaVendas.get(ticker).getFirst() > notaConsolidadaCompras.get(ticker).getFirst())
				ordem = getOrdemComNovoCustoTotal(notaConsolidadaVendas, ticker, 'v',
							notaConsolidadaVendas.get(ticker).getFirst() - notaConsolidadaCompras.get(ticker).getFirst());
			/*
			 *  Se houver o mesmo número de venda e compra, houve somente day trade para este ticker.
			 *  Com isso, é para retornar null, já que esta função retorna a ordem do tipo swing trade. 
			 */
		}
		else if(notaConsolidadaCompras.containsKey(ticker))
			ordem = getOrdemComNovoCustoTotal(notaConsolidadaCompras, ticker, 'c', notaConsolidadaCompras.get(ticker).getFirst());
		else
			ordem = getOrdemComNovoCustoTotal(notaConsolidadaVendas, ticker, 'v', notaConsolidadaVendas.get(ticker).getFirst());
		return ordem;
	}

	/*
	 *  Para desmembrar um pouco a função anterior, é que esta foi criada. 
	 *  Ela calcula o novo custo total, além do tratamento de exceção. 
	 */
	private static Ordem getOrdemComNovoCustoTotal(Map<String, Pair<Integer, Double>> notaConsolidada, String ticker, 
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
