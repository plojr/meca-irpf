package grp.meca.irpf.Controllers.Basico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import grp.meca.irpf.Repositories.OrdemRepository;

@Controller
@RequestMapping("/basico")
public class OrdemController {
	
	@Autowired
	private OrdemRepository ordemRepository;
	
	@GetMapping("/deletar_ordem")
	public String deletarOrdem(@ModelAttribute(value="id") int id) {
		ordemRepository.deleteById(id);
		return "redirect:/basico/corretagem";
	}
}
