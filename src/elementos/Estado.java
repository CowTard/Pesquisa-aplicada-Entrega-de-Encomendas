package elementos;

import java.util.ArrayList;

public class Estado {
	public Nó nóAtual;
	public Veículo veículo;
	public ArrayList<Encomenda> encomendasPorRecolher;
	
	public Estado pai;
	
	public double f, g, h;
	
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
		return (nóAtual.getNome().equals(est.nóAtual.getNome()) && getVolumeEncs() == est.getVolumeEncs());
	}
	
	public int getVolumeEncs(){
		int volume = 0;
		for (Encomenda temp: encomendasPorRecolher)
			volume += temp.getVolume();
		return volume;
	}
}
