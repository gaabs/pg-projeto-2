package Utils;

public class ProjecaoPontos extends MatrixUtil{
	
	public static double[][] projetar(double[][] matrizM, double[][] matrizD, int n, int D, int m) {
		double[][] matrizDt = transpor(matrizD, n, D),
				   matrizMt = transpor(matrizM, D, m),
				   matrizMtM = multiplicar(matrizMt, matrizM, m, D, m),
				   matrizMtMi = inverter(matrizMtM, m),
				   matrizMMtMi = multiplicar(matrizM, matrizMtMi, D, m, m),
				   matrizProjecao = multiplicar(matrizMMtMi, matrizMt, D, m, D),
				   matrizResultado = multiplicar(matrizProjecao, matrizDt, D, D, n),
				   matrizResultadoTransposto = transpor(matrizResultado, D, n); //nxD
		return matrizResultadoTransposto;
	}
	
	/*Há grande chance disto ai esta errado.
	 * Sabendo disto é necessario q alguém traduza isso:
	 * 
	 * "Projetar cada ponto que a camera enxerga para as coordenadas da tela (x, y)

	Coordenadas = ( d*(Xalfa/Zalfa) , d*(Yalfa/Zalfa) )

	Normalizando para ficar entre -1 e 1
	((d/Hx)*(Xalfa/Zalfa) , (d/Hy)*(Yalfa/Zalfa))
	( 		Xd			  ,			Yd			)


	Para mapear para a tela do computador 
	(onde o centro do plano esta no topo esquerdo da tela)
	Xs = (Xd+1)/2 * (ResX - 1)
	Ys = (1-Yd)/2 * (ResY - 1)"
	 * 
	 * 
	 * 
	 * */
	
}
