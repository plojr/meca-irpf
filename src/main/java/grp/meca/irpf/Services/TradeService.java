package grp.meca.irpf.Services;

import java.util.List;
import java.util.Map;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.EventoExtraordinario;
import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import lombok.Data;

@Data
public abstract class TradeService {
	protected enum TipoTrade {
		DAYTRADE,
		SWINGTRADE
	}
	/*
	 * Esta variável será do tipo 2022 => [1 => (1000.0, 150), 2 => (-400.0, 0)], 
	 * 							  2021 => [1 => (100, 0), 2 => (400,0) ] etc.
	 * O Integer do Map mais "externo" é o ano. O Integer mais "interno" é o mês.
	 * O Double é o lucro/imposto/venda daquele mês/ano.
	 */
	protected Map<Integer, Map<Integer, Double>> anoMesLucro;
	protected Map<Integer, Map<Integer, Double>> anoMesImposto;
	protected Map<Integer, Map<Integer, Double>> anoMesPrejuizoAcumulado;
	
	/*
	 * Irá calcular o imposto a ser pago e os lucros mensais. Irá atualizar o prejuízo acumulado também.
	 */
	public abstract void calcularDadosDoTrade(List<NotaDeCorretagem> corretagens, List<EventoExtraordinario> eventos);
	
	public abstract TipoTrade getTipoTrade();
	
	// Retornar a taxa a ser paga de acodo com o Trade.
	public abstract double getTaxaIR();

	/*
	 * Uma nota consolidada, variável encontrada dentro da função, é uma onde todas as negociações 
	 * do mesmo tipo de um ticker são agrupadas em um único item do Map.
	 * Por exemplo: se houver 3 ordens de compra de xpto, então será consolidada uma única 
	 * ordem de compra de xpto com o preço médio ponderado e a quantidade igual à soma da quantidade 
	 * das três ordens.
	 */
	protected static void atualizarNotaConsolidada(Map<String, Pair<Integer, Double>> notaConsolidada, Ordem ordem) {
		String ticker = ordem.getTicker().getCodigo();
		if(notaConsolidada.containsKey(ticker)) {
			int quantidadeAtual = notaConsolidada.get(ticker).getFirst();
			double custoAtual = notaConsolidada.get(ticker).getSecond();
			notaConsolidada.put(ticker, Pair.of(ordem.getQuantidade() + quantidadeAtual, ordem.getPreco()*ordem.getQuantidade() + custoAtual));
		}
		else
			notaConsolidada.put(ticker, Pair.of(ordem.getQuantidade(), ordem.getPreco()*ordem.getQuantidade()));
	}
}
