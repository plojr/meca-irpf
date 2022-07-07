package grp.meca.irpf.Controllers.Eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import grp.meca.irpf.Repositories.AquisicaoRepository;

@Controller
@RequestMapping("/eventos")
public class AquisicaoController {
	
	@Autowired
	private AquisicaoRepository aquisicaoRepository;
	
	@GetMapping("/aquisicao")
	public String aquisicaoGet(Model model) {
		model.addAttribute("aquisicoes", aquisicaoRepository.findAll());
		return "eventos/aquisicao";
	}
}
