package elementos;

import java.util.ArrayList;

public class Veiculo {
	private float gasolinaMáx;
	private float gasolinaAtual;
	private int cargaMáx;
	private int cargaAtual;
	ArrayList<Encomenda> encomendas;
	
	public Veiculo(float gasolinaMáx, int cargaMáx) {
		this.gasolinaMáx = gasolinaMáx;
		this.cargaMáx = cargaMáx;
		gasolinaAtual = gasolinaMáx;
		cargaAtual = 0;
		encomendas = new ArrayList<Encomenda>();
	}
	
	public Veiculo(Veiculo veiculo) {
		this.gasolinaMáx = veiculo.gasolinaMáx;
		this.cargaMáx = veiculo.cargaMáx;
		gasolinaAtual = veiculo.gasolinaAtual;
		cargaAtual = veiculo.cargaAtual;
		encomendas = new ArrayList<Encomenda>(veiculo.encomendas);
	}

	public void encherDeposito() {
		gasolinaAtual = gasolinaMáx;
	}

	public void gastarGasolina(double d) {
		gasolinaAtual -= d;
	}

	public void addEncomenda(Encomenda enc) {
		int volumeEncomenda = enc.getVolume();
		if (cargaAtual + volumeEncomenda > cargaMáx) throw new IllegalArgumentException();
		encomendas.add(enc);
		cargaAtual += volumeEncomenda;
	}

	public void decCargaEncomenda(Encomenda enc) {
		int volumeEncomenda = enc.getVolume();
		if (cargaAtual - volumeEncomenda < 0 || !encomendas.contains(enc)) throw new IllegalArgumentException();
		cargaAtual -= volumeEncomenda;
	}
	
	public float getGasolinaMáx() {
		return gasolinaMáx;
	}

	public void setGasolinaMáx(float gasolinaMáx) {this.gasolinaMáx = gasolinaMáx;}
	
	public float getGasolinaAtual() {
		return gasolinaAtual;
	}
	
	public int getCargaMáx() {
		return cargaMáx;
	}

	public void setCargaMáx(int cargaMáx) {this.cargaMáx = cargaMáx;}
	
	public int getCargaAtual() {
		return cargaAtual;
	}
	
	public ArrayList<Encomenda> getEncomendas() {
		return encomendas;
	}
}
