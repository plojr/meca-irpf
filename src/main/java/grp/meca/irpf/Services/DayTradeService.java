package grp.meca.irpf.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import grp.meca.irpf.Models.Ticker;

public class DayTradeService extends TradeService {

	@Override
	public void calculaDadosDoTrade(List<NotaDeCorretagem> corretagens) {
		this.lucro = new HashMap<>();
		this.imposto = new HashMap<>();
		for(NotaDeCorretagem corretagem: corretagens) {
			//double taxas = corretagem.getTaxas(), valorBrutoDaCorretagem = corretagem.getValorBruto();
			List<Ordem> ordensDayTrade = getOrdensDayTrade(corretagem);
			/*
			 * Este Map<String, Double> é para auxiliar na hora de percorrer cada lista de ordem.
			 * Ele vai guardar algo do tipo: Map["xpto3"] = 10;
			 */
			Map<String, Double> lucroDiario = new HashMap<>();
			for(Ordem ordem: ordensDayTrade) {
				double valor = 0, valorDaOrdem = ordem.getPreco()*ordem.getQuantidade();
				//double taxaDaOrdem = taxas*valorDaOrdem/Math.abs(valorBrutoDaCorretagem);
				double taxaDaOrdem = corretagem.getTaxaDaOrdem(valorDaOrdem);
				if(lucroDiario.containsKey(ordem.getTicker().getCodigo()))
					valor = lucroDiario.get(ordem.getTicker().getCodigo());
				if(ordem.getTipo() == 'c')
					valor += -1*valorDaOrdem - taxaDaOrdem;
				else
					valor += valorDaOrdem - taxaDaOrdem;
				lucroDiario.put(ordem.getTicker().getCodigo(), valor);
			}
			int mes = corretagem.getDate().getMonthValue(), ano = corretagem.getDate().getYear();
			for(Entry<String, Double> entry: lucroDiario.entrySet())
				atualizarLucro(this.lucro, mes, ano, entry.getValue());
		}
	}

	public double getTaxaIR() {
		return 0.20;
	}

	private static List<Ordem> getOrdensDayTrade(NotaDeCorretagem corretagem) {
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
		atualizarNotasDeCorretagem(notaConsolidadaCompras, notaConsolidadaVendas, tickers, corretagem);
		for(String ticker: tickers) {
			List<Ordem> ordensDayTrade = getOrdensDayTradeByTicker(ticker, notaConsolidadaCompras, notaConsolidadaVendas);
			if(ordensDayTrade.size() != 0)
				ordens.addAll(ordensDayTrade);
		}
		return ordens;
	}

	private static List<Ordem> getOrdensDayTradeByTicker(String ticker,
			Map<String, Pair<Integer, Double>> notaConsolidadaCompras,
			Map<String, Pair<Integer, Double>> notaConsolidadaVendas) {
		List<Ordem> ordens = new ArrayList<>();
		if(notaConsolidadaCompras.containsKey(ticker) && notaConsolidadaVendas.containsKey(ticker)) {
			int quantidade = Math.min(notaConsolidadaCompras.get(ticker).getFirst(), notaConsolidadaVendas.get(ticker).getFirst());
			double precoMedioCompra = notaConsolidadaCompras.get(ticker).getSecond()/notaConsolidadaCompras.get(ticker).getFirst();
			double precoMedioVenda = notaConsolidadaVendas.get(ticker).getSecond()/notaConsolidadaVendas.get(ticker).getFirst();
			try {
				Ordem ordemVenda = new Ordem('v', quantidade, new Ticker(ticker), precoMedioVenda, null);
				Ordem ordemCompra = new Ordem('c', quantidade, new Ticker(ticker), precoMedioCompra, null);
				ordens.add(ordemCompra);
				ordens.add(ordemVenda);
			} catch(Exception e) {
				System.err.println("Erro em DayTradeService.getOrdensDayTradeByTicker, com a seguinte mensagem: " + e.getMessage());
			}
		}
		return ordens;
	}
	
}
