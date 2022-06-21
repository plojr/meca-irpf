package grp.meca.irpf.Pojos;

import java.util.List;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Services.DayTradeService;
import grp.meca.irpf.Services.SwingTradeService;
import lombok.Data;

@Data
public class Relatorio {
	
	private DayTradeService dtService;
	private SwingTradeService stService;
	
	public Relatorio(List<NotaDeCorretagem> corretagens) {
		dtService = new DayTradeService();
		dtService.calcularDadosDoTrade(corretagens);
		stService = new SwingTradeService();
		stService.calcularDadosDoTrade(corretagens);
	}
}
