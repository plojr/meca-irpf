package grp.meca.irpf.Models;

import java.util.List;
import java.util.Map;

import grp.meca.irpf.Pojos.Carteira;
import grp.meca.irpf.Services.DayTradeService;
import lombok.Data;

@Data
public class Relatorio {

	private Carteira carteira;
	private Map<Integer, Map<Integer, Double>> lucroDayTrade;
	private Map<Integer, Map<Integer, Double>> impostoDayTrade;
	
	public Relatorio(List<NotaDeCorretagem> corretagens) throws Exception {
		this.carteira = new Carteira();
		this.carteira.setCarteira(corretagens);
		DayTradeService dayTradeSrv = new DayTradeService();
		dayTradeSrv.calculaDadosDoTrade(corretagens);
		lucroDayTrade = dayTradeSrv.getLucro();
		impostoDayTrade = dayTradeSrv.getImposto();
	}
	
}
