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
	
	public ArrayList<Nó> obterPossíveisNós(Estado atual){
		ArrayList<Nó> nósPossiveis = new ArrayList<Nó>();
		
		for (Nó temp : nós.values()){
			if (temp.getNome().equals(atual.getNóAtual().getNome())) continue;
			else if (atual.getVeículo().getCargaAtual() == 0){
				if (temp.getClass().getSimpleName().equals("PontoRecolha")) nósPossiveis.add(temp);
				else if (temp.getClass().getSimpleName().equals("PontoAbastecimento") && atual.getVeículo().getGasolinaAtual() != atual.getVeículo().getGasolinaMáx()) nósPossiveis.add(temp);
			}
			else if (atual.getVeículo().getGasolinaAtual() - distânciaAté(atual.getNóAtual(),temp)*0.08 > 0){
				if (atual.getVeículo().getCargaAtual() == atual.getVeículo().getCargaMáx()){
					if (!temp.getClass().getSimpleName().equals("PontoRecolha")) nósPossiveis.add(temp);
				}else nósPossiveis.add(temp);
			}else if (atual.getVeículo().getCargaAtual() == atual.getVeículo().getCargaMáx()){
				if (atual.getVeículo().getGasolinaAtual() == atual.getVeículo().getGasolinaMáx()){
					if (temp.getClass().getSimpleName().equals("PontoEntrega")) nósPossiveis.add(temp);
				}else nósPossiveis.add(temp);
			}
	    }
		
		return nósPossiveis;
	}

	public int distânciaAté(Nó nó1, Nó nó2) {
		if (!nós.containsKey(nó2.getNome())) throw new IllegalArgumentException();
		else if (nó1.equals(nó2)) return 0;
		else return minimumCost(nó1, nó2);
	}
	
	private int minimumCost(Nó nó1, Nó nó2){
		
		ArrayList<Nó> tempNós = new ArrayList<Nó>();

		for (Nó temp : nós.values()) {
			temp.dist = Integer.MAX_VALUE;
			temp.previous = null;
			tempNós.add(temp);
		}
		nó1.dist = 0;
		
		while(!tempNós.isEmpty()){
			Nó temp = getMenorDistancia(nó1, tempNós);
			tempNós.remove(temp);
			
			for (Nó temp_2 : temp.getVizinhos().keySet()) {
				int alt = temp.dist + temp.getVizinhos().get(temp_2);
				if (alt < temp_2.dist){
					temp_2.dist = alt;
					temp_2.previous = temp;
				}
			}
		}
		Nó temp = nó2;
		while(temp.previous != null){
			//System.out.print(temp.getNome() + ">");
			temp = temp.previous;
		}
		//System.out.println(temp.getNome());
		//System.out.println("DISTANCIA = " + nó2.dist);
		return nó2.dist;
	}
	
	private Nó getMenorDistancia(Nó nó, ArrayList<Nó> lista){
		Nó temp = lista.get(0);
		int menorDist = Integer.MAX_VALUE;
		for (int i = 0; i < lista.size(); i++){
			if (menorDist > lista.get(i).dist){
				menorDist = lista.get(i).dist;
				temp = lista.get(i);
			}
		}
		return temp;
	}
	
}
