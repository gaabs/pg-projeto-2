# TEMA 5: Gouraud X Phong #

## Objetivo ##
### Parte Geral ###
Implementar o método de visualização de objetos triangulados, através do
algoritmo de conversão por varredura, com métodos de interpolação de Phong, com a
visibilidade garantida pelo algoritmo do “z-buffer”.
### Parte Específica ###
Produzir comparativo entre Gouraud e Phong com duas janelas para
visualização simultânea.

## Descrição
O usuário, através de arquivos-texto ou interface gráfica, entra com dados do objeto (triangulado, com lista dos vértices e da conectividade, que determina os triângulos, de um arquivo-texto), atributos do objeto (ka, kd e ks, pontos flutuantes entre 0 e 1, n, ponto flutuante positivo e Od, tripla de pontos flutuantes entre 0 e 1), atributos da cena (Ia, IL, triplas de ponto flutuante entre 0 e 255, PL, tripla de ponto flutuante) e os atributos da câmera virtual (C, N e V, triplas de pontos flutuantes, d, hx, e hy, pontos flutuantes positivos). O seu sistema começa preparando a câmera, ortogonalizando V e gerando U, e depois os normalizando, fazer a mudança de coordenadas para o sistema de vista de todos os vértices do objeto e da posição da fonte de luz PL, gerar as normais dos triângulos e gerar as normais dos vértices (como recomendado em sala de aula). O sistema terá duas janelas, uma para o objeto apresentado por Gouraud e o outro por Phong. Para cada triângulo, calculam-se as projeções dos seus vértices, calculam-se as cores dos vértices (utilizando as normais dos vértices, e calculando-se L, V e R e os substituindo na equação de iluminação) e inicia-se a sua conversão por varredura. Para cada pixel (x, yscan), calculam-se suas coordenadas baricêntricas com relação aos vértices projetados, e multiplicam-se essas coordenadas pelos correspondentes vértices do triângulo 3D original para se obter uma aproximação para o ponto 3D original correspondente ao pixel atual. Após uma consulta ao z-buffer, se for o caso, calcula-se uma aproximação para a normal do ponto utilizando-se mesmas coordenadas baricêntricas multiplicadas pelas normais dos respectivos vértices originais. Calculam-se também os demais vetores (L, V e R) e os substitui na equação do modelo de iluminação de Phong produzindo a cor do pixel atual (da janela direita). Para o Gouraud, utilizam-se as coordenadas baricêntricas multiplicadas pelas respectivas cores dos vértices e pinta-se na janela da esquerda (cada ponto determinado pela varredura gera dois pixels, um em cada janela).

## Passos Gerais de Sílvio ##

1- "Calibrar" a câmera

2- Converter as coordenadas

3- calcular as normais nos triângulos

4- Calcular as normais nos vértices

5- zerar o zbuffer e tal?

6 – Para cada triângulo:

6.1 – Projeta seus vértices (P1’, P2’, P3’)

6.2 – Algoritmo de pintura (scan-conversion)

6.2.1 – Para cada pixel, encontre suas cordenadas baricêntricas (P’ = a*P1’ + b*P2’ + c*P3’)

6.2.2 – Encontre ponto no mundo 3D (P = a*P1 + b*P2 + c*P3)

	Consulte o Z-buffer se P.z < Z_buffer(P.x,P.y)

Eq de iluminação: N = a*N1 + b*N2 + c*N3

Encontre L,R,V normalize

## Fotos ##
https://goo.gl/photos/R9EoxUfP6e33dSZx8

# video dos algoritmos ##
https://www.facebook.com/l.php?u=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DjWaeoKI4cwY&h=2AQG0Lfik&s=1

## phong em c++  ##
http://www.ozone3d.net/tutorials/glsl_lighting_phong.php