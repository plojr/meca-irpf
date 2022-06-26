package grp.meca.irpf.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import grp.meca.irpf.Models.EventoExtraordinario;
import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ticker;
import grp.meca.irpf.Pojos.Relatorio;
import grp.meca.irpf.Repositories.BonificacaoRepository;
import grp.meca.irpf.Repositories.CisaoRepository;
import grp.meca.irpf.Repositories.DesdobramentoRepository;
import grp.meca.irpf.Repositories.FusaoRepository;
import grp.meca.irpf.Repositories.GrupamentoRepository;
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
	private TickerRepository tickerRepositoy;
	
	@Autowired
	private BonificacaoRepository bonificacaoRepository;
	
	@Autowired
	private CisaoRepository cisaoRepository;
	
	@Autowired
	private DesdobramentoRepository desdobramentoRepository;
	
	@Autowired
	private FusaoRepository fusaoRepository;
	
	@Autowired
	private GrupamentoRepository grupamentoRepository;
	
	@GetMapping("/dados_declaracao")
	public String mostrarDadosDeclaracao(Model model) {
		List<EventoExtraordinario> eventos = new ArrayList<>();
		eventos.addAll(bonificacaoRepository.findAll());
		eventos.addAll(cisaoRepository.findAll());
		eventos.addAll(desdobramentoRepository.findAll());
		eventos.addAll(fusaoRepository.findAll());
		eventos.addAll(grupamentoRepository.findAll());
		List<NotaDeCorretagem> corretagens = corretagemRepository.findAllByOrderByDateAsc();
		for(NotaDeCorretagem nc: corretagens)
			nc.setOrdens(ordemRepository.findByNotaDeCorretagem(nc));
		List<Ticker> tickers = tickerRepositoy.findAllByOrderByCodigo();
		Relatorio relatorio = new Relatorio(corretagens, eventos);
		model.addAttribute("carteira", relatorio.getStService().getCarteira(tickers));
		model.addAttribute("dayTrade", relatorio.getDtService().getDayTradeList());
		model.addAttribute("swingTrade", relatorio.getStService().getSwingTradeList());
		model.addAttribute("swingTradeNaoTributavel", relatorio.getStService().getSwingTradeListNaoTributavel());
		model.addAttribute("lucroNaoTributavel", relatorio.getStService().getLucroNaoTributavelPorAno());
		return "relatorio";
	}
}
