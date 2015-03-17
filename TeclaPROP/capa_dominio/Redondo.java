package teclado.capa_dominio;

import java.util.ArrayList;

/**
 * Representa un teclat rodo
 * @author albert.calvo.ibanez
 */
public class Redondo extends Teclado {
    private int levelAux;
    
    /**
     * Constructora per defecte
     */
    public Redondo() {
        super();        
    }
    
    /**
     * Calcula levelAux, nivell pel qual l'array per nivells deixa de creixer
     * @param N nombre de tecles
     * @return nivell levelAux
     */
    private int calcLevelAux(int N) {
        int level = 0;
        int numCaselles = 1;
        int i = 1;
        while (numCaselles < N) {
            numCaselles += 6*i;
            ++i;
            ++level;
        }
        return level;
    }
    
    /**
     * Calcula un array amb les tecles per nivells
     * @param arrayNivells array amb les tecles per nivells
     * @param N numero de tecles
     */
    @Override
    public void calcArrays(ArrayList<int[]> arrayNivells, int N) {
        int M = 0; // nombre de nodes en el graf
        int level = 0;
        int numPos = 1;
        levelAux = calcLevelAux(N);
         while(M < N) {
              int[] alevel = new int[numPos];
              int i = 0;
              while(M < N && i < alevel.length) {
                    alevel[i] = M;
                    ++M; ++i;
              }
              while(i < alevel.length) {alevel[i] = -1; ++i;}
              arrayNivells.add(level,alevel);
              ++level;
              if (level <= levelAux) numPos +=2;
         }
    }

    /**
     * Calcula el graf del teclat rodo
     * @param arrayNivells array amb les tecles per nivells
     * @param graf graf amb les tecles com a nodes i les adjacencies con una llista
     * @param N numero de tecles
     */
    @Override
    public void calcGraph(ArrayList<int[]> arrayNivells, ArrayList<int[]> graf, int N) {
        int level = 0;
        int it = 0;
        
        for(int i = 0; i < N; ++i) {
            int  ady[] =  {-1,-1,-1,-1,-1,-1};                              // ady[0] -> amunt
                                                                            // ady[1] -> esquerra-amunt
            if (N == 1) { // nomes 1 tecla                                  // ady[2] -> esquerra-abaix
                ady[0] = ady[1] = ady[2] = ady[3] = ady[4] = ady[5] = -1;   // ady[3] -> abaix
                graf.add(i,ady);                                            // ady[4] -> dreta-abaix
            }                                                               // ady[5] -> dreta-abaix
            else { // mes d'una tecla
                int[] v0 = arrayNivells.get(level);
                int medio = v0.length/2;
                
                // adjacencies amb el nivell de dalt
                if (level-1 >= 0) {
                    int[] vAmunt = arrayNivells.get(level-1);
                    if (level > levelAux) {
                        if (it < medio) { // a l'esquerra
                            ady[0] = vAmunt[it];
                            ady[5] = vAmunt[it+1];
                        }
                        else if (it == medio) { // al mig
                            ady[0] = vAmunt[it];
                        }
                        else { // a la dreta
                            if (it < vAmunt.length) ady[0] = vAmunt[it];
                            if (it-1 < vAmunt.length) ady[1] = vAmunt[it-1];
                        }
                    }
                    else {
                        if (it < medio) { // a l'esquerra
                            if (it-1 >= 0) ady[0] = vAmunt[it-1];
                            ady[5] = vAmunt[it];
                        }
                        else if (it == medio) { // al mig
                            ady[0] = vAmunt[it-1];
                        }
                        else { // a la dreta
                            if (it-1 < vAmunt.length) ady[0] = vAmunt[it-1];
                            ady[1] = vAmunt[it-2];
                        }
                    }
                }
                
                // adjacencies amb el nivell d'abaix
                if (level+1 < arrayNivells.size()) {
                    int[] vAbaix = arrayNivells.get(level+1);
                    if (level >= levelAux) {
                        if (it < medio) { // a l'esquerra
                            ady[3] = vAbaix[it];
                            if (it-1 >= 0) ady[2] = vAbaix[it-1];
                        }
                        else if (it == medio) { // al mig
                            ady[3] = vAbaix[it];
                            if (it-1 >= 0) ady[2] = vAbaix[it-1];
                            ady[4] = vAbaix[it+1];
                        }
                        else { // a la dreta
                            ady[3] = vAbaix[it];
                            if (it+1 < vAbaix.length) ady[4] = vAbaix[it+1];
                        }
                    }
                    else {
                        if (it < medio) { // a l'esquerra
                            ady[3] = vAbaix[it+1];
                            ady[2] = vAbaix[it];
                        }
                        else if (it == medio) { // al mig
                            ady[3] = vAbaix[it+1];
                            ady[2] = vAbaix[it];
                            ady[4] = vAbaix[it+2];
                        }
                        else { // a la dreta
                            if (it+1 < vAbaix.length) ady[3] = vAbaix[it+1];
                            if (it+2 < vAbaix.length) ady[4] = vAbaix[it+2];
                        }
                    }
                }
                
                // adjacencies amb el mateix nivell
                if (it-1 >= 0) { // te element a l'esquerra
                    if (it < medio) { // a l'esquerra
                        ady[1] = v0[it-1];
                    }
                    else if (it == medio) { // al mig
                        ady[1] = v0[it-1];
                    }
                    else { // a la dreta
                        ady[2] = v0[it-1];
                    }
                }
                
                if (it+1 < v0.length) { // te element a la dreta
                    if (it < medio) { // a l'esquerra
                        ady[4] = v0[it+1];
                    }
                    else if (it == medio) { // al mig
                        ady[5] = v0[it+1];
                    }
                    else { // a la dreta
                        ady[5] = v0[it+1];
                    }
                }
                
                graf.add(i,ady); 
                ++it;
                if (it == v0.length) {it = 0; ++level;}
            }
        }
    }
}