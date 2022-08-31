package grp.meca.irpf.Controllers.Basico;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import grp.meca.irpf.Models.Basico.Ordem;
import grp.meca.irpf.Models.Basico.Ticker;
import grp.meca.irpf.Repositories.OrdemRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
@RequestMapping("/basico")
public class OrdemController {
	
	@Autowired
	private OrdemRepository ordemRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@PostMapping("/editar_ordem")
	public String editarOrdem(@RequestParam Map<String, String> parametros) {
		List<Integer> idList = new ArrayList<>();
		for(Entry<String, String> entry: parametros.entrySet())
			if(entry.getKey().substring(0, 2).equals("id"))
				idList.add(Integer.parseInt(entry.getValue()));
		for(int id: idList) {
			if(parametros.get("editar_" + id) != null && parametros.get("editar_" + id).equals("on")) {
				char tipo = parametros.get("tipo_" + id).equals("Compra") ? 'c' : 'v';
				Ticker ticker = tickerRepository.findByCodigo(parametros.get("codigo_" + id));
				int quantidade = Integer.parseInt(parametros.get("quantidade_" + id));
				double preco = Double.parseDouble(parametros.get("preco_" + id));
				try {
					Ordem ordem = new Ordem(tipo, quantidade, ticker, preco, ordemRepository.findById(id).get().getNotaDeCorretagem());
					ordem.setId(id);
					ordemRepository.save(ordem);
				} catch(Exception e) {
					System.err.println("Erro ao editar a ordem de id " + id);
				}
			}
		}
		return "redirect:/basico/corretagem";
	}
	
	@GetMapping("/deletar_ordem")
	public String deletarOrdem(@ModelAttribute(value="id") int id) {
		ordemRepository.deleteById(id);
		return "redirect:/basico/corretagem";
	}
}
