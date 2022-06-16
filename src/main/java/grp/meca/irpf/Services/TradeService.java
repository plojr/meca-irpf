package grp.meca.irpf.Services;

import java.util.Map;
import java.util.Set;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import lombok.Data;

@Data
public abstract class TradeService {
	
	/*
	 * Esta variável será do tipo "janeiro" => 1000.0, "fevereiro" => -400.0 etc.
	 */
	protected Map<String, Double> lucroMensal;
	
	/*
	 * A Receita te permite abater, dos lucros futuros, o prejuízo acumulado que
	 * você, eventualmente, tenha.
	 */
	protected double prejuizoAcumulado;
	
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
