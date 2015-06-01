package elementos;

import java.util.ArrayList;

public class Estado {
	private Nó nóAtual;
	private Veículo veículo;
	private ArrayList<Encomenda> encomendasPorRecolher;
	
	private Estado pai;
	
	private int f, g, h;
	
	public Estado(Nó nóAtual, Veículo veículo, ArrayList<Encomenda> encomendasPorRecolher) {
		this.nóAtual = nóAtual;
		this.veículo = veículo;
		this.encomendasPorRecolher = encomendasPorRecolher;
		pai = null;
		f = 0; g = 0; h = 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Estado est = (Estado) obj;
		return (nóAtual.getNome().equals(est.nóAtual.getNome()) && getVolumeEncomendasPorRecolher() == est.getVolumeEncomendasPorRecolher() && veículo.getCargaAtual() == est.veículo.getCargaAtual());
	}
	
	public Nó getNóAtual() {
		return nóAtual;
	}
	
	public Veículo getVeículo() {
		return veículo;
	}
	
	public ArrayList<Encomenda> getEncomendasPorRecolher() {
		return encomendasPorRecolher;
	}
	
	public int getVolumeEncomendasPorRecolher() {
		int volume = 0;
		for (Encomenda temp: encomendasPorRecolher)
			volume += temp.getVolume();
		return volume;
	}
	
	public Estado getPai() {
		return pai;
	}
	
	public void setPai(Estado pai) {
		this.pai = pai;
	}
	
	public int getF() {
		return f;
	}
	
	public void setF(int f) {
		this.f = f;
	}
	
	public int getG() {
		return g;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public int getH() {
		return h;
	}
	
	public void setH(int h) {
		this.h = h;
	}
}
