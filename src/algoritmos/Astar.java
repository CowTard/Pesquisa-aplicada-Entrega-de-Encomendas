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
			
			listaAberta.remove(menorF);
			
			ArrayList<Estado> sucessores = sucessores(menorF);
			
			pesqSucessores:
			for (Estado sucessor : sucessores) {
				if (sucessor.encomendasPorEntregar == 0) return construirCaminhoAPartirDe(sucessor); // Se chegou ao estado final
				
				sucessor.g = menorF.g + heurísticaCusto(menorF, sucessor);
				sucessor.h = heurísticaCusto(sucessor, fim);
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
	
	private int heurísticaCusto(Estado estadoDe, Estado estadoPara) {
		return (estadoPara.encomendasPorEntregar - estadoDe.encomendasPorEntregar) * 10 + grafo.distânciaAté(estadoDe.nóAtual, estadoPara.nóAtual);
	}
	
	private ArrayList<Estado> sucessores(Estado estado) {
		ArrayList<Nó> nósSucessores = grafo.obterPossíveisNós(estado);
		ArrayList<Estado> estadosSucessores = new ArrayList<Estado>();
		
		for (Nó sucessor : nósSucessores) {
			Estado novoEstado = new Estado(sucessor, new Veículo(estado.veículo), estado.encomendasPorEntregar);
			
			novoEstado.veículo.gastarGasolina(0/*TODO: grafo.distânciaAté(estado.nóAtual, sucessor) * .08*/);
			
			if (sucessor.getClass().getSimpleName().equals("PontoEntrega")) { // Descarregar veículo
				Iterator<Encomenda> itEncsVeículo = novoEstado.veículo.getEncomendas().iterator();
				while (itEncsVeículo.hasNext()) {
					Encomenda enc = itEncsVeículo.next();
					if (enc.getDestino().equals(sucessor)) {
						novoEstado.veículo.remEncomenda(enc);
						itEncsVeículo.remove();
						novoEstado.encomendasPorEntregar -= enc.getVolume();
					}
				}
			}
			else if (sucessor.getClass().getSimpleName().equals("PontoRecolha")) { // Carregar veículo. TODO: só carregar o que conseguir
				Iterator<Encomenda> itEncsNó = sucessor.getEncomendasDaqui().iterator();
				while (itEncsNó.hasNext()) {
					Encomenda enc = itEncsNó.next();
					novoEstado.veículo.addEncomenda(enc);
					itEncsNó.remove(); // TODO: verificar se está mesmo a remover encomendas dos nós do grafo
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
		
		return caminho;
	}
}
