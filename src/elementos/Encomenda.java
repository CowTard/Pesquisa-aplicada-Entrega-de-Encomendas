package elementos;

public final class Encomenda {
	private final PontoRecolha origem;
	private final PontoEntrega destino;
	private final int volume;
	
	public Encomenda(PontoRecolha origem, PontoEntrega destino, int volume) {
		this.origem = origem;
		this.destino = destino;
		this.volume = volume;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Encomenda enc = (Encomenda) obj;
		return (destino.equals(enc.destino) && volume == enc.volume);
	}
	
	public PontoEntrega getDestino() {
		return destino;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public PontoRecolha getOrigem(){
		return origem;
	}
}
