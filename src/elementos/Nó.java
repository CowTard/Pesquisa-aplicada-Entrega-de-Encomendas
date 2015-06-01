package elementos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Nó {
	
	/*
	 *  INICIO VARIAVEIS DIJKSTRA
	 */
	public int dist = Integer.MAX_VALUE;
	public Nó previous = null;
	
	/*
	 * FIM VARIAVEIS DIJKSTRA
	 */
	
	public int x;
	public int y;
	
	private String nome;
	private Map<Nó, Integer> distanciasMinimas = new HashMap<Nó, Integer>();
	private Map<Nó, Integer> vizinhos = new HashMap<Nó, Integer>();
	private ArrayList<Encomenda> encomendasAqui = new ArrayList<Encomenda>();
	
	public Nó(String nome, int x, int y) {
		this.nome = nome;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Nó nó = (Nó) obj;
		return nome.equals(nó.nome);
	}

	
	public Map<Nó, Integer> getVizinhos() {
		return vizinhos;
	}
	
	
	public void addVizinho(Nó novoVizinho, int distância) {
		vizinhos.put(novoVizinho, distância);
	}
	
	public void addDistânciaMinima(Nó novoVizinho, int distância){
		distanciasMinimas.put(novoVizinho, distância);
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
