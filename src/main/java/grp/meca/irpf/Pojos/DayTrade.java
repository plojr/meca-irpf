package grp.meca.irpf.Pojos;

import lombok.Data;

@Data
public class DayTrade {
	
	private int mes;
	private int ano;
	private double lucro;
	private double imposto;
	private static double prejuizoAcumulado;
	
	public DayTrade(int mes, int ano, double lucro, double imposto) {
		super();
		this.mes = mes;
		this.ano = ano;
		this.lucro = lucro;
		this.imposto = imposto;
	}

	public static double getPrejuizoAcumulado() {
		return prejuizoAcumulado;
	}
	
	public static void setPrejuizoAcumulado(double prejuizoAcumulado) {
		DayTrade.prejuizoAcumulado = prejuizoAcumulado;
	}
	
}
