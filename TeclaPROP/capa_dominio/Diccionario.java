package teclado.capa_dominio;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

/**
 * Representa un diccionari
 * @author sergi.galicia
 */
public class Diccionario {
    private String name;
    private Hashtable<String,Double> words;    
    private int alphabet;
    private double[][] frequencies;

    /**
     * Constructora per defecte
     */
    public Diccionario() {}
    
    /**
     * Constructora amb parametres
     * @param name nom del diccionari
     * @param words hastable amb les paraules i les seves frequencies del diccionari
     * @param alphabet alfabet del diccionari
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public Diccionario(String name, Hashtable<String,Double> words, int alphabet) throws ExcepcionesTeclado {
        if("".equals(name)) throw new ExcepcionesTeclado(ExcepcionesTeclado.no_name);  
        this.name = name;
        this.words = words;
        this.alphabet = alphabet;
    }  
            
    /**
     * @return nom del diccionari
     */
    public String getName() {
        return name;
    }

    /**
     * Setter del nom del diccionari
     * @param name nom del diccionari
     */
    public void setName(String name) throws ExcepcionesTeclado {
        if("".equals(name)) throw  new ExcepcionesTeclado(ExcepcionesTeclado.no_name);
        this.name = name;
    } 
        
    /**
     * @return hashtable amb les paraules i la seva frequencia del diccionari
     */
    public Hashtable<String, Double> getWords() {
        return words;
    }

    /**
     * Setter de la hashtable de paraules i frequencies
     * @param words hashtable amb les paraules i la seva frequencia del diccionari
     */
    public void setWords(Hashtable<String, Double> words) {
        this.words = words;
    }

    /**
     * @return alfabet del diccionari
     */
    public int getAlphabet() {
        return alphabet;
    }

    /**
     * Setter de l'alfabet del diccionari
     * @param alphabet identificador de l'alfabet del diccionari
     * @throws driver_diccionario.ExcepcionesTeclado
     */
    public void setAlphabet(int alphabet)throws ExcepcionesTeclado {
        if(alphabet < 0) throw new ExcepcionesTeclado(ExcepcionesTeclado.no_exist_alph);
        else this.alphabet = alphabet;
    }

    /**
     * @return matriu de frequencies
     */
    public double[][] getFrequencies() {
        return frequencies;
    }

    /**
     * Setter de la matriu de frequencies
     * @param frequencies matriu de frequencies
     */
    public void setFrequencies(double[][] frequencies) {
        this.frequencies = frequencies;
    }
    
    /**
     * Afegeix una paraula amb la seva frequencia
     * @param word paraula a afegir
     * @param freq frequencia a afegir
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public void addWord(String word,double freq)throws ExcepcionesTeclado{
        if(freq <= 0) throw new ExcepcionesTeclado(ExcepcionesTeclado.invalid_f);
        else{
            if(words.containsKey(word)) throw new ExcepcionesTeclado(ExcepcionesTeclado.no_word);
            else words.put(word,freq);
        }
    }
    
    /**
     * Esborra una paraula
     * @param word paraula a esborrar
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public boolean deleteWord(String word)throws ExcepcionesTeclado{ 
           if(words.containsKey(word)){
               words.remove(word);
               if(words.isEmpty())return true;
               return false;
           }  
           else throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_word); 
    }

    /**
     * Modifica la frequencia d'una paraula
     * @param word paraula a modificar
     * @param freq nova frequencia de la paraula
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public void modifyFreq(String word,double freq)throws ExcepcionesTeclado {
        if(freq <= 0) throw new ExcepcionesTeclado(ExcepcionesTeclado.invalid_f);
        else{            
            if(words.containsKey(word)){ System.out.println("contine");words.put(word,freq);}
            else throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_word);
        }
    }    
    
    /**
     * Calcula la matriu de frequencies
     * @param matching arraylist amb els caracters del diccionari
     * @throws teclado.capa_dominio.ExcepcionesTeclado     
     */
    public void calculate(ArrayList<Character> matching) throws ExcepcionesTeclado{  
        int n = matching.size();
        frequencies = new double[n][n];
        String word; 
        Double freq;
        int i, j; 
        if(matching.size() == 1) throw new ExcepcionesTeclado(ExcepcionesTeclado.no_exist_word);        
        for(Map.Entry<String, Double> entry : words.entrySet()){
            word = entry.getKey();
            freq = entry.getValue(); 
            i = matching.indexOf(word.charAt(0));
            for (n = 1; n < word.length(); n++) {
                j = matching.indexOf(word.charAt(n));
                if(i != j) { 
                    frequencies[i][j] += freq; 
                    frequencies[j][i] += freq;
                }
                i=j;    
            }
        }
    }
 
    /**
     * Cerca si un carcater esta al diccionari
     * @param character caracter a cercar
     * @return si es troba el caracter dins del diccionari
     */
    public boolean found(Character character){
        boolean trobat = false;
        Enumeration<String> it = words.keys();
        while(!trobat && it.hasMoreElements()){
            String p = it.nextElement();
            for(int i = 0; i < p.length();++i){
                Character character1 = p.charAt(i);
                if(character.equals(character1)) trobat = true;
            }            
        }
        return trobat;
    }

    /**
     * Mostra les paraules i les seves frequencies del diccionari
     */
    public void printWords() { 
        for(Map.Entry<String, Double> entry : words.entrySet()){
                System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    
    /**
     * Suma les frequencies d'un caracter 
     * @param row columna a llegir
     * @return suma de frequencies per al caracter 
     */
    public double getFreqCharacter(int row) {
        double sum=0;
        for(int i =0; i < frequencies.length; ++i) 
             sum += frequencies[row][i];
        return sum; 
    }
}