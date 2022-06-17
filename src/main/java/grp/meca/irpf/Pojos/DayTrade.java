package grp.meca.irpf.Pojos;

public class DayTrade extends Trade {
	
	private static double prejuizoAcumulado;
	
	public DayTrade(int mes, int ano, double lucro, double imposto) {
		super(mes, ano, lucro, imposto);
	}

	public static double getPrejuizoAcumulado() {
		return prejuizoAcumulado;
	}
	
	public static void setPrejuizoAcumulado(double prejuizoAcumulado) {
		DayTrade.prejuizoAcumulado = prejuizoAcumulado;
	}
	
}
