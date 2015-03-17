package teclado.capa_dominio;

import java.util.ArrayList;

/**
 * Representa un teclat triangular
 * @author albert.calvo.ibanez
 */
public class Triangular extends Teclado {
    /**
     * Constructora per defecte
     */
    public Triangular(){
        super();
    }
    
    /**
     * Calcula un array amb les tecles per nivells
     * @param arrayNivells array amb les tecles per nivells
     * @param N numero de tecles
     */
    @Override
    public void calcArrays(ArrayList<int[]> arrayNivells, int N) {
        int M=0; // nombre de nodes en el graf
        int level=0;
        // tamañ d'una linia sera igual a level*2 + 1 
         while (M < N) {
              int num_pos= level*2 + 1;
              int[] alevel = new int[num_pos];
              int i=0; 
              while(M < N && i < alevel.length) {
                    alevel[i] = M;
                    if(M==N-1 && i==0 && level != 0) {
                        for(i=0; i < alevel.length; ++i) alevel[i] = -1; 
                        alevel[1] = M;   
                    } 
                    ++M; ++i;
              }
              while(i < alevel.length) { alevel[i] = -1; ++i;}
              arrayNivells.add(level,alevel);
              ++level; 
         }
    }

    /**
     * Calcula el graf del teclat triangular
     * @param arrayNivells array amb les tecles per nivells
     * @param graf graf amb les tecles com a nodes i les adjacencies con una llista
     * @param N numero de tecles
     */
    @Override
    public void calcGraph(ArrayList<int[]> arrayNivells, ArrayList<int[]> graf, int N) {
        int level=0;
        int it=0;
        
        for(int i = 0; i < N; ++i) { 
            int  ady[] =  {-1,-1,-1};
                                                            // ady[0] -> amunt/abaix
            if (N == 1) { // nomes 1 tecla                  // ady[1] -> esquerra
                ady[0] = ady[1] = ady[2] = -1;              // ady[2] -> dreta
                graf.add(i,ady);
            }      
            else { // mes d'una tecla
                int[] v0 = arrayNivells.get(level);
                if(i == N-1 && v0[0] == -1) {
                    ++it;
                    int[] vAmunt = arrayNivells.get(level-1);
                    ady[0] = vAmunt[it-1];
                }
                else {
                    // adjacencies cap amunt/abaix (verticals)
                    if (level-1 >= 0 && it%2 != 0) {
                        // existeix un nivell amunt i te adjacencies
                        int[] vAmunt = arrayNivells.get(level-1);
                        ady[0] = vAmunt[it-1];
                    }
                    if (level+1 < arrayNivells.size() && it%2 == 0) {
                        // existeix un nivell abaix i te adjacencies
                        int[] vAbaix = arrayNivells.get(level+1);
                        ady[0] = vAbaix[it+1];
                    }

                    // adjacencies cap a l'esquerra/dreta (horitzontals)
                    if (it-1 >= 0) {
                        // te element a l'esquerra
                        ady[1] = v0[it-1];
                    }
                    if (it+1 < v0.length) {
                        // te element a la dreta
                        ady[2] = v0[it+1];
                    }
                }
                graf.add(i,ady); 
                ++it;
                if (it == v0.length) {it = 0; ++level;}
            }
        }
    }
}