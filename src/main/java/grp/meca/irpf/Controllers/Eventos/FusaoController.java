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
import grp.meca.irpf.Models.Eventos.Fusao;
import grp.meca.irpf.Repositories.FusaoRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
@RequestMapping("/eventos")
public class FusaoController {
	
	@Autowired
	private FusaoRepository fusaoRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("fusao")
	public String fusaoGet(Model model) {
		model.addAttribute("fusoes", fusaoRepository.findAll());
		return "eventos/fusao";
	}

	@GetMapping("/deletar_fusao")
	public String deletarFusao(@ModelAttribute(value="id") int id) {
		fusaoRepository.deleteById(id);
		return "redirect:/eventos/fusao";
	}
	
	@PostMapping("fusao")
	public String fusaoPost(@RequestParam Map<String, String> parametros) {
		Ticker tickerEmpresa1 = tickerRepository.findByCodigo(parametros.get("codigo_1"));
		if(tickerEmpresa1 == null) tickerEmpresa1 = tickerRepository.save(new Ticker(parametros.get("codigo_1")));
		Ticker tickerEmpresa2 = tickerRepository.findByCodigo(parametros.get("codigo_2"));
		if(tickerEmpresa2 == null) tickerEmpresa2 = tickerRepository.save(new Ticker(parametros.get("codigo_2")));
		Ticker tickerNovaEmpresa = tickerRepository.findByCodigo(parametros.get("codigo_nova"));
		if(tickerNovaEmpresa == null) tickerNovaEmpresa = tickerRepository.save(new Ticker(parametros.get("codigo_nova")));
		LocalDate data = LocalDate.parse(parametros.get("data"));
		double proporcaoEmpresa1 = Double.parseDouble(parametros.get("proporcao_empresa_1"));
		double proporcaoEmpresa2 = Double.parseDouble(parametros.get("proporcao_empresa_2"));
		fusaoRepository.save(new Fusao(tickerEmpresa1, data, tickerEmpresa2, tickerNovaEmpresa, proporcaoEmpresa1, proporcaoEmpresa2));
		return "redirect:/eventos/fusao";
	}
	
	@PostMapping("/editar_fusao")
	public String editarFusaoPost(@RequestParam Map<String, String> parametros) {
		int quantidade = Integer.parseInt(parametros.get("quantidade"));
		for(int i = 0; i < quantidade; i++) {
			if(parametros.containsKey("editar_" + i)) {
				int id = Integer.parseInt(parametros.get("id_" + i));
				Ticker empresa1 = tickerRepository.findByCodigo(parametros.get("codigo_1_" + i));
				if(empresa1 == null) empresa1 = tickerRepository.save(new Ticker(parametros.get("codigo_1_" + i)));
				Ticker empresa2 = tickerRepository.findByCodigo(parametros.get("codigo_2_" + i));
				if(empresa2 == null) empresa2 = tickerRepository.save(new Ticker(parametros.get("codigo_2_" + i)));
				Ticker novaEmpresa = tickerRepository.findByCodigo(parametros.get("codigo_nova_" + i));
				if(novaEmpresa == null) novaEmpresa = tickerRepository.save(new Ticker(parametros.get("codigo_nova_" + i)));
				LocalDate data = LocalDate.parse(parametros.get("data_" + i));
				double proporcaoDeAcoes1 = Double.parseDouble(parametros.get("proporcao_empresa_1_" + i));
				double proporcaoDeAcoes2 = Double.parseDouble(parametros.get("proporcao_empresa_2_" + i));
				Fusao fusao = new Fusao(empresa1, data, empresa2, novaEmpresa, proporcaoDeAcoes1, proporcaoDeAcoes2);
				fusao.setId(id);
				fusaoRepository.save(fusao);
			}
		}
		return "redirect:/eventos/fusao";
	}
}
