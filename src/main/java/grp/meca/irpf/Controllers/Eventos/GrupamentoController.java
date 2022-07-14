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
import grp.meca.irpf.Models.Eventos.Grupamento;
import grp.meca.irpf.Repositories.GrupamentoRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
@RequestMapping("/eventos")
public class GrupamentoController {
	
	@Autowired
	private GrupamentoRepository grupamentoRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("/grupamento")
	public String adicionarGrupamentoGet(Model model) {
		model.addAttribute("grupamentos", grupamentoRepository.findAllByOrderByDataEvento());
		return "eventos/grupamento";
	}
	
	@PostMapping("/grupamento")
	public String adicionarGrupamentoPost(@RequestParam Map<String, String> parametros) {
		Ticker ticker = tickerRepository.findByCodigo(parametros.get("codigo"));
		if(ticker == null)
			ticker = tickerRepository.save(new Ticker(parametros.get("codigo")));
		LocalDate data = LocalDate.parse(parametros.get("data"));
		double proporcao = Double.parseDouble(parametros.get("proporcao"));
		Grupamento grupamento = new Grupamento(ticker, data, proporcao);
		grupamentoRepository.save(grupamento);
		return "redirect:/eventos/grupamento";
	}
	
	@PostMapping("/editar_grupamento")
	public String editarGrupamentoPost(@RequestParam Map<String, String> parametros) {
		int quantidade = Integer.parseInt(parametros.get("quantidade"));
		for(int i = 0; i < quantidade; i++) {
			if(parametros.containsKey("editar_" + i)) {
				int id = Integer.parseInt(parametros.get("id_" + i));
				Ticker empresa = tickerRepository.findByCodigo(parametros.get("codigo_" + i));
				LocalDate data = LocalDate.parse(parametros.get("data_" + i));
				double proporcaoDeAcoes = Double.parseDouble(parametros.get("proporcao_" + i));
				Grupamento grupamento = new Grupamento(empresa, data, proporcaoDeAcoes);
				grupamento.setId(id);
				grupamentoRepository.save(grupamento);
			}
		}
		return "redirect:/eventos/grupamento";
	}
}
