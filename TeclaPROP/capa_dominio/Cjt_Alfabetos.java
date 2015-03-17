package teclado.capa_dominio;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @autor sergi.galicia
 */
public class Cjt_Alfabetos {
    private ArrayList<Alfabeto> alphabets;
    
     /**
     * Constructora per defecte
     */
    public Cjt_Alfabetos(){
        alphabets = new ArrayList<Alfabeto>(); 
    }
    
     /**
     * Permet obtenir l'arraylist del conjunt alfabets
     * @return array amb el conjunt de alfabets
     */
    public ArrayList<Alfabeto> getAlphabets() {
        return alphabets;
    }

     /**
     * Permet definir un nou arraylist de alfabets
     * @param alphabets arraylist que representa un conjunt de alfabets
     */
    public void setAlphabets(ArrayList<Alfabeto> alphabets) {
        this.alphabets = alphabets;
    }
    
    /**
     * Permet obtenir un alphabet donada una posicio
     * @param i posicio on es troba el alfabet
     */
    public Alfabeto getAlphabetPos(int i){
        return alphabets.get(i);
    }
    
    /**
     * Retorna la posicio de lalfabet que conte el character
     * @param c arraylist de caracters
     * @return posicio on es troba el caracter
     */
    public int chekAlphabetByCharacters(ArrayList<Character> c) { 
        int position = -1;
        int i = 0; 
        while(i < alphabets.size() && position == -1) {
            if(alphabets.get(i).getCharacters().size() == c.size()) { 
                int j = 0;
                while(j < c.size() && alphabets.get(i).getCharacters().contains(c.get(j)))
                      j++;                        
                if(j == c.size()) position = i;                
            }            
            i++;
        }        
        return position;
   }
    
    /**
     * Permet crear un nou alfabet 
     * @param name_Alp nom del nou Alfabet
     * @param name_Dicc nom del diccionari
     * @param words hashtable que representa un diccionari conte paraula - frequencia 
     * @return posicio on es crea el nou alfabet
     * @throws ExcepcionesTeclado
     */
    public int createAlph(String name_Alp, ArrayList<Character> characters, String name_Dicc, Hashtable<String, Double> words) throws ExcepcionesTeclado{
        Alfabeto alp = new Alfabeto(name_Alp);
        alp.setCharacters(characters); 
        alphabets.add(alp);
        int pos = alphabets.indexOf(alp);
        alp.createDicc(words, name_Dicc, pos);
        return pos;
    }
    
    /**
     * Permet obtenir la posicio d'un alfabet a partir del seu nom
     * @param name nom del alfabet a cercar
     * @return posicio on es troba l'alfabet
     */
    public int findAlphabet(String name){
        int pos = -1;
        int i = 0;
        while(i < alphabets.size() && pos == -1){
            if(name.equals(alphabets.get(i).getName())) pos = i;
            i++;
        }
        return pos;
    }
   
    /**
     * Permet obtenir la posicio d'un diccionari
     * @param name nom del diccionari
     * @return posicio on es troba el diccionari
     */
    public int existDictionary(String name) {
        int pos = -1;
        int i = 0;
        while (i < alphabets.size() && pos == -1){
            if(alphabets.get(i).findDictionary(name) != -1) pos = i;
            ++i;
        } 
        return pos;
    }
    
    /**
     * Permet afegir un nou alfabe
     * @param name_Dicc nom del diccionari
     * @param words hashtable que representa un diccionari conte paraula - frequencia 
     * @param characters arraylist de caracters
     * @throws ExcepcionesTeclado
     */
    public void newElement(String name_Dicc, Hashtable<String, Double> words, ArrayList<Character> characters)throws ExcepcionesTeclado{ 
        int same = chekAlphabetByCharacters(characters);   
        if(same != -1 ) alphabets.get(same).createDicc(words, name_Dicc, same);
        else {
                System.out.println("Introduce el nombre del alfabeto");
                Scanner in = new Scanner(System.in);
                String name_Alp = in.next(); 
                name_Alp = validateNameAlphabet(name_Alp);
                createAlph(name_Alp, characters, name_Dicc, words);
       }   
    }
    
