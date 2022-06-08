package grp.meca.irpf.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Repositories.NotaDeCorretagemRepository;
import grp.meca.irpf.Repositories.OrdemRepository;

@Controller
public class MainController {
	
	@Autowired
	private OrdemRepository ordemRepository;
	
	@Autowired
	private NotaDeCorretagemRepository corretagemRepository;

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/corretagens")
	public String mostrarCorretagens(Model model) {
		List<NotaDeCorretagem> corretagens = corretagemRepository.findAll();
		for(NotaDeCorretagem nc: corretagens)
			nc.setOrdens(ordemRepository.findByNotaDeCorretagem(nc));
		model.addAttribute("corretagens", corretagens);
		return "corretagens";
	}
	
	
}
