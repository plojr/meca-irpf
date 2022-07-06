package grp.meca.irpf.Controllers.Basico;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import grp.meca.irpf.Controllers.ErroController;
import grp.meca.irpf.Models.Basico.Ticker;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
@RequestMapping("/basico")
public class TickerController {

	@Autowired
	private TickerRepository tickerRepository;
	
	@PostMapping("/editar_ticker")
	public String editarTickerPost(@RequestParam Map<String, String> parametros, Model model) {
		List<Ticker> tickers = tickerRepository.findAllByOrderByCodigo();
		model.addAttribute("tickers", tickers);
		int quantidade = Integer.parseInt(parametros.get("quantidade"));
		for(int i = 0; i < quantidade; i++) {
			if(parametros.containsKey("editar_" + i)) {
				int id = Integer.parseInt(parametros.get("id_" + i));
				String codigo = parametros.get("codigo_" + i);
				String cnpj = parametros.get("cnpj_" + i);
				boolean bdr = parametros.get("bdr_" + i).equals("Sim");
				Ticker ticker = new Ticker();
				ticker.setId(id);
				ticker.setCodigo(codigo);
				ticker.setCnpj(cnpj);
				ticker.setBdr(bdr);
				tickerRepository.save(ticker);
			}
		}
		return "redirect:/basico/ticker";
	}
	
	@GetMapping("/ticker")
	public String adicionarTickerGet(Model model) {
		List<Ticker> tickers = tickerRepository.findAllByOrderByCodigo();
		model.addAttribute("tickers", tickers);
		return "basico/ticker";
	}
	
	@PostMapping("/ticker")
	public String adicionarTickerPost(@ModelAttribute("ticker") Ticker ticker, Model model) {
		if(tickerRepository.findByCodigo(ticker.getCodigo()) != null) {
			model.addAttribute("mensagemDeErro", "Ticker " + ticker.getCodigo() + " já existente!");
			return new ErroController().getErro(model);
		}
		if(ticker.getCnpj().equals(""))
			ticker.setCnpj("00000000000000");
		tickerRepository.save(ticker);
		List<Ticker> tickers = tickerRepository.findAllByOrderByCodigo();
		model.addAttribute("tickers", tickers);
		return "redirect:/basico/ticker";
	}
	
	
}
