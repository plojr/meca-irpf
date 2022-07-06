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
import grp.meca.irpf.Models.Eventos.Cisao;
import grp.meca.irpf.Repositories.CisaoRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
@RequestMapping("/eventos")
public class CisaoController {

	@Autowired
	private CisaoRepository cisaoRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("/cisao")
	public String cisaoGet(Model model) {
		model.addAttribute("cisoes", cisaoRepository.findAll());
		return "eventos/cisao";
	}
	
	@PostMapping("/cisao")
	public String cisaoPost(@RequestParam Map<String, String> parametros, Model model) {
		Ticker tickerEmpresaOriginal = tickerRepository.findByCodigo(parametros.get("codigo_empresa_original"));
		if(tickerEmpresaOriginal == null)
			tickerEmpresaOriginal = tickerRepository.save(new Ticker(parametros.get("codigo_empresa_original")));
		Ticker tickerNovaEmpresa = tickerRepository.findByCodigo(parametros.get("codigo_nova_empresa"));
		if(tickerNovaEmpresa == null)
			tickerNovaEmpresa = tickerRepository.save(new Ticker(parametros.get("codigo_nova_empresa")));
		LocalDate data = LocalDate.parse(parametros.get("data"));
		double parteCindida = Double.parseDouble(parametros.get("parte_cindida"));
		double proporcao = Double.parseDouble(parametros.get("proporcao"));
		Cisao cisao = new Cisao(tickerEmpresaOriginal, data, tickerNovaEmpresa, parteCindida, proporcao);
		cisaoRepository.save(cisao);
		return "redirect:/eventos/cisao";
	}
}
