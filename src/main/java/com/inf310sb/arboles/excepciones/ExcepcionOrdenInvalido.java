/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf310sb.arboles.excepciones;

/**
 *
 * @author harol
 */
public class ExcepcionOrdenInvalido extends Exception{

    public ExcepcionOrdenInvalido() {
        super("Arbol con orden inválido");
    }

    public ExcepcionOrdenInvalido(String string) {
        super(string);
    }
    
}
