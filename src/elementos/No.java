package elementos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class No {
	
	/*
	 *  INICIO VARIAVEIS DIJKSTRA
	 */
	public int dist = Integer.MAX_VALUE;
	public No previous = null;
	
	/*
	 * FIM VARIAVEIS DIJKSTRA
	 */


	/*
     *  PARA EFEITOS DE REPRESENTAÇÃO GRÁFICA
     */
	public int posX;
	public int posY;
	public boolean noCaminho;

    /*
	 * FIM VARIAVEIS REPRESENTAÇÃO GRÁFICA
	 */
	
	private String nome;
	private Map<No, Integer> distanciasMinimas = new HashMap<No, Integer>();
	private Map<No, Integer> vizinhos = new HashMap<No, Integer>();
	private ArrayList<Encomenda> encomendasAqui = new ArrayList<Encomenda>();
	private HashMap<String, Aresta> mapaCaminhos = new HashMap<String, Aresta>();
	
	public No(String nome, int posX, int posY) {
		this.nome = nome;
		this.posX = posX;
		this.posY = posY;
		setNoCaminho(false);
	}

    public No(String nome) {
        this.nome = nome;
    }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		No no = (No) obj;
		return nome.equals(no.nome);
	}

	
	public Map<No, Integer> getVizinhos() {
		return vizinhos;
	}
	
	
	public void addVizinho(No novoVizinho, int distância) {
		vizinhos.put(novoVizinho, distância);
	}
	
	public void addDistânciaMinima(No novoVizinho, int distância){
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

	public boolean addAresta(Aresta a) {
		if (mapaCaminhos.get(a.getNomeCaminho()) != null)
			return false;
		else
			mapaCaminhos.put(a.getNomeCaminho(), a);
		return true;
	}

	public boolean removeAresta(Aresta a) {
		if (mapaCaminhos.containsKey(a.getNomeCaminho())) {
			mapaCaminhos.remove(a.getNomeCaminho());
			return true;
		}
		return false;
	}

	public Aresta arestaEmNo(String _nomeNo) {
		return mapaCaminhos.get(_nomeNo);
	}

	public HashMap<String, Aresta> getmapaCaminhos() {
		return this.mapaCaminhos;
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public void setX(int posX) {
		this.posX = posX;
	}

	public void setY(int posY) {
		this.posY = posY;
	}

	public boolean noCaminho() {
		return noCaminho;
	}

	public void setNoCaminho(boolean noCaminho) {
		this.noCaminho = noCaminho;

		if (!noCaminho) {
			ArrayList<Aresta> arestas = new ArrayList<Aresta>(
					mapaCaminhos.values());

			for (Aresta a : arestas) {
				a.setNoCaminho(noCaminho);
			}
		}
	}

}
