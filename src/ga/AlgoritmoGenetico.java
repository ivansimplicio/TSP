package ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import sort.Insertion;

/**
 * Classe que contem todas as acoes que resolvem o problema, utilizando o contexto do algoritmo generico.
 * 
 * @author Ivan Simplício
 */
public class AlgoritmoGenetico {
	
	/**
	 * Povoa a populacao com rotas (solucoes) aleatorias.
	 * @param populacao O array a ser povoado.
	 * @param cidadeInicial Cidade inicial do trajeto.
	 * @param numeroCidades Numero de cidades definidas no problema.
	 */
	public static void gerarPopulacaoAleatoria(Cromossomo[] populacao, int cidadeInicial, int numeroCidades, boolean permitirCromossomosRepetidos) {
		for(int i = 0; i < populacao.length; i++) {
			Cromossomo novoCromossomo = new Cromossomo(cidadeInicial, numeroCidades);
			if(!permitirCromossomosRepetidos) {
				while(cromossomoJaExiste(populacao, novoCromossomo.getRota())) {
					novoCromossomo = new Cromossomo(cidadeInicial, numeroCidades);
				}
			}
			populacao[i] = novoCromossomo;
		}
	} 
	
	/**
	 * Exibe todos cromossomos da populacao.
	 * @param populacao A populacao a ser exibida.
	 * @param titulo Titulo da geracao.
	 */
	public static void exibirPopulacao(Cromossomo[] populacao, String titulo) {
		System.out.println(titulo);
		for(int i = 0; i < populacao.length; i++) {
			System.out.println(String.format("C%03d) %s", i+1, populacao[i].printRota()));
		}
	}
	
	/**
	 * Exibe a melhor rota e o seu custo associado.
	 * @param populacao Populacao para realizacao da consulta da melhor rota.
	 */
	public static void exibirMelhorRota(Cromossomo[] populacao) {
		System.out.println("\nRota com menor custo:");
		System.out.println(populacao[0].printRota());
	}
	
	/**
	 * Ordena a populacao utilizando o algoritmo InsertionSort.
	 * @param populacao A populacao de cromossomos a ser ordenada.
	 */
	public static void ordenarPopulacao(Cromossomo[] populacao) {
		Insertion.sort(populacao);
	}
	
	/**
	 * Coordena as acoes para realizar a evolucao da populacao.
	 * @param populacao A populacao a ser evoluida.
	 * @param numeroGeracoes Numero de evolucoes (geracoes) sobre a populacao.
	 * @param taxaDeSobrevivencia Taxa de sobrevivencia dos melhores individuos da populacao a cada geracao.
	 * @param exibirGeracoes Valor booleano que define se o usuario deseja ou nao visualizar a populacao a cada evolucao.
	 */
	public static void evoluirPopulacao(Cromossomo[] populacao, int numeroDeGeracoes, double taxaDeSobrevivencia, boolean exibirGeracoes, boolean permitirCromossomosRepetidos) {
		for(int i = 0; i < numeroDeGeracoes; i++) {
			evoluirPopulacao(populacao, taxaDeSobrevivencia, permitirCromossomosRepetidos);
			ordenarPopulacao(populacao);
			if(exibirGeracoes && numeroDeGeracoes != (i+1))
				exibirPopulacao(populacao, String.format("\nGeração %04d", i+1));
		}
	}	
	
	/**
	 * Realiza a evolucao da populacao.
	 * @param populacao A populacao a ser evoluida.
	 * @param taxaDeSobrevivencia Taxa de sobrevivencia dos melhores individuos da populacao a cada geracao.
	 */
	private static void evoluirPopulacao(Cromossomo[] populacao, double taxaDeSobrevivencia, boolean permitirCromossomosRepetidos) {
		int inicioNovaGeracao = (int) (taxaDeSobrevivencia * populacao.length);
		Random rand = null;
		
		for(int i = inicioNovaGeracao; i < populacao.length; i++) {
			rand = new Random();
			
			Cromossomo pai1 = populacao[rand.nextInt(inicioNovaGeracao)];
			Cromossomo pai2 = null;
			
			if(permitirCromossomosRepetidos) {
				pai2 = selecionaPaiM2(populacao, pai1, inicioNovaGeracao, rand);
			}else {
				pai2 = selecionaPaiM1(populacao, pai1, inicioNovaGeracao, rand);
			}
			int[] novoCromossomo = aplicaCruzamentoMutacao(pai1, pai2);
			if(!permitirCromossomosRepetidos) {
				while(cromossomoJaExiste(populacao, novoCromossomo)) {
					novoCromossomo = aplicaCruzamentoMutacao(pai1, pai2);
				}
			}
			populacao[i] = new Cromossomo(novoCromossomo);
		}
	}
	
