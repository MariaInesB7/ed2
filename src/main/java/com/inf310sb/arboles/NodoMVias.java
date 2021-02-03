/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf310sb.arboles;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author harol
 */
public class NodoMVias<K,V> {
    private List<K> listaDeClaves;
    private List<V> listaDeValores;
    private List<NodoMVias<K, V>> listaDeHijos;

    public NodoMVias(int orden) {
        listaDeHijos = new LinkedList<>();
        listaDeClaves = new LinkedList<>();
        listaDeValores = new LinkedList<>();
        for (int i = 0; i < orden - 1; i++) {
            listaDeHijos.add(NodoMVias.nodoVacio());
            listaDeClaves.add((K) NodoMVias.datoVacio());
            listaDeValores.add((V)NodoMVias.datoVacio());
        }//siempre un hijo mas 
        listaDeHijos.add(NodoMVias.nodoVacio());
    }
    
    public NodoMVias(int orden, K primerClave, V primerValor){
        this(orden);//llamada a su propio constructor
                    //funciona just en primera linea del constructor
        this.listaDeClaves.set(0, primerClave);
        this.listaDeValores.set(0, primerValor);
    }
    
    public static NodoMVias nodoVacio(){
        return null;
    }
    
    public static Object datoVacio(){
        return null;
    }
    
    /**
     * retorna la clave de la posicion inidcada por el parametro posición
     * pre-condicion: El parametro posición indica una posicion valida
     * en el arreglo de la lista de claves
     */
    public K getClave(int posicion) {
        return this.listaDeClaves.get(posicion);
    }

    public void setClave(int posicion, K clave) {
        this.listaDeClaves.set(posicion, clave);
    }

    public V getValor(int posicion) {
        return this.listaDeValores.get(posicion);
    }

    public void setValor(int posicion, V valor) {
        this.listaDeValores.set(posicion, valor);
    }
    
    public NodoMVias<K,V> getHijo(int posicion){
        return this.listaDeHijos.get(posicion);
    }
    
    public void setHijo(int posicion, NodoMVias<K,V> nodo){
        this.listaDeHijos.set(posicion, nodo);
    }
    
    public static boolean esNodoVacio(NodoMVias nodo){
        return nodo == NodoMVias.nodoVacio();
    }
    
    public boolean esClaveVacia(int posicion){
        return this.listaDeClaves.get(posicion) == NodoMVias.datoVacio();
    }
    
    public boolean esHijoVacio(int posicion){
        return this.listaDeValores.get(posicion) == NodoMVias.nodoVacio();
    }
    
    public boolean esHoja(){
        for (int i = 0; i < this.listaDeHijos.size(); i++){
            if (!this.esHijoVacio(i)){
                return false;
            }
        }
        return true;
    }
    
    public boolean estanClavesLenas(){
        for (int i = 0; i < this.listaDeClaves.size(); i++){
            if (this.esClaveVacia(i)){
                return false;
            }
        }
        return true;
    }
    
    public int cantidadDeClavesNoVacios(){
        int cantidad = 0;
        for (int i = 0; i < this.listaDeClaves.size(); i++){
            if (!this.esClaveVacia(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
    
    public int cantidadDeHijosVacias(){
        int cantidad = 0;
        for (int i = 0; i < this.listaDeHijos.size(); i++){
            if (this.esHijoVacio(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
    //POSIBLE METODO CantidadDeClavesVacios
    //               cantidadDeHijosNoVacias
    
}
