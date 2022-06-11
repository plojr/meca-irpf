package grp.meca.irpf.Services;

public interface Trade {
	
	// Retornar o imposto a ser pago.
	public double getImposto();
	
	// Retornar a taxa a ser paga de acodo com o Trade.
	public double getTaxaIR();
}
