/*
 * Cada nota de corretagem tem uma lista de ordens, uma data e um valor líquido.
 * O valor bruto pode ser calculado a partir das ordens. O mesmo pode ser dito
 * para as taxas a partir da diferença do valor bruto e o líquido.
 */

package grp.meca.irpf.Models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "notas_de_corretagem")
public class NotaDeCorretagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private LocalDate date;
	
	@Column(nullable = false)
	private double valorLiquido;
	
	public NotaDeCorretagem() {
		
	}
	
	public NotaDeCorretagem(LocalDate date, double valorLiquido) {
		this.date = date;
		this.valorLiquido = valorLiquido;
	}
	
	// TODO
	@JsonIgnore
	public double getValorBruto() {
		return 0;
	}

	@JsonIgnore
	public double getTaxas() {
		return getValorBruto() - getValorLiquido();
	}
	
}
