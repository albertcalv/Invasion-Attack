package teclado.capa_dominio;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/**
 * Representa un conjunt de solucions
 * @author evelyn.rovira
 */
public class Cjt_Soluciones {
    private ArrayList<Solucion> solutions;
    
    /**
     * Constructora per defecte
     */
    public Cjt_Soluciones(){
        solutions = new ArrayList<Solucion>();
    }
    
    /**
     * @return arraylist amb totes les solucions
     */
    public ArrayList<Solucion> getSolutions() {
        return solutions;
    }
    
    /**
     * Genera una solucio
     * @param freq matriu de frequencies
     * @param dist matriu de distancies
     * @param matching arraylist amb els caracters d'un diccionari
     * @param alg algoritme seleccionar: 1-BranchAndBound, 2-Genetic, 3-Greedy
     * @param idKeyboard identificador del teclat
     * @param idDictionary identificador del diccionari
     * @param idAlphabet identificador de l'alfabet
     * @throws ExcepcionesTeclado
     */
    public void generatesSolution(double[][] freq, double[][] dist, ArrayList<Character> matching, int alg,
            String nameDictionary, String nameKeyboard) {
        boolean trobat = false;
        int i = 0;
        while (!trobat && i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameDictionary().equals(nameDictionary) && sol.getNameKeyboard().equals(nameKeyboard)) trobat = true;
            if (!trobat) ++i;
        }
        if (trobat) {
            System.out.println("S'ha esborrat la solucio solucio antiga");
            solutions.remove(i);
        }
        Solucion solucion = new Solucion(nameDictionary,nameKeyboard);
        solucion.generatesSolution(freq, dist, alg);
        solucion.translateSolution(matching);
        solutions.add(solucion);
        if (trobat) System.out.println("S'ha generat la nova solucio");
        else System.out.println("S'ha generat la solucio");
    }
    
    /**
     * Esborra una solucio
     * @param idKeyboard identificador del teclat
     * @param idDictionary identificador del diccionari
     * @param idAlphabet identificador de l'alfabet
     * @throws ExcepcionesTeclado
     */
    public void deleteSolution(String nameDictionary, String nameKeyboard) throws ExcepcionesTeclado {
        boolean trobat = false;
        int i = 0;
        while (!trobat && i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameDictionary().equals(nameDictionary) && sol.getNameKeyboard().equals(nameKeyboard)) trobat = true;
            if (!trobat) ++i;
        }
        if (trobat) {
            solutions.remove(i);
            System.out.println("S'ha esborrat la solucio");
        }
    }
    
    /**
     * Mostra totes les solucions
     * @throws ExcepcionesTeclado
     */
    public void printSolutions() throws ExcepcionesTeclado {
        for (int i = 0; i < solutions.size(); ++i) {
            Solucion sol = solutions.get(i);
            String nameDictionary = sol.getNameDictionary();
            String nameKeyboard = sol.getNameKeyboard();
            System.out.println("solucio: Diccionari " + nameDictionary + "  Teclat " + nameKeyboard);
            System.out.println(sol.toString());
        }
        if (solutions.size() == 0) System.out.println("Conjunt buit");
    }
    
    /**
     * Obte una solucio
     * @param idKeyboard identificador del teclat
     * @param idDictionary identificador del diccionari
     * @param idAlphabet identificador de l'alfabet
     * @return solucio que s'identificar pels identificadors
     * @throws ExcepcionesTeclado
     */
    public LinkedHashMap<Character,Integer>  getSolucion(String nameDictionary, String nameKeyboard) throws ExcepcionesTeclado {
        boolean trobat = false;
        int i = 0;
        while (!trobat && i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameDictionary().equals(nameDictionary) && sol.getNameKeyboard().equals(nameKeyboard)) trobat = true;
            if (!trobat) ++i;
        }
        
        LinkedHashMap<Character,Integer> traduccion = new LinkedHashMap<Character,Integer>(0);
        if (trobat) {
            traduccion = solutions.get(i).getAsignacion();
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_sol);
        
        return traduccion;
    }
    
    /**
     * Obte una solucio
     * @param index posicio en solutions
     * @return solucio que s'identificar per la posicio index
     * @throws ExcepcionesTeclado
     */
    public LinkedHashMap<Character,Integer> getSolucion_i(int index) throws ExcepcionesTeclado {
        boolean trobat = false;
        LinkedHashMap<Character,Integer> traduccion = new LinkedHashMap<Character,Integer>(0);
        if (index < solutions.size() && index >= 0) trobat = true;
        
        if (trobat) {
            traduccion = solutions.get(index).getAsignacion();
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_sol);
        
        return traduccion;
    }
    
    /**
     * Calcula una cel·la de la taula d'estadistiques (Comparar Topologias y Comparar Diccionarios)
     * @param dist matriu de distancies
     * @param matching arraylist amb els caracters d'un diccionari
     * @param words hashtable amb totes les paraules i la seva frequencia d'un diccionari
     * @param idKeyboard identificador del teclat
     * @param idDictionary identificador del diccionari
     * @param idAlphabet identificador de l'alfabet
     * @return cost d'un diccionari amb una topologia
     * @throws ExcepcionesTeclado
     */
    public double getStatistic(double[][] dist, ArrayList<Character> matching, Hashtable<String,Double> words,
            String nameDictionary, String nameKeyboard) throws ExcepcionesTeclado {
        double count_total = 0;
        
        boolean trobat = false;
        int i = 0;
        while (!trobat && i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameDictionary().equals(nameDictionary) && sol.getNameKeyboard().equals(nameKeyboard)) trobat = true;
            if (!trobat) ++i;
        }
        
        LinkedHashMap<Character,Integer> traduccion = new LinkedHashMap<Character,Integer>(0);
        if (trobat) {
            count_total = solutions.get(i).getStatistic(dist, matching, words);
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_sol);
        
        return count_total;
    }

    /**
     * Mostra els identificadors dels teclats que te un diccionari
     * @param idDictionary identificador del diccionari
     */
    public void getKeyboardsOfDic(String nameDictionary) { 
        for(int i = 0; i < solutions.size(); i++){
            Solucion sol = solutions.get(i);
            if(sol.getNameDictionary().equals(nameDictionary)) 
                System.out.println(solutions.get(i).getNameKeyboard() + " ");
        }
    }
    
    /**
     * Cerca la posicio d'una solucio en el conjunt amb uns identificador concrets
     * @param idKeyboard identificador del teclat
     * @param idDictionary identificador del diccionari
     * @param idAlphabet identificador de l'alfabet
     * @return posicio de la solucio dins del conjunt
     * @throws teclado.capa_dominio.ExcepcionesTeclado
     */
    public int getPosSolution(String nameDictionary, String nameKeyboard) throws ExcepcionesTeclado {
        boolean trobat = false;
        int i = 0;
        while (!trobat && i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameDictionary().equals(nameDictionary) && sol.getNameKeyboard().equals(nameKeyboard)) trobat = true;
            if (!trobat) ++i;
        }
        
        int posicio = -1;
        if (trobat) posicio = i;
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_sol);
        return posicio;
    }

    /**
     * Mostra una solucio per la posicio en el conjunt
     * @param index posicio de la solucio dins del conjunt
     */
    public void printSolution(int index) {
        System.out.println(solutions.get(index).toString());
    }
    
    public void deleteSolutionKeyboard(String nameKeyboard) throws ExcepcionesTeclado {
        boolean trobat = false;
        int i = 0;
        while (!trobat && i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameKeyboard().equals(nameKeyboard)) trobat = true;
            if (!trobat) ++i;
        }
        if (trobat) {
            solutions.remove(i);
            System.out.println("S'ha esborrat la solucio");
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_sol);
    }
    
    public void deleteSolutionDictionary(String nameDictionary) throws ExcepcionesTeclado {
        int i = 0;
        int count = 0;
        while (i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameDictionary().equals(nameDictionary)) {
                solutions.remove(i);
                count++;
            }
            else ++i;
        }
        if (count != 0) System.out.println("S'han esborrat " + count + " solucio(ns)");
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_sol);
    }
    
    public void printAllSolutions(){
        for(int i = 0; i < solutions.size(); i++)
            System.err.println("sol: " + i + " nameDic: " + solutions.get(i).getNameDictionary()
                    + " nameKeyb: " + solutions.get(i).getNameKeyboard());
   }
     
    /**
     * Retorna el numero de solucions amb el mateix alfabet i diccionari 
     * @param pos_d identificador del dicionari
     * @param pos_a identificador del alfabet 
     * @return numero total de solucions 
     */
    public int numberSolutions(String nameDictionary) throws ExcepcionesTeclado {
       int n_solutions=0;
       for(int i = 0; i < solutions.size(); ++i) {
           if(solutions.get(i).getNameDictionary() == nameDictionary) ++n_solutions;
       }
       if(n_solutions == 0 ) throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_sol);
        return n_solutions;
    }

    public int getPartSol(String nameDictionary, int last_position) throws ExcepcionesTeclado {
        int pos_sol = -1;
        int i = 0;
        while(i < solutions.size() && pos_sol == -1){ 
            if(solutions.get(i).getNameDictionary() == nameDictionary && last_position < i){
                pos_sol = i;
            }
            i++;
        }
        if(pos_sol == -1) throw new ExcepcionesTeclado(ExcepcionesTeclado.no_ex_sol);
        return pos_sol;
    } 

    public void statistics1(int last_position, double[][] mat_dist, Hashtable<String, Double> words, ArrayList<Character> matching, int top) throws ExcepcionesTeclado { 
        switch(top){
            case 1: System.out.print("Triangular:  " ); break;
            case 2: System.out.print("Rectangular:  " ); break;
            case 3: System.out.print("Redondo:  " ); break;
            default: throw new ExcepcionesTeclado(ExcepcionesTeclado.invalidaguments);
        } 
        double dist = solutions.get(last_position).getStatistic(mat_dist, matching, words);
        System.out.println(dist);
    }
    
    public void statistics3(int last_position, double[][] mat_dist, Hashtable<String, Double> words, ArrayList<Character> matching, int top) throws ExcepcionesTeclado { 
        switch(top){
            case 1: System.out.print("Triangular:  " ); break;
            case 2: System.out.print("Rectangular:  " ); break;
            case 3: System.out.print("Redondo:  " ); break;
            default: throw new ExcepcionesTeclado(ExcepcionesTeclado.invalidaguments);
        } 
        System.out.print(solutions.get(last_position).getNameDictionary()+": ");
        double dist =  solutions.get(last_position).getStatistic(mat_dist, matching, words);
        System.out.println(dist);
    }
    
    public boolean existSolDictionary(String nameDictionary) {
        boolean trobat = false;
        int i = 0;
        while (!trobat && i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameDictionary().equals(nameDictionary)) trobat = true;
            ++i;
        }
        
        return trobat;
    }
    
    public int getPosSolKeyboard(String nameKeyboard) {
        boolean trobat = false;
        int i = 0;
        while (!trobat && i < solutions.size()) {
            Solucion sol = solutions.get(i);
            if (sol.getNameKeyboard().equals(nameKeyboard)) trobat = true;
            if (!trobat) ++i;
        }
        
        return i;
    }
}
