package algoritmos;

import elementos.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Astar {
	private Grafo grafo;
	
	public Astar(Grafo grafo) {
		this.grafo = grafo;
		préMapeamento();
	}
	
	public ArrayList<Estado> executar(Estado inicial) {
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
				if (sucessor.getVolumeEncomendasPorRecolher() == 0 && sucessor.getVeiculo().getCargaAtual() == 0) return construirCaminhoAPartirDe(sucessor);
				sucessor.setG(menorF.getG() + grafo.distânciaAté(menorF.getNoAtual(), sucessor.getNoAtual()));
				sucessor.setH(heuristicaCusto(sucessor));
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
	
	private int heuristicaCusto(Estado estado) {
		if (!estado.getEncomendasPorRecolher().isEmpty()) {
			No origem = estado.getEncomendasPorRecolher().get(0).getOrigem();
			int distânciaAtualARecolha = grafo.distânciaAté(estado.getNoAtual(), origem);
			int distânciaOrigemADestino = grafo.distânciaAté(origem, estado.getEncomendasPorRecolher().get(0).getDestino());
		
			return distânciaAtualARecolha + distânciaOrigemADestino;
		} else {
			No destino = estado.getVeiculo().getEncomendas().get(0).getDestino();
			return grafo.distânciaAté(estado.getNoAtual(), destino);
		}
	}
	
	private ArrayList<Estado> sucessores(Estado estado) {
		ArrayList<No> nosSucessores = grafo.obterNosPossiveis(estado);
		ArrayList<Estado> estadosSucessores = new ArrayList<Estado>();
		
		for (No sucessor : nosSucessores) {
			Estado novoEstado = new Estado(sucessor, new Veiculo(estado.getVeiculo()), new ArrayList<Encomenda>(estado.getEncomendasPorRecolher()));
			
			novoEstado.getVeiculo().gastarGasolina(grafo.distânciaAté(estado.getNoAtual(), sucessor) * .08);
			
			if (sucessor.getClass().getSimpleName().equals("PontoEntrega")) { // Descarregar veiculo
				Iterator<Encomenda> itEncsVeiculo = novoEstado.getVeiculo().getEncomendas().iterator();
				while (itEncsVeiculo.hasNext()) {
					Encomenda enc = itEncsVeiculo.next();
					if (enc.getDestino().equals(sucessor)) {
						novoEstado.getVeiculo().decCargaEncomenda(enc);
						itEncsVeiculo.remove();
					}
				}
			}
			else if (sucessor.getClass().getSimpleName().equals("PontoRecolha")) { // Carregar veiculo
				Iterator<Encomenda> itEncsNo = sucessor.getEncomendasDaqui().iterator();
				while (itEncsNo.hasNext()) {
					Encomenda enc = itEncsNo.next();
					
					if (!novoEstado.getEncomendasPorRecolher().contains(enc)) continue; // Se a encomenda já foi carregada previamente
					if (novoEstado.getVeiculo().getCargaAtual() + enc.getVolume() > novoEstado.getVeiculo().getCargaMáx()) continue; // Se a encomenda for exceder o limite do veiculo
					
					novoEstado.getEncomendasPorRecolher().remove(enc);
					novoEstado.getVeiculo().addEncomenda(enc);
				}
			}
			else novoEstado.getVeiculo().encherDeposito();
			
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

	private void préMapeamento() {
		for (No temp : grafo.getNos().values()) {
			for (No temp_1 : grafo.getNos().values()) {
				int distance = grafo.distânciaAté(temp, temp_1);
				temp.addDistânciaMinima(temp,distance);
				temp_1.addDistânciaMinima(temp_1,distance);
			}
		}
	}
}
