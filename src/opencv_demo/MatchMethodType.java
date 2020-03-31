/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv_demo;

/**
 *
 * @author Christian
 */
public enum MatchMethodType {    
    SQDIFF(0), 
    SQDIFF_NORMED(1), 
    TM_CCORR(2), 
    TM_CCORR_NORMED(3), 
    TM_COEFF(4), 
    TM_COEFF_NORMED(5);
     
    private final int valor;
    
    MatchMethodType(int valorOpcao){
        valor = valorOpcao;
    }
    public int getValor(){
        return valor;
    }
}