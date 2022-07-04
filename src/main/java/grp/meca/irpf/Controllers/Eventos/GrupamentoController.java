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
	
	@GetMapping("/adicionar_grupamento")
	public String adicionarGrupamentoGet(Model model) {
		model.addAttribute("grupamentos", grupamentoRepository.findAll());
		return "eventos/adicionar_grupamento";
	}
	
	@PostMapping("/adicionar_grupamento")
	public String adicionarGrupamentoPost(@RequestParam Map<String, String> parametros, Model model) {
		Ticker ticker = tickerRepository.findByCodigo(parametros.get("codigo"));
		if(ticker == null)
			ticker = tickerRepository.save(new Ticker(parametros.get("codigo")));
		LocalDate data = LocalDate.parse(parametros.get("data"));
		double proporcao = Double.parseDouble(parametros.get("proporcao"));
		Grupamento grupamento = new Grupamento(ticker, data, proporcao);
		grupamentoRepository.save(grupamento);
		model.addAttribute("grupamentos", grupamentoRepository.findAll());
		return "redirect:/eventos/adicionar_grupamento";
	}
}
