package teclado.capa_dominio;

/**
 * Representa les excepcions del projecte
 * @author albert.calvo.ibanez
 * @author sergi.galicia
 * @author ester.lorente
 * @author evelyn.rovira
 */
public class ExcepcionesTeclado extends Exception {
    public static String dicc_exist = "Ja existeix un diccionari amb aquest nom";
    public static String dicc_noexist = "No existeix un diccionari amb aquest nom";
    public static String no_exist_word = "Insertar una paraula";
    public static String no_name = "Nom buit";
    public static String no_freqquence = "No existeix frequencia";
    public static String invalid_f = "Frequencia invalida";
    public static String inv_pos = "Access a posicio no valida";
    public static String no_number = "Introdueix numeros per les frequencies"; //part visual
    public static String no_word = "Ja existeix la paraula";
    public static String no_valid_action = "Action es 1(Consola), 2(Arxiu) o 3(Pagina Web)";
    public static String no_ex_word = "No existeix la paraula";
    public static String no_valid_keyword = "Type_keyboard es 1(Rectangular), 2(Triangular) o 3(Redo)";
    public static String no_characters = "Caracters buits";
    public static String error_read = "Error al llegir de consola";
    public static String no_valid_algorithm = "1(Branch&Bound Gilmore), 2(Heuristic Genetic), 3(Heuristic Greedy)";
    public static String sol_exist = "Ja existeix una solucio per aquest teclat, diccionari i alfabet";
    public static String no_ex_sol = "No existeix aquesta solucio";
    public static String invalidaguments = "Arguments invalids";
    public static String no_exist_alph = "No existeix aquest alfabet";
    public static String not_mod_preed = "No es poden modificar els diccionaris predeterminats";  
    public static String ex_keyboard ="El teclat ya existeix";
    
    /**
     * Constructora amb string
     * @param n string amb l'excepcio
     */
    public ExcepcionesTeclado(String n){ 
        super(n);   
    }
}
