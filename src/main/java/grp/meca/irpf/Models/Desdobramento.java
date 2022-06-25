/*
 * Desobramento é um evento onde uma empresa decide multiplicar suas ações em N vezes para
 * aumentar a quantidade de ações da empresa. Quando a ação está em um valor
 * nominal muito alto, é muito comum ter desdobramentos para que a ação tenha mais liquidez.
 * Um exemplo de desdobramento foi a Copel em 2021.
 * Para cada ação de Copel, qualquer que fosse a classe, que o investidor possuía, 
 * ele passou a possuir 10.
 * Obviamente o preço da ação é dividido por 10.
 * No caso da variável "proporção", para o exemplo acima, o valor será 10.
 */

package grp.meca.irpf.Models;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class Desdobramento extends EventoExtraordinario {

	@Column
	private double proporcao;
}
