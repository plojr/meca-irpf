package grp.meca.irpf.Services;

import java.util.ArrayList;
import java.util.List;

import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;

public class SwingTrade implements Trade {

	@Override
	public double getImposto() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTaxaIR() {
		return 0.15;
	}
	
	public static List<Ordem> getOrdensSwingTrade(NotaDeCorretagem corretagem) {
		List<Ordem> ordens = new ArrayList<>();
		
		return ordens;
	}
}
