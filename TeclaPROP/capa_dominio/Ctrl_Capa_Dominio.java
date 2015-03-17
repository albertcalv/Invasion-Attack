package teclado.capa_dominio;

import java.io.*; 
import java.util.*;  
import teclado.capa_datos.Ctrl_Capa_Datos;

/**
 *
 * @autor evelyn.rovira
 */
public class Ctrl_Capa_Dominio {  
    private Cjt_Alfabetos alphabets;
    private Cjt_Teclados keyboards;
    private Cjt_Soluciones solutions; 
    private Ctrl_Capa_Datos ctrl;
    
     /**
     * Constructora
     */    
    public Ctrl_Capa_Dominio(){  
        alphabets = new Cjt_Alfabetos();
        solutions = new Cjt_Soluciones(); 
        keyboards = new Cjt_Teclados();
        ctrl = new Ctrl_Capa_Datos(); 
    } 
    
     /**
     * Carrega els diccionaris predeterminats del sisteme
     * @throws  teclado.capa_dominio.ExcepcionesTeclado
     * @throws  FileNotFoundException
     * @throws  IOException
     */    
    public void loadDictPred() throws ExcepcionesTeclado, FileNotFoundException, IOException {
        Hashtable<String, Double> words = new Hashtable<String, Double>();
        ArrayList<Character> match = new ArrayList<Character>();
        String name = null;
        for (int i = 1; i < 4; i++){
             ctrl.load(i);        
             words = ctrl.getWords();
             match = ctrl.getMatching();
             switch (i){
                     case 1: name = "Catala"; break;
                     case 2: name = "Castellano"; break;
                     case 3: name = "English"; break;
             }
            alphabets.createAlph(name, match, name, words);        
        }  
    }
    
    /**
     * Crea un nou diccionari
     * @param name_Dicc nom del dicionari
     * @param words paraules amb la seva freqüència
     * @param characters caràcters de l'alfabet
     * @throws  teclado.capa_dominio.ExcepcionesTeclado 
     */
    public void createNewElement(String name_Dicc, Hashtable<String, Double> words, ArrayList<Character> characters) throws ExcepcionesTeclado {  
        alphabets.newElement(name_Dicc, words,characters);
    }
    
     /**
     * Mostra per pantalla els alfabets del sistema
     */ 
    public void alphabetsOfTheSystem(){
        alphabets.printAlphabets();        
    }
    
    /**
     * Afegeix una nova paraula a un diccionari
     * @param new_word nova paraula
     * @param new_freq frequencia de la nova paraula
     * @param dic diccionari al qual es vol afegir la nova paraula
     * @throws teclado.capa_dominio.ExcepcionesTeclado    
     */     
    public void addElementWord(String new_word, double new_freq, String dic) throws ExcepcionesTeclado{     
        alphabets.addElement(new_word,new_freq,dic);
    } 
    
    /**
     * Esborra una paraula de un dicionari
     * @param dic diccinari del qual es vol esborrar la paraula
     * @param p paraula que es vol esborrar
     * @throws ExcepcionesTeclado 
     */ 
    public void deleteElement(String dic, String p) throws ExcepcionesTeclado {
        alphabets.deleteElement(dic,p);
    }
    
    /**
     * Modifica la frequencia de una paraula
     * @param dic dicionari del qual es vol modificar la paraula
     * @param p paraula de la qual es vol modificar la freqüència
     * @param freq nova frequencia
     * @throws ExcepcionesTeclado 
     */
    public void modifyElement(String dic, String p, double freq) throws ExcepcionesTeclado{
       alphabets.modifyFreqWord(dic,p,freq);
    }
    
    /**
     * Mostra per patalla tots els diccionaris del un alfabet
     * @param name_al alfabet del qual es vol mostrar els seus diccinaris
     * @throws ExcepcionesTeclado 
     */
    public void printDictionarys(String name_al) throws ExcepcionesTeclado{
        alphabets.printDictionarys(name_al);
    }
    
