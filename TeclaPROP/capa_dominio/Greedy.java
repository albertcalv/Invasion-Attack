/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package teclado.capa_dominio;

/**
 *
 * @author grup3
 */
public class Greedy extends Algoritmo {

    public Greedy() {
        super();
    }
    
    @Override
    public void execute(double[][] freq, double[][] dist) {
        int N = freq.length;
        cost_min = -2;
        double cost_part = 0;
        sol = new int[N];
        int[] sol_part = new int[N];
        for (int i = 0; i < N; ++i) sol_part[i] = i;
    }
}
