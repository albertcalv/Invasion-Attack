package teclado.capa_dominio;

/**
 * Representa un algoritme
 * @author ester.lorente
 */
public abstract class Algoritmo {
    protected double cost_min;
    protected int[] sol;
    
    /**
     * Constructora per defecte
     */
    public Algoritmo() {}
    
    /**
     * @return cost minim de la solucio
     */
    public double getCost() {
        return cost_min;
    }
    
    /**
     * @return solucio de cost minim
     */
    public int[] getSol() {
        return sol;
    }
    
    /**
     * Metode abstracte per executar l'algoritme
     * @param freq matriu de frequencies
     * @param dist matriu de distancies
     */
    public abstract void execute(double[][] freq, double[][] dist);
    
    /**
     * @return string amb la solucio
     */
    @Override
    public String toString() {
        String s = "Solucio: ";
        for (int i = 0; i < sol.length; ++i) s += sol[i]+1 + " ";
        s += "\n";
        s += "Cost: " + cost_min + "\n";
        return s;
    }
}