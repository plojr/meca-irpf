package grp.meca.irpf.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import grp.meca.irpf.Models.Basico.NotaDeCorretagem;
import grp.meca.irpf.Models.Basico.Ordem;
import grp.meca.irpf.Models.Basico.Ticker;
import grp.meca.irpf.Models.Eventos.Cisao;
import grp.meca.irpf.Models.Eventos.EventoExtraordinario;
import grp.meca.irpf.Pojos.ItemCarteira;
import grp.meca.irpf.Pojos.Relatorio;

@SpringBootTest
public class TestesCisao01 {
	
	private final double EPSILON = 0.01;
	@Test
	public void teste01() {
		try {
			NotaDeCorretagem nc1 = new NotaDeCorretagem(LocalDate.of(2019, 12, 3), -3000.90);
			Ticker ticker1 = new Ticker("sanb3"), ticker2 = new Ticker("gett3");
			List<Ticker> tickers = new ArrayList<>();
			tickers.add(ticker1);
			Ordem ordem1 = new Ordem('c', 100, ticker1, 10, nc1);
			List<Ordem> ordens1 = new ArrayList<>();
			ordens1.add(ordem1);
			nc1.setOrdens(ordens1);
			List<NotaDeCorretagem> corretagens = new ArrayList<>();
			corretagens.add(nc1);
			// lista de eventos extraordinários
			Cisao cisao1 = new Cisao(ticker1, LocalDate.of(2020, 1, 3), ticker2, 0.10, 0.25);
			List<EventoExtraordinario> eventos = new ArrayList<>();
			eventos.add(cisao1);
			Relatorio relatorio = new Relatorio(corretagens, eventos);
			List<ItemCarteira> itensCarteira = relatorio.getStService().getCarteira(tickers);
			for(ItemCarteira ic: itensCarteira) {
				if(ic.getTicker().equals("sanb3")) {
					assertEquals(2700.81, ic.getCustoTotal(), EPSILON);
					assertEquals(100, ic.getQuantidade());
				}
				else if(ic.getTicker().equals("gett3")) {
					assertEquals(300.09, ic.getCustoTotal(), EPSILON);
					assertEquals(25, ic.getQuantidade());
				}
			}
		} catch(Exception e) {
			System.err.println("TestesCisao01.teste01(): " + e.getMessage());
		}
	}
}
