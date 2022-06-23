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
import grp.meca.irpf.Pojos.ItemCarteira;
import grp.meca.irpf.Pojos.Relatorio;

@SpringBootTest
class Testes03 {
	private final double EPSILON = 0.01;
	@Test
	void testeSwingTrade03() {
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
			Ticker tickerITSA4 = new Ticker("itsa4"), tickerBBDC3 = new Ticker("bbdc3");
			List<Ticker> tickers = new ArrayList<>();
			tickers.add(tickerITSA4);
			tickers.add(tickerBBDC3);
			Ordem ordem1 = new Ordem('c', 100, tickerITSA4, 10, nc1), 
				  ordem2 = new Ordem('c', 100, tickerBBDC3, 20, nc1);
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
			Ticker tickerPETR3 = new Ticker("petr3"), tickerMGLU3 = new Ticker("mglu3");
			tickers.add(tickerPETR3);
			tickers.add(tickerMGLU3);
			Ordem ordem3 = new Ordem('c', 200, tickerPETR3, 15, nc2), 
				  ordem4 = new Ordem('c', 100, tickerBBDC3, 20, nc2),
				  ordem5 = new Ordem('c', 500, tickerMGLU3, 25, nc2);
			List<Ordem> ordens2 = new ArrayList<>();
			ordens2.add(ordem3);
			ordens2.add(ordem4);
			ordens2.add(ordem5);
			nc2.setOrdens(ordens2);
			assertEquals(5.25, nc2.getTaxas(), EPSILON);
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
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 3 (com suas respectivas ordens):
			 * Friday	2020-12-04	-15004.50	
			 * c	200	itsa4	15
			 * c	100	bbdc3	20
			 * c	500	mglu3	20
			 * 
			 */
			NotaDeCorretagem nc3 = new NotaDeCorretagem(LocalDate.of(2020, 12, 4), -15004.50);
			Ordem ordem6 = new Ordem('c', 200, tickerITSA4, 15, nc3),
				  ordem7 = new Ordem('c', 100, tickerBBDC3, 20, nc3),
				  ordem8 = new Ordem('c', 500, tickerMGLU3, 20, nc3);
			List<Ordem> ordens3 = new ArrayList<>();
			ordens3.add(ordem6);
			ordens3.add(ordem7);
			ordens3.add(ordem8);
			nc3.setOrdens(ordens3);
			assertEquals(4.5, nc3.getTaxas(), EPSILON);
			corretagens.add(nc3);
			relatorio = new Relatorio(corretagens);
			itensCarteira = relatorio.getStService().getCarteira(tickers);
			assertEquals(4, itensCarteira.size());
			for(ItemCarteira ic: itensCarteira) {
				if(ic.getTicker().equals("itsa4"))
					assertEquals(4001.20, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("bbdc3"))
					assertEquals(6001.80, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("petr3"))
					assertEquals(3000.90, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("mglu3"))
					assertEquals(22506.75, ic.getCustoTotal(), EPSILON);
			}
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 4 (com suas respectivas ordens):
			 * Monday	2021-01-04	499.85	
			 * v	50	itsa4	10
			 * 
			 */
			NotaDeCorretagem nc4 = new NotaDeCorretagem(LocalDate.of(2021, 1, 4), 499.85);
			Ordem ordem9 = new Ordem('v', 50, tickerITSA4, 10, nc4);
			List<Ordem> ordens4 = new ArrayList<>();
			ordens4.add(ordem9);
			nc4.setOrdens(ordens4);
			assertEquals(0.15, nc4.getTaxas(), EPSILON);
			corretagens.add(nc4);
			relatorio = new Relatorio(corretagens);
			itensCarteira = relatorio.getStService().getCarteira(tickers);
			assertEquals(4, itensCarteira.size());
			for(ItemCarteira ic: itensCarteira) {
				if(ic.getTicker().equals("itsa4"))
					assertEquals(3334.33333333, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("bbdc3"))
					assertEquals(6001.80, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("petr3"))
					assertEquals(3000.90, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("mglu3"))
					assertEquals(22506.75, ic.getCustoTotal(), EPSILON);
			}
			assertEquals(167.0166666666, relatorio.getStService().getAnoMesPrejuizoAcumulado().get(2021).get(1), EPSILON);
			assertEquals(0, relatorio.getStService().getAnoMesImposto().get(2021).get(1), EPSILON);
			assertEquals(-167.0166666666, relatorio.getStService().getAnoMesLucro().get(2021).get(1), EPSILON);
			assertEquals(500, relatorio.getStService().getAnoMesVenda().get(2021).get(1), EPSILON);
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 5 (com suas respectivas ordens):
			 * Thursday	2021-03-04	37488.75
			 * v	250	itsa4	20
			 * v	300	bbdc3	25
			 * v	1000	mglu3	25
			 * 
			 */
			NotaDeCorretagem nc5 = new NotaDeCorretagem(LocalDate.of(2021, 3, 4), 37488.75);
			Ordem ordem10 = new Ordem('v', 250, tickerITSA4, 20, nc5);
			Ordem ordem11 = new Ordem('v', 300, tickerBBDC3, 25, nc5);
			Ordem ordem12 = new Ordem('v', 1000, tickerMGLU3, 25, nc5);
			List<Ordem> ordens5 = new ArrayList<>();
			ordens5.add(ordem10);
			ordens5.add(ordem11);
			ordens5.add(ordem12);
			nc5.setOrdens(ordens5);
			assertEquals(11.25, nc5.getTaxas(), EPSILON);
			corretagens.add(nc5);
			relatorio = new Relatorio(corretagens);
			itensCarteira = relatorio.getStService().getCarteira(tickers);
			assertEquals(1, itensCarteira.size());
			for(ItemCarteira ic: itensCarteira) {
				if(ic.getTicker().equals("itsa4"))
					assertEquals(0, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("bbdc3"))
					assertEquals(0, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("petr3"))
					assertEquals(3000.90, ic.getCustoTotal(), EPSILON);
				else if(ic.getTicker().equals("mglu3"))
					assertEquals(0, ic.getCustoTotal(), EPSILON);
			}
			assertEquals(5645.866, relatorio.getStService().getAnoMesLucro().get(2021).get(3), EPSILON);
			assertEquals(0, relatorio.getStService().getAnoMesPrejuizoAcumulado().get(2021).get(3), EPSILON);
			assertEquals(37500, relatorio.getStService().getAnoMesVenda().get(2021).get(3), EPSILON);
			assertEquals(821.827, relatorio.getStService().getAnoMesImposto().get(2021).get(3), EPSILON);
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 6 (com suas respectivas ordens):
			 * Tuesday 2021-05-04 1999.40
			 * v 300 petr3 10
			 * 
			 */
			NotaDeCorretagem nc6 = new NotaDeCorretagem(LocalDate.of(2021, 5, 4), 1999.40);
			Ordem ordem13 = new Ordem('v', 200, tickerPETR3, 10, nc6);
			List<Ordem> ordens6 = new ArrayList<>();
			ordens6.add(ordem13);
			nc6.setOrdens(ordens6);
			assertEquals(0.6, nc6.getTaxas(), EPSILON);
			corretagens.add(nc6);
			relatorio = new Relatorio(corretagens);
			itensCarteira = relatorio.getStService().getCarteira(tickers);
			assertEquals(0, itensCarteira.size());
			assertEquals(1001.50, relatorio.getStService().getAnoMesPrejuizoAcumulado().get(2021).get(5), EPSILON);
			assertEquals(0, relatorio.getStService().getAnoMesImposto().get(2021).get(5), EPSILON);
			assertEquals(-1001.50, relatorio.getStService().getAnoMesLucro().get(2021).get(5), EPSILON);
			assertEquals(2000, relatorio.getStService().getAnoMesVenda().get(2021).get(5), EPSILON);
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 7 (com suas respectivas ordens):
			 * Tuesday 2021-05-05 -200060.01
			 * c 10000 itsa4 20
			 * 
			 */
			NotaDeCorretagem nc7 = new NotaDeCorretagem(LocalDate.of(2021, 5, 5), -200060.01);
			Ordem ordem14 = new Ordem('c', 10000, tickerITSA4, 20, nc7);
			List<Ordem> ordens7 = new ArrayList<>();
			ordens7.add(ordem14);
			nc7.setOrdens(ordens7);
			assertEquals(60.01, nc7.getTaxas(), EPSILON);
			corretagens.add(nc7);
			relatorio = new Relatorio(corretagens);
			itensCarteira = relatorio.getStService().getCarteira(tickers);
			assertEquals(1, itensCarteira.size());
			assertEquals(200060.01, itensCarteira.get(0).getCustoTotal(), EPSILON);
			/*
			 * c = compra
			 * v = venda
			 * Nota de corretagem 8 (com suas respectivas ordens):
			 * Tuesday 2021-06-04 299909.99
			 * v 10000 itsa4 30
			 * 
			 */
			NotaDeCorretagem nc8 = new NotaDeCorretagem(LocalDate.of(2021, 6, 4), 299909.99);
			Ordem ordem15 = new Ordem('v', 10000, tickerITSA4, 30, nc8);
			List<Ordem> ordens8 = new ArrayList<>();
			ordens8.add(ordem15);
			nc8.setOrdens(ordens8);
			assertEquals(90.01, nc8.getTaxas(), EPSILON);
			corretagens.add(nc8);
			relatorio = new Relatorio(corretagens);
			itensCarteira = relatorio.getStService().getCarteira(tickers);
			assertEquals(0, itensCarteira.size());
			assertEquals(0, relatorio.getStService().getAnoMesPrejuizoAcumulado().get(2021).get(6), EPSILON);
			assertEquals(14827.272, relatorio.getStService().getAnoMesImposto().get(2021).get(6), EPSILON);
			assertEquals(99849.985, relatorio.getStService().getAnoMesLucro().get(2021).get(6), EPSILON);
			assertEquals(300000, relatorio.getStService().getAnoMesVenda().get(2021).get(6), EPSILON);
		} catch(Exception e) {
			//System.err.println("Erro em testeSwingTrade02(): " + e.getMessage());
			e.printStackTrace();
		}
	}
}
