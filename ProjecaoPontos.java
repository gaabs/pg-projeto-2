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
}
