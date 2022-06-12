package grp.meca.irpf.Models;

import java.util.List;
import java.util.Map;

import grp.meca.irpf.Services.Carteira;
import lombok.Data;

@Data
public class Relatorio {

	private Carteira carteira;
	// Estes Map<String, Double> são mapeamentos de mês -> lucro/prejuízo.
	private Map<String, Double> swingTradeIsento;
	private Map<String, Double> swingTradeTributavel;
	private Map<String, Double> mensalDayTrade;
	private double prejuizoAcumuladoSwingTrade;
	private double prejuizoAcumuladoDayTrade;
	
	// TODO
	public Relatorio(List<NotaDeCorretagem> corretagens) throws Exception {
		this.carteira = new Carteira();
		this.carteira.getCarteira(corretagens);
	}
	
	// TODO
	public double getSomaLucroSwingTradeIsento() {
		double lucro = 0;
		
		return lucro;
	}
	
	
	
}
