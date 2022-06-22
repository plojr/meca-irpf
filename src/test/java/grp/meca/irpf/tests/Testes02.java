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
class Testes02 {
	private final double EPSILON = 0.000001;
	@Test
	void testeSwingTrade02() {
		try {
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
		} catch(Exception e) {
			System.err.println("Erro em testeSwingTrade01()");
		}
	}
	
	@Test
	void testeDayTrade02() {
		try {
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
			List<DadoDayTrade> dtList = relatorio.getDtService().getDayTradeList();
			assertThat(dtList.size() > 0);
			for(DadoDayTrade ddt: dtList) {
				if(ddt.getAno() == 2022 && ddt.getMes() == 1) {
					assertEquals(999.1, ddt.getLucro(), EPSILON);
					assertEquals(199.82, ddt.getImposto(), EPSILON);
				}
			}
		} catch (Exception e) {
			System.err.println("Erro em testeDayTrade01()");
		}
	}
}
