package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import algoritmos.Astar;
import elementos.*;

public class Main {
	static Scanner input;
	private static HashMap<String, Nó> grafo;
	
	public static void main(String[] args) throws IOException {
		
		grafo = new HashMap<String, Nó>();
		input = new Scanner(System.in);
		
		System.out.println("# Pesquisa aplicada à entrega de encomendas");
		String path = getInput("Nome do ficheiro de nós: ");
		createNodes(getNodes("./res/mapa_1.txt")); //+ path));
		
		path = getInput("Nome do ficheiro de arestas entre nós: ");
		createConn(getNodes("./res/mapa_1_vz.txt")); // + path));
		
		path = getInput("Nome do ficheiro de encomendas para nós: ");
		createEnco(getNodes("./res/mapa_1_mr.txt")); // + path));
		
		//Astar teste = new Astar(grafo.get("A"), grafo.get("E"));
		//Astar teste = new Astar(new Grafo(grafo));
		
		Grafo grf = new Grafo(grafo);
		ArrayList<Nó> n = grf.obterPossíveisNós(new Estado(grafo.get("A"),new Veículo(100,200),300));
		for (Nó temp : n){
			System.out.println(temp.getNome());
		}
		//teste.executar(inicial, fim);
	}
	
	private static String getInput(String text){
		System.out.print(text);
		return input.nextLine();
	}
	
	private static ArrayList<String> getNodes(String path) throws IOException{
		FileReader map = new FileReader(path);
		BufferedReader buffer = new BufferedReader(map);
		
		ArrayList<String> nodes = new ArrayList<String>();
		
		while(true){
			String linha = buffer.readLine();
			if (linha == null) break;
			else nodes.add(linha);
		}
		buffer.close();
		
		return nodes;
	}
	
	private static void createNodes(ArrayList<String> ficheiro){
		for (int i = 0; i < ficheiro.size(); i++){
			String[] partes = ficheiro.get(i).split("-");

			if (partes[1].equals("PE")) grafo.put(partes[0] ,new PontoEntrega(partes[0]));
			else if (partes[1].equals("PA")) grafo.put(partes[0] ,new PontoAbastecimento(partes[0]));
			else grafo.put(partes[0] ,new PontoRecolha(partes[0]));
		}
	}
	
	private static void createConn(ArrayList<String> ficheiro){
		for (int i = 0; i < ficheiro.size(); i++){
			String[] partes = ficheiro.get(i).split("-");
			
			grafo.get(partes[0]).addVizinho(grafo.get(partes[1]), Integer.parseInt(partes[2]));
			grafo.get(partes[1]).addVizinho(grafo.get(partes[0]), Integer.parseInt(partes[2]));
			
		}
	}
	
	private static void createEnco(ArrayList<String> ficheiro){
		for (int i = 0; i < ficheiro.size(); i++){
			String[] partes = ficheiro.get(i).split("-");

			grafo.get(partes[0]).addEncomenda(new Encomenda(new PontoRecolha(partes[1]), Integer.parseInt(partes[2])));
		}
	}
}
