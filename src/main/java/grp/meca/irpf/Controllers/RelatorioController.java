package grp.meca.irpf.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Relatorio;
import grp.meca.irpf.Models.Ticker;
import grp.meca.irpf.Repositories.NotaDeCorretagemRepository;
import grp.meca.irpf.Repositories.OrdemRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
public class RelatorioController {

	@Autowired
	private NotaDeCorretagemRepository corretagemRepository;
	
	@Autowired
	private OrdemRepository ordemRepository;
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@GetMapping("/dados_declaracao")
	public String mostrarDadosDeclaracao(Model model) {
		List<NotaDeCorretagem> corretagens = corretagemRepository.findAllByOrderByDateAsc();
		for(NotaDeCorretagem nc: corretagens)
			nc.setOrdens(ordemRepository.findByNotaDeCorretagem(nc));
		Relatorio relatorio = null;
		try {
			relatorio = new Relatorio(corretagens);
		} catch(Exception e) {
			System.out.println("RelatorioController.mostrarDadosDeclaracao(): " + e.getMessage());
			model.addAttribute("mensagemDeErro", e.getMessage());
			return "erro";
		}
		model.addAttribute("carteira", relatorio.getCarteira().getItensCarteira(Ticker.getMapCodigoCnpj(tickerRepository.findAll())));
		return "relatorio";
	}
}
