package grp.meca.irpf.Pojos;

public class DadoSwingTrade extends DadoTrade {

	private double venda;
	
	private static double prejuizoAcumulado;
	
	public DadoSwingTrade(int mes, int ano, double lucro, double imposto, double venda) {
		super(mes, ano, lucro, imposto);
		this.venda = venda;
	}

	public static double getPrejuizoAcumulado() {
		return prejuizoAcumulado;
	}
	
	public static void setPrejuizoAcumulado(double prejuizoAcumulado) {
		DadoSwingTrade.prejuizoAcumulado = prejuizoAcumulado;
	}
	
	public double getVenda() {
		return venda;
	}
	
	public void setVenda(double venda) {
		this.venda = venda;
	}
}
