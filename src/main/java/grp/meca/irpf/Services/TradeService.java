package grp.meca.irpf.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import lombok.Data;

@Data
public abstract class TradeService {
	
	/*
	 * Esta variável será do tipo 2022 => [1 => (1000.0, 150), 2 => (-400.0, 0)], 
	 * 							  2021 => [1 => (100, 0), 2 => (400,0) ] etc.
	 * O Integer do Map mais "externo" é o ano. O Integer mais "interno" é o mês.
	 * O primeiro Double do Pair é o lucro daquele mês/ano.
	 * O segundo Double é o imposto a ser pago no mesmo período.
	 */
	protected Map<Integer, Map<Integer, Pair<Double, Double>>> lucroImposto;
	
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

	protected void atualizarLucro(Map<Integer, Map<Integer, Pair<Double, Double>>> lucro, int mes, int ano,
			double valorDoLucro) {
		if(lucro.containsKey(ano)) {
			if(lucro.get(ano).containsKey(mes))
				valorDoLucro += lucro.get(ano).get(mes).getFirst();
			lucro.get(ano).put(mes, Pair.of(valorDoLucro, 0.));
		}
		else {
			Map<Integer, Pair<Double, Double>> lucroMensal = new HashMap<>();
			lucroMensal.put(mes, Pair.of(valorDoLucro, 0.));
			lucro.put(ano, lucroMensal);
		}
	}
	/*
	 * Calcular o imposto de cada mês/ano, caso exista.
	 * Atualizar também o prejuízo acumulado e retorná-lo.
	 */
	protected double atualizarImposto(Map<Integer, Map<Integer, Pair<Double, Double>>> lucroImposto) {
		double prejuizoAcumulado = 0;
		for(Entry<Integer, Map<Integer, Pair<Double, Double>>> anoLucroImposto: this.lucroImposto.entrySet()) {
			for(Entry<Integer, Pair<Double, Double>> mesLucroImposto: anoLucroImposto.getValue().entrySet()) {
				int ano = anoLucroImposto.getKey();
				int mes = mesLucroImposto.getKey();
				if(mes == 1) prejuizoAcumulado = 0;
				double lucro = mesLucroImposto.getValue().getFirst();
				double imposto = 0;
				if(lucro < 0)
					prejuizoAcumulado += Math.abs(lucro);
				else {
					if(prejuizoAcumulado > 0) {
						prejuizoAcumulado -= lucro;
						if(prejuizoAcumulado >= 0)
							imposto = 0;
						else {
							imposto = getTaxaIR()*Math.abs(prejuizoAcumulado);
							prejuizoAcumulado = 0;
						}
					}
					else {
						imposto = getTaxaIR()*lucro;
					}
				}
				this.lucroImposto.get(ano).put(mes, Pair.of(lucro, imposto));
			}
		}
		return prejuizoAcumulado;
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
