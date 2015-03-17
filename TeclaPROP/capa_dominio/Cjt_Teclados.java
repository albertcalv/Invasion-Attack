package teclado.capa_dominio;


import java.util.ArrayList;

/**
 * Representa un conjunt de teclats
 * @author albert.calvo.ibanez
 */
public class Cjt_Teclados {
    private ArrayList<Teclado> keyboards;

    public int posKeyboard(String name) throws ExcepcionesTeclado {
        int pos = -1;     
        int i=0;
        while(i < keyboards.size() && pos == -1) {
           if(name.equals(keyboards.get(i).getName())) pos = i; 
           i++;
        } 
        return pos;
    }
    
    /**
     * Constructora per defecte
     */
    public Cjt_Teclados() {
        keyboards = new ArrayList<>();
    }

    /**
     * Permet obtenir l'arraylist de teclats 
     * @return array amb el conjunt de teclats
     */
    public ArrayList<Teclado> getKeyboards() {
        return keyboards;
    }

    /**
     * Permet definir un nou arraylist de teclats
     * @param keyboards arraylist que representa un conjunt de teclats
     */
    public void setKeyboars(ArrayList<Teclado> keyboards) {
        this.keyboards = keyboards;
    }
    
    /**
     * Permet coneixer si un teclat identificat per la posicio existeix
     * @param name nom del diccionari
     * @return boolea que indica cert si existeix el teclat o cas contrari fals si no 
     * existeix
     * @throws driver_cjt_teclados.ExcepcionesTeclado
     */
    public boolean existKeyboard(String name) throws ExcepcionesTeclado {
        boolean found=false;     
        int i=0;
        while(i < keyboards.size() && !found) {
           if(name.equals(keyboards.get(i).getName())) found=true; 
           else ++i;
        }
        return found; 
    }
    
    /**
     * Permet obtenir un teclat resident en una posicio 
     * @param n posició del teclat 
     * @return retorna el teclat identificat per n
     */
    public Teclado getTeclado(int n) {
        return keyboards.get(n);
    }
    
    /**
     * Genera un nou teclat 
     * @param n, tamany del teclat
     * @param nom, nom del teclat
     * @return posicio on es crea el teclat 
     * @throws driver_cjt_teclados.ExcepcionesTeclado 
     */
    public int newKeyboard(int n, String nom) throws ExcepcionesTeclado {
        Teclado tec; 
        String anom;
        if(n==1) anom = nom.concat("1");
        else if(n==2)  anom = nom.concat("2");
        else   anom = nom.concat("3");
 
        if(!existKeyboard(anom)) {
            if(n==1) 
                tec = new Triangular(); 
            else if(n==2) 
                tec = new Rectangular(); 
            else 
                tec = new Redondo(); 
            tec.setName(anom);
            keyboards.add(tec);
            return keyboards.indexOf(tec);
        }
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.ex_keyboard);
    }

    /**
     * Genera la matriu de distancies per a un teclat. 
     * @param p posicio on es troba el teclat a tractar 
     * @param N tamany de la matriu 
     * @throws driver_cjt_teclados.ExcepcionesTeclado 
     */
    public void calculateDistPos(int p, int N) throws ExcepcionesTeclado {
        if(p >= 0 && p < keyboards.size()) 
            keyboards.get(p).calculateDistances(N);
        else throw new ExcepcionesTeclado(ExcepcionesTeclado.invalidaguments);
    }

    /**
     * Permet obtenir la matriu de Distancies 
     * @param name, nom del diccionari
     * @return matriu de posicions 
     * @throws driver_cjt_teclados.ExcepcionesTeclado 
     */
    public double[][] getMatrixDistancesPos(String name) throws ExcepcionesTeclado {
      int id_k  = posKeyboard(name);
      if(id_k >= 0 && id_k < keyboards.size()) 
          return keyboards.get(id_k).getDist();
      else throw new ExcepcionesTeclado(ExcepcionesTeclado.invalidaguments);  
    }

    public int getTypeOfKeyboard(String name) throws ExcepcionesTeclado {
       int id_k =  posKeyboard(name);
       if(keyboards.get(id_k) instanceof Triangular) return 1;
       else if(keyboards.get(id_k) instanceof Rectangular) return 2;
       else return 3;        
    }
    
    public void printKeyboards() {
       System.out.println("Teclats");
       for(int i = 0; i < keyboards.size(); ++i) {
           System.out.println(" " + keyboards.get(i).getName());
       }
    }
    
    public void deleteKeyboard(String name) throws ExcepcionesTeclado {
        int id_k = posKeyboard(name);
        keyboards.remove(id_k); 
    }
}