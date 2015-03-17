package teclado.capa_dominio;

/**
 * Representa un parell d'enter i double
 * @author albert.calvo.ibanez
 */
public class Pair {
    public Integer key;
    public Double value;
    
    /**
     * Constructora amb key i valor
     * @param key clau del pair
     * @param value valor del pair
     */
    public Pair(int key, double value) {
        this.key = key;
        this.value = value;
    }
    
    /**
     * @return key del parell
     */
    public int getKey(){
        return key;
    }
    
    /**
     * @return valor del parell
     */
    public double getValue() {
        return value;
    }
    
    /**
     * @param o1 parell 1
     * @param o2 parell 2
     * @return resultat de comparar dos parells
     */
	public int compare(Pair a1, Pair a2) {
        if(a1.value > a2.value) {
            return 1;
        }
        else if(a1.value < a2.value) {
            return -1;
        }
        return 0;
        
    }
    
    /**
     * @param o parell
     * @return resultat de comparar
     */
	public int compareTo(Pair a2) {
        if(this.value > a2.value) {
            return 1;
        }
        else if(this.value < a2.value) {
            return -1;
        }
        return 0;
    }
}