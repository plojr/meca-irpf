package grp.meca.irpf.Pojos;

import java.util.List;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Services.DayTradeService;
import lombok.Data;

@Data
public class Relatorio {
	
	private DayTradeService dtService;
	
	public Relatorio(List<NotaDeCorretagem> corretagens) {
		dtService = new DayTradeService();
		dtService.calcularDadosDoTrade(corretagens);
	}
}
