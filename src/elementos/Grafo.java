package elementos;

import java.util.ArrayList;
import java.util.HashMap;

public class Grafo {
	private HashMap<String, Nó> nós;
	private Veículo veículo;
	
	public Grafo(HashMap<String, Nó> nós){
		this.nós = nós;
		veículo = new Veículo(80,200);
	}
	
	public ArrayList<Encomenda> encomendasAEntregar(Nó nó) {
		ArrayList<Encomenda> encomendasAEntregar = new ArrayList<Encomenda>();
		
		for (Nó cadaNó : nós.values()) {
			if (cadaNó.equals(nó)) continue;
			
			for (Encomenda enc : cadaNó.getEncomendasDaqui()) {
				if (enc.getDestino().equals(nó)) encomendasAEntregar.add(enc);
			}
		}
		
		return encomendasAEntregar;
	}
	
	public HashMap<String, Nó> getNós() {
		return nós;
	}
	
	public Veículo getVeículo() {
		return veículo;
	}
	
	public ArrayList<Nó> obterPossiveisNós(Estado actual){
		ArrayList<Nó> nósPossiveis = new ArrayList<Nó>();
		
		for (Nó temp : nós.values()){
			if (temp.getNome().equals(actual.nóAtual.getNome())) continue;
			else if (actual.veículo.getCargaAtual() == 0){
				if (temp.getClass().getSimpleName().equals("PontoRecolha")) nósPossiveis.add(temp);
				else if (temp.getClass().getSimpleName().equals("PontoAbastecimento") && actual.veículo.getGasolinaAtual() != actual.veículo.getGasolinaMáx()) nósPossiveis.add(temp);
			}
			else if (actual.veículo.getGasolinaAtual() - actual.nóAtual.distânciaAté(this.nós,temp)*0.08 > 0){
				if (actual.veículo.getCargaAtual() == actual.veículo.getCargaMáx()){
					if (!temp.getClass().getSimpleName().equals("PontoRecolha")) nósPossiveis.add(temp);
				}else nósPossiveis.add(temp);
			}else if (actual.veículo.getCargaAtual() == actual.veículo.getCargaMáx()){
				if (actual.veículo.getGasolinaAtual() == actual.veículo.getGasolinaMáx()){
					if (temp.getClass().getSimpleName().equals("PontoEntrega")) nósPossiveis.add(temp);
				}else nósPossiveis.add(temp);
			}
	    }
		
		return nósPossiveis;
	}
}
