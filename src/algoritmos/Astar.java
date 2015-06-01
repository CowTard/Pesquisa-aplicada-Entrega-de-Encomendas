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
		
		inicial.setF(0);
		listaAberta.add(inicial);
		
		while (!listaAberta.isEmpty()) {
			Estado menorF = listaAberta.get(0);
			for (Estado est : listaAberta)
				if (est.getF() < menorF.getF()) menorF = est;
			listaAberta.remove(menorF);
			
			ArrayList<Estado> sucessores = sucessores(menorF);
			
			pesqSucessores:
			for (Estado sucessor : sucessores) {
				if (sucessor.getVolumeEncomendasPorRecolher() == 0 && sucessor.getVeículo().getCargaAtual() == 0) return construirCaminhoAPartirDe(sucessor);
				sucessor.setG(menorF.getG() + grafo.distânciaAté(menorF.getNóAtual(), sucessor.getNóAtual()));
				sucessor.setH(heurísticaCusto(sucessor));
				sucessor.setF(sucessor.getG() + sucessor.getH());
				
				for (Estado estAberto : listaAberta)
					if (estAberto.equals(sucessor) && estAberto.getF() < sucessor.getF()) continue pesqSucessores;
				for (Estado estFechado : listaFechada)
					if (estFechado.equals(sucessor) && estFechado.getF() < sucessor.getF()) continue pesqSucessores;
				
				listaAberta.add(sucessor);
			}
			
			listaFechada.add(menorF);
		}
		
		return null;
	}
	
	private int heurísticaCusto(Estado estado) {
		if (!estado.getEncomendasPorRecolher().isEmpty()) {
			Nó origem = estado.getEncomendasPorRecolher().get(0).getOrigem();
			int distânciaAtualARecolha = grafo.distânciaAté(estado.getNóAtual(), origem);
			int distânciaOrigemADestino = grafo.distânciaAté(origem, estado.getEncomendasPorRecolher().get(0).getDestino());
		
			return distânciaAtualARecolha + distânciaOrigemADestino;
		} else {
			Nó destino = estado.getVeículo().getEncomendas().get(0).getDestino();
			return grafo.distânciaAté(estado.getNóAtual(), destino);
		}
	}
	
	private ArrayList<Estado> sucessores(Estado estado) {
		ArrayList<Nó> nósSucessores = grafo.obterPossíveisNós(estado);
		ArrayList<Estado> estadosSucessores = new ArrayList<Estado>();
		
		for (Nó sucessor : nósSucessores) {
			Estado novoEstado = new Estado(sucessor, new Veículo(estado.getVeículo()), new ArrayList<Encomenda>(estado.getEncomendasPorRecolher()));
			
			novoEstado.getVeículo().gastarGasolina(0/*TODO: grafo.distânciaAté(estado.nóAtual, sucessor) * .08*/);

			if (sucessor.getClass().getSimpleName().equals("PontoEntrega")) { // Descarregar veículo
				Iterator<Encomenda> itEncsVeículo = novoEstado.getVeículo().getEncomendas().iterator();
				while (itEncsVeículo.hasNext()) {
					Encomenda enc = itEncsVeículo.next();
					if (enc.getDestino().equals(sucessor)) {
						novoEstado.getVeículo().remEncomenda(enc);
						itEncsVeículo.remove();
						//System.out.println("Encomendas por entregar:" + novoEstado.encomendasPorEntregar);
					}
				}
			}
			else if (sucessor.getClass().getSimpleName().equals("PontoRecolha")) { // Carregar veículo. TODO: só carregar o que conseguir
				Iterator<Encomenda> itEncsNó = sucessor.getEncomendasDaqui().iterator();
				while (itEncsNó.hasNext()) {
					Encomenda enc = itEncsNó.next();
					if (!novoEstado.getEncomendasPorRecolher().contains(enc)) continue;
					novoEstado.getEncomendasPorRecolher().remove(enc);
					novoEstado.getVeículo().addEncomenda(enc);
				}
			}
			else { // TODO: Abastecer veículo
			}
			
			novoEstado.setPai(estado);
			
			estadosSucessores.add(novoEstado);
		}
		
		return estadosSucessores;
	}
	
	private ArrayList<Estado> construirCaminhoAPartirDe(Estado fim) {
		ArrayList<Estado> caminho = new ArrayList<Estado>();
		
		Estado atual = fim;
		
		while (atual.getPai() != null) {
			caminho.add(0, atual);
			atual = atual.getPai();
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
