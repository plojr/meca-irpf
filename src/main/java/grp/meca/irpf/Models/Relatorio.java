package grp.meca.irpf.Models;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Relatorio {

	private int id;
	
	private List<ItemCarteira> carteira;
	// Estes Map<String, Double> são mapeamentos de mês -> lucro/prejuízo.
	private Map<String, Double> swingTradeIsento;
	private Map<String, Double> swingTradeTributavel;
	private Map<String, Double> mensalDayTrade;
	private double prejuizoAcumuladoSwingTrade;
	private double prejuizoAcumuladoDayTrade;
	
	// TODO
	public Relatorio(List<NotaDeCorretagem> corretagens) {
		
	}
	
	// TODO
	public double getSomaLucroSwingTradeIsento() {
		double lucro = 0;
		
		return lucro;
	}
	
}
