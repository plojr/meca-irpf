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
import grp.meca.irpf.Pojos.DadoSwingTrade;
import grp.meca.irpf.Pojos.ItemCarteira;
import grp.meca.irpf.Utils.MapUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class SwingTradeService extends TradeService {
	private final double LIMITE_PARA_IR = 20000;
	/*
	 * Mapeamento de ticker.codigo para um par de quantidade e custo total.
	 */
	private Map<String, Pair<Integer, Double>> carteira;
	private Map<Integer, Map<Integer, Double>> anoMesVenda;

	public SwingTradeService() {
		this.anoMesLucro = new HashMap<>();
		this.anoMesImposto = new HashMap<>();
		this.anoMesPrejuizoAcumulado = new HashMap<>();
		this.anoMesVenda = new HashMap<>();
		this.carteira = new HashMap<>();
	}
	
	@Override
	public double getTaxaIR() {
		return 0.15;
	}
	
	@Override
	public TipoTrade getTipoTrade() {
		return TipoTrade.SWINGTRADE;
	}
	
	@Override
	public void calcularDadosDoTrade(List<NotaDeCorretagem> corretagens) {
		for(NotaDeCorretagem corretagem: corretagens) {
			int mes = corretagem.getDate().getMonthValue();
			int ano = corretagem.getDate().getYear();
			List<Ordem> ordens = getOrdensSwingTrade(corretagem);
			for(Ordem ordem: ordens) {
				String codigo = ordem.getTicker().getCodigo();
				if(ordem.getTipo() == 'c') {
					if(this.carteira.containsKey(codigo)) {
						double novoCusto = this.carteira.get(codigo).getSecond() + ordem.getPreco()*ordem.getQuantidade();
						int novaQuantidade = this.carteira.get(codigo).getFirst() + ordem.getQuantidade();
						this.carteira.put(codigo, Pair.of(novaQuantidade, novoCusto));
					}
					else {
						double novoCusto = ordem.getPreco()*ordem.getQuantidade();
						int novaQuantidade = ordem.getQuantidade();
						this.carteira.put(codigo, Pair.of(novaQuantidade, novoCusto));
					}
				}
				else {
					int novaQuantidade = this.carteira.get(codigo).getFirst() - ordem.getQuantidade();
					double precoMedio = this.carteira.get(codigo).getSecond()/carteira.get(codigo).getFirst();
					double novoCusto = precoMedio*novaQuantidade;
					MapUtil.add(anoMesVenda, ano, mes, ordem.getQuantidade()*ordem.getPreco());
					this.carteira.put(codigo, Pair.of(novaQuantidade, novoCusto));
					double lucro = ordem.getQuantidade()*(ordem.getPreco() - precoMedio);
					MapUtil.add(anoMesLucro, ano, mes, lucro);
				}
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
				else if(anoMesVenda.get(ano).get(mes) > LIMITE_PARA_IR) {
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
	
	private List<Ordem> getOrdensSwingTrade(NotaDeCorretagem corretagem) {
		//double taxas = corretagem.getTaxas(), valorBrutoDaCorretagem = corretagem.getValorBruto();
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
				atualizarNotaConsolidada(notaConsolidadaCompras, ordem);
			else
				atualizarNotaConsolidada(notaConsolidadaVendas, ordem);
		}
		List<Ordem> ordens = new ArrayList<>();
		// Para cada ticker, pegar a ordem (seja de compra ou de venda) consolidada relativa ao swing trade.
		for(String ticker: tickers) {
			Ordem ordem = getOrdemSwingTrade(notaConsolidadaCompras, notaConsolidadaVendas, ticker);
			/*
			 * Será null somente no caso de haver somente o day trade para o ticker.
			 * Com isso, se ordem for null, é para ignorar.
			 */
			if(ordem != null) {
				ordem.setTaxas(corretagem.getTaxaDaOrdem(ordem.getPreco()*ordem.getQuantidade()));
				ordens.add(ordem);
			}
		}
		return ordens;
	}

	private Ordem getOrdemSwingTrade(Map<String, Pair<Integer, Double>> notaConsolidadaCompras,
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
	private Ordem getOrdemComNovoCustoTotal(Map<String, Pair<Integer, Double>> notaConsolidada, String ticker, 
											char tipo, int quantidade) {
		Ordem ordem = null;
		double custoTrade = quantidade*notaConsolidada.get(ticker).getSecond()/notaConsolidada.get(ticker).getFirst();
		try {
			ordem = new Ordem(tipo, quantidade, new Ticker(ticker), custoTrade/quantidade, null);
		} catch(Exception e) {
			System.out.println("A função SwingTradeService.getOrdemSwingTrade() lançou exceção ao instanciar uma Ordem."
					+ "É possível que haja algum erro lançamento de nota de corretagem e/ou ordem. A mensagem de "
					+ "exceção foi: " + e.getMessage());
		}
		return ordem;
	}
	
	public List<DadoSwingTrade> getSwingTradeList() {
		List<DadoSwingTrade> dadoSwingTradeList = new ArrayList<>();
		for(Entry<Integer, Map<Integer, Double>> anoMap: anoMesLucro.entrySet()) {
			int ano = anoMap.getKey();
			for(Entry<Integer, Double> mesMap: anoMap.getValue().entrySet()) {
				int mes = mesMap.getKey();
				double lucro = anoMesLucro.get(ano).get(mes);
				double prejuizoAcumulado = anoMesPrejuizoAcumulado.get(ano).get(mes);
				double venda = anoMesVenda.get(ano).get(mes);
				double imposto = anoMesImposto.get(ano).get(mes);
				dadoSwingTradeList.add(new DadoSwingTrade(mes, ano, lucro, imposto, prejuizoAcumulado, venda));
			}
		}
		return dadoSwingTradeList;
	}
	
	public List<ItemCarteira> getCarteira(List<Ticker> tickers) {
		List<ItemCarteira> itensCarteira = new ArrayList<>();
		for(Ticker ticker: tickers)
			if(this.carteira.containsKey(ticker.getCodigo()) && this.carteira.get(ticker.getCodigo()).getFirst() != 0)
				itensCarteira.add(new ItemCarteira(ticker.getCodigo(), ticker.getCnpj(), 
						this.carteira.get(ticker.getCodigo()).getFirst(), 
						this.carteira.get(ticker.getCodigo()).getSecond()));
		return itensCarteira;
	}
}
