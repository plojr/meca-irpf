package grp.meca.irpf.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;

public class Carteira {
	
	/*
	 *  A variável quantidadeCusto guardará a quantidade e custo total, dada um ticker.
	 *  Exemplo: se tiver 1000 ações de xpto3 a um custo de 10000.00, 500 ações de abcd3 a 20000.00, etc
	 *  então: quantidadeCusto["xpto3"] = Pair(1000, 10000), quantidadeCusto["abcd3"] = Pair(500, 20000) etc.
	 */
	private Map<String, Pair<Integer, Double>> carteira;
	
	public void getCarteira(List<NotaDeCorretagem> corretagens) throws Exception {
		carteira = new HashMap<>();
		for(NotaDeCorretagem corretagem: corretagens) {
			/*
			 * Para calcular a carteira, é preciso excluir os day trades, pois eles são calculados à parte.
			 * A função getOrdensSwingTrade elimina todos os day trades, retornando apenas as ordens relativas ao swing trade.
			 */
			List<Ordem> ordens = SwingTrade.getOrdensSwingTrade(corretagem);
			for(Ordem ordem: ordens) {
				if(ordem == null)
					throw new Exception("Carteira.getCarteira(): variável ordem é null!");
				String codigo = ordem.getTicker().getCodigo();
				int quantidade = 0;
				double custoTotal = 0;
				try {
					Pair<Integer, Double> qc = getAtualizacaoDeQuantidadeCusto(ordem);
					quantidade = qc.getFirst();
					custoTotal = qc.getSecond();
				} catch(Exception e) {
					throw new Exception("Carteira.getCarteira(): " + e.getMessage());
				}
				// O novo custo total será nova quantidade * (Custo total anterior / quantidade anterior)
				// (Custo total anterior / quantidade anterior) é o preço médio antes da atualização de quantidade e custo total.
				carteira.put(codigo, Pair.of(quantidade, custoTotal));
			}
		}
	}

	// Dadas a ordem e a quantidade de um ticker, calcular a nova quantidade.
	private Pair<Integer, Double> getAtualizacaoDeQuantidadeCusto(Ordem ordem) throws Exception {
		if(ordem == null)
			throw new Exception("Carteira.getAtualizacaoDeQuantidadeCusto(): ordem é null!");
		String codigo = ordem.getTicker().getCodigo();
		int quantidade = 0;
		double custoTotal = 0;
		if(ordem.getTipo() == 'v') {
			if(!carteira.containsKey(codigo) || carteira.get(codigo).getFirst() == 0)
				throw new Exception("Está tentando vender a ação " + codigo + " sem tê-la!");
			if(quantidade > carteira.get(codigo).getFirst())
				throw new Exception("Está tentando vender mais ações de " + codigo + " do que tem em carteira!");
			quantidade = carteira.get(codigo).getFirst() - ordem.getQuantidade();
			custoTotal = quantidade*(carteira.get(codigo).getSecond()/carteira.get(codigo).getFirst());
		}
		// Se for compra, a nova quantidade será somada à quantidade atual daquele ticker.
		// O novo custo total será o custo já atribuído àquele ticker somado ao preço*quantidade da ordem.
		else {
			if(carteira.containsKey(codigo)) {
				quantidade = carteira.get(codigo).getFirst() + ordem.getQuantidade();
				custoTotal = carteira.get(codigo).getSecond() + ordem.getQuantidade()*ordem.getPreco();
			}
			else {
				quantidade = ordem.getQuantidade();
				custoTotal = ordem.getQuantidade()*ordem.getPreco();
			}
		}
		/* A Receita Federal te permite adicionar, ao custo total, as taxas envolvidas naquela compra.
		 * Isso significa que o custo de obter aquela ação aumentará e, com isso, você terá uma 
		 * vantagem fiscal ao vendê-la, podendo também usar a taxa da venda para diminuir o ganho de capital e 
		 * pagar menos imposto de renda.
		 * Para mais informações a respeito: (Lei no 8.383, de 30 de dezembro de 1991, art. 27; e Regulamento do 
		 * Imposto sobre a Renda -RIR/2018, art. 841, § 2o, aprovado pelo Decreto no 9.580, de 22 de novembro de 
		 * 2018; e Instrução Normativa RFB no 1.585, de 31 de agosto de 2015, art. 56, § 3o)
		 */
		return Pair.of(quantidade, custoTotal + ordem.getTaxas());
	}
	
	public List<ItemCarteira> getItensCarteira(Map<String, String> tickerCnpj) {
		List<ItemCarteira> itensCarteira = new ArrayList<>();
		for(Entry<String, Pair<Integer, Double>> entry: carteira.entrySet()) {
			/*
			 *  Depois de várias notas de corretagem, possivelmente, haverá alguns tickers com quantidade
			 *  igual a zero. Não há interesse nesses, portanto é para ignorá-los.
			 */
			if(entry.getValue().getFirst() != 0)
				itensCarteira.add(new ItemCarteira(entry.getKey(), tickerCnpj.get(entry.getKey()), 
						entry.getValue().getFirst(), entry.getValue().getSecond()));
		}
		return itensCarteira;
	}
	
}
