package elementos;

public class Encomenda {
	private PontoRecolha destino;
	private int volume;
	
	public Encomenda(PontoRecolha destino, int volume) {
		this.destino = destino;
		this.volume = volume;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Encomenda enc = (Encomenda) obj;
		return (destino.equals(enc.destino) && volume == enc.volume);
	}
	
	public PontoRecolha getDestino() {
		return destino;
	}
	
	public int getVolume() {
		return volume;
	}
}
