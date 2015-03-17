package teclado.capa_dominio;

/**
 * Representa l'algoritme Branch and Bound
 * @author ester.lorente
 */
public class BranchAndBound extends Algoritmo {
    
    /**
     * Constructora per defecte
     */
    public BranchAndBound() {
        super();
    }
    
    /**
     * Metode que inicialitza el Branch and Bound
     * @param freq matriu de frequencies
     * @param dist matriu de distancies
     */
    @Override
    public void execute(double[][] freq, double[][] dist) {
        int N = freq.length;
        cost_min = -1;
        double cost_part = 0;
        sol = new int[N];
        int[] sol_part = new int[N];
        for (int i = 0; i < N; ++i) sol_part[i] = i;
        
        BnB(freq,dist,0,sol_part, cost_part);
    }
    
    /**
     * Calcula el nombre de 0s en la direccio address
     * @param h matriu d'Hungarian
     * @param i posicio en les files
     * @param j posicio en les columnes
     * @param address direccio: 1-up, 2-right, 3-down, 4-left
     * @param row_invalid array amb les invalidacions de files
     * @param col_invalid array amb les invalidacions de files
     * @return nombre de 0s en la direccio address
     */
    private int numberCero(double[][] h, int i, int j, int address, boolean[] row_invalid, boolean[]col_invalid){
        if(i < 0 || i >= h.length || j < 0 || j >= h.length) return 0;
        else {
            int c = 0;
            if(h[i][j] == 0 && !row_invalid[i] && !col_invalid[j]) c = 1;
            switch(address) {
                case 1: // up
                    c += numberCero(h, i-1, j, address, row_invalid, col_invalid);
                    break;
                case 2: // right
                    c += numberCero(h, i, j+1, address, row_invalid, col_invalid);
                    break;
                case 3: // down
                    c += numberCero(h, i+1, j, address, row_invalid, col_invalid);
                    break;
                case 4: // left
                    c += numberCero(h, i, j-1, address, row_invalid, col_invalid);
                    break;
            }
            return c;
        }
    }
    
    /**
     * Calcula el nombre de linies per cubrir la matriu
     * @param h matriu d'Hungarian
     * @param row_invalid array amb les invalidacions de files
     * @param col_invalid array amb les invalidacions de files
     * @param ceros matriu amb les posicion del 0s per files
     * @param cero_rows array amb el nombre de 0s per files
     * @param cero_cols array amb el nombre de 0s per columnes
     * @return nombre de linies per cubrir la matriu
     */
    private int cover(double[][] h, boolean[] row_invalid, boolean[] col_invalid, int[][] ceros, int[] cero_rows, int[] cero_colums) {
        int lines = 0;
        
        for (int i = 0; i < h.length; ++i) {
            row_invalid[i] = false;             // row_invalid[i] = si hi ha 0 en la fila i
            col_invalid[i] = false;             // col_invalid[i] = si hi ha 0 en la columna i
            cero_rows[i] = 0;                   // cero_rows[i] = nombre de 0s en la fila i
            cero_colums[i] = 0;                 // cero_colums[j] = nombre de ceros en la columna j
            for (int j = 0; j < h.length; ++j) {
                ceros[i][j] = 0;                // ceros[i][..] = indica les columnes on hi ha 0s en la fila i de h
            }                                    
        }
        
        for(int i = 0; i < h.length; i++) {     // calcul de la matriu h
            for(int j = 0; j < h.length; j++) {
                if(h[i][j] == 0) {              // si hi ha un 0 en h[i][j]
                    ceros[i][cero_rows[i]] = j; // cero[i][k] = hi ha un 0 en la columna j
                    cero_rows[i] += 1;          // hi ha un 0 en la fila i ------> k
                    cero_colums[j] += 1;        // hi ha un 0 en la columna j ---> k
                }
            }
        }
        
        for(int i = 0; i < h.length; i++) {     // tachar linies
            if(!row_invalid[i]) {                                                       // si la fila no esta coberta
                for(int j = 0; j < h.length; j++) {
                    if(!col_invalid[j] && !row_invalid[i] && h[i][j] == 0) {           // si la fila icolumna no esta coberta i te un 0
                        int up = numberCero(h, i, j, 1, row_invalid, col_invalid);     // up = ceros amunt
                        int down = numberCero(h, i, j, 3, row_invalid, col_invalid);   // down = ceros abaix
                        int right = numberCero(h, i, j, 2, row_invalid, col_invalid);  // right = ceros a la dreta
                        int left = numberCero(h, i, j, 4, row_invalid, col_invalid);   // left = ceros a l'esquerra
                        
                        int columns = up + down;                                // rows = ceros en la fila
                        int rows = right + left;                                // columns = ceros en la columna
                        
                        if(rows >= columns) row_invalid[i] = true;              // si hi ha mes d'un 0 a la fila, cobrim la fila
                        else col_invalid[j] = true;                             // sino, cobrim la columna
                        lines += 1;                                             // incrementem el numero de linies
                    }
                }
            }
        }
        return lines;
    }
    
