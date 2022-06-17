package grp.meca.irpf.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import lombok.Data;

@Data
public abstract class TradeService {
	
	/*
	 * Esta variável será do tipo 2022 => [1 => 1000.0, 2 => -400.0], 
	 * 							  2021 => [1 => 100, 2 => 400 ] etc.
	 */
	protected Map<Integer, Map<Integer, Double>> lucro;
	protected Map<Integer, Map<Integer, Double>> imposto;
	
	/*
	 * A Receita te permite abater, dos lucros futuros, o prejuízo acumulado que
	 * você, eventualmente, tenha.
	 */
	protected double prejuizoAcumulado;
	
	/*
	 * Irá calcular o imposto a ser pago e os lucros mensais. Irá atualizar o prejuízo acumulado também.
	 */
	public abstract void calculaDadosDoTrade(List<NotaDeCorretagem> corretagens);
	
	// Retornar a taxa a ser paga de acodo com o Trade.
	public abstract double getTaxaIR();

	protected void atualizarLucro(Map<Integer, Map<Integer, Double>> lucro, int mes, int ano, double valor) {
		if(lucro.containsKey(ano)) {
			if(lucro.get(ano).containsKey(mes))
				valor += lucro.get(ano).get(mes);
			lucro.get(ano).put(mes, valor);
		}
		else {
			Map<Integer, Double> lucroMensal = new HashMap<>();
			lucroMensal.put(mes, valor);
			lucro.put(ano, lucroMensal);
		}
	}

	/*
	 * Uma nota consolidada, variável encontrada dentro da função, é uma onde todas as negociações 
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
	
	protected static void atualizarNotasDeCorretagem(Map<String, Pair<Integer, Double>> notaConsolidadaCompras,
			Map<String, Pair<Integer, Double>> notaConsolidadaVendas, Set<String> tickers,
			NotaDeCorretagem corretagem) {
		for(Ordem ordem: corretagem.getOrdens()) {
			String ticker = ordem.getTicker().getCodigo();
			tickers.add(ticker);
			if(ordem.getTipo() == 'c')
				atualizarNotaConsolidada(notaConsolidadaCompras, ticker, ordem);
			else
				atualizarNotaConsolidada(notaConsolidadaVendas, ticker, ordem);
		}
	}
}
