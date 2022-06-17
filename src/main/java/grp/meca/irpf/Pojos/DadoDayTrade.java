package grp.meca.irpf.Pojos;

public class DadoDayTrade extends DadoTrade {
	
	private static double prejuizoAcumulado;
	
	public DadoDayTrade(int mes, int ano, double lucro, double imposto) {
		super(mes, ano, lucro, imposto);
	}

	public static double getPrejuizoAcumulado() {
		return prejuizoAcumulado;
	}
	
	public static void setPrejuizoAcumulado(double prejuizoAcumulado) {
		DadoDayTrade.prejuizoAcumulado = prejuizoAcumulado;
	}
	
}
