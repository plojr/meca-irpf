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
		List<Ordem> ordens = new ArrayList<>();
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
		for(Ordem ordem: corretagem.getOrdens()) {
			String ticker = ordem.getTicker().getCodigo();
			tickers.add(ticker);
			if(ordem.getTipo() == 'c')
				atualizarNotaConsolidada(notaConsolidadaCompras, ticker, ordem);
			else
				atualizarNotaConsolidada(notaConsolidadaVendas, ticker, ordem);
		}
		// Para cada ticker, pegar a ordem (seja de compra ou de venda) consolidada relativa ao swing trade.
		for(String ticker: tickers) {
			Ordem ordem = getOrdemTrade(notaConsolidadaCompras, notaConsolidadaVendas, ticker);
			/*
			 * Será null somente no caso de haver um day trade, onde a quantidade de ações compradas
			 * é igual à quantidade de ações vendidas. 
			 */
			if(ordem != null) {
				ordem.setTaxas(taxas*(ordem.getPreco()*ordem.getQuantidade()/Math.abs(valorBrutoDaCorretagem)));
				ordens.add(ordem);
			}
		}
		return ordens;
	}
	
}
