package grp.meca.irpf.Models;

import java.util.List;

import grp.meca.irpf.Pojos.Carteira;
import grp.meca.irpf.Pojos.DayTrade;
import grp.meca.irpf.Services.DayTradeService;
import lombok.Data;

@Data
public class Relatorio {

	private Carteira carteira;
	private List<DayTrade> dayTradeList;
	
	public Relatorio(List<NotaDeCorretagem> corretagens) throws Exception {
		this.carteira = new Carteira();
		this.carteira.setCarteira(corretagens);
		DayTradeService dayTradeSrv = new DayTradeService();
		dayTradeSrv.calculaDadosDoTrade(corretagens);
		dayTradeList = dayTradeSrv.getDayTradeList();
	}
	
}
