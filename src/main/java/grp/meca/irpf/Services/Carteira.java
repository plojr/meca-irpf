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
	
	public void gerarCarteira(List<NotaDeCorretagem> corretagens) {
		carteira = new HashMap<>();
		for(NotaDeCorretagem corretagem: corretagens) {
			List<Ordem> ordens = SwingTrade.getOrdensSwingTrade(corretagem);
			for(Ordem ordem: ordens) {
				String codigo = ordem.getTicker().getCodigo();
				int quantidade = 0;
				double custoTotal = 0;
				try {
					quantidade = getNovaQuantidade(ordem);
				} catch(Exception e) {
					System.out.println(e.getMessage());
					return;
				}
				// O novo custo total será nova quantidade * (Custo total anterior / quantidade anterior)
				// (Custo total anterior / quantidade anterior) é o preço médio antes da atualização de quantidade e custo total.
				custoTotal = quantidade*(carteira.get(codigo).getSecond()/carteira.get(codigo).getFirst());
				carteira.put(codigo, Pair.of(quantidade, custoTotal));
			}
		}
	}
	
	// Dada uma ordem e a quantidade de um ticker, calcular a nova quantidade dele.
	private int getNovaQuantidade(Ordem ordem) throws Exception {
		String codigo = ordem.getTicker().getCodigo();
		int quantidade = ordem.getQuantidade();
		if(ordem.getTipo() == 'v') {
			if(!carteira.containsKey(codigo) || carteira.get(codigo).getFirst() == 0)
				throw new Exception("Está tentando vender a ação " + codigo + " sem tê-la!");
			if(quantidade > carteira.get(codigo).getFirst())
				throw new Exception("Está tentando vender mais ações de " + codigo + " do que tem.");
			quantidade = carteira.get(codigo).getFirst() - quantidade;
		}
		else
			quantidade += carteira.get(codigo).getFirst();
		return quantidade;
	}
	
	public List<ItemCarteira> getCarteira() {
		List<ItemCarteira> itensCarteira = new ArrayList<>();
		for(Entry<String, Pair<Integer, Double>> entry: carteira.entrySet())
			itensCarteira.add(new ItemCarteira(entry.getKey(), entry.getValue().getFirst(), entry.getValue().getSecond()));
		return itensCarteira;
	}
	
}
