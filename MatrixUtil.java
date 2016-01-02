public class MatrixUtil {
	//Verifica se matriz é nula
	public static boolean nula(double[][] matriz, int m, int n){
		for(int i = 0; i < m; i++){
			for(int j = 0; j < n; j++){
				if(matriz[i][j] != 0)
					return false;
			}
		}

		return true;
	}

	//Retorna uma matriz nula mxn
	public static double[][] matrizNula(int m, int n){
		double[][] matrizNula = new double[m][n];
		for(int i = 0; i < m; i++){
			for(int j = 0; j < n; j++){
				matrizNula[i][j] = 0;
			}
		}

		return matrizNula;
	}

	//Tirar colunas
	public static double[][] tirarColuna(double[][] matriz, int m, int n, int c){
		double[][] matrizReduzida = new double[m][n - 1];
		for(int i = 0; i < m; i++){
			int k = 0;
			for(int j = 0; j < n; j++){
				if(j != c)	{
					matrizReduzida[i][k] = matriz[i][j];
					k++;
				}
			}
		}

		return matrizReduzida;
	}

	//Multiplica duas matrizes
	public static double[][] multiplicar(double[][] matriz1, double[][] matriz2, int n, int D, int m){
		double[][] matriz = new double[n][m];
		double valor = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
				for(int k = 0; k < D; k++){
					valor += matriz1[i][k] * matriz2[k][j];
				}
				matriz[i][j] = valor;
				valor = 0;
			}
		}

		return matriz;
	}

	//Recebe vetores e retorna uma base do seu espaço coluna
	public static int[] colunasDependentes(double[][] matriz, int m, int n){
		//Duplica matriz
		double[][] matrizReduzida = new double[m][n];
		for(int i = 0; i < m; i++){
			for(int j = 0; j < n; j++){
				matrizReduzida[i][j] = matriz[i][j];
			}
		}

		//Escalonamento
		int indiceC = 0;
		int nivelPivo = 0;
		int[] colunasDependentes = new int[n];
		for(int i = 0; i < n; i++){
			boolean pivoEncontrado = false;
			double pivo = 0;

			//Procura um pivô
			for(int k = nivelPivo; k < m && !pivoEncontrado; k++){
				if(matrizReduzida[k][i] != 0){
					//Encontrou
					pivoEncontrado = true;
					pivo = matrizReduzida[k][i];
					//Troca linha
					if(k != nivelPivo){
						trocarLinha(matrizReduzida, n, nivelPivo, k);
					}
					//Anula valores
					for(int j = 0; j < m; j++){
						if(j != nivelPivo){
							double mult = (-1 * matrizReduzida[j][i])/pivo;
							somarLinha(matrizReduzida, n, nivelPivo, j, mult);
						}
					}
					//Divide valor chegando em 1
					if(pivo != 1){
						double mult = 1/pivo;
						multiplicarLinha(matrizReduzida, n, nivelPivo, mult);
					}
					nivelPivo++;
				}
			}
			//Registra coluna dependente
			if(!pivoEncontrado){
				colunasDependentes[indiceC] = i;
				indiceC++;
			}
		}

		int[] newColunasDependentes = new int[indiceC];
		for(int i = 0; i < indiceC; i++){
			newColunasDependentes[i] = colunasDependentes[i];
		}

		return newColunasDependentes;
	}

	//Transpor matriz
	public static double[][] transpor(double[][] matriz, int m, int n){
		double[][] matrizTransposta = new double[n][m];
		for(int i = 0; i < m; i++){
			for(int j = 0; j < n; j++){
				matrizTransposta[j][i] = matriz[i][j];
			}
		}

		return matrizTransposta;
	}

	//Inverte uma matriz
	public static double[][] inverter(double[][] matriz, int n) {
		double[][] matrizInversa = new double[n][n];
		double[][] matrizDuplicada = new double[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				matrizDuplicada[i][j] = matriz[i][j];
			}
		}

		//Transforma em identidade
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(i == j)
					matrizInversa[i][j] = 1;
				else
					matrizInversa[i][j] = 0;
			}
		}

		//Escalonamento alterando identidade
		for(int i = 0; i < n; i++){
			boolean pivoEncontrado = false;
			double pivo = 0;

			//Procura um pivô
			for(int k = i; k < n && !pivoEncontrado; k++){
				if(matriz[k][i] != 0){
					//Encontrou
					pivoEncontrado = true;
					pivo = matrizDuplicada[k][i];
					//Troca linha
					if(k != i){
						trocarLinha(matrizDuplicada, n, i, k);
						trocarLinha(matrizInversa, n, i, k);
					}
					//Anula valores
					for(int j = 0; j < n; j++){
						if(j != i){
							double mult = (-1 * matrizDuplicada[j][i])/pivo;
							somarLinha(matrizDuplicada, n, i, j, mult);
							somarLinha(matrizInversa, n, i, j, mult);
						}
					}
					//Divide valor chegando em 1
					if(pivo != 1){
						double mult = 1/pivo;
						multiplicarLinha(matrizDuplicada, n, i, mult);
						multiplicarLinha(matrizInversa, n, i, mult);
					}
				}
			}
		}

		return matrizInversa;
	}

	//Troca duas linhas
	public static void trocarLinha(double[][] matriz, int n, int linha1, int linha2){
		for(int i = 0; i < n; i++){
			double aux = matriz[linha1][i];
			matriz[linha1][i] = matriz[linha2][i];
			matriz[linha2][i] = aux;
		}
	}

	//Soma duas linhas
	public static void somarLinha(double[][] matriz, int n, int linha1, int linha2, double mult){
		for(int i = 0; i < n; i++){
			matriz[linha2][i] += matriz[linha1][i] * mult;
		}
	}

	//Multiplica linha por escalar
	public static void multiplicarLinha(double[][] matriz, int n, int linha, double mult){
		for(int i = 0; i < n; i++){
			matriz[linha][i] *= mult;
		}
	}

	public static String matrizToString(double[][] matriz, int m, int n) {
		String s = "";
		for(int i = 0; i < m; i++){
			s += "[";
			for(int j = 0; j < n; j++){
				if(j != n - 1)
					s += matriz[i][j] + " ";
				else
					s += matriz[i][j] + "]";
			}
			s += "\n";
		}
		return s;
	}
}