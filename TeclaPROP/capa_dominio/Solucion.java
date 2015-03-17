package teclado.capa_dominio;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa una solucio
 * @author ester.lorente
 */
public class Solucion {
    private String nameDictionary;
    private String nameKeyboard;
    private Algoritmo algoritmo;
    private LinkedHashMap<Character,Integer> asignacion;
    
    /**
     * Constructora per defecte
     */
    public Solucion() {}
    
    /**
     * Constructora per identificadors
     * @param nameDictionary nom del diccionari
     * @param nameKeyboard nom del teclat
     */
    public Solucion(String nameDictionary, String nameKeyboard) {
        this.nameDictionary = nameDictionary;
        this.nameKeyboard = nameKeyboard;
    }
    
    /**
     * Genera una solucio
     * @param freq matriu de frequencies
     * @param dist matriu de distancies
     * @param alg algoritme seleccionar: 1-BranchAndBound, 2-Genetic, 3-Greedy
     */
    public void generatesSolution(double[][] freq, double[][] dist, int alg) {
        if (alg == 1) algoritmo = new BranchAndBound();
        else if (alg == 2) algoritmo = new Genetico();
        else if (alg == 3) algoritmo = new Greedy();
        algoritmo.execute(freq, dist);
    }
    
    /**
     * Tradueix una solucio
     * @param matching arraylist amb els caracters d'un diccionari
     */
    public void translateSolution(ArrayList<Character> matching) {
        int[] solucion = algoritmo.getSol();
        asignacion = new LinkedHashMap<Character,Integer>(solucion.length);
        for (int i = 0; i < matching.size(); ++i) {
            Character caracter = matching.get(i);
            int tecla = solucion[i];
            asignacion.put(caracter,tecla);
        }
    }

    public String getNameDictionary() {
        return nameDictionary;
    }

    public String getNameKeyboard() {
        return nameKeyboard;
    }
    
    /**
     * @return array amb la solucio
     */
    public int[] getSolution() {
        return algoritmo.getSol();
    }
    
    /**
     * @return cost de la solucio
     */
    public double getCost() {
        return algoritmo.getCost();
    }

    /**
     * @return asignacio de caracter-tecla
     */
    public LinkedHashMap<Character, Integer> getAsignacion() {
        return asignacion;
    }
    
    /**
     * @return string amb la solucio
     */
    @Override
    public String toString() {
        String s = "";
        
        for(Map.Entry<Character, Integer> entry : asignacion.entrySet()){
            Character caracter = entry.getKey();
            int tecla = entry.getValue(); 
            System.out.println("caracter " + caracter + "  tecla " + (tecla+1));
        }
        
        s += "Cost: " + algoritmo.getCost();
        
        return s;
    }
    
    /**
     * Calcula una cel·la de la taula d'estadistiques (Comparar Topologias y Comparar Diccionarios)
     * @param dist matriu de distancies
     * @param matching arraylist amb els caracters d'un diccionari
     * @param words hashtable amb totes les paraules i la seva frequencia d'un diccionari
     * @return cost d'un diccionari amb una topologia
     */
    public double getStatistic(double[][] dist, ArrayList<Character> matching, Hashtable<String,Double> words){
        double count_total = 0;
        String word; Double freq;
        int sol[] = algoritmo.getSol();
        
        for(Map.Entry<String, Double> entry : words.entrySet()) { 
            double count_local = 0;
            word = entry.getKey();
            freq = entry.getValue();
            int i, j;
            i = sol[matching.indexOf(word.charAt(0))]; //Firt position represents i
            for (int n = 1; n < word.length(); n++) {
                j = sol[matching.indexOf(word.charAt(n))];
                if(i != j) {
                    count_local += dist[i][j];
                }
                i = j; 
            }
            count_total += count_local*freq;
         }
        return count_total;
    }
}
