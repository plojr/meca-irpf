package grp.meca.irpf.Pojos;

import java.util.List;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Services.DayTradeService;
import lombok.Data;

@Data
public class Relatorio {

	private Carteira carteira;
	private List<DadoDayTrade> dadoDayTradeList;
	
	public Relatorio(List<NotaDeCorretagem> corretagens) throws Exception {
		this.carteira = new Carteira();
		this.carteira.setCarteira(corretagens);
		DayTradeService dayTradeSrv = new DayTradeService();
		dayTradeSrv.calculaDadosDoTrade(corretagens);
		dadoDayTradeList = dayTradeSrv.getDayTradeList();
	}
	
}
