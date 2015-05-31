package elementos;

public class Estado {
	public Nó nóAtual;
	public Veículo veículo;
	public int encomendasPorEntregar;
	
	public Estado pai;
	
	public int f, g, h;
	
	public Estado(Nó nóAtual, Veículo veículo, int encomendasPorEntregar) {
		this.nóAtual = nóAtual;
		this.veículo = veículo;
		this.encomendasPorEntregar = encomendasPorEntregar;
		pai = null;
		f = 0; g = 0; h = 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Estado est = (Estado) obj;
		return (nóAtual.getNome().equals(est.nóAtual.getNome()) && encomendasPorEntregar == est.encomendasPorEntregar);
	}
}
