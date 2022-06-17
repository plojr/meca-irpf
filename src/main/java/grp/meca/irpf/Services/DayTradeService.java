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

public class DayTradeService extends TradeService {

	@Override
	public void calculaDadosDoTrade(List<NotaDeCorretagem> corretagens) {
		this.lucro = new HashMap<>();
		this.imposto = new HashMap<>();
		for(NotaDeCorretagem corretagem: corretagens) {
			List<Ordem> ordensDayTrade = getOrdensDayTrade(corretagem);
			/*
			 * Este Map<String, Double> é para auxiliar na hora de percorrer cada lista de ordem.
			 * Ele vai guardar algo do tipo: Map["xpto3"] = 10;
			 */
			Map<String, Double> lucroDiario = new HashMap<>();
			for(Ordem ordem: ordensDayTrade) {
				double valorOrdem = 0;
				if(lucroDiario.containsKey(ordem.getTicker().getCodigo()))
					valorOrdem = lucroDiario.get(ordem.getTicker().getCodigo());
				if(ordem.getTipo() == 'c')
					valorOrdem += -1*(ordem.getQuantidade()*ordem.getPreco());
				else
					valorOrdem += (ordem.getQuantidade()*ordem.getPreco());
				lucroDiario.put(ordem.getTicker().getCodigo(), valorOrdem);
			}
			int mes = corretagem.getDate().getMonthValue(), ano = corretagem.getDate().getYear();
			for(Entry<String, Double> entry: lucroDiario.entrySet())
				atualizarLucro(this.lucro, mes, ano, entry.getValue());
		}
	}

	public double getTaxaIR() {
		return 0.20;
	}

	public static List<Ordem> getOrdensDayTrade(NotaDeCorretagem corretagem) {
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
		
		return ordens;
	}
	
}
