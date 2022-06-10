/*
 * Esta classe serve de ajuda para lançar os dados no programa da Receita Federal
 * no campo CNPJ em "Bens e Direitos".
 */

package grp.meca.irpf.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Cnpj {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String ticker;
	
	@Column(nullable = false)
	private String cnpj;
}
