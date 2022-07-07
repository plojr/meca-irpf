package grp.meca.irpf.Controllers.Eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import grp.meca.irpf.Repositories.FusaoRepository;

@Controller
@RequestMapping("/eventos")
public class FusaoController {
	
	@Autowired
	private FusaoRepository fusaoRepository;
	
	@GetMapping("fusao")
	public String fusaoGet(Model model) {
		model.addAttribute("fusoes", fusaoRepository.findAll());
		return "eventos/fusao";
	}
	
	@PostMapping("fusao")
	public String fusaoPost() {
		
		return "redirect:/eventos/fusao";
	}
}
