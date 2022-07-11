package grp.meca.irpf.Controllers.Eventos;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import grp.meca.irpf.Models.Basico.Ticker;
import grp.meca.irpf.Models.Eventos.Bonificacao;
import grp.meca.irpf.Repositories.BonificacaoRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
@RequestMapping("/eventos")
public class BonificacaoController {
	
	@Autowired
	private BonificacaoRepository bonificacaoRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("/bonificacao")
	public String bonificacaoGet(Model model) {
		model.addAttribute("bonificacoes", bonificacaoRepository.findAllByOrderByDataEvento());
		return "eventos/bonificacao";
	}
	
	@PostMapping("/bonificacao")
	public String bonificacaoPost(@RequestParam Map<String, String> parametros) {
		Ticker ticker = tickerRepository.findByCodigo(parametros.get("codigo"));
		if(ticker == null)
			ticker = tickerRepository.save(new Ticker(parametros.get("codigo")));
		LocalDate data = LocalDate.parse(parametros.get("data"));
		double proporcao = Double.parseDouble(parametros.get("proporcao"));
		double preco = Double.parseDouble(parametros.get("preco"));
		bonificacaoRepository.save(new Bonificacao(ticker, data, proporcao, preco));
		return "redirect:/eventos/bonificacao";
	}
	
	@PostMapping("/editar_bonificacao")
	public String editarBonificacaoPost(@RequestParam Map<String, String> parametros) {
		int quantidade = Integer.parseInt(parametros.get("quantidade"));
		for(int i = 0; i < quantidade; i++) {
			if(parametros.containsKey("editar_" + i)) {
				int id = Integer.parseInt(parametros.get("id_" + i));
				Ticker empresa = tickerRepository.findByCodigo(parametros.get("codigo_" + i));
				LocalDate data = LocalDate.parse(parametros.get("data_" + i));
				double proporcaoDeAcoes = Double.parseDouble(parametros.get("proporcao_" + i));
				double precoPorAcao = Double.parseDouble(parametros.get("preco_" + i));
				Bonificacao bonificacao = new Bonificacao(empresa, data, proporcaoDeAcoes, precoPorAcao);
				bonificacao.setId(id);
				bonificacaoRepository.save(bonificacao);
			}
		}
		return "redirect:/eventos/bonificacao";
	}
}