    /**
     * Mostra per patalla tots els diccionaris del sisteme
     * @throws ExcepcionesTeclado 
     */
    public void printDictionarys() throws ExcepcionesTeclado{
        alphabets.printDictionarys();
    }
    
    /**
     * genera una solució
     * @param dic diccionari per qual es vol generar una solucio
     * @param type_keyboard tipus de topolgia
     * @param alg tipus d'algorisme emprat
     * @throws ExcepcionesTeclado 
     */
    public void generateSolution(String dic, int type_keyboard, int alg) throws ExcepcionesTeclado{    
        int n = alphabets.getN(dic);
        int a = alphabets.existDictionary(dic);
        //int d = alphabets.getIdDic(dic);   
        if(a == -1) throw  new ExcepcionesTeclado(ExcepcionesTeclado.dicc_noexist);
        double[][] matrix_freq = new double[n][n];
        alphabets.calculateFreqPos(dic);
        matrix_freq = alphabets.getMatrixFreqPos(dic); 
        int pos_k  = keyboards.posKeyboard(dic.concat(String.valueOf(type_keyboard)));
        if( pos_k == -1)
            pos_k = keyboards.newKeyboard(type_keyboard, dic); 
        double[][] matrix_dist = new double[n][n];
        keyboards.calculateDistPos(pos_k,n);
        String name_k = keyboards.getTeclado(pos_k).getName();
        matrix_dist = keyboards.getMatrixDistancesPos(name_k); 
        ArrayList<Character> map = new ArrayList<Character>();
        map = alphabets.getAlphabetPos(a).getCharacters();  
        System.out.println("Buscant solució... ");
        solutions.generatesSolution(matrix_freq, matrix_dist, map, alg, dic, name_k); 
        int id = solutions.getPosSolution(dic, name_k);
        solutions.printSolution(id);        
    }
    /**
     * Mostra per pantalla els teclats asociats a un diccionari
     * @param name_dic diccionari del qual es vol saber els seus teclats asociats
     * @throws ExcepcionesTeclado 
     */
    public void keyboardsOfDictionarys(String name_dic) throws ExcepcionesTeclado {  
        solutions.getKeyboardsOfDic(name_dic);      
    }

    /***
     * Esborrar un diccionari
     * @param name_dicc nom del diccionari que es vol esborrar
     * @throws ExcepcionesTeclado 
     */
    public void deleteDict(String name_dicc) throws ExcepcionesTeclado{ 
        alphabets.deleteDict(name_dicc);
        solutions.deleteSolutionDictionary(name_dicc);
    }

    /**
     * Calcula para un dicionari la estadistca1
     * @param name_d nom del diccionari que es vol calcular la estadictica
     * @throws ExcepcionesTeclado 
     */
    public void calculateStatistics1(String name_d) throws ExcepcionesTeclado {
        int pos_a = alphabets.existDictionary(name_d);
        int pos_d = alphabets.getIdDic(name_d);
        int n_sol = solutions.numberSolutions(name_d);
        Hashtable<String, Double> words = new Hashtable<String, Double>();
        ArrayList<Character> matching = new ArrayList<Character>();
        words = alphabets.getAlphabetPos(pos_a).getDictionaryPos(pos_d).getWords();
        matching = alphabets.getAlphabetPos(pos_a).getCharacters();
        int n = alphabets.getN(name_d);
        double[][] mat_dist = new double[n][n];
        int last_position = -2;
        int top; 
        String name_k;
        for(int i = 0; i < n_sol; i++){  
            last_position = solutions.getPartSol(name_d, last_position);
            name_k = solutions.getSolutions().get(last_position).getNameKeyboard();
            mat_dist = keyboards.getMatrixDistancesPos(name_k);  
            top = keyboards.getTypeOfKeyboard(name_k); 
            solutions.statistics1(last_position, mat_dist, words, matching, top);
        } 
    }
    
    /**
     * Calcula la estadictca2 para un diccionari
     * @param name nom del diccionari per el qual es vol calcular la estadictica2
     * @throws ExcepcionesTeclado 
     */
    public void calculateStatistics2(String name) throws ExcepcionesTeclado {
        alphabets.statistics2(name);
    }     
    