    /**
     * Calcula el cost d'Hungarian
     * @param G matriu de Gilmore
     * @return cost d'Hungarian
     */
    private double hungarian(double[][] G) {
        double cost = 0;
        double[][] h = new double[G.length][G.length];
        for(int i = 0; i < G.length; i++) {
            for (int j = 0; j < G.length; ++j) {
                h[i][j] = G[i][j];
            }
        }
        
        // PART 1: restar per cada fila el valor minim d'aquella fila
        double[] rows_min = new double[h.length];   // row_min[i] = valor minim en la fila i
        for(int i = 0; i < h.length; i++) {
            rows_min[i] = h[i][0];
            for(int j = 1; j < h.length; j++) {
                if(h[i][j] < rows_min[i]){
                    rows_min[i] = h[i][j];
                }            
            }
            for(int k = 0; k < h.length; k++) {
                h[i][k] -= rows_min[i];        
            }
        }
        
        // PART 2: restar per cada columna el valor minim d'aquella columna
        double[] colum_min = new double[h.length];  // col_min[i] = valor minim en la columna i
        for(int i = 0; i < h.length; i++) {
            colum_min[i] = h[0][i];
            for(int j = 1; j < h.length; j++) {
                if(h[j][i] < colum_min[i]){
                    colum_min[i] = h[j][i];
                }
            }
            for(int k = 0; k < h.length; k++){
                h[k][i] -= colum_min[i];          
            }
        }
        
        // PART 3: mirar quantes linies es necessiten per cobrir els 0s de la matriu
        boolean[] row_invalid = new boolean[h.length];  // row_invalid[i] = si la fila i esta coberta
        boolean[] col_invalid = new boolean[h.length];  // row_invalid[i] = si la columna i esta coberta
        int[] cero_rows = new int[h.length];            // cero_rows[i] = nombre de 0s en la fila i
        int[] cero_colums = new int[h.length];          // cero_columns[i] =nombre de 0s en al columna i
        int[][] ceros = new int[h.length][h.length];    // ceros[i][..] = indica les columnes on hi ha 0s en la fila i de h
        
        // lines = nombre de linies per cubrir la matriu
        int lines = cover(h, row_invalid, col_invalid, ceros, cero_rows, cero_colums);
        
        // PART 4: sumar als nombres coberts, el nombre minim no cobert
        double min = 0;
        while(lines < h.length) {                           // buscar el minim no cubiert (por fila y columna)
            boolean first = true;
            for(int i = 0; i < h.length; i++) {
                if(!row_invalid[i]){                        // si no esta cubiert por fila
                    for(int j = 0; j < h.length; j++) {
                        if(!col_invalid[j]){                // si no esta cubiert por columna
                            if(first){
                                first = false;
                                min = h[i][j];
                            }
                            else{
                                if(h[i][j] < min){
                                    min = h[i][j];
                                }
                            }
                        }
                    }
                }
            }
            for(int i = 0; i < h.length; i++) {             // sumar als nombres coberts el nombre minim no cobert
                for(int j = 0; j < h.length; j++) {
                    if(row_invalid[i]){                     // si esta cubiert por fila
                        h[i][j] += min;
                    }
                    if(col_invalid[j]){                     // si esta cubiert por columna
                        h[i][j] += min;
                    }
                }
            }
            
            // PART 5: restar el valor minim de la matriu a TOTA la matriu
            double min_matrix = h[0][0];
            for(int i = 0; i < h.length; i++) {             // min_matrix = valor minim de la matriu
                for(int j = 0; j < h.length; j++) {
                    if(min_matrix > h[i][j]){
                        min_matrix = h[i][j];
                    }
                }
            }
            for(int i = 0; i < h.length; i++) {             // restar el valor minim a toda la matriu
                for(int j = 0; j < h.length; j++) {
                    h[i][j] -= min_matrix;
                }
            }
            lines = cover(h, row_invalid, col_invalid, ceros, cero_rows, cero_colums);
        }
        
        for(int i = 0; i < h.length; i++) {
            boolean found = false;
            int j = 0;
            while(j < h.length && !found){
                if(!row_invalid[j]){
                    found = (cero_rows[j] == 1);
                }
                if(!found) j++;
            }
            if(!found){
                boolean first_row = true;
                for(int k = 0; k < h.length; k++) {
                    if(first_row && !row_invalid[k]) {
                        first_row = false;
                        j = k;
                    }
                    else if(!row_invalid[k] && cero_rows[k] < cero_rows[j]) j = k;
                }
            }
            if (!found && j == h.length) --j;
            cost += G[j][ceros[j][0]];
            row_invalid[j] = true;
            col_invalid[ceros[j][0]] = true;
            cero_rows[j] = 0;
            int col = ceros[j][0];
            
            for(int k = 0; k < h.length; k++) {
                if(!row_invalid[k]) {
                    for(int w = 0; w < cero_rows[k]; w++) {
                        if(ceros[k][w] == col) {
                            for (int y = w; y < cero_rows[k] - 1; y++) {
                                ceros[k][y] = ceros[k][y+1];
                            }
                            cero_rows[k] -=1;
                        }
                    }         
                }   
            }
        }
        return cost;
    }
    
