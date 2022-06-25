package grp.meca.irpf.Models;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class Bonificacao extends EventoExtraordinario {

	@Column(nullable = false)
	private double proporcao;
	
	@Column(nullable = false)
	private double preco;
}
