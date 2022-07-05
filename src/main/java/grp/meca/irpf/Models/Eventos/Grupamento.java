/*
 * Grupamento é um evento onde uma empresa decide agrupar um número N de ações para
 * diminuir a quantidade de ações da empresa. Normalmente, quando a ação está valendo 
 * menos do que R$ 1.00, é muito comum ter grupamentos para que a ação saia desse valor.
 * Um exemplo de grupamento foi o caso da Magazine Luiza, em 2015.
 * Para cada 8 ações mglu3 que o investidor possuía, ele passou a possuir somente 1.
 * Obviamente o preço por ação é multiplicado por 8.
 * No caso da variável "proporção", para o exemplo acima, o valor será 8.
 */

package grp.meca.irpf.Models.Eventos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import grp.meca.irpf.Models.Basico.Ticker;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class Grupamento extends EventoExtraordinario {

	public Grupamento() {}
	
	public Grupamento(Ticker ticker1, LocalDate dataEvento, double proporcao) {
		super(ticker1, dataEvento);
		this.proporcao = proporcao;
	}

	@Column(nullable = false)
	private double proporcao;
}
