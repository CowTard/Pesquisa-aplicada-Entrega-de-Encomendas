package algoritmos;

import java.util.ArrayList;
import java.util.Iterator;

import elementos.Encomenda;
import elementos.Estado;
import elementos.Grafo;
import elementos.Nó;
import elementos.Veículo;

public class Astar {
	private Grafo grafo;
	
	public Astar(Grafo grafo) {
		this.grafo = grafo;
		préMapeamento();
	}
	
	public ArrayList<Estado> executar(Estado inicial, Estado fim) {
		ArrayList<Estado> listaAberta = new ArrayList<Estado>();
		ArrayList<Estado> listaFechada = new ArrayList<Estado>();
		
		inicial.f = 0;
		listaAberta.add(inicial);
		
		while (!listaAberta.isEmpty()) {
			Estado menorF = listaAberta.get(0);
			for (Estado est : listaAberta)
				if (est.f < menorF.f) menorF = est;
			//System.out.println(menorF.nóAtual.getNome());
			listaAberta.remove(menorF);
			
			ArrayList<Estado> sucessores = sucessores(menorF);
			
			//System.out.print(menorF.nóAtual.getNome() + "> ");
			//for(int i = 0; i < sucessores.size(); i++)
				//System.out.print(sucessores.get(i).nóAtual.getNome());
			
			//System.out.println();
			pesqSucessores:
			for (Estado sucessor : sucessores) {
				if (300-sucessor.getVolumeEncs()-sucessor.veículo.getCargaAtual() == 0) return construirCaminhoAPartirDe(sucessor); // Se chegou ao estado final
				System.out.println("Encomendas: " + sucessor.getVolumeEncs());
				sucessor.g = menorF.g + grafo.distânciaAté(menorF.nóAtual, sucessor.nóAtual);
				sucessor.h = heurísticaCusto(sucessor);
				sucessor.f = sucessor.g + sucessor.h;
				
				for (Estado estAberto : listaAberta)
					if (estAberto.equals(sucessor) && estAberto.f < sucessor.f) continue pesqSucessores;
				for (Estado estFechado : listaFechada)
					if (estFechado.equals(sucessor) && estFechado.f < sucessor.f) continue pesqSucessores;
				
				listaAberta.add(sucessor);
			}
			
			listaFechada.add(menorF);
			listaAberta.remove(menorF);
		}
		
		return null;
	}
	
	private double heurísticaCusto(Estado estado){
		
		Nó origem = estado.encomendasPorRecolher.get(0).getOrigem();
		int distanciaActualArecolha = grafo.distânciaAté(estado.nóAtual, origem);
		int distanciaOrigemADestino = grafo.distânciaAté(origem, estado.encomendasPorRecolher.get(0).getDestino());
		
		return distanciaActualArecolha + distanciaOrigemADestino;
	}
	
	private ArrayList<Estado> sucessores(Estado estado) {
		ArrayList<Nó> nósSucessores = grafo.obterPossíveisNós(estado);
		ArrayList<Estado> estadosSucessores = new ArrayList<Estado>();
		
		for (Nó sucessor : nósSucessores) {
			Estado novoEstado = new Estado(sucessor, new Veículo(estado.veículo), new ArrayList<Encomenda>(estado.encomendasPorRecolher));
			
			novoEstado.veículo.gastarGasolina(0/*TODO: grafo.distânciaAté(estado.nóAtual, sucessor) * .08*/);

			if (sucessor.getClass().getSimpleName().equals("PontoEntrega")) { // Descarregar veículo
				Iterator<Encomenda> itEncsVeículo = novoEstado.veículo.getEncomendas().iterator();
				while (itEncsVeículo.hasNext()) {
					Encomenda enc = itEncsVeículo.next();
					if (enc.getDestino().equals(sucessor)) {
						novoEstado.veículo.remEncomenda(enc);
						itEncsVeículo.remove();
						//System.out.println("Encomendas por entregar:" + novoEstado.encomendasPorEntregar);
					}
				}
			}
			else if (sucessor.getClass().getSimpleName().equals("PontoRecolha")) { // Carregar veículo. TODO: só carregar o que conseguir
				Iterator<Encomenda> itEncsNó = sucessor.getEncomendasDaqui().iterator();
				while (itEncsNó.hasNext()) {
					Encomenda enc = itEncsNó.next();
					novoEstado.encomendasPorRecolher.remove(enc);
					novoEstado.veículo.addEncomenda(enc);
					//itEncsNó.remove(); // TODO: verificar se está mesmo a remover encomendas dos nós do grafo
				}
			}
			else { // TODO: Abastecer veículo
			}
			
			novoEstado.pai = estado;
			
			estadosSucessores.add(novoEstado);
		}
		
		return estadosSucessores;
	}
	
	private ArrayList<Estado> construirCaminhoAPartirDe(Estado fim) {
		ArrayList<Estado> caminho = new ArrayList<Estado>();
		
		Estado atual = fim;
		
		while (atual.pai != null) {
			caminho.add(0, atual);
			atual = atual.pai;
		}
		caminho.add(0,atual);
		return caminho;
	}

	private void préMapeamento(){
		
		for(Nó temp : grafo.getNós().values()){
			for (Nó temp_1 : grafo.getNós().values()){
				int distance = grafo.distânciaAté(temp, temp_1);
				temp.addDistânciaMinima(temp,distance);
				temp_1.addDistânciaMinima(temp_1,distance);
			}
		}
	}
}
