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

public class SwingTrade implements Trade {

	@Override
	public double getImposto() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTaxaIR() {
		return 0.15;
	}
	/*
	 * A função getOrdensSwingTrade elimina todos os day trades, retornando apenas as ordens relativas ao swing trade.
	 * 
	 * Uma nota consolidada, variável encontrada dentro da função, é uma nota onde todas as negociações do mesmo tipo 
	 * de um ticker são agrupadas em um único item do Map.
	 * Por exemplo: se houver 3 ordens de compra de xpto, então será consolidada uma única ordem de compra de xpto com o preço
	 * médio ponderado e a quantidade igual à soma da quantidade das três ordens.
	 */
	public static List<Ordem> getOrdensSwingTrade(NotaDeCorretagem corretagem) {
		List<Ordem> ordens = new ArrayList<>();
		Map<String, Pair<Integer, Double>> notaConsolidadaCompras = new HashMap<>(), notaConsolidadaVendas = new HashMap<>();
		Set<String> tickers = new LinkedHashSet<>();
		for(Ordem ordem: corretagem.getOrdens()) {
			String ticker = ordem.getTicker().getCodigo();
			tickers.add(ticker);
			if(ordem.getTipo() == 'c')
				atualizarNotaConsolidada(notaConsolidadaCompras, ticker, ordem);
			else
				atualizarNotaConsolidada(notaConsolidadaVendas, ticker, ordem);
		}
		for(String ticker: tickers) {
			Ordem ordem = getOrdemSwingTrade(notaConsolidadaCompras, notaConsolidadaVendas, ticker);
			/*
			 * Será null somente no caso de haver um day trade, onde a quantidade de ações compradas
			 * é igual à quantidade de ações vendidas. 
			 */
			if(ordem != null)
				ordens.add(ordem);
		}
		return ordens;
	}

	private static void atualizarNotaConsolidada(Map<String, Pair<Integer, Double>> notaConsolidada, String ticker,
			Ordem ordem) {
		if(notaConsolidada.containsKey(ticker)) {
			int quantidadeAtual = notaConsolidada.get(ticker).getFirst();
			double custoAtual = notaConsolidada.get(ticker).getSecond();
			notaConsolidada.put(ticker, Pair.of(ordem.getQuantidade() + quantidadeAtual, ordem.getPreco()*ordem.getQuantidade() + custoAtual));
		}
		else
			notaConsolidada.put(ticker, Pair.of(ordem.getQuantidade(), ordem.getPreco()*ordem.getQuantidade()));
	}

	private static Ordem getOrdemSwingTrade(Map<String, Pair<Integer, Double>> notaConsolidadaCompras,
			Map<String, Pair<Integer, Double>> notaConsolidadaVendas, String ticker) {
		Ordem ordem = null;
		/*
		 *  Isto é, se é day trade, olhar a quantidade de day trade. Exemplo:
		 *  Se houve compra de 100 xpto e venda de 30 xpto, é considerado que houve day trade de 30 xpto e 
		 *  um swing trade de 70 xpto, neste caso, uma compra. Se essas ações, tanto a compra de 100 xpto quanto a venda de 30,
		 *  foram negociadas em mais de um trade, então é considerado o preço médio de compra e preço médio de venda. 
		 */
		System.out.println("SwingTrade.getOrdemSwingTrade(): " + ticker + " " + notaConsolidadaCompras.containsKey(ticker) + " " + notaConsolidadaVendas.containsKey(ticker));
		if(notaConsolidadaCompras.containsKey(ticker) && notaConsolidadaVendas.containsKey(ticker)) {
			// Significa que houve mais compra do que venda.
			if(notaConsolidadaCompras.get(ticker).getFirst() > notaConsolidadaVendas.get(ticker).getFirst())
				ordem = getOrdemSwingTrade(notaConsolidadaCompras, ticker, 'c', 
							notaConsolidadaCompras.get(ticker).getFirst() - notaConsolidadaVendas.get(ticker).getFirst());
			// Significa que houve mais venda do que compra.
			else if(notaConsolidadaVendas.get(ticker).getFirst() > notaConsolidadaCompras.get(ticker).getFirst())
				ordem = getOrdemSwingTrade(notaConsolidadaVendas, ticker, 'v',
							notaConsolidadaVendas.get(ticker).getFirst() - notaConsolidadaCompras.get(ticker).getFirst());
		}
		else if(notaConsolidadaCompras.containsKey(ticker))
			ordem = getOrdemSwingTrade(notaConsolidadaCompras, ticker, 'c', notaConsolidadaCompras.get(ticker).getFirst());
		else
			ordem = getOrdemSwingTrade(notaConsolidadaVendas, ticker, 'v', notaConsolidadaVendas.get(ticker).getFirst());
		return ordem;
	}

	private static Ordem getOrdemSwingTrade(Map<String, Pair<Integer, Double>> notaConsolidada, String ticker, char tipo, int quantidade) {
		Ordem ordem = null;
		double custoSwingTrade = quantidade*notaConsolidada.get(ticker).getSecond()/notaConsolidada.get(ticker).getFirst();
		try {
			ordem = new Ordem(tipo, quantidade, new Ticker(ticker), custoSwingTrade/quantidade, null);
		} catch(Exception e) {
			System.out.println("SwingTrade.getOrdemSwingTrade(): " + e.getMessage());
		}
		System.out.println("SwingTrade.getOrdemSwingTrade():  " + ticker + " " + ordem);
		return ordem;
	}
	
}
