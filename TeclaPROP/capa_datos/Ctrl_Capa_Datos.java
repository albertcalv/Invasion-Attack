package teclado.capa_datos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 *
 * @autor evelyn.rovira
 */
public class Ctrl_Capa_Datos {
    
    
    private Hashtable<String, Double> words;
    private ArrayList<Character> matching; 
    private String path;
    private String name;
      
     /**
     * Constructora per defecte
     */
    public Ctrl_Capa_Datos() {
        words = new Hashtable<String, Double>();
        matching = new ArrayList<Character>();
    }

    /**
     * Retorna la hastable de paraules amb la seva frequencia
     * @return hashtable que representa un conjunt de paraules
     */
    public Hashtable<String, Double> getWords() {
        return words;
    }
    
    /**
     * Permet asignar la hastable 
     * @param words hastable de paraules amb la seva frequencia
     */
    public void setWords(Hashtable<String, Double> words) {
        this.words = words;
    }

     /**
     * Permet obtenir l'array de matching
     * @return matching array amb els caracters 
     */
    public ArrayList<Character> getMatching() {
        return matching;
    }

     /**
     * Permet llegir els diccionaris predefinits al sistema
     * @param opc integer que defineix els tres dicionaris predefinits al sistema
     */
    public void load(int opc) throws FileNotFoundException, IOException{
        InteraccUser iu = new InteraccUser();
        
        switch(opc) {
            case 1: iu.Rdictionary(0,1);
                    path = iu.getPath();
                break;
                
            case 2: iu.setPath(path);
                    iu.Rdictionary(0,2); 
                break;
                
            case 3: iu.setPath(path);
                    iu.Rdictionary(0,3);
                break;
        }
        words = iu.getDictionary();
        matching = iu.getMatching();
    }   
    
     /**
     * Permet desar un diccionari al sistema 
     * @param name nom del diccionari a desar
     * @param words hastable de paraules amb la seva frequencia
     */
    public void saveDictionary(String name,Hashtable<String, Double> words) throws IOException {
        InteraccUser iu = new InteraccUser(); 
        iu.Wdictionary(name,words); 
    }
    
        
     /**
     * Permet carregar un diccionari al sistema
     */
    public void loadDictionary() throws FileNotFoundException, IOException {
            InteraccUser iu = new InteraccUser(); 
            iu.Rdictionary(1,0);
            words = iu.getDictionary();
            matching = iu.getMatching();
            name = iu.getName();
    }
    
        
     /**
     * Retorna el nom del diccionari
     * @return name, nom del diccionari
     */
    public String getNameDictionary() {
        return name;
    }
    
        
     /**
     * Permet un diccionari de consola
     */
    public void readConsole() {
         InteraccUser iu = new InteraccUser(); 
         iu.parseConsole();
         words = iu.getDictionary();
         matching = iu.getMatching();
    }
    
    
     /**
     * Permet llegir un text desat a un directori 
     */
    public void readText() throws IOException {
         InteraccUser iu = new InteraccUser(); 
         iu.parseFile();
         words = iu.getDictionary();
         matching = iu.getMatching();
    }
}