    /**
     * Calcula el cost de Gilmore
     * @param freq matriu de frequencies
     * @param dist matriu de distancies
     * @param level posicio a calcular en la solucio parcial
     * @param part_sol array amb la solucion parcial
     * @return cost de Gilmore
     */
    private double gilmore(double[][] freq, double[][] dist, int level, int[] part_sol) {
        int size = part_sol.length;
        int size_m = size - (level+1);
        double[][] C1 = new double[size_m][size_m];
        double[][] C2 = new double[size_m][size_m];
        double[][] G = new double[size_m][size_m];
        double cost = 0;
        
        // C1 = 
        for(int i = level+1; i < size; i++) {
            for(int j = level+1; j < size; j++) {
                cost = 0;
                for(int k = 0; k <= level; k++) {
                    cost += freq[i][k]*dist[part_sol[j]][part_sol[k]];
                }
                C1[i-(level+1)][j-(level+1)] = cost;
            }
        }
        
        // C2 = 
        for(int i = level+1; i < size; i++) {
            for(int j = level+1; j < size; j++) {
                cost = 0;
                for(int k = level+1; k < size; k++) {
                    cost += freq[i][k]*dist[part_sol[j]][part_sol[size - 1 - k]];
                }
                C2[i-(level+1)][j-(level+1)] = cost;
            }
        }
        
        for(int i = 0; i < size_m; i++) {
            for(int j = 0; j < size_m; j++) {
                G[i][j] = C1[i][j] + C2[i][j];
            }
        }
        
        return hungarian(G);
    }
    
    /**
     * Intercanvia dues posicions
     * @param part_sol array amb la solucio parcial
     * @param level posicio per intercanviar
     * @param j posicio per intercanviar
     */
    private void asignate(int[] part_sol, int level, int j) {
        int x = part_sol[level];
        part_sol[level]= part_sol[j];
        part_sol[j] = x;
    }
    
    /**
     * Calcula el cost d'assignar una posicio
     * @param level posicio a calcular en la solucio parcial
     * @param part_sol array amb la solucion parcial
     * @param freq matriu de frequencies
     * @param dist matriu de distancies
     * @return cost d'assignar la posicio level
     */
    private double costAsigned(int level, int[] part_sol, double[][] freq, double[][] dist) {
        double cost = 0;
        for(int i = 0; i < level; i++){
            cost += freq[level][i]*dist[part_sol[level]][part_sol[i]];
            cost += freq[i][level]*dist[part_sol[i]][part_sol[level]];
        }
        return cost;
    }
    
    /**
     * Metode per executar el Branch and Bound
     * @param freq matriu de frequencies
     * @param dist matriu de distancies
     * @param level posicio a calcular en la solucio parcial
     * @param sol_part array amb la solucion parcial
     * @param cost_part cost de la solucio parcial
     */
    private void BnB(double[][] freq, double[][] dist, int level, int[] sol_part, double cost_part) {
        if(level == freq.length) {
            if(cost_min == -1){                             // coste_min == -1
                cost_min = cost_part;                       // coste_min = cost_parcial
                for(int i = 0; i < freq.length; ++i){
                    sol[i] = sol_part[i];
                }
            }
            else {
                if(cost_part < cost_min) {                  // cost_parcial < coste_min
                    cost_min = cost_part;                   // coste_min = cost_parcial
                    for(int i = 0; i < freq.length; ++i){
                        sol[i] = sol_part[i];
                    }
                }
            }
        }
        else {
            for(int j = level; j < freq.length; j++) {
                // assignar
                asignate(sol_part,j,level);
                
                double cost_cal = costAsigned(level, sol_part, freq, dist);
                cost_part += cost_cal;
                if(cost_part < cost_min || cost_min == -1) {
                    double cost_g = gilmore(freq, dist, level, sol_part);
                    if(cost_min == -1 || cost_g + cost_part < cost_min) {
                        BnB(freq, dist, level+1, sol_part, cost_part);
                    }
                }
                
                // dessasignar
                asignate(sol_part,j,level);
                cost_part -= cost_cal;
            }
        }
    }
    
}
