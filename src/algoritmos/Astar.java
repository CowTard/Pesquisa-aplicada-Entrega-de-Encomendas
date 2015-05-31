package algoritmos;

import java.util.ArrayList;

import elementos.Estado;

public class Astar {
	public void executar(Estado inicial) {
		ArrayList<Estado> listaAberta = new ArrayList<Estado>();
		ArrayList<Estado> listaFechada = new ArrayList<Estado>();
		
		inicial.f = 0;
		listaAberta.add(inicial);
	}
}