    /**
     * Carrega un diccionari 
     * @throws FileNotFoundException
     * @throws ExcepcionesTeclado
     * @throws IOException 
     */
    public void loadDictionary() throws FileNotFoundException, ExcepcionesTeclado, IOException {
        ctrl.loadDictionary();
        Hashtable<String, Double> words = new Hashtable<String, Double>();
        ArrayList<Character> match = new ArrayList<Character>();
        words = ctrl.getWords();
        match = ctrl.getMatching();
        String name = ctrl.getNameDictionary();
        name = alphabets.validateNameDiccionary(name);
        createNewElement(name, words, match);
    }
    
    /**
     * Desa un diccionari
     * @param name nom del dcicionari que es vol desa
     * @throws ExcepcionesTeclado
     * @throws IOException 
     */
    public void saveDictionary(String name) throws ExcepcionesTeclado, IOException {
        Hashtable<String, Double> words = new Hashtable<String, Double>(); 
        int pos_a = alphabets.existDictionary(name);
        int pos_d = alphabets.getAlphabetPos(pos_a).findDictionary(name);
        words = alphabets.getAlphabetPos(pos_a).getDictionaryPos(pos_d).getWords();
        ctrl.saveDictionary(name,words);
    }

    /**
     * Mostra per pantalla les paraules de un diccionari
     * @param d nom del diccionari del qual es vol veure les sevas paraules
     * @throws ExcepcionesTeclado 
     */
    public void printWordsOfDictionary(String d) throws ExcepcionesTeclado { 
        alphabets.printWordsOfDictionary(d);
    }

    /**
     * Mostra per pantalla els caracters de un alfabet
     * @param name_ap nom del alfabet del que es vol veure els seus caracters
     * @throws ExcepcionesTeclado 
     */
    public void printCharactersAlph(String name_ap) throws ExcepcionesTeclado {
        alphabets.printCharactersPos(name_ap);
    }

    /**
     * Comprovar que no exiteix cap altre diccionari amb aquest nom
     * @param name nom que es vol comprovar 
     * @return el nom valid
     * @throws ExcepcionesTeclado 
     */
    public String validateNames(String name) throws ExcepcionesTeclado {         
        return alphabets.validateNameDiccionary(name); 
    }

    /**
     * Calcula la estadictica 3
     * @param top tipus de topologia
     * @param names_dicc nom del diccionari que es vol calcular la estadistica
     * @throws ExcepcionesTeclado 
     */
    public void calculateStatistics3(int top, ArrayList<String> names_dicc) throws ExcepcionesTeclado {
        Hashtable<String, Double> words = new Hashtable<String, Double>();
        ArrayList<Character> match = new ArrayList<Character>();  
        double[][] mat_dis = new double[match.size()][match.size()]; 
        for(int i = 0; i < names_dicc.size(); i++){
            String name_dic = names_dicc.get(i);
            String name_k = name_dic.concat(String.valueOf(top));
            int pos_sol = solutions.getPosSolKeyboard(name_k);
            if(pos_sol != -1) {
                int pos_a = alphabets.existDictionary(name_dic);
                if(pos_a != -1){ 
                    int pos_d = alphabets.getIdDic(name_dic);
                    words = alphabets.getAlphabetPos(pos_a).getDictionaryPos(pos_d).getWords();
                    match = alphabets.getAlphabetPos(pos_a).getCharacters();
                    mat_dis = keyboards.getMatrixDistancesPos(name_k);
                    solutions.statistics3(pos_sol, mat_dis, words, match, top);
                }
            }                   
        }       
    }

    /**
     * Mostra per pantalla els teclats existents 
     */
    public void printKeyboards() {
        keyboards.printKeyboards();
    }

    /**
     * Esborra un teclat
     * @param name_k nom del teclat que es vol esborrar
     * @throws ExcepcionesTeclado 
     */
    public void deleteKeyboard(String name_k) throws ExcepcionesTeclado { 
        keyboards.deleteKeyboard(name_k);
        solutions.deleteSolutionKeyboard(name_k);
    }   
    
}
    
