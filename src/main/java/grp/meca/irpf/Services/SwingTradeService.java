package grp.meca.irpf.Services;

import java.util.List;
import java.util.Map;

import grp.meca.irpf.Models.NotaDeCorretagem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class SwingTradeService extends TradeService {

	private Map<Integer, Map<Integer, Double>> anoMesVenda;
	
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
		
	}

}
