package grp.meca.irpf.Controllers.Eventos;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import grp.meca.irpf.Models.Basico.Ticker;
import grp.meca.irpf.Models.Eventos.Desdobramento;
import grp.meca.irpf.Repositories.DesdobramentoRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
@RequestMapping("/eventos")
public class DesdobramentoController {
	
	@Autowired
	private DesdobramentoRepository desdobramentoRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("/desdobramento")
	public String desdobramentoGet(Model model) {
		model.addAttribute("desdobramentos", desdobramentoRepository.findAllByOrderByDataEvento());
		return "eventos/desdobramento";
	}
	
	@GetMapping("/deletar_desdobramento")
	public String deletarDesdobramento(@ModelAttribute(value="id") int id) {
		desdobramentoRepository.deleteById(id);
		return "redirect:/eventos/desdobramento";
	}
	
	@PostMapping("/desdobramento")
	public String desdobramentoPost(@RequestParam Map<String, String> parametros, Model model) {
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
		return "redirect:/eventos/desdobramento";
	}
	
	@PostMapping("/editar_desdobramento")
	public String editarDesdobramentoPost(@RequestParam Map<String, String> parametros) {
		int quantidade = Integer.parseInt(parametros.get("quantidade"));
		for(int i = 0; i < quantidade; i++) {
			if(parametros.containsKey("editar_" + i)) {
				int id = Integer.parseInt(parametros.get("id_" + i));
				Ticker empresa = tickerRepository.findByCodigo(parametros.get("codigo_" + i));
				LocalDate data = LocalDate.parse(parametros.get("data_" + i));
				double proporcaoDeAcoes = Double.parseDouble(parametros.get("proporcao_" + i));
				Desdobramento desdobramento = new Desdobramento(empresa, data, proporcaoDeAcoes);
				desdobramento.setId(id);
				desdobramentoRepository.save(desdobramento);
			}
		}
		return "redirect:/eventos/desdobramento";
	}
}
