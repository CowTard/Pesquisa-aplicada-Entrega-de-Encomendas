package elementos;

import java.util.ArrayList;

public class Veículo {
	private final float gasolinaMáx;
	private float gasolinaAtual;
	private final int cargaMáx;
	private int cargaAtual;
	ArrayList<Encomenda> encomendas;
	
	public Veículo(float gasolinaMáx, int cargaMáx) {		
		this.gasolinaMáx = gasolinaMáx;
		this.cargaMáx = cargaMáx;
		gasolinaAtual = 0;
		cargaAtual = 0;
		encomendas = new ArrayList<Encomenda>();
	}
	
	public void encherDepósito() {
		gasolinaAtual = gasolinaMáx;
	}
	
	public void addEncomenda(Encomenda enc) {
		int volumeEncomenda = enc.getVolume();
		/*if (cargaAtual + volumeEncomenda > cargaMáx) throw new IllegalArgumentException();*/ // TODO: Descomentar
		encomendas.add(enc);
		cargaAtual += volumeEncomenda;
	}
	
	public void remEncomenda(Encomenda enc) {
		int volumeEncomenda = enc.getVolume();
		/*if (cargaAtual - volumeEncomenda < 0 || !encomendas.contains(enc)) throw new IllegalArgumentException();*/ // TODO: Descomentar
		encomendas.remove(enc);
		cargaAtual -= volumeEncomenda;
	}
	
	public void remEncomendas(ArrayList<Encomenda> encs) {
		for (Encomenda enc : encs)
			remEncomenda(enc);
	}
	
	public float getGasolinaMáx() {
		return gasolinaMáx;
	}
	
	public float getGasolinaAtual() {
		return gasolinaAtual;
	}
	
	public float getCargaMáx() {
		return cargaMáx;
	}
	
	public float getCargaAtual() {
		return cargaAtual;
	}
	
	public ArrayList<Encomenda> getEncomendas() {
		return encomendas;
	}
}
