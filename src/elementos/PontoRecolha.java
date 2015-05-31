package elementos;

import java.util.ArrayList;

public class PontoRecolha extends Nó {
	private ArrayList<Encomenda> encomendas = new ArrayList<Encomenda>();
	
	public PontoRecolha(String nome) {
		super(nome);
	}
	
	public ArrayList<Encomenda> getEncomendas() {
		return encomendas;
	}
	
	/*
	public void addEncomenda(Encomenda enc) {
		encomendas.add(enc);
	}
	
	public void remEncomenda(Encomenda enc) {
		if (!encomendas.contains(enc)) throw new IllegalArgumentException();
		encomendas.remove(enc);
	}*/
}
