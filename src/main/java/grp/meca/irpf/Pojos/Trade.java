package grp.meca.irpf.Pojos;

import lombok.Data;

@Data
public abstract class Trade {
	
	private int mes;
	private int ano;
	private double lucro;
	private double imposto;
	
	public Trade(int mes, int ano, double lucro, double imposto) {
		super();
		this.mes = mes;
		this.ano = ano;
		this.lucro = lucro;
		this.imposto = imposto;
	}
}
