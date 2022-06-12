package grp.meca.irpf.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Relatorio;
import grp.meca.irpf.Repositories.NotaDeCorretagemRepository;

@Controller
public class RelatorioController {

	@Autowired
	private NotaDeCorretagemRepository corretagemRepository;
	
	@GetMapping("/dados_declaracao")
	public String mostrarDadosDeclaracao(Model model) {
		List<NotaDeCorretagem> corretagens = corretagemRepository.findAll();
		Relatorio relatorio = null;
		try {
			relatorio = new Relatorio(corretagens);
		} catch(Exception e) {
			model.addAttribute("mensagemDeErro", e.getMessage());
			return "redirect:/erro";
		}
		model.addAttribute("relatorio", relatorio);
		return "relatorio";
	}
}
