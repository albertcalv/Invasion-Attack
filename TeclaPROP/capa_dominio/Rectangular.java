package teclado.capa_dominio;

import java.util.ArrayList;

/**
 * Representa un teclat rectangular
 * @author albert.calvo.ibanez
 */
public class Rectangular extends Teclado {
    private int base;
    private int altura;
    
    /**
     * Constructora per defecte
     */
    public Rectangular() {
        super();
    }
    
    /**
     * Calcula la base i l'altura del teclat rectangular
     * @param N nombre de tecles
     */
    private void calculaBH(int N) {
        this.N = N;
        base = 1;
        altura = 1;
        while (base*altura < N) {
            if (base > altura+N/10+1) ++altura;
            else ++base;
        }
    }
    
    /**
     * Calcula un array amb les tecles per nivells
     * @param arrayNivells array amb les tecles per nivells
     * @param N numero de tecles
     */
    @Override
    public void calcArrays(ArrayList<int[]> arrayNivells, int N) {
        calculaBH(N);
        int M = 0; // nombre de nodes en el graf
        int level = 0;
         while (M < N) {
              int num_pos = base;
              int[] alevel = new int[num_pos];
              int i = 0; 
              while(M < N && i < alevel.length) {
                    alevel[i] = M;
                    ++M; ++i;
              }
              while(i < alevel.length) {alevel[i] = -1; ++i;}
              arrayNivells.add(level,alevel);
              ++level;
         }
    }

    /**
     * Calcula el graf del teclat rectangular
     * @param arrayNivells array amb les tecles per nivells
     * @param graf graf amb les tecles com a nodes i les adjacencies con una llista
     * @param N numero de tecles
     */
    @Override
    public void calcGraph(ArrayList<int[]> arrayNivells, ArrayList<int[]> graf, int N) {
        int level = 0;
        int it = 0;
        
        for(int i = 0; i < N; ++i) { 
            int  ady[] =  {-1,-1,-1,-1};
                                                            // ady[0] -> amunt
            if (N == 1) { // nomes 1 tecla                  // ady[1] -> abaix
                ady[0] = ady[1] = ady[2] = ady[3] = -1;     // ady[2] -> izquierda
                graf.add(i,ady);                            // ady[3] -> derecha
            }      
            else { // mes d'una tecla
                int[] v0 = arrayNivells.get(level);
                
                // adjacencies cap amunt
                if (level-1 >= 0) {
                    int[] vAmunt = arrayNivells.get(level-1);
                    ady[0] = vAmunt[it];
                }
                
                // adjacencies cap abaix
                if (level+1 < arrayNivells.size()) {
                    int[] vAbaix = arrayNivells.get(level+1);
                    ady[1] = vAbaix[it];
                }
                
                // adjacencies cap esquerra/dreta (horitzontals)
                if (it-1 >= 0) { // te un element a l'esquerra
                    ady[2] = v0[it-1];
                }
                if (it+1 < v0.length) { // te un element a la dreta
                    ady[3] = v0[it+1];
                }
                
                graf.add(i,ady); 
                ++it;
                if (it == v0.length) {it = 0; ++level;}
            }
        }
    }
}
