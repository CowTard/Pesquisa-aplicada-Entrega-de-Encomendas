package elementos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class Grafo {
	private HashMap<String, No> nos;
	private Veiculo veiculo;
	
	public Grafo(HashMap<String, No> nos){
		this.nos = nos;
		veiculo = new Veiculo(80,200);
	}

	public Grafo(){
		nos = new HashMap<String, No>();
		veiculo = new Veiculo(80,200);
	}
	
	public ArrayList<Encomenda> encomendasAEntregar(No no) {
		ArrayList<Encomenda> encomendasAEntregar = new ArrayList<Encomenda>();
		
		for (No cadaNo : nos.values()) {
			if (cadaNo.equals(no)) continue;
			
			for (Encomenda enc : cadaNo.getEncomendasDaqui()) {
				if (enc.getDestino().equals(no)) encomendasAEntregar.add(enc);
			}
		}
		
		return encomendasAEntregar;
	}
	
	public HashMap<String, No> getNos() {
		return nos;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}

	public ArrayList<No> obterNosPossiveis(Estado atual) {
		ArrayList<No> nosPossiveis = new ArrayList<No>();

		for (No temp : nos.values()){
			if (temp.equals(atual.getNoAtual())) continue;
			else {
				int cargaAtual = atual.getVeiculo().getCargaAtual();
				int cargaMáx = atual.getVeiculo().getCargaMáx();
				float gasolinaAtual = atual.getVeiculo().getGasolinaAtual();
				float gasolinaMáx = atual.getVeiculo().getGasolinaMáx();

				if (cargaAtual == 0 && temp.getClass().getSimpleName().equals("PontoEntrega")) continue;

				int cargaPossivel = cargaMáx - cargaAtual;
				int encomendasPossiveis = 0;

				for(int i= 0; i < temp.getEncomendasDaqui().size();i++){
					if ( temp.getEncomendasDaqui().get(i).getVolume() <= cargaPossivel) {
						encomendasPossiveis++;
						break;
					}
				}
				if (temp.getClass().getSimpleName().equals("PontoRecolha") && encomendasPossiveis == 0) continue;
				if (gasolinaAtual - distânciaAté(atual.getNoAtual(), temp)*0.08 <= 0) continue;

				nosPossiveis.add(temp);
			}
		}

		return nosPossiveis;
	}

	public int distânciaAté(No no1, No no2) {
		if (!nos.containsKey(no2.getNome())) throw new IllegalArgumentException();
		else if (no1.equals(no2)) return 0;
		else return minimumCost(no1, no2);
	}

	private int minimumCost(No no1, No no2){

		ArrayList<No> tempNos = new ArrayList<No>();

		for (No temp : nos.values()) {
			temp.dist = Integer.MAX_VALUE;
			temp.previous = null;
			tempNos.add(temp);
		}
		no1.dist = 0;

		while(!tempNos.isEmpty()){
			No temp = getMenorDistancia(no1, tempNos);
			tempNos.remove(temp);

			for (No temp_2 : temp.getVizinhos().keySet()) {
				int alt = temp.dist + temp.getVizinhos().get(temp_2);
				if (alt < temp_2.dist){
					temp_2.dist = alt;
					temp_2.previous = temp;
				}
			}
		}
		return no2.dist;
	}
	
	private No getMenorDistancia(No no, ArrayList<No> lista){
		No temp = lista.get(0);
		int menorDist = Integer.MAX_VALUE;
		for (int i = 0; i < lista.size(); i++){
			if (menorDist > lista.get(i).dist){
				menorDist = lista.get(i).dist;
				temp = lista.get(i);
			}
		}
		return temp;
	}

	public No getNo(String n) {
		return nos.get(n);
	}

	public boolean addNo(No n) {
		if (nos.get(n.getNome()) != null)
			return false;
		else
			nos.put(n.getNome(), n);
		return true;
	}

	public boolean removeNo(No n) {
		if (nos.get(n.getNome()) != null) {
			nos.remove(n.getNome());
			return true;
		}
		return false;
	}

	public boolean removeNoNome(String n) {
		if (nos.get(n) != null) {
			nos.remove(n);
			return true;
		}
		return false;
	}

	private static ArrayList<String> getNodes(String path) throws IOException {
		ArrayList<String> nodes = new ArrayList<String>();
		File file = new File(path);

		if(file.exists()){
			try {
				nodes = (ArrayList<String>) Files.readAllLines(file.toPath(), Charset.defaultCharset());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return nodes;
	}


	private void createNodes(ArrayList<String> ficheiro){
		for (int i = 0; i < ficheiro.size(); i++){
			String[] partes = ficheiro.get(i).split("-");

			if (partes[1].equals("PE")) nos.put(partes[0], new PontoEntrega(partes[0],Integer.parseInt(partes[2]),Integer.parseInt(partes[3])));
			else if (partes[1].equals("PA")) nos.put(partes[0], new PontoAbastecimento(partes[0],Integer.parseInt(partes[2]),Integer.parseInt(partes[3])));
			else nos.put(partes[0], new PontoRecolha(partes[0],Integer.parseInt(partes[2]),Integer.parseInt(partes[3])));
		}
	}

	private void createConn(ArrayList<String> ficheiro){
		for (int i = 0; i < ficheiro.size(); i++){
			String[] partes = ficheiro.get(i).split("-");
			nos.get(partes[0]).addVizinho(nos.get(partes[1]), Integer.parseInt(partes[2]));
			nos.get(partes[1]).addVizinho(nos.get(partes[0]), Integer.parseInt(partes[2]));

			Aresta a = new Aresta(partes[0]+"-"+partes[1], Integer.parseInt(partes[2]), nos.get(partes[0]), nos.get(partes[1]));
			Aresta a2 = new Aresta(partes[1]+"-"+partes[0], Integer.parseInt(partes[2]),nos.get(partes[1]),nos.get(partes[0]));
			nos.get(partes[0]).addAresta(a);
			nos.get(partes[1]).addAresta(a2);
		}
	}

	public void lerGrafo(String nome) throws IOException {
		createNodes(getNodes("./res/" + nome));
		createConn(getNodes("./res/" + "mapa_1_vz.txt"));
	}

    public ArrayList<Encomenda> lerInventario(String nome) throws IOException {
        ArrayList<Encomenda> encs = new ArrayList<Encomenda>();
		ArrayList<String> nodes = new ArrayList<String>();
		FileReader map = new FileReader("./res/mapa_1_mr.txt");
		BufferedReader buffer = new BufferedReader(map);

		while(true){
			String linha = buffer.readLine();
			if (linha == null) break;
			else nodes.add(linha);
		}
		buffer.close();

        for (int i = 0; i < nodes.size(); i++){
            String[] partes = nodes.get(i).split("-");
            Encomenda enc = new Encomenda((PontoRecolha) nos.get(partes[0]), (PontoEntrega) nos.get(partes[1]), Integer.parseInt(partes[2]));
            encs.add(enc);
            nos.get(partes[0]).addEncomenda(enc);
        }

        return encs;
    }

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

}

