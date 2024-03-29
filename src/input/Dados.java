package input;

/**
 * Classe que contem os dados providos para resolucao do problema.
 * 
 * @author Ivan Simplício
 */
public class Dados {
	
	/**
	 * Retorna a nomeacao de cada uma das 8 cidades.
	 * @return A representacao de cada cidade.
	 */
	public static String[] getCidades() {
		String[] cidades = {"A", "B", "C", "D", "E", "F", "G", "H"};
		return cidades;
	}
	
	/**
	 * Retorna a matriz 8x8 que contem a distancia entre cada par de cidades.
	 * @return Uma matriz 8x8 de distancias entre as cidades.
	 */
	public static int[][] getDistancias() {
		int[][] distancias =	{{ 0, 42, 61, 30, 17, 82, 31, 11 },
								{ 42,  0, 14, 87, 28, 70, 19, 33 },
								{ 61, 14,  0, 20, 81, 21,  8, 29 },
								{ 30, 87, 20,  0, 34, 33, 91, 10 },
								{ 17, 28, 81, 34,  0, 41, 34, 82 },
								{ 82, 70, 21, 33, 41,  0, 19, 32 },
								{ 31, 19,  8, 91, 34, 19,  0, 59 },
								{ 11, 33, 29, 10, 82, 32, 59,  0 }};
		
		return distancias;
	}
}
