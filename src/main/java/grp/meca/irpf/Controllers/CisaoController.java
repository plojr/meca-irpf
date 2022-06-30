package grp.meca.irpf.Controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import grp.meca.irpf.Models.Cisao;
import grp.meca.irpf.Models.Ticker;
import grp.meca.irpf.Repositories.CisaoRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
public class CisaoController {

	@Autowired
	private CisaoRepository cisaoRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("adicionar_cisao")
	public String cisaoGet(Model model) {
		model.addAttribute("cisoes", cisaoRepository.findAll());
		return "eventos/adicionar_cisao";
	}
	
	@PostMapping("adicionar_cisao")
	public String cisaoPost(@RequestParam Map<String, String> parametros, Model model) {
		Ticker tickerEmpresaOriginal = tickerRepository.findByCodigo(parametros.get("codigo"));
		if(tickerEmpresaOriginal == null)
			tickerEmpresaOriginal = tickerRepository.save(new Ticker(parametros.get("codigo")));
		Ticker tickerEmpresaCindida = tickerRepository.findByCodigo(parametros.get("codigo_cindida"));
		if(tickerEmpresaCindida == null)
			tickerEmpresaCindida = tickerRepository.save(new Ticker(parametros.get("codigo")));
		LocalDate data = LocalDate.parse(parametros.get("data"));
		double parteCindida = Double.parseDouble(parametros.get("parte_cindida"));
		double proporcao = Double.parseDouble(parametros.get("proporcao"));
		Cisao cisao = new Cisao(tickerEmpresaOriginal, data, tickerEmpresaCindida, parteCindida, proporcao);
		cisaoRepository.save(cisao);
		return "redirect:/adicionar_cisao";
	}
}
