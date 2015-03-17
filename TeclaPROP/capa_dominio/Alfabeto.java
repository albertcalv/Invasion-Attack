package teclado.capa_dominio;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Representa un alfabet
 * @author sergi.galicia
 */
public class Alfabeto { 
    private String name;
    private ArrayList <Diccionario> dictionary; 
    private ArrayList <Character> characters;    
    
    /**
     * Recalcula les matrius de freqüències dels diccionaris
     */
    private void refreshFrequencies() throws ExcepcionesTeclado {
         for(int i = 0; i < dictionary.size(); ++i) 
             dictionary.get(i).calculate(characters);
    }     
    
    /**
     * Constructora per defecte
     */
    public Alfabeto(){}
    
    /**
     * Constructora amb nom de l'alfabet
     * @param name nom de l'alfabet
     */
    public Alfabeto(String name) { 
        dictionary = new ArrayList<Diccionario>();
        characters = new ArrayList<Character>();
        this.name = name;
    }
     
    /**
     * Constructora amb arraylist de caracters
     * @param characters arraylist de caracters
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public Alfabeto(ArrayList<Character> characters) throws ExcepcionesTeclado{  
       if(characters.isEmpty()) throw new ExcepcionesTeclado(ExcepcionesTeclado.no_characters);
        this.characters = characters;
    }

    /**
     * @return nom de l'alfabet
     */
    public String getName() {
        return name;
    }

    /**
     * Setter del nom de l'alfabet
     * @param name nom de l'alfabet
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public void setName(String name) throws ExcepcionesTeclado {
        if("".equals(name)) throw  new ExcepcionesTeclado(ExcepcionesTeclado.no_name);
        else this.name = name;
    }

    /**
     * @return array dels diccionaris de l'alfabet
     */
    public ArrayList<Diccionario> getDictionary() {
        return dictionary;
    }