       /**
     * Permet afegir una nova paraula al diccionari 
     * @param dicc nom del diccionari
     * @param new_word nova paraula a afegit 
     * @param new_frew frequencia de la paraula
     * @param dic diccionari on es vol afegir la paraula
     * @throws ExcepcionesTeclado
     */
    public void addElement(String new_word, Double new_freq, String dic)throws ExcepcionesTeclado {
        if(dic.equals("Catala") || dic.equals("English") || dic.equals("Castellano")) throw new ExcepcionesTeclado(ExcepcionesTeclado.not_mod_preed); 
        int pos_a = existDictionary(dic);  
        if(pos_a == -1) throw  new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);
        int pos_dic = alphabets.get(pos_a).findDictionary(dic);
        boolean b = alphabets.get(pos_a).itBelongAlphabet(new_word);  
        if(b) alphabets.get(pos_a).insertWord(dic, new_word , new_freq);
        else{
            Hashtable<String, Double> h_new = new Hashtable<String,Double>();
            h_new = alphabets.get(pos_a).getDictionaryPos(pos_dic).getWords();
            h_new.put(new_word,new_freq);
            ArrayList<Character> chars = new ArrayList<Character>();
            createMatching(chars, h_new);            
            int d = chekAlphabetByCharacters(chars); 
            if(d != -1)                
                 alphabets.get(d).createDicc(h_new, dic, pos_a);           
            else{
                System.out.println("Introduce el nombre del alfabeto");
                Scanner in = new Scanner(System.in);
                String name_Alp = in.next();    
                name_Alp = validateNameAlphabet(name_Alp);
                createAlph(name_Alp,chars,dic,h_new);
            }
            boolean vacio = alphabets.get(pos_a).deleteDicc(pos_dic);
            if(vacio) alphabets.remove(pos_a);
        }          
    }
    
       /**
     * Permet llistar tots els Alfabets 
     */
    void printAlphabets() {
        for(int i = 0; i < alphabets.size(); i++){
            System.out.println(alphabets.get(i).getName());
        }
    }

       /**
     * Permet eliminar una paraula d'un diccioanri
     * @param dic nom del diccionari
     * @param p paraula que es vol afegir 
     * @throws ExcepcionesTeclado
     */
    void deleteElement(String dic, String p) throws ExcepcionesTeclado{
        if(dic.equals("Catala") || dic.equals("English") || dic.equals("Castellano")) throw new ExcepcionesTeclado(ExcepcionesTeclado.not_mod_preed);
        int pos_a = existDictionary(dic);
        if(pos_a == -1) throw  new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);
        boolean pertany = alphabets.get(pos_a).deleteWord(dic, p);
        if(alphabets.get(pos_a).getDictionary().isEmpty()) alphabets.remove(pos_a);
        else{         
            if(!pertany){                
            int pos_d =  alphabets.get(pos_a).findDictionary(dic);             
            Hashtable<String, Double> h_new = alphabets.get(pos_a).getDictionaryPos(pos_d).getWords(); 
            int new_p = belongsWordsToAlphabet(h_new);
                if(new_p != -1){ 
                    alphabets.get(new_p).createDicc(h_new, dic, new_p);                    
                } 
            
                else {
                    System.out.println("Introduce el nombre del alfabeto");
                    Scanner in = new Scanner(System.in);
                    String name_Alp = in.next();
                    name_Alp = validateNameAlphabet(name_Alp);
                    ArrayList<Character> matching = new ArrayList<Character>();
                    createMatching(matching, h_new);
                    createAlph(name_Alp,matching,dic,h_new);
                    boolean vacio = alphabets.get(pos_a).deleteDicc(pos_d);
                    if(vacio) alphabets.remove(pos_a);
                }
            } 
        }
    }
    
       /**
     * Retorna la posicio on existeix un Alfabet amb un diccionari identic que 
     * el pasat per parametre 
     * @param words hashtable que representa un diccionari conte paraula - frequencia 
     * @return posicio on es troba el alfabet
     */
    public int belongsWordsToAlphabet(Hashtable<String, Double> words){
        int pos = -1;
        int i = 0;
        while(i < alphabets.size() && pos == -1){
            if(compare(words, alphabets.get(i).getCharacters())) pos = i;
            i++;       
        }
        return pos;    
    }

       /**
     * Permet afegir un nou alfabe
     * @param name_Dicc nom del diccionari
     * @param words hashtable que representa un diccionari conte paraula - frequencia 
     * @param characters arraylist de caracters
     */
    public boolean compare(Hashtable<String, Double> h_new , ArrayList<Character> matching){
       boolean trobat = false;
       boolean pertany = false;
       Character c;
       int i = 0;
       while(i < matching.size() && !pertany){
            c = matching.get(i);
            trobat = false;
            Enumeration<String> en = h_new.keys();
            while(en.hasMoreElements() && !trobat){
                String word = en.nextElement(); 
                int j = 0;
                while(j < word.length() && !trobat){
                    if(c.equals(word.charAt(j))){ 
                        trobat = true;
                    }
                    ++j;
                }
            }
            if(!en.hasMoreElements() && !trobat) pertany = true;
            i++;
        }
        if(i == matching.size() && !pertany) return true;
        else return false;        
    }
    
       /**
     * Permet modificar la frequencia d'una paraula
     * @param dicc nom del diccionari on esta la paraula
     * @param p nom de la paraula 
     * @param freq frequencia de la paraula a modificar
     * @throws ExcepcionesTeclado
     */
    public void modifyFreqWord(String dic, String p, double freq)throws ExcepcionesTeclado{ 
        int pos_a = existDictionary(dic); 
        if(pos_a != -1) alphabets.get(pos_a).modFreq(dic, p, freq);        
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);        
    }
   
       /**
     * Retorna el numero de caracters d'un diccionari
     * @param dic nom del diccionari
     * @return numero de caracters del diccionari 
     * @throws ExcepcionesTeclado
     */
    public int getN(String dic) throws ExcepcionesTeclado {
        int pos_a = existDictionary(dic);
        if( pos_a != -1) { 
            return alphabets.get(pos_a).getN();
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);
    }
    
       /**
     * Permet obtenir l'Id del diccionari
     * @param dic nom del diccionari
     * @return id que identifica el diccionari
     * @throws ExcepcionesTeclado
     */
    public int getIdDic(String dic) throws ExcepcionesTeclado { 
        int pos_a = existDictionary(dic);
        if( pos_a != -1) { 
            return alphabets.get(pos_a).findDictionary(dic);
        }
        else throw  new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist); 
    }

       /**
     * Permet calcular les frequencies d'un diccioanari
     * @param dic nom del diccionari
     * @throws ExcepcionesTeclado
     */
    public void calculateFreqPos(String dic) throws ExcepcionesTeclado { 
        int pos_a = existDictionary(dic);
        if(pos_a != -1) {
            int pos_d = alphabets.get(pos_a).findDictionary(dic);
            alphabets.get(pos_a).getDictionaryPos(pos_d).calculate(alphabets.get(pos_a).getCharacters());
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);
    }

       /**
     * Permet obtenir la Matriu de frequencies 
     * @param dic  nom del diccionari
     * @return matriu de frequencies
     * @throws ExcepcionesTeclado
     */
    public double[][] getMatrixFreqPos(String dic) throws ExcepcionesTeclado { 
        int pos_a = existDictionary(dic);
        if(pos_a != -1){
            int pos_d = alphabets.get(pos_a).findDictionary(dic);
            return alphabets.get(pos_a).getDictionaryPos(pos_d).getFrequencies();
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);
    }

       /**
     * Permet llistar un diccionari identificat per un nom 
     * @param name nom del diccionari
     * @throws ExcepcionesTeclado
     */
    public void printDictionarys(String name) throws ExcepcionesTeclado { 
        System.out.println("Alfabet: " + name);
        int pos = findAlphabet(name);
        if(pos != -1) {
            String name_dicc = null;
            for(int i = 0; i < alphabets.get(pos).getDictionary().size(); i++){
                name_dicc = alphabets.get(pos).getDictionaryPos(i).getName();
                System.out.println(name_dicc);
            }
        }
        else throw  new ExcepcionesTeclado(ExcepcionesTeclado.no_exist_alph);
    }

       /**
     * Permet llistar els dicionaris dels diferents alfabets
     * @throws ExcepcionesTeclado
     */
    public void printDictionarys() throws ExcepcionesTeclado { 
        for(int i = 0; i < alphabets.size(); i++){
            printDictionarys(alphabets.get(i).getName());
            System.out.println(" ");
        }
    }

       /**
     * Permet esborrar un diccionari
     * @param name_Dicc nom del diccionari
     * @throws ExcepcionesTeclado
     */
    public void deleteDict(String name_dicc) throws ExcepcionesTeclado { 
        if(name_dicc.equals("Catala") || name_dicc.equals("English") || name_dicc.equals("Castellano")) throw new ExcepcionesTeclado(ExcepcionesTeclado.not_mod_preed);
        int pos_a = existDictionary(name_dicc);
        if(pos_a != -1) {
            int pos_d = alphabets.get(pos_a).findDictionary(name_dicc);
            alphabets.get(pos_a).deleteDicc(pos_d);
            if(alphabets.get(pos_a).getDictionary().isEmpty())
                alphabets.remove(pos_a);
        }
        else throw  new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);
        
    }

    /**
     * Permet llistar les paraules d'un diccionari
     * @param d nom del diccionari
     * @throws ExcepcionesTeclado 
     */
    public void printWordsOfDictionary(String d) throws ExcepcionesTeclado { 
        int pos_a = existDictionary(d); 
        if(pos_a != -1){
           int pos_d = alphabets.get(pos_a).findDictionary(d);
           alphabets.get(pos_a).getDictionaryPos(pos_d).printWords();
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);
    }

    /**Permet llistar els caracters d'un lafabet
     * @param name_ap nom del alfabet
     * @throws ExcepcionesTeclado 
     */
    public void printCharactersPos(String name_ap) throws ExcepcionesTeclado {
        int pos = findAlphabet(name_ap);
        if(pos != -1) alphabets.get(pos).printCharacters();
        else throw  new ExcepcionesTeclado(ExcepcionesTeclado.no_exist_alph);
    }

    /**
     * Permet crear larray de matching
     * @param matching array de caracters 
     * @param words hahtable de paraula-freq
     */   
    private void createMatching(ArrayList<Character> matching, Hashtable<String, Double> words) {        
         for(Map.Entry<String, Double> entry : words.entrySet()){
               String word = entry.getKey();
                 for(int n=0;n<word.length();n++) {
                    char c = word.charAt(n);
                    if(!matching.contains(c)) matching.add(c); 
                }
         }
    }
     
    /**
     * Permet normalitzar el nom del diccionari
     * @param name nom del diccionari
     * @return nom del diccionari normalitzat
     */
    public String validateNameDiccionary(String name)  { 
        String new_name = name;
        for(int i = 0; i < alphabets.size();i++)
           new_name = alphabets.get(i).validateNameDiccionary(new_name);
        return new_name;
    }

    /**
     * Permet coneixer si existeix el nom dun Alfabet
     * @param name nom del alfabet
     * @return nom del alfabet
     */
    public String validateNameAlphabet(String name) { 
        while(findAlphabet(name) != -1){
                System.out.println("Introduzca otro nombre porque este ya existe");
                Scanner in = new Scanner(System.in);
                name = in.next();
        }
        return name;
    }

    /**
     * 
     * Permet calcular les estadistiques 2 
     * @param name nom del alfabet
     * @throws ExcepcionesTeclado 
     */
    public void statistics2(String name) throws ExcepcionesTeclado{
        int pos = findAlphabet(name);
        if(pos==-1) throw new ExcepcionesTeclado(ExcepcionesTeclado.no_exist_alph);
        alphabets.get(pos).calcStatistics2();
    }
 
}