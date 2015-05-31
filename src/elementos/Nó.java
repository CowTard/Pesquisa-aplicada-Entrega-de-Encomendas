package elementos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Nó implements Comparable<Nó> {
	
	/*
	 *  INICIO VARIAVEIS DIJKSTRA
	 */
	public int dist = Integer.MAX_VALUE;
	public Nó previous = null;
	
	private String nome;
	private Map<Nó, Integer> vizinhos = new HashMap<Nó, Integer>();
	private ArrayList<Encomenda> encomendasAqui = new ArrayList<Encomenda>();
	
	public Nó(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Nó nó = (Nó) obj;
		return nome.equals(nó.nome);
	}

	/*
	public ArrayList<Nó> getVizinhos() {
		Set<Nó> nósSet = vizinhos.keySet();
		ArrayList<Nó> nós = new ArrayList<Nó>();
		nós.addAll(nósSet);
		return nós;
	}
	*/
	
	public Map<Nó, Integer> getVizinhos() {
		return vizinhos;
	}
	
	public void addVizinho(Nó novoVizinho, int distância) {
		vizinhos.put(novoVizinho, distância);
	}
	
	public ArrayList<Encomenda> getEncomendasDaqui() {
		return encomendasAqui;
	}
	
	public void addEncomenda(Encomenda enc) {
		encomendasAqui.add(enc);
	}
	
	public void remEncomenda(Encomenda enc) {
		encomendasAqui.remove(enc);
	}
	
	public void remEncomendas(ArrayList<Encomenda> encs) {
		for (Encomenda enc : encs)
			encomendasAqui.remove(enc);
	}
	
	public String getNome() {
		return nome;
	}
}
