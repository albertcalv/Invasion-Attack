/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package teclado.capa_presentacion;
import java.io.*;
import java.util.*; 
import teclado.capa_dominio.*; 

public class Driver_Controlador {  
       
    private static String setPath(int n) throws ExcepcionesTeclado, IOException {
        String path = null;
        if(n==1) { //SetPath actua per a fitxer
            System.out.println("Entri la ruta del directori on es troben els fitxers"
                         + " diccionari:"); 
            System.out.println(">>>");      
        }
        else  //SetPath per a pagines web
            System.out.println("Entri l'adreça de la pagina web amb text pla: http:// solament");
        try {            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));   
            path = entrada.readLine(); 
        }
        catch (IOException e) {
            
        }
        return path;
    }
    
    private static String dirPath(String path) { 
        File dir = new File(path);
        String[] fitxers = dir.list();        
            if (fitxers == null)
                System.out.println("Ningun fitxer al directori");
            else {
                for (int x=0;x<fitxers.length;x++)
                    System.out.println("[" + x + "]" + fitxers[x]);
                
               System.out.println("Entri el numero corresponent al diccionari amb "
                       + " el cual vol treballar, en cas contrari escrigui -1 \n per a tornar a "
                       + " especificar una nova ruta:");
               System.out.println(">>>");               
               try {
                   BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
                   int aux = Integer.parseInt(entrada.readLine());
                   if(aux == -1) return "-1";
                   else {
                       System.out.println(fitxers[aux]);
                       return fitxers[aux];                     
                   }
               }                  
               catch (IOException e) {} 
             }        
         return "-1";       
    }
    
    private static void parseConsole(ArrayList<Character> matching, Hashtable<String, Double> dictionary) throws ExcepcionesTeclado { 
        Scanner read = new Scanner(System.in);
        System.out.println("N paraules  a introduir:");
        int N = read.nextInt();        
        System.out.println("Paraula - Frequencia");
        String word; 
        Double freq;
        for(int i=0; i < N; ++i) {
            word = read.next().toUpperCase();  
            String nword = " ".concat(word).concat(" ");
            freq = read.nextDouble();  
            if(!dictionary.containsKey(nword)){
                dictionary.put(nword,freq);
                for(int n=0;n<nword.length();n++) {
                    char c = nword.charAt(n);
                    if(!matching.contains(c)) matching.add(c); 
                }             
            }           
            else{
                dictionary.put(nword, dictionary.get(nword).doubleValue()+freq);                 
            }
        } 
    }
    
    private static void parseFile(ArrayList<Character> matching, Hashtable<String, Double> dictionary) throws ExcepcionesTeclado, IOException {
            String path = setPath(1); 
            String xfile = dirPath(path);
            String aux = path.concat("\\").concat(xfile);
            System.out.println(aux);            
            Scanner in = null;         
            in = new Scanner(new FileReader(aux));          
            String word; 
            while(in.hasNext()) {
                word = in.next().toUpperCase();
                String nword = " ".concat(word).concat(" ");
                 if (dictionary.containsKey(nword)) {
                    double freq = dictionary.get(nword) + 1;
                    dictionary.put(nword,freq);
                }
                else {
                    dictionary.put(nword,1.0);
                     for (int n=0;n<nword.length();n++) {
                           if(!matching.contains(nword.charAt(n)))
                                matching.add(nword.charAt(n));     
                     }
                 }               
            }
            in.close();           
    }
       
    private static void options() {  
        System.out.println(" 1:  Crear un nou diccionari mitjançant consola");
        System.out.println(" 2:  Crear un nou diccionari mitjançant un text"); 
        System.out.println(" 3:  Carrega un diccionari");
        System.out.println(" 4:  Afegir una nova paraula");
        System.out.println(" 5:  Eliminar una paraula");
        System.out.println(" 6:  Desar diccionari");
        System.out.println(" 7:  Consultar paraules d'un diccionari");
        System.out.println(" 8:  Modificar frequencia d'una paraula");
        System.out.println(" 9:  Consultar un alfabet");
        System.out.println("10:  Consultar un diccionari d'un alfabet");
        System.out.println("11:  Consultar teclats d'un diccionari");
        System.out.println("12:  Consultar estadistiques"); 
        System.out.println("13:  Esborrar un diccionari");
        System.out.println("14:  Calcular una solucio");
        System.out.println("15:  Esborrar un teclat");
        System.out.println("16:  Sortir"); 
    }
   
    public static void main(String args[]) throws ExcepcionesTeclado, IOException, Exception {
        System.out.println("    TECLAPRPOP");
        System.out.println("---------------------");
        Ctrl_Capa_Dominio ctrl = new Ctrl_Capa_Dominio(); 
        ctrl.loadDictPred();
        options(); 
        System.out.println("---------------------");
        System.out.println("Seleccioni una opcio");
        Scanner in = new Scanner(System.in);
        String value = in.next(); 
        
        while(!value.equals("16")) {
            try{
                if(value.equals("0")) options();
                
                else if(value.equals("1") || value.equals("2")){
                  System.out.println("Introdueix el nom del diccionari: ");
                  String name_dic = in.next(); 
                  name_dic = ctrl.validateNames(name_dic); 
                  ArrayList<Character> matching = new ArrayList<Character>();
                  Hashtable<String, Double> words = new Hashtable<String, Double>();
                  if(value.equals("1")) parseConsole(matching,words);                
                  else parseFile(matching,words);  
                  ctrl.createNewElement(name_dic,words,matching);               
                }   
                
                else  if(value.equals("3")){ 
                  ctrl.loadDictionary();                
                }
                
                else if(value.equals("4")){
                    System.out.println("Introdueix una nova paraula: ");
                    String w = in.next().toUpperCase(); 
                    String nword = " ".concat(w).concat(" ");
                    System.out.println("Introdueix la frequencia de la paraula");
                    double f = in.nextDouble();
                    System.out.println("Introdueixi el diccionari per a afegir la nova frequencia");
                    ctrl.printDictionarys();
                    String d = in.next();
                    ctrl.addElementWord(nword, f, d);                  
                }
                
                else if(value.equals("5")){                
                    System.out.println("Introdueixi el diccionari");
                    ctrl.printDictionarys();
                    String d = in.next();   
                    System.out.println("Introdueixi la paraula a eliminar: ");
                    ctrl.printWordsOfDictionary(d);
                    String w = in.next().toUpperCase(); 
                    String nword = " ".concat(w).concat(" ");
                    ctrl.deleteElement(d, nword);
                }
                
                else if(value.equals("6")){
                    System.out.println("Selecciona el diccionari a desar");
                    ctrl.printDictionarys();
                    String name = in.next();
                    ctrl.saveDictionary(name);
                }   
                
                else if(value.equals("7")){
                    System.out.println("Seleccioni el diccionari");
                    ctrl.printDictionarys();
                    String name_dicc = in.next();
                    ctrl.printWordsOfDictionary(name_dicc);
                }
                
                else if(value.equals("8")){
                    System.out.println("Introdueixi el diccionari a eliminar");
                    ctrl.printDictionarys();
                    String d = in.next();
                    System.out.println("Introdueixi la paraula a modificar: ");
                    ctrl.printWordsOfDictionary(d);
                    String w = in.next().toUpperCase(); 
                    String nword = " ".concat(w).concat(" ");
                    System.out.println("Introdueixi la frequencia de la paraula");
                    double f = in.nextDouble();
                    ctrl.modifyElement(d, nword, f);  
                }
                
                else if(value.equals("9")){
                    System.out.println("Seleccioni l'alfabet");
                    ctrl.alphabetsOfTheSystem();
                    String name_ap = in.next();                
                    ctrl.printCharactersAlph(name_ap);
                }
                
                else if(value.equals("10")){
                    System.out.println("Seleccioni l'alfabet");
                    ctrl.alphabetsOfTheSystem();
                    String alf = in.next();
                    ctrl.printDictionarys(alf);                
                }
                
                else if(value.equals("11")){
                    System.out.println("Seleccioni el diccionari"); 
                    ctrl.printDictionarys();
                    String name_dic = in.next();
                    ctrl.keyboardsOfDictionarys(name_dic);
                }
                
                else if(value.equals("12")){
                        System.out.println("Seleccioni quin tipus d'estadistiques vol realitzar");
                        System.out.println("1: Comparar un diccionari per a diferents topologies");
                        System.out.println("2: Comparar caracters d'un alfabet");
                        System.out.println("3: Comparar una topologia per a diferents diccionaris");
                        int op = in.nextInt();
                        
                        if(op == 1){            
                            System.out.println("Seleccioni el diccionari");
                            ctrl.printDictionarys();
                            String name_d = in.next(); 
                            ctrl.calculateStatistics1(name_d);                    
                        }
                        else if(op == 2){
                            System.out.println("Seleccioni un alfabet");
                            ctrl.alphabetsOfTheSystem();
                            String name_a = in.next();
                            ctrl.calculateStatistics2(name_a);                   
                        }
                        else if(op == 3){
                            System.out.println("Seleccioni la topologia. 1: Triangular, 2: Rectangular, 3: Rodo ");
                            int top = in.nextInt();
                            System.out.println("Seleccioni un alfabet");
                            ctrl.alphabetsOfTheSystem();
                            String name_alp = in.next();
                            
                            System.out.println("Introdueixi els noms dels diccionaris,per acabar '.' ");
                            ctrl.printDictionarys(name_alp);
                            ArrayList<String> names_dicc = new ArrayList<String>();
                            String name_dic = in.next();
                              while(!name_dic.equals(".")){
                                names_dicc.add(name_dic);
                                name_dic = in.next();                        
                            } 
                            ctrl.calculateStatistics3(top,names_dicc);
                        }
                        else System.out.println("Opcio incorrecte, torna a comença"); 
                    } 
                
                    else if(value.equals("13")){
                        System.out.println("Seleccioni el diccionari a eliminar");
                        ctrl.printDictionarys();
                        String name_dicc = in.next();
                        ctrl.deleteDict(name_dicc);
                    }

                    else if(value.equals("14")){
                        System.out.println("Seleccioni un diccionari");
                        ctrl.printDictionarys();
                        String name = in.next();
                        System.out.println("Seleccioni la topologia. 1: Triangular, 2: Rectangular, 3: Rodo ");
                        int type_keyboard = in.nextInt(); 
                        System.out.println("Seleccioni l algorisme: 1: B&B Cota Gilmore, 2: Heuristic1, 3: Heuristic2");
                        int alg = in.nextInt();
                        ctrl.generateSolution(name, type_keyboard, alg);
                    }

                    else if(value.equals("15")){
                          System.out.println("Introduixi el nom del teclat a eliminar");
                          ctrl.printKeyboards();
                          String name_k = in.next();
                          ctrl.deleteKeyboard(name_k);
                    } 

                    else System.out.println("Seleccioni una nova opcio");
                    value = in.next();
                }
            catch(ExcepcionesTeclado e){
                System.out.println(e.getMessage());
                System.out.println("Seleccioni l accio correcta o premi 0 per a veure les opcions");
                value = in.next();
            }
          System.out.println("---------------------");  
        }         
    }

    
    
}

    

