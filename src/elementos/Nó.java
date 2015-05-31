package elementos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Nó implements Comparable<Nó> {
	
	/*
	 *  INICIO VARIAVEIS DIJKSTRA
	 */
	public int dist = Integer.MAX_VALUE;
	public Nó previous = null;
	
	private String nome;
	private double f = 0, g = 0, h = 0;
	private Map<Nó, Integer> vizinhos = new HashMap<Nó, Integer>();
	private ArrayList<Encomenda> encomendasAqui = new ArrayList<Encomenda>();
	private Nó nóPai;
	
	
	public Nó(String nome) {
		this.nome = nome;
		this.nóPai = null;
	}

	@Override
	public int compareTo(Nó nó2) { // Compara dois nós usando o seu valor f
		return (f < nó2.f) ? -1 : (f == nó2.f) ? 0 : 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Nó nó = (Nó) obj;
		return nome.equals(nó.nome);
	}

	public ArrayList<Nó> getVizinhos() {
		Set<Nó> nósSet = vizinhos.keySet();
		ArrayList<Nó> nós = new ArrayList<Nó>();
		nós.addAll(nósSet);
		return nós;
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

	public int distânciaAté(HashMap<String, Nó> grafo, Nó nó2) throws IllegalArgumentException {
		if (!grafo.containsKey(nó2.getNome())) throw new IllegalArgumentException();
		else if (vizinhos.containsKey(nó2)) return vizinhos.get(nó2);
		else return minimumCost(grafo, nó2);
	}
	
	public int minimumCost(HashMap<String,Nó> graph, Nó nó2){
		ArrayList<Nó> tempNós = new ArrayList<Nó>();

		for (Nó temp : graph.values()){
			temp.dist = Integer.MAX_VALUE;
			temp.previous = null;
			tempNós.add(temp);
		}
		this.dist = 0;
		
		while(!tempNós.isEmpty()){
			Nó temp = getMenorDistancia(tempNós);
			tempNós.remove(temp);
			
			for (Nó temp_2 : temp.getVizinhos()){
				int alt = temp.dist + temp.vizinhos.get(temp_2);
				if (alt < temp_2.dist){
					temp_2.dist = alt;
					temp_2.previous = temp;
				}
			}
		}
		Nó temp = nó2;
		while(temp.previous != null){
			System.out.print(temp.getNome() + ">");
			temp = temp.previous;
		}
		System.out.println(temp.getNome());
		System.out.println("DISTANCIA = " + nó2.dist);
		return nó2.dist;
	}
	
	public Nó getMenorDistancia(ArrayList<Nó> lista){
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
	
	public String getNome() {
		return nome;
	}

	public double getF() {
		return f;
	}

	public double getG() {
		return g;
	}

	public double getH() {
		return h;
	}
	
	public Nó getPai(){
		return this.nóPai;
	}

	public void setF(double f) {
		this.f = f;
	}

	public void setG(double g) {
		this.g = g;
	}

	public void setH(double h) {
		this.h = h;
	}
	
	public void setPai(Nó pai){
		this.nóPai = pai;
	}
}
