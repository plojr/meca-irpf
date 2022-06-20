package grp.meca.irpf.Pojos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class DadoSwingTrade extends DadoTrade {

	private double venda;
	
	public DadoSwingTrade(int mes, int ano, double lucro, double imposto, double prejuizoAcumulado, double venda) {
		super(mes, ano, lucro, imposto, prejuizoAcumulado);
		this.venda = venda;
	}

}
