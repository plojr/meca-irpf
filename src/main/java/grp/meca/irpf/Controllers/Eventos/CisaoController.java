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
	
	@GetMapping("/deletar_cisao")
	public String deletarCisao(@ModelAttribute(value="id") int id) {
		cisaoRepository.deleteById(id);
		return "redirect:/eventos/cisao";
	}
	
	
	@PostMapping("/cisao")
	public String cisaoPost(@RequestParam Map<String, String> parametros) {
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
	
	@PostMapping("/editar_cisao")
	public String editarCisaoPost(@RequestParam Map<String, String> parametros) {
		System.out.println("|" + parametros.get("quantidade") + "|");
		int quantidade = Integer.parseInt(parametros.get("quantidade"));
		for(int i = 0; i < quantidade; i++) {
			if(parametros.containsKey("editar_" + i)) {
				int id = Integer.parseInt(parametros.get("id_" + i));
				Ticker empresaOriginal = tickerRepository.findByCodigo(parametros.get("codigo_empresa_original_" + i));
				Ticker novaEmpresa = tickerRepository.findByCodigo(parametros.get("codigo_nova_empresa_" + i));
				LocalDate data = LocalDate.parse(parametros.get("data_" + i));
				double parteCindida = Double.parseDouble(parametros.get("parte_cindida_" + i));
				double proporcao = Double.parseDouble(parametros.get("proporcao_" + i));
				Cisao cisao = new Cisao(empresaOriginal, data, novaEmpresa, parteCindida, proporcao);
				cisao.setId(id);
				cisaoRepository.save(cisao);
			}
		}
		return "redirect:/eventos/cisao";
	}
}
