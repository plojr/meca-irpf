package grp.meca.irpf.Pojos;

import lombok.Data;

@Data
public abstract class DadoTrade {
	
	private int mes;
	private int ano;
	private double lucro;
	private double imposto;
	
	public DadoTrade(int mes, int ano, double lucro, double imposto) {
		super();
		this.mes = mes;
		this.ano = ano;
		this.lucro = lucro;
		this.imposto = imposto;
	}
}
