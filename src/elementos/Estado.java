package elementos;

import java.util.ArrayList;

public class Estado {
	private No noAtual;
	private Veiculo veiculo;
	private ArrayList<Encomenda> encomendasPorRecolher;
	
	private Estado pai;
	
	private int f, g, h;
	
	public Estado(No noAtual, Veiculo veiculo, ArrayList<Encomenda> encomendasPorRecolher) {
		this.noAtual = noAtual;
		this.veiculo = veiculo;
		this.encomendasPorRecolher = encomendasPorRecolher;
		pai = null;
		f = 0; g = 0; h = 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Estado est = (Estado) obj;
		return (noAtual.getNome().equals(est.noAtual.getNome()) && getVolumeEncomendasPorRecolher() == est.getVolumeEncomendasPorRecolher() && veiculo.getCargaAtual() == est.veiculo.getCargaAtual());
	}
	
	public No getNoAtual() {
		return noAtual;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
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
