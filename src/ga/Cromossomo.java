package ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import input.Dados;

/**
 * Representacao de um cromossomo, com as caracteristicas definidas pelo dominio do problema.
 * De maneira geral, representa uma rota (solucao) e o seu custo associado.
 * 
 * @author Ivan Simplício
 */
public class Cromossomo {
	
	private int[] rota = null;
	private int custoDaRota = 0;
	
	/**
	 * Esse construtor deve ser utilizado quando se deseja criar um novo cromossomo com rota definida aleatoriamente.
	 * @param cidadeInicial Cidade inicial do percurso do caixeiro.
	 * @param numeroCidades Numero de cidades do problema definido.
	 */
	public Cromossomo(int cidadeInicial, int numeroCidades) {
		gerarRotaAleatoria(cidadeInicial, numeroCidades);
		calculaCustoDaRota();
	}
	
	/**
	 * Esse construtor deve ser utilizado quando se deseja criar um novo cromossomo passando uma rota previamente validada.
	 * @param rota A rota previamente validada.
	 */
	public Cromossomo(int[] rota) {
		this.rota = rota;
		calculaCustoDaRota();
	}

	/**
	 * Retorna a rota percorrida.
	 * @return Uma solucao do trajeto.
	 */
	public int[] getRota() {
		return rota;
	}

	/**
	 * Atribuir uma nova rota previamente validada ao cromossomo.
	 * @param rota A nova rota a ser atribuida.
	 */
	public void setRota(int[] rota) {
		this.rota = rota;
	}

	/**
	 * Retorna o custo associado a rota percorrida.
	 * @return O custo da rota.
	 */
	public int getCustoDaRota() {
		return custoDaRota;
	}

	/**
	 * Atribuir um custo previamente calculado para a nova rota atribuida.
	 * @param custoDaRota O custo da rota.
	 */
	public void setCustoDaRota(int custoDaRota) {
		this.custoDaRota = custoDaRota;
	}
	
	/**
	 * Retorna uma representacao da rota e do seu custo associado no formato String.
	 * @return Uma String da rota e custo da solucao.
	 */
	public String printRota() {
		StringBuilder printRota = new StringBuilder();
		String[] cidades = Dados.getCidades();
		int[] rota = getRota();
		
		for(int i = 0; i < rota.length-1; i++) {
			printRota.append(String.format("%s > ", cidades[rota[i]])); 
		}
		printRota.append(cidades[rota[rota.length-1]]);
		printRota.append(String.format(" --- Custo: %d", getCustoDaRota()));
		return printRota.toString();
	}
	
	/**
	 * Um valor que representa a comparacao com outro Cromossomo.
	 * @param Cromossomo a ser comparado.
	 * @return -1 se for menor que c; 0 se for igual a c; 1 se for maior que c.
	 */
	public int compare(Cromossomo c) {
		if(getCustoDaRota() < c.getCustoDaRota()) {
			return -1;
		}else if(getCustoDaRota() > c.getCustoDaRota()) {
			return 1;
		}else {
			return 0;
		}
	}
	
	/**
	 * Gera uma nova rota (solucao) aleatoria e valida.
	 * @param cidadeInicial Cidade inicial do trajeto.
	 * @param numeroCidades Numero de cidades definidas no problema.
	 */
	private void gerarRotaAleatoria(int cidadeInicial, int numeroCidades) {
		int[] rota = new int[numeroCidades+1];
		List<Integer> cities = new ArrayList<>();
		cities.add(0);
		cities.add(1);
		cities.add(2);
		cities.add(3);
		cities.add(4);
		cities.add(5);
		cities.add(6);
		cities.add(7);
		
		cities.remove(cidadeInicial);
		rota[0] = cidadeInicial;
		
		Random rand = new Random();
		for(int i = 1; i < numeroCidades; i++) {
			rota[i] = cities.remove(rand.nextInt(cities.size()));
		}
		rota[numeroCidades] = cidadeInicial;
		setRota(rota);
	}
	
	/**
	 * Calcula o custo da rota atribuida ao Cromossomo.
	 */
	private void calculaCustoDaRota() {
		int[][] distancias = Dados.getDistancias();
		int[] rota = getRota();
		int custo = 0;
		
		for(int i = 0; i < rota.length - 1; i++) {
			custo += distancias[rota[i]][rota[i+1]];
		}
		setCustoDaRota(custo);
	}
}
