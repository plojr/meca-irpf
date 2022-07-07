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
import grp.meca.irpf.Models.Eventos.Aquisicao;
import grp.meca.irpf.Repositories.AquisicaoRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
@RequestMapping("/eventos")
public class AquisicaoController {
	
	@Autowired
	private AquisicaoRepository aquisicaoRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("/aquisicao")
	public String aquisicaoGet(Model model) {
		model.addAttribute("aquisicoes", aquisicaoRepository.findAll());
		return "eventos/aquisicao";
	}
	
	@PostMapping("/aquisicao")
	public String aquisicaoPost(@RequestParam Map<String, String> parametros) {
		Ticker tickerEmpresaCompradora = tickerRepository.findByCodigo(parametros.get("codigo_empresa_compradora"));
		if(tickerEmpresaCompradora == null) tickerEmpresaCompradora = tickerRepository.save(new Ticker(parametros.get("codigo_empresa_compradora")));
		Ticker tickerEmpresaAdquirida = tickerRepository.findByCodigo(parametros.get("codigo_empresa_adquirida"));
		if(tickerEmpresaAdquirida == null) tickerEmpresaAdquirida = tickerRepository.save(new Ticker(parametros.get("codigo_empresa_adquirida")));
		LocalDate data = LocalDate.parse(parametros.get("data"));
		Double proporcaoAcoes = parametros.get("proporcao_acoes").equals("") ? null : Double.parseDouble(parametros.get("proporcao_acoes"));
		Double precoPorAcao = parametros.get("preco_por_acao").equals("") ? null : Double.parseDouble(parametros.get("preco_por_acao"));
		aquisicaoRepository.save(new Aquisicao(tickerEmpresaCompradora, data, tickerEmpresaAdquirida, proporcaoAcoes, precoPorAcao));
		return "redirect:/eventos/aquisicao";
	}
}
