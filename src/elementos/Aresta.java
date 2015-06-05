package elementos;

public class Aresta {
    private String nomeCaminho;
    private double custo;
    private No noOrigem;
    private No noDestino;
    private boolean noCaminho;

    public Aresta(String nomeCaminho, double custo, No noOrigem, No noDestino) {
        this.nomeCaminho = nomeCaminho;
        this.custo = custo;
        this.noOrigem = noOrigem;
        this.noDestino = noDestino;
        setNoCaminho(false);
    }

    public Aresta(String nomeCaminho) {
        this.nomeCaminho = nomeCaminho;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double distancia) {
        this.custo = custo;
    }

    public No getNoOrigem() {
        return noOrigem;
    }

    public void setNoOrigem(No noOrigem) {
        this.noOrigem = noOrigem;
    }

    public No getNoDestino() {
        return noDestino;
    }

    public void setNoDestino(No noDestino) {
        this.noDestino = noDestino;
    }

    public String getNomeCaminho() {
        return nomeCaminho;
    }

    public void setNomeCaminho(String nomeCaminho) {
        this.nomeCaminho = nomeCaminho;
    }

    public boolean equals(Aresta ar){
        return (this.nomeCaminho.equals(ar.nomeCaminho));
    }

    public boolean noCaminho() {
        return noCaminho;
    }

    public void setNoCaminho(boolean noCaminho) {
        this.noCaminho = noCaminho;
    }
}
