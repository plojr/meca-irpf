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
import grp.meca.irpf.Pojos.DadoDayTrade;
import grp.meca.irpf.Utils.MapUtil;

public class DayTradeService extends TradeService {
	
	public DayTradeService() {
		this.anoMesLucro = new HashMap<>();
		this.anoMesImposto = new HashMap<>();
		this.anoMesPrejuizoAcumulado = new HashMap<>();
	}

	@Override
	public double getTaxaIR() {
		return 0.20;
	}
	
	@Override
	public TipoTrade getTipoTrade() {
		return TipoTrade.DAYTRADE;
	}
	
	@Override
	public void calcularDadosDoTrade(List<NotaDeCorretagem> corretagens) {
		/*
		 * Para cada corretagem, serão separadas as ordens swing trade das day trade.
		 * Com as ordens day trade, será calculado o lucro/prejuízo para cada ticker,
		 * guardando este valor em um Map<String, Double>. A String é para o ticker, e
		 * o Double é para o lucro/prejuízo.
		 * Este valor (o lucro/prejuízo) será colocado em um Map que representará
		 * o mês e ano, com o lucro/prejuízo e também o imposto a ser pago.
		 */
		for(NotaDeCorretagem corretagem: corretagens) {
			List<Ordem> ordensDayTrade = getOrdensDayTrade(corretagem);
			if(ordensDayTrade.size() == 0) continue;
			/*
			 * Este Map<String, Double> é para auxiliar na hora de percorrer cada lista de ordem,
			 * calculando o lucro ou o prejuízo daquele ticker.
			 * Ele vai guardar algo do tipo: Map["xpto3"] = 1000.0;
			 */
			double lucroDiario = 0;
			/*
			 * Este loop é para calcular o lucro/prejuízo de day trade das ordens da
			 * nota de corretagem.
			 */
			for(Ordem ordem: ordensDayTrade) {
				double valorDaOrdem = ordem.getPreco()*ordem.getQuantidade();
				double taxaDaOrdem = corretagem.getTaxaDaOrdem(valorDaOrdem);
				if(ordem.getTipo() == 'c')
					lucroDiario += -1*valorDaOrdem - taxaDaOrdem;
				else
					lucroDiario += valorDaOrdem - taxaDaOrdem;
			}
			/*
			 * Tendo calculado o lucro, as próximas etapas são: atualizar o Map de ano, mes e lucro;
			 * calcular o imposto de renda a pagar e também atualizar o prejuízo acumulado.
			 * A variável relativa à venda também será atualizada, mas ela não é necessária para
			 * o lançamento dos dados no software da Receita Federal.
			 */
			int mes = corretagem.getDate().getMonthValue(), ano = corretagem.getDate().getYear();
			if(this.anoMesLucro.containsKey(ano)) {
				if(this.anoMesLucro.get(ano).containsKey(mes)) {
					double novoLucro = lucroDiario + this.anoMesLucro.get(ano).get(mes);
					this.anoMesLucro.get(ano).put(mes, novoLucro);
				}
				else {
					this.anoMesLucro.get(ano).put(mes, lucroDiario);
				}
			}
			else {
				Map<Integer, Double> mesLucro = new HashMap<>();
				mesLucro.put(mes, lucroDiario);
				anoMesLucro.put(ano, mesLucro);
			}
		}
		// Calcular o imposto e o prejuízo acumulado.
		double prejuizoAcumulado = 0;
		for(Entry<Integer, Map<Integer, Double>> anoMap: anoMesLucro.entrySet()) {
			int ano = anoMap.getKey();
			for(Entry<Integer, Double> mesMap: anoMap.getValue().entrySet()) {
				int mes = mesMap.getKey();
				double lucro = anoMesLucro.get(ano).get(mes), imposto = 0;
				if(lucro < 0)
					prejuizoAcumulado += Math.abs(lucro);
				else {
					if(lucro > prejuizoAcumulado) {
						imposto = getTaxaIR()*(lucro - prejuizoAcumulado);
						prejuizoAcumulado = 0;
					}
					else
						prejuizoAcumulado -= lucro;
				}
				MapUtil.put(anoMesPrejuizoAcumulado, ano, mes, prejuizoAcumulado);
				MapUtil.put(anoMesImposto, ano, mes, imposto);
			}
		}
	}
	
	private List<Ordem> getOrdensDayTrade(NotaDeCorretagem corretagem) {
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
			tickers.add(ordem.getTicker().getCodigo());
			if(ordem.getTipo() == 'c')
				atualizarNotaConsolidada(notaConsolidadaCompras, ordem);
			else
				atualizarNotaConsolidada(notaConsolidadaVendas, ordem);
		}
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
	
	public List<DadoDayTrade> getDayTradeList() {
		List<DadoDayTrade> dadoDayTradeList = new ArrayList<>();
		for(Entry<Integer, Map<Integer, Double>> anoMap: anoMesLucro.entrySet()) {
			int ano = anoMap.getKey();
			for(Entry<Integer, Double> mesMap: anoMap.getValue().entrySet()) {
				int mes = mesMap.getKey();
				double lucro = anoMesLucro.get(ano).get(mes);
				double imposto = anoMesImposto.get(ano).get(mes);
				double prejuizoAcumulado = anoMesPrejuizoAcumulado.get(ano).get(mes);
				dadoDayTradeList.add(new DadoDayTrade(mes, ano, lucro, imposto, prejuizoAcumulado));
			}
		}
		return dadoDayTradeList;
	}
	
}
