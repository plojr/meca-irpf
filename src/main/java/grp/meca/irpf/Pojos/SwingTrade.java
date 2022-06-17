package grp.meca.irpf.Pojos;

public class SwingTrade extends Trade {

	private double venda;
	
	private static double prejuizoAcumulado;
	
	public SwingTrade(int mes, int ano, double lucro, double imposto, double venda) {
		super(mes, ano, lucro, imposto);
		this.venda = venda;
	}

	public static double getPrejuizoAcumulado() {
		return prejuizoAcumulado;
	}
	
	public static void setPrejuizoAcumulado(double prejuizoAcumulado) {
		SwingTrade.prejuizoAcumulado = prejuizoAcumulado;
	}
	
	public double getVenda() {
		return venda;
	}
	
	public void setVenda(double venda) {
		this.venda = venda;
	}
}
