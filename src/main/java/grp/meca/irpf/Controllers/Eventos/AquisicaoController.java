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
		model.addAttribute("aquisicoes", aquisicaoRepository.findAllByOrderByDataEventoAsc());
		return "eventos/aquisicao";
	}
	
	@PostMapping("/aquisicao")
	public String aquisicaoPost(@RequestParam Map<String, String> parametros) {
		Ticker tickerEmpresaCompradora = tickerRepository.findByCodigo(parametros.get("codigo_empresa_compradora"));
		if(tickerEmpresaCompradora == null) tickerEmpresaCompradora = tickerRepository.save(new Ticker(parametros.get("codigo_empresa_compradora")));
		Ticker tickerEmpresaAdquirida = tickerRepository.findByCodigo(parametros.get("codigo_empresa_adquirida"));
		if(tickerEmpresaAdquirida == null) tickerEmpresaAdquirida = tickerRepository.save(new Ticker(parametros.get("codigo_empresa_adquirida")));
		LocalDate data = LocalDate.parse(parametros.get("data"));
		Double proporcaoAcoes = parametros.get("proporcao_acoes").equals("") ? 0. : Double.parseDouble(parametros.get("proporcao_acoes"));
		Double precoPorAcao = parametros.get("preco_por_acao").equals("") ? 0. : Double.parseDouble(parametros.get("preco_por_acao"));
		aquisicaoRepository.save(new Aquisicao(tickerEmpresaCompradora, data, tickerEmpresaAdquirida, proporcaoAcoes, precoPorAcao));
		return "redirect:/eventos/aquisicao";
	}
	
	@GetMapping("/deletar_aquisicao")
	public String deletarAquisicao(@ModelAttribute(value="id") int id) {
		aquisicaoRepository.deleteById(id);
		return "redirect:/eventos/aquisicao";
	}
	
	@PostMapping("/editar_aquisicao")
	public String editarAquisicaoPost(@RequestParam Map<String, String> parametros) {
		int quantidade = Integer.parseInt(parametros.get("quantidade"));
		for(int i = 0; i < quantidade; i++) {
			if(parametros.containsKey("editar_" + i)) {
				int id = Integer.parseInt(parametros.get("id_" + i));
				Ticker empresaCompradora = tickerRepository.findByCodigo(parametros.get("codigo_empresa_compradora_" + i));
				Ticker empresaAdquirida = tickerRepository.findByCodigo(parametros.get("codigo_empresa_adquirida_" + i));
				LocalDate data = LocalDate.parse(parametros.get("data_" + i));
				double proporcaoDeAcoes = Double.parseDouble(parametros.get("proporcao_acoes_" + i));
				double precoPorAcao = Double.parseDouble(parametros.get("preco_por_acao_" + i));
				Aquisicao aquisicao = new Aquisicao(empresaCompradora, data, empresaAdquirida, proporcaoDeAcoes, precoPorAcao);
				aquisicao.setId(id);
				aquisicaoRepository.save(aquisicao);
			}
		}
		return "redirect:/eventos/aquisicao";
	}
}