    /**
     * Setter dels diccionaris de l'alfabet
     * @param dictionary arraylist amb els diccionaris de l'alfabet
     */
    public void setDictionary(ArrayList<Diccionario> dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * @return arraylist dels caracters de l'alfabet
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Setter dels caracters de l'alfabet
     * @param characters arraylist dels caracters de l'alfabet
     */
    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
    
    /**
     * Afegeix un diccionari
     * @param dicc diccionari a afegir
     */
    public void addDictionary(Diccionario dicc) {
        dictionary.add(dicc);
    }
    
    /**
     * Crea un diccionari
     * @param words hashtable de les paraules i la seva frequencia del diccionari
     * @param name nom del diccionari
     * @param idAlphabet identificador de l'alfabet
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public void createDicc(Hashtable<String, Double> words, String name,int idAlphabet) throws ExcepcionesTeclado{
        Diccionario dicc = new Diccionario(name,words,idAlphabet);
        dicc.calculate(characters);
        dictionary.add(dicc); 
    }
    
    /**
     * @param i posicio del diccionari dins del conjunt de diccionaris
     * @return matriu de frequencia del diccionari a la posicio i del conjunt de diccionaris
     */
    public double[][] getMatrixF(int i){         
        return dictionary.get(i).getFrequencies(); 
    }
     
    /**
     * @param name nom del diccionari a buscar
     * @return posicio del diccionari dins del conjunt
     */
    public int findDictionary(String name) {
        int pos = -1;
        int i = 0;
        while(i < dictionary.size() && pos == -1){
              if(name.equals(dictionary.get(i).getName())) pos = i;
              i++; 
        } 
        return pos;
    }
    
    /**
     * @return nombre de caracters
     */
    public int getN(){
        return characters.size();
    }
    
    /**
     * Inserta una paraula en un diccionari identificat per nom
     * @param name nom del diccionari
     * @param word paraula a inserir
     * @param freq frequencia de la paraula a inserir
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public void insertWord(String name,String word,double freq) throws ExcepcionesTeclado {
        int p = findDictionary(name);
        dictionary.get(p).addWord(word, freq);
    }
    
    /**
     * Esborra una paraula dins d'un diccionari identificat per nom i diu si pertany a aquest diccionari
     * @param name nom del diccionari
     * @param word paraula a esborrar
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public boolean deleteWord(String name,String word) throws ExcepcionesTeclado{
        int pos = findDictionary(name);
        if(pos != -1){              
           if(dictionary.get(pos).deleteWord(word)){
                dictionary.remove(pos);                       
                return false;
           }
           else if(belongsDictionaryToAlphabet(pos)) return true;
                else return false;
        }        
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist); 
    }
    
    /**
     * Modifica la frequencia d'una paraula en un diccionari
     * @param name nom del diccionari
     * @param word paraula a modifica
     * @param freq nova frequencia de la paraula
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public void modFreq(String name, String word, double freq) throws ExcepcionesTeclado{
            int pos = findDictionary(name);
            dictionary.get(pos).modifyFreq(word, freq);
    }
         
    /**
     * Comprova si els caracters pertanyen a l'alfabet
     * @param word paraula
     * @return si la paraula pertany a l'alfabet
     */
    public boolean itBelongAlphabet(String word){
        boolean belongs = true;
        int i = 0;
        while(belongs && i < word.length()){
            Character character = word.charAt(i);
            if(!characters.contains(character)) belongs = false;
            ++i;
        }            
        return belongs;
    }
    
    /**
     * @param i posicio del diccionari dins del conjunt de diccionaris
     * @return diccionari en la posicio i del conjunt de diccionaris
     * @throws teclado.capa_dominio.ExcepcionesTeclado 
     */
    public Diccionario getDictionaryPos(int i)throws ExcepcionesTeclado{
       if(i < 0 || i >= dictionary.size()) throw new ExcepcionesTeclado(ExcepcionesTeclado.inv_pos);
       else return dictionary.get(i);       
    }
    
    /**
     * Calcula la matriu de frequencies d'un diccionari per la posicio en el conjunt
     * @param i posicio del diccionari dins del conjunt de diccionaris
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public void calculateFreqMatrixPos(int i) throws ExcepcionesTeclado {
        if(i < 0 || i >= dictionary.size()) throw new ExcepcionesTeclado(ExcepcionesTeclado.inv_pos);    
        else dictionary.get(i).calculate(characters);   
    } 
    
    /**
     * Esborra un diccionari en la posicio del conjunt
     * @param i posicio del diccionari dins del conjunt de diccionaris
     * @return si el conjunt de diccionaris es buit
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public boolean deleteDicc(int i)throws ExcepcionesTeclado{
        boolean esBuit = false;
        if(i < 0 || i >= dictionary.size()) throw new ExcepcionesTeclado(ExcepcionesTeclado.inv_pos);
        else {
            dictionary.remove(i);
            if(dictionary.isEmpty()) esBuit = true;
            return esBuit;
        }        
    }

    /**
     * Mostra els caracters de l'alfabet
     */
    public void printCharacters() { 
        for(int i= 0; i < characters.size(); i++)
            System.out.print(characters.get(i) + " "); 
        System.out.println( " \n" + characters.size());
    }

    private boolean belongsDictionaryToAlphabet(int pos) {
        int i = 0;
        boolean trobat = true;  
        while(i < characters.size() && trobat){
            if(!dictionary.get(pos).found(characters.get(i))) trobat = false;
            i++;
        }
        return trobat;       
    }
    
    /**
     * Estadisiques 2 permet saber la freqencia dels caracters en els diferents 
     * diccionaris del lalfabet
     */
       
    public void calcStatistics2() throws ExcepcionesTeclado {
        refreshFrequencies();
        System.out.println("Total de: " + characters.size() + " caracters");
        System.out.println("en " + dictionary.size() + " diccionaris");
        for(int i = 0; i < characters.size(); ++i) {
            double sum=0;
            for(int j = 0; j < dictionary.size(); ++j)
                sum += dictionary.get(j).getFreqCharacter(i);
            System.out.println("Caracter: " + characters.get(i) + " Frequencia " + sum);
        }     
    }
    
   /**
     * Comprova si no hi ha un diccionari amb aquest nom
     * @param name nom del diccionari quenom del diccionari que es vol inserir
     * @return un nom valid 
     */
    
    public String validateNameDiccionary(String name)  { 
        while(findDictionary(name) != -1){
                System.out.println("Introduzca otro nombre porque este ya existe");
                Scanner in = new Scanner(System.in);
                name = in.next();
        }
        return name;
    }
}