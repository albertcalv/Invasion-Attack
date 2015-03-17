package teclado.capa_dominio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Representa un teclat
 * @author albert.calvo.ibanez
 */ 
public abstract class Teclado {
    protected String name;
    protected int N;
    protected double[][] dist;
    
    /**
     * Executa el dijkstra per a un node
     * @param graf arraylist amb les tecles com a nodes i adjacencies com array
     * @param node tecla pel qual es vol obtenir les distancies minimes
     */
    private void dijkstra_i(ArrayList<int[]> graf, int node) {
        for (int j = 0; j < N; ++j) dist[node][j] = Double.POSITIVE_INFINITY;
        dist[node][node] = 0;
        
        boolean[] visitado = new boolean[N];
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>(4, new Comparator<Pair>() {
            public int compare(Pair a1, Pair a2) {
                return a1.value.compareTo(a2.value);
            }
        });
        pq.add(new Pair(node,0));
        
        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int u = p.getKey();
            if (u != -1 && !visitado[u]) {
                visitado[u] = true;
                int[] ady = graf.get(u);
                for (int i = 0; i < ady.length; ++i) {
                    int v = ady[i];
                    int c = 1;
                    if (v != -1 && dist[node][v] > dist[node][u] + c) {
                        dist[node][v] = dist[node][u] + c;
                        pq.add(new Pair(v,dist[node][v]));
                    }
                }
            }
        }
    }
    
    /**
     * Executa el dijkstra per a tots els nodes
     * @param graf arraylist amb les tecles com a nodes i adjacencies com array
     */
    private void dijkstra(ArrayList<int[]> graf) {
        dist = new double[N][N];
        for (int i = 0; i < N; ++i) {
            dijkstra_i(graf,i);
        }
    }
    
    /**
     * Constructora per defecte
     */
    public Teclado(){}
    
    /**
     * @return nom del teclat
     */
    public String getName() {
        return name;
    }

    /**
     * Setter del nom del teclat
     * @param name nom del teclat
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return matriu de distancies
     */
    public double[][] getDist() {
        return dist;
    }

    /**
     * Setter de la matriu de distancies
     * @param dist matriu de distancies
     */
    public void setDist(double[][] dist) {
        this.dist = dist;
    }
    
    /**
     * Calcula la matriu de distancies
     * @param N nombre de tecles
     */
    public void calculateDistances(int N) {
        this.N = N;
        ArrayList<int[]> arrayNivells = new ArrayList<int[]>();
        calcArrays(arrayNivells,N);
        
        ArrayList<int[]> graf = new ArrayList<int[]>(N);
        calcGraph(arrayNivells,graf,N);
        
        double[][] dist = new double[N][N];
        dijkstra(graf);
    }
    
    /**
     * Calcula l'array de tecles per nivells
     * @param arrayNivells array amb les tecles per nivells
     * @param N nombre de tecles
     */
    public abstract void calcArrays(ArrayList<int[]> arrayNivells, int N);
    
    /**
     * Calcula el graf del teclat
     * @param arrayNivells array amb les tecles per nivells
     * @param graf arraylist amb les tecles com a nodes i adjacencies com array
     * @param N nombre de tecles
     */
    public abstract void calcGraph(ArrayList<int[]> arrayNivells, ArrayList<int[]> graf, int N);
}
