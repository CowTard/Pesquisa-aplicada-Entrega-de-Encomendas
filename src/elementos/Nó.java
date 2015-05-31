package elementos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Nó implements Comparable<Nó> {
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
	/*
	public Map<Nó, Integer> getVizinhos() {
		return vizinhos;
	}
	*/
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

	public int distânciaAté(Nó nó2) throws IllegalArgumentException {
		if (!vizinhos.containsKey(nó2)) throw new IllegalArgumentException();
		return vizinhos.get(nó2);
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
