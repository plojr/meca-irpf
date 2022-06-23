package grp.meca.irpf.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import grp.meca.irpf.Models.Ticker;
import grp.meca.irpf.Pojos.DadoDayTrade;
import grp.meca.irpf.Pojos.ItemCarteira;
import grp.meca.irpf.Pojos.Relatorio;

@SpringBootTest
class Testes01 {
	private final double EPSILON = 0.01;
	@Test
	void testeSwingTrade01() {
		try {
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 1 (com suas respectivas ordens):
			 * Friday	2020-01-03	-3000.90	
			 * c	100	itsa4	10
			 * c	100	bbdc3	20
			 */
			NotaDeCorretagem nc1 = new NotaDeCorretagem(LocalDate.of(2020, 1, 3), -3000.90);
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
			assertEquals(nc1.getTaxas(), 0.9, EPSILON);
			List<NotaDeCorretagem> corretagens = new ArrayList<>();
			corretagens.add(nc1);
			Relatorio relatorio = new Relatorio(corretagens);
			List<ItemCarteira> itensCarteira = relatorio.getStService().getCarteira(tickers);
			assertEquals(2, itensCarteira.size());
			assertThat(itensCarteira.size() > 0);
			for(ItemCarteira ic: itensCarteira) {
				if(ic.getTicker().equals("itsa4"))
					assertEquals(1000.30, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("bbdc3"))
					assertEquals(2000.60, ic.getCustoTotal(), EPSILON);
			}
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 2 (com suas respectivas ordens):
			 * Wednesday	2020-02-05	-17505.25	
			 * c	200	petr3	15
			 * c	100	bbdc3	20
			 * c	500	mglu3	25
			 * 
			 */
			NotaDeCorretagem nc2 = new NotaDeCorretagem(LocalDate.of(2020, 2, 5), -17505.25);
			Ticker ticker3 = new Ticker("petr3"), ticker4 = new Ticker("mglu3");
			tickers.add(ticker3);
			tickers.add(ticker4);
			Ordem ordem3 = new Ordem('c', 200, ticker3, 15, nc2), 
				  ordem4 = new Ordem('c', 100, ticker2, 20, nc2),
				  ordem5 = new Ordem('c', 500, ticker4, 25, nc2);
			List<Ordem> ordens2 = new ArrayList<>();
			ordens2.add(ordem3);
			ordens2.add(ordem4);
			ordens2.add(ordem5);
			nc2.setOrdens(ordens2);
			assertEquals(nc2.getTaxas(), 5.25, EPSILON);
			corretagens.add(nc2);
			relatorio = new Relatorio(corretagens);
			itensCarteira = relatorio.getStService().getCarteira(tickers);
			assertEquals(4, itensCarteira.size());
			for(ItemCarteira ic: itensCarteira) {
				if(ic.getTicker().equals("itsa4"))
					assertEquals(1000.30, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("bbdc3"))
					assertEquals(4001.20, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("petr3"))
					assertEquals(3000.90, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("mglu3"))
					assertEquals(12503.75, ic.getCustoTotal(), EPSILON);
			}
		} catch(Exception e) {
			System.err.println("Erro em testeSwingTrade01(): " + e.getMessage());
		}
	}
	
	@Test
	void testeDayTrade01() {
		try {
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 1 (com suas respectivas ordens):
			 * c 100 itsa4 10
			 * v 100 itsa4 20
			 * 
			 */
			NotaDeCorretagem nc1 = new NotaDeCorretagem(LocalDate.of(2020, 1, 3), 999.10);
			Ticker ticker1 = new Ticker("itsa4");
			List<Ticker> tickers = new ArrayList<>();
			tickers.add(ticker1);
			Ordem ordem1 = new Ordem('c', 100, ticker1, 10, nc1), 
				  ordem2 = new Ordem('v', 100, ticker1, 20, nc1);
			List<Ordem> ordens1 = new ArrayList<>();
			ordens1.add(ordem1);
			ordens1.add(ordem2);
			nc1.setOrdens(ordens1);
			assertEquals(0.9, nc1.getTaxas(), EPSILON);
			List<NotaDeCorretagem> corretagens = new ArrayList<>();
			corretagens.add(nc1);
			Relatorio relatorio = new Relatorio(corretagens);
			List<DadoDayTrade> dtList = relatorio.getDtService().getDayTradeList();
			assertThat(dtList.size() > 0);
			for(DadoDayTrade ddt: dtList) {
				if(ddt.getAno() == 2022 && ddt.getMes() == 1) {
					assertEquals(999.1, ddt.getLucro(), EPSILON);
					assertEquals(199.82, ddt.getImposto(), EPSILON);
					assertEquals(0, ddt.getPrejuizoAcumulado(), EPSILON);
				}
			}
		} catch (Exception e) {
			System.err.println("Erro em testeDayTrade01(): " + e.getMessage());
		}
	}
}
