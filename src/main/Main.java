package main;

import ga.AlgoritmoGenetico;
import ga.Cromossomo;

/**
 * Classe principal (executavel).
 * 
 * @author Ivan Simplício
 */
public class Main {
	
	public static final int CIDADE_INICIAL = 0;							// [A=0, B=1, C=2, D=3, E=4, F=5, G=6, H=7]
	public static final int NUMERO_DE_CIDADES = 8;						// Numero de cidades do problema (imutavel, definido pelo problema)
	public static final int TAMANHO_DA_POPULACAO = 10;					// Quantidade de solucoes (cromossomos) em cada geracao
	public static final int NUMERO_DE_GERACOES = 10;					// Numero de geracoes sobre uma populacao (criterio de parada)
	public static final double TAXA_DE_SOBREVIVENCIA = 0.4;				// Taxa de sobrevivencia dos melhores individuos da populacao a cada geracao
	public static final boolean EXIBIR_GERACOES = false;				// Valor booleano que define se o usuario deseja ou nao visualizar a populacao a cada evolucao.
	public static final boolean PERMITIR_CROMOSSOMOS_REPETIDOS = false; // Permite ou não a existencia de cromossomos com solucoes iguais na populacao, tanto na criacao quanto na evolucao.
	
	public static void main(String[] args) {
		
		Cromossomo[] populacao = new Cromossomo[TAMANHO_DA_POPULACAO];
		
		AlgoritmoGenetico.gerarPopulacaoAleatoria(populacao, CIDADE_INICIAL, NUMERO_DE_CIDADES, PERMITIR_CROMOSSOMOS_REPETIDOS);
		AlgoritmoGenetico.ordenarPopulacao(populacao);
		AlgoritmoGenetico.exibirPopulacao(populacao, "Geração 0000 - População inicial ordenada");
		AlgoritmoGenetico.evoluirPopulacao(populacao, NUMERO_DE_GERACOES, TAXA_DE_SOBREVIVENCIA, EXIBIR_GERACOES, PERMITIR_CROMOSSOMOS_REPETIDOS);
		AlgoritmoGenetico.exibirPopulacao(populacao, String.format("\nGeração %04d - População final ordenada", NUMERO_DE_GERACOES));
		AlgoritmoGenetico.exibirMelhorRota(populacao);
	}
}
