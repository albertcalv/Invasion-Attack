package teclado.capa_datos;

import java.io.*;
import java.util.*;

/**
 *
 * @autor albert.calvo.ibanez
 */
public class InteraccUser {

    private static Hashtable<String,Double> dictionary;  
    private static ArrayList<Character> matching;
    private static String path;
    private static String name;

     /**
     * Permet obtenir el path 
     * @return path 
     */
    public static String getPath() {
        return path;
    }

     /**
     * Permet asignar un path 
     * @params path nou path
     */
    public static void setPath(String path) {
        InteraccUser.path = path;
    }
    
     /**
     * Llegeix quin es el path introduit per l'usuari
     * @params n on n es un identificador per les diferents opcions
     */
    private static void setPath(int n) throws IOException { 
        if(n==0)  
               System.out.println("Ruta dels diccionaris per defecte:"); 
       else if(n==1) 
            System.out.println("Ruta del diccionari a llegir:"); 
        else if(n==2) 
            System.out.println("Ruta per a desar el diccionari");
        else if(n==3)
            System.out.println("Ruta per a cercar un arxiu que conte un text");
        else 
            System.out.println("Entri l'adreça de la pagina web amb text pla: http:// solament");   
        
            BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));   
            path = entrada.readLine();     
    } 

     /**
     * Llista el contingut dun path i l'usuari selecciona un arxiu
     * @param path a llistar
     * @return arxiu seleccionat per l'usuari o -1 si es erroni
     */
    private static String dirPath(String path) {
        File dir = new File(path);
        String[] fitxers = dir.list();        
            if (fitxers == null)
                System.out.println("Ningun fitxer al directori");
            else {
                for (int x=0;x<fitxers.length;x++)
                    System.out.println("[" + x + "]" + fitxers[x]);
                
               System.out.println("Entri el numero corresponent al txt cas contrari premi -1 per a sortir");          
               try {
                   BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
                   int opc = Integer.parseInt(entrada.readLine());
                   if(opc == -1) return "-1";
                   else  return fitxers[opc];
               }                  
               catch (IOException e) {} 
            }        
         return "-1";  
    }
    
     /**
     * Permet llegir un fitxer 
     * @param path indica la ruta on es troba el fitxer
     */
    private void readfile(String path) throws FileNotFoundException {
         Scanner in = null;
         in = new Scanner(new FileReader(path));
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
    
     /**
     * Permet llegir un diccionari
     * @param path indica la ruta on es troba el fitxer amb un diccionari
     */
    private void readdictionary(String path) throws FileNotFoundException {
           Scanner in = null;
           in = new Scanner(new FileReader(path));
           String word;
           double freq = 0; 
            while(in.hasNext()) {
               word = in.next().toUpperCase(); 
               freq = in.nextDouble();  
               String nword = " ".concat(word).concat(" ");
               dictionary.put(nword,freq);
                for (int n=0;n<nword.length();n++) {
                       if(!matching.contains(nword.charAt(n)))
                            matching.add(nword.charAt(n));     
                }
            }
     }
    
     /**
     * Permet treure les 4 ultimes posicions dun string
     * @return l'string original menys els cuatre ultims caracters
     */
    private String normalitza(String name) { 
        return  name.substring(0, name.length() - 4); 
    }
    /**
     * Constructora per defecte
     */
    public InteraccUser(){
        dictionary = new Hashtable<String, Double>();
        matching = new ArrayList<Character>(); 
        path = " ";
     }
     
     /**
     * Retorna larray de matching 
     * @return array de caracters
     */
    public  ArrayList<Character> getMatching() {
        return matching;
    }
    
     /**
     * Retorna el diccionari
     * @return hashtable diccionari
     */
    public Hashtable<String, Double> getDictionary() {
        return dictionary;
    }
    
     /**
     * Permet llegir dun arxiu amb un text 
     * @return name, nom del diccionari
     */
    public void parseFile() throws FileNotFoundException, IOException {
         setPath(3);
         String arxiu = dirPath(path);
         String pathc = path.concat("\\").concat(arxiu); 
         if(!arxiu.equals("-1")) readfile(pathc); 
    }
   
     /**
     * Permet llegir paraula-frequencia de consola
     */
    public void parseConsole() {
        matching = new ArrayList<Character>();
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
    
     /**
     * Posible funcio per a parsejar pagines web, reservat per a opcional
     */
    public void parseWeb() {

    }

     /**
     * Retorna el nom del fitxer on es llegeix
     * @return name nom del fitxer
     */
    public static String getName() {
        return name;
    }
    
     /**
     * Llegeix un arxiu tipus diccionari
     * @param opc indica si es fa la lectura dun diccionari predefinit o dun altre
     * @param dicc indica quuin diccionari predefinit es vol llegir
     */
    public void Rdictionary(int opc, int dicc) throws FileNotFoundException, IOException {
        String arxiu = null;
        String pathc = null;
        switch(opc) {
            case 0:
                switch(dicc) {
                    case 1: 
                        setPath(0);
                        arxiu="diccionari_catala.txt";
                        break;
                    case 2: arxiu="diccionari_castella.txt";
                        break;
                    case 3: arxiu="diccionari_angles.txt";
                        break;
                }
                pathc = path.concat("\\").concat(arxiu); 
           break;
                
           case 1:
               System.out.println("Introduir la  ruta on es troben els fitxers");
               setPath(1);
               arxiu = dirPath(path);
               name = normalitza(arxiu);
               pathc = path.concat("\\").concat(arxiu); 
           break;
        }
        if(!arxiu.equals("-1")) readdictionary(pathc);
      }

     /**
     * Escriu un diccionari a un fitxer de text
     * @param nom nom del diccionari a desar
     * @param words hastable amb el diccionari paraula-frequencia
     */
    public void Wdictionary(String name,Hashtable<String, Double> words) throws IOException {
        setPath(2);
        String nFitxer = path.concat("\\").concat(name).concat(".txt"); 
        File fitxer = new File(nFitxer);
        if (fitxer.exists()) {
             System.out.println("El fitxer ya existeix voleu sobrescriure(S/N)");
             BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));   
             String opt = entrada.readLine(); 
             if(opt=="N") {
                 System.out.println("Introdueix un nou nom per a desar o be introdueixi el mateix"
                         + "per a sobrescriure");
                 String noufitxer = entrada.readLine(); 
                 nFitxer = path.concat("\\").concat(noufitxer).concat(".txt");
             }  
        }
        File nfitxer = new File(nFitxer);
        BufferedWriter wf = new BufferedWriter(new FileWriter(nfitxer));
        for(Map.Entry<String, Double> entry : words.entrySet()){
            String word = entry.getKey();
            Double afreq = entry.getValue();
            int freq = afreq.intValue();
            wf.write(word + " " + freq);
        }
        wf.close();     
      }
}
