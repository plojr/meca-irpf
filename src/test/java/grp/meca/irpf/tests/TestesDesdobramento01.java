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
import grp.meca.irpf.Models.Eventos.Desdobramento;
import grp.meca.irpf.Models.Eventos.EventoExtraordinario;
import grp.meca.irpf.Pojos.ItemCarteira;
import grp.meca.irpf.Pojos.Relatorio;

@SpringBootTest
public class TestesDesdobramento01 {

	private final double EPSILON = 0.01;
	@Test
	public void teste01() {
		try {
			NotaDeCorretagem nc1 = new NotaDeCorretagem(LocalDate.of(2019, 12, 3), -3000.90);
			Ticker ticker1 = new Ticker("itsa4"), ticker2 = new Ticker("bbdc3");
			List<Ticker> tickers = new ArrayList<>();
			tickers.add(ticker1);
			tickers.add(ticker2);
			Ordem ordem1 = new Ordem('c', 100, ticker1, 10, nc1), 
				  ordem2 = new Ordem('c', 100, ticker2, 20, nc1);
			List<Ordem> ordens1 = new ArrayList<>();
			ordens1.add(ordem1);
			ordens1.add(ordem2);
			nc1.setOrdens(ordens1);
			List<NotaDeCorretagem> corretagens = new ArrayList<>();
			corretagens.add(nc1);
			// lista de eventos extraordinários
			Desdobramento desdobramento1 = new Desdobramento(ticker1, LocalDate.of(2020, 1, 3), 10);
			Desdobramento desdobramento2 = new Desdobramento(ticker2, LocalDate.of(2020, 2, 3), 5);
			List<EventoExtraordinario> eventos = new ArrayList<>();
			eventos.add(desdobramento1);
			eventos.add(desdobramento2);
			Relatorio relatorio = new Relatorio(corretagens, eventos);
			List<ItemCarteira> itensCarteira = relatorio.getStService().getCarteira(tickers);
			for(ItemCarteira ic: itensCarteira) {
				if(ic.getTicker().equals("itsa4")) {
					assertEquals(1000.30, ic.getCustoTotal(), EPSILON);
					assertEquals(1000, ic.getQuantidade());
				}
				else if(ic.getTicker().equals("bbdc3")) {
					assertEquals(2000.60, ic.getCustoTotal(), EPSILON);
					assertEquals(500, ic.getQuantidade());
				}
			}
		} catch(Exception e) {
			System.err.println("TesteDesdobramento01.teste01(): " + e.getMessage());
		}
	}
	
	@Test
	public void teste02() {
		try {
			NotaDeCorretagem nc1 = new NotaDeCorretagem(LocalDate.of(2020, 12, 3), -3000.90);
			Ticker ticker1 = new Ticker("itsa4"), ticker2 = new Ticker("bbdc3");
			List<Ticker> tickers = new ArrayList<>();
			tickers.add(ticker1);
			tickers.add(ticker2);
			Ordem ordem1 = new Ordem('c', 100, ticker1, 10, nc1), 
				  ordem2 = new Ordem('c', 100, ticker2, 20, nc1);
			List<Ordem> ordens1 = new ArrayList<>();
			ordens1.add(ordem1);
			ordens1.add(ordem2);
			nc1.setOrdens(ordens1);
			List<NotaDeCorretagem> corretagens = new ArrayList<>();
			corretagens.add(nc1);
			// lista de eventos extraordinários
			Desdobramento desdobramento1 = new Desdobramento(ticker1, LocalDate.of(2020, 1, 3), 10);
			Desdobramento desdobramento2 = new Desdobramento(ticker2, LocalDate.of(2020, 2, 3), 5);
			List<EventoExtraordinario> eventos = new ArrayList<>();
			eventos.add(desdobramento1);
			eventos.add(desdobramento2);
			Relatorio relatorio = new Relatorio(corretagens, eventos);
			List<ItemCarteira> itensCarteira = relatorio.getStService().getCarteira(tickers);
			for(ItemCarteira ic: itensCarteira) {
				if(ic.getTicker().equals("itsa4")) {
					assertEquals(1000.30, ic.getCustoTotal(), EPSILON);
					assertEquals(100, ic.getQuantidade());
				}
				else if(ic.getTicker().equals("bbdc3")) {
					assertEquals(2000.60, ic.getCustoTotal(), EPSILON);
					assertEquals(100, ic.getQuantidade());
				}
			}
		} catch(Exception e) {
			System.err.println("TesteDesdobramento01.teste01(): " + e.getMessage());
		}
	}
}
