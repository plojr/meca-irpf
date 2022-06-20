package grp.meca.irpf.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Pojos.Relatorio;
import grp.meca.irpf.Repositories.NotaDeCorretagemRepository;
import grp.meca.irpf.Repositories.OrdemRepository;

@Controller
public class RelatorioController {

	@Autowired
	private NotaDeCorretagemRepository corretagemRepository;
	
	@Autowired
	private OrdemRepository ordemRepository;
	
	@GetMapping("/dados_declaracao")
	public String mostrarDadosDeclaracao(Model model) {
		List<NotaDeCorretagem> corretagens = corretagemRepository.findAllByOrderByDateAsc();
		for(NotaDeCorretagem nc: corretagens)
			nc.setOrdens(ordemRepository.findByNotaDeCorretagem(nc));
		Relatorio relatorio = new Relatorio(corretagens);
		model.addAttribute("dayTrade", relatorio.getDtService().getDayTradeList());
		return "relatorio";
	}
}
