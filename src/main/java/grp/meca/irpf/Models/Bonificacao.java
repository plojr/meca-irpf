/*
 * Ás vezes, a empresa quer aumentar seu capital social. Então ela propõe
 * uma bonificação. Com isso, o investidor "ganha" uma porcentagem de ações.
 * É possível adicionar um custo a este ganho para que seu preço médio aumente,
 * fazendo que o lucro contábil seja menor e, com isso, pague menos imposto de renda.
 * Na prática, o investidor não tem que desembolsar nenhum dinheiro. Porém, em teoria,
 * as ações que ele ganha têm um custo contábil. Este custo deve ser declarado.
 * Um exemplo de bonificação foi a do Bradesco em 2021, onde  o investidor "ganhou"
 * 10% da quantidade de ações que possuía a um custo contábil de R$ 4,128165265 por ação.
 */

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