	/**
	 * Faz o cruzamento-mutacao entre dois cromossomos pais para geracao de um novo cromossomo filho.
	 * @param p1 Cromossomo-Pai-1
	 * @param p2 Cromossomo-Pai-2
	 * @return O novo cromossomo gerado.
	 */
	private static int[] aplicaCruzamentoMutacao(Cromossomo p1, Cromossomo p2) {
		Random rand = new Random();
		int qntElementos = 3+rand.nextInt(2); //sorteia quantas caracteristas vao ser herdadas de cada pai (3 ou 4 herancas)
		List<Integer> rota = new ArrayList<>();
		int[] p1Rota = p1.getRota();
		for(int i = 1; i < p1Rota.length-1; i++) {
			rota.add(p1Rota[i]);
		}
		
		//configurando o estado inicial do novo cromossomo
		int[] novoCromossomo = new int[p1Rota.length];
		Arrays.fill(novoCromossomo, -1);
		novoCromossomo[0] = p1Rota[0];
		novoCromossomo[p1Rota.length-1] = p1Rota[p1Rota.length-1];
		
		//herdando caracteristicas do pai 1
		for(int i = 0; i < qntElementos; i++) {	
			int pos = 1+rand.nextInt(p1Rota.length-2);
			while(posicaoPovoada(novoCromossomo, pos)) {
				pos = 1+rand.nextInt(p1Rota.length-2);
			}
			novoCromossomo[pos] = p1Rota[pos];
			rota.remove(new Integer(p1Rota[pos]));
		}
		
		//herdando caracteristicas do pai 2
		int[] p2Rota = p2.getRota();
		for(int pos = 1; pos < (p2.getRota().length-2)-qntElementos; pos++) {
			if(!posicaoPovoada(novoCromossomo, pos)) {
				if(!cidadeEstaMapeada(novoCromossomo, p2Rota[pos])) {
					novoCromossomo[pos] = p2Rota[pos];
					rota.remove(new Integer(p2Rota[pos]));
				}
			}
		}
		
		//preenchendo o novo cromossomo com as cidades que ainda nao foram mapeadas
		for(int pos = 1; pos < p2Rota.length-1; pos++) {
			if(!posicaoPovoada(novoCromossomo, pos)) {
				int cidade = rand.nextInt(rota.size());
				novoCromossomo[pos] = rota.remove(cidade);
			}
		}
		return novoCromossomo;
	}
	
	/**
	 * Faz a selecao de um segundo pai, dado que o primeiro ja foi selecionado.
	 * @param populacao A populacao da qual o pai sera sorteado.
	 * @param pai1 O primeiro pai ja sorteado.
	 * @param inicioNovaGeracao Valor que define o limite entre a geracao anterior e a nova geracao.
	 * @param rand Objeto Random para utilizacao auxiliar no sorteio.
	 * @return Um segundo pai (selecionado entre as melhores solucoes) diferente do primeiro pai.
	 */
	private static Cromossomo selecionaPaiM1(Cromossomo[] populacao, Cromossomo pai1, int inicioNovaGeracao, Random rand) {
		Cromossomo pai2;
		do {
			pai2 = populacao[rand.nextInt(inicioNovaGeracao)];
		}while(Arrays.equals(pai1.getRota(), pai2.getRota()));
		return pai2;
	}
	
	/**
	 * Faz a selecao de um segundo pai, dado que o primeiro ja foi selecionado.
	 * @param populacao A populacao da qual o pai sera sorteado.
	 * @param pai1 O primeiro pai ja sorteado.
	 * @param inicioNovaGeracao Valor que define o limite entre a geracao anterior e a nova geracao.
	 * @param rand Objeto Random para utilizacao auxiliar no sorteio.
	 * @return Um segundo pai (possivelmente selecionado entre as melhores solucoes) diferente do primeiro pai.
	 */
	private static Cromossomo selecionaPaiM2(Cromossomo[] populacao, Cromossomo pai1, int inicioNovaGeracao, Random rand) {
		Cromossomo pai2;
		int cont = 0;
		do {
			pai2 = populacao[rand.nextInt(inicioNovaGeracao)];
			cont++;
		}while(Arrays.equals(pai1.getRota(), pai2.getRota()) && cont != 3);
		
		if(cont == 3) {
			int r = inicioNovaGeracao + rand.nextInt(populacao.length-inicioNovaGeracao);
			pai2 = populacao[r];
		}
		return pai2;
	}
	
	/**
	 * Verifica se um cromossomo ja esta presente na populacao.
	 * @param populacao A populacao a ser verificada.
	 * @param cromossomo O novo cromossomo a ser verificado.
	 * @return true se o cromossomo ja estiver presente na populacao, false se nao.
	 */
	private static boolean cromossomoJaExiste(Cromossomo[] populacao, int[] cromossomo) {
		for(int pos = 0; pos < populacao.length; pos++) {
			if(populacao[pos] != null)
				if(Arrays.equals(populacao[pos].getRota(), cromossomo))
					return true;
		}
		return false;
	}
	
	/**
	 * Verifica se uma cidade ja esta mapeada em uma solucao.
	 * @param novoCromossomo O novo cromossomo a ser verificado.
	 * @param cidade A cidade a ser verificada.
	 * @return true se ja tiver sido mapeada, false se nao.
	 */
	private static boolean cidadeEstaMapeada(int[] novoCromossomo, int cidade) {
		for(int i = 1; i < novoCromossomo.length-1; i++) {
			if(novoCromossomo[i] == cidade)
				return true;
		}
		return false;
	}
	
	/**
	 * Verifica se uma determinada posicao ja teve alguma cidade mapeada.
	 * @param novoCromossomo O novo cromossomo a ser verificado.
	 * @param pos A posicao da solucao a ser verificada.
	 * @return true se ja tiver sido povoada, false se nao.
	 */
	private static boolean posicaoPovoada(int[] novoCromossomo, int pos) {
		if(novoCromossomo[pos] != -1) {
			return true;
		}
		return false;
	}
}