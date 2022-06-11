/*
* Estas classes servem somente para gerar valores aleatórios de notas de corretagem e ordens relativas a estas notas.
* Estes valores não validam uma carteira, isto é, com eles, é possível vender uma ação antes mesmo de comprar.
* Eles servem somente para popular o banco com dados válidos, porém aleatórios e não tem participação no código principal.
*/
package grp.meca.irpf.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import grp.meca.irpf.Models.Ticker;
import grp.meca.irpf.Repositories.NotaDeCorretagemRepository;
import grp.meca.irpf.Repositories.OrdemRepository;
import grp.meca.irpf.Repositories.TickerRepository;

@Controller
public class TesteController {
	
	private static String[] tickers = {"itsa4", "petr3", "vale3", "fesa4", "cple3", "bbas3", "bbdc3", "cmig4"};
	private static int[] anos = {2019, 2020, 2021, 2022};
	
	@Autowired
	private TickerRepository tickerRepository;
	
	@Autowired
	private OrdemRepository ordemRepository;
	
	@Autowired
	private NotaDeCorretagemRepository corretagemRepository;
	
	@GetMapping("/teste")
	public String saveTeste(Model model) {
		List<Ordem> ordens = gerarOrdens((int)(Math.random()*10) + 1, tickerRepository);
		NotaDeCorretagem nc = gerarNotaDeCorretagem(ordens, tickerRepository);
		corretagemRepository.save(nc);
		for(Ordem ordem: ordens) {
			ordem.setNotaDeCorretagem(nc);
			ordemRepository.save(ordem);
		}
		model.addAttribute("notaDeCorretagem", nc);
		ordens = ordemRepository.findByNotaDeCorretagem(nc);
		for(Ordem ordem: ordens)
			System.out.println(ordem);
		model.addAttribute("ordens", ordens);
		return "teste";
	}
	
	// Preciso passar o tickerRepository como parâmetro, pois ele será null se chamado fora de saveTeste();
	public Ticker getTicker(String codigo, TickerRepository tickerRepository) {
		Ticker ticker = tickerRepository.findByCodigo(codigo);
		if(ticker == null) {
			ticker = new Ticker(codigo);
			tickerRepository.save(ticker);
		}
		return ticker;
	}
	
	// Preciso passar o tickerRepository como parâmetro, pois ele será null se chamado fora de saveTeste();
	private NotaDeCorretagem gerarNotaDeCorretagem(List<Ordem> ordens, TickerRepository tickerRepository) {
		NotaDeCorretagem nc = new NotaDeCorretagem();
		int diaDoAno = (int)(Math.random()*365 + 1);
		int ano = anos[(int)(Math.random()*anos.length)];
		nc.setDate(LocalDate.ofYearDay(ano, diaDoAno));
		double valorLiquido = 0.0;
		for(Ordem ordem: ordens) {
			double valor = ordem.getQuantidade()*ordem.getPreco();
			double taxas = (int)((valor/3333.0)*100)/100.;
			if(ordem.getTipo() == 'c')
				valorLiquido -= valor;
			else
				valorLiquido += valor;
			valorLiquido -= taxas;
		}
		nc.setValorLiquido(valorLiquido);
		return nc;
	}
	
	// Preciso passar o tickerRepository como parâmetro, pois ele será null se chamado fora de saveTeste();
	private List<Ordem> gerarOrdens(int qt, TickerRepository tickerRepository) {
		List<Ordem> ordens = new ArrayList<>();
		for(int i = 0; i < qt; i++) {
			Ordem ordem = new Ordem();
			ordem.setPreco((int)((Math.random()*50)*100)/100. + 0.01);
			ordem.setQuantidade(((int)(Math.random()*10 + 1))*100);
			ordem.setTipo(Math.random() < 0.5 ? 'c' : 'v');
			ordem.setTicker(getTicker(tickers[(int)(Math.random()*tickers.length)], tickerRepository));
			ordens.add(ordem);
		}
		return ordens;
	}
}
