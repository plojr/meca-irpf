package grp.meca.irpf.Controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import grp.meca.irpf.Models.Desdobramento;
import grp.meca.irpf.Models.Ticker;
import grp.meca.irpf.Repositories.DesdobramentoRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
public class DesdobramentoController {
	
	@Autowired
	private DesdobramentoRepository desdobramentoRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("adicionar_desdobramento")
	public String adicionarDesdobramentoGet(Model model) {
		model.addAttribute("desdobramentos", desdobramentoRepository.findAll());
		return "eventos/adicionar_desdobramento";
	}
	
	@PostMapping("adicionar_desdobramento")
	public String adicionarDesdobramentoPost(@RequestParam Map<String, String> parametros, Model model) {
		Ticker ticker = tickerRepository.findByCodigo(parametros.get("codigo"));
		if(ticker == null) {
			ticker = new Ticker(parametros.get("codigo"));
			ticker = tickerRepository.save(ticker);
		}
		LocalDate data = LocalDate.parse(parametros.get("data"));
		double proporcao = Double.parseDouble(parametros.get("proporcao"));
		Desdobramento desdobramento = new Desdobramento(ticker, data, proporcao);
		desdobramentoRepository.save(desdobramento);
		model.addAttribute("desdobramentos", desdobramentoRepository.findAll());
		return "redirect:/adicionar_desdobramento";
	}
}
