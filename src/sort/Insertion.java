package sort;

import ga.Cromossomo;

/**
 * Classe responsavel por ordenacao de dados.
 * 
 * @author Ivan Simplício
 */
public class Insertion {
	
	/**
	 * Metodo para ordenacao de uma populacao de cromossomos com base no metodo compare (que compara o custo das rotas)
	 * @param populacao Populacao de cromossomos a ser ordenada.
	 */
	public static void sort(Cromossomo[] populacao) {
		for(int i = 1; i < populacao.length; i++) {
			Cromossomo aux = populacao[i];
			int j = i;
			
			while((j>0) && (populacao[j-1].compare(aux) > 0)) {
				populacao[j] = populacao[j-1];
				j--;
			}
			populacao[j] = aux;
		}
	}
}
