/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf310sb.arboles;

/**
 *
 * @author harol
 */
public class AVL <K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V>{
    private static final byte DIFERENCIA_MAXIMA = 1;
    
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }
        super.raiz = this.insertar(this.raiz,claveAInsertar,valorAInsertar);
    }
    
    private NodoBinario<K,V> insertar(NodoBinario<K,V> nodoActual,K claveAInsertar, V valorAInsertar){
        if (NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar,valorAInsertar);
            return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        if (claveAInsertar.compareTo(claveActual) > 0 ){
            NodoBinario<K,V> supuestoNuevoHDerecho = insertar(nodoActual.getHijoDerecho(),
                                    claveAInsertar,valorAInsertar);
            nodoActual.setHijoDerecho(supuestoNuevoHDerecho);
            return this.balancear(nodoActual);
        }
        if (claveAInsertar.compareTo(claveActual) < 0 ){
            NodoBinario<K,V> supuestoNuevoHIzquierdo = insertar(nodoActual.getHijoIzquierdo(),
                                    claveAInsertar,valorAInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHIzquierdo);
            return this.balancear(nodoActual);
        }
        //SI LLEGA ACA sig qu ele nodo actual esta la calve a insertar
        nodoActual.setValor(valorAInsertar);
        return nodoActual;       
    }
    
    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaRamaIzq = altura(nodoActual.getHijoIzquierdo());
        int alturaRamaDer = altura(nodoActual.getHijoIzquierdo());
        int diferencia = alturaRamaIzq - alturaRamaDer;
        if (diferencia > DIFERENCIA_MAXIMA) {
            //balancear hacia izquierda
            NodoBinario<K, V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
            alturaRamaIzq = altura(hijoIzquierdo.getHijoIzquierdo());
            alturaRamaDer = altura(hijoIzquierdo.getHijoDerecho());
            if (alturaRamaDer > alturaRamaIzq) {
                return this.rotacionDobleADerecha(nodoActual);
            } else {
                return this.rotacionSimpleADerecha(nodoActual);
            }
        } else if (diferencia < -DIFERENCIA_MAXIMA) {
            //balancear hacia derecha REALIZAR 
            NodoBinario<K, V> hijoDerecho = nodoActual.getHijoDerecho();
            alturaRamaIzq = altura(hijoDerecho.getHijoDerecho());
            alturaRamaDer = altura(hijoDerecho.getHijoDerecho());
            if (alturaRamaDer > alturaRamaIzq) {
                return this.rotacionDobleAIzquierda(nodoActual);
            } else {
                return this.rotacionSimpleAIzquierda(nodoActual);
            }  //balancear hacia derecha REALIZADA
        }
        return nodoActual;
    }
    
    private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoIzquierdo();//nodoIzqu para balanceo
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoIzquierdo());//actualizar punteroNodo
        nodoQueRota.setHijoDerecho(nodoActual);//rotacion hecha apunta a nodo actual
        return nodoQueRota;
    }
    //metodo falta completado
    private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoDerecho();//nodoDere para balanceo
        nodoActual.setHijoDerecho(nodoQueRota.getHijoDerecho());//actualizar punteroNodo
        nodoQueRota.setHijoIzquierdo(nodoActual);//rotacion hecha apunta a nodo actual
        return nodoQueRota;
    }
  
    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> nodoActual){
        //rotacion simple a izquierda luego rotacion a derecha
        NodoBinario<K,V> nodoRotaAIzquierda = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoRotaAIzquierda);
        return rotacionSimpleADerecha(nodoActual); 
    }
    
    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> nodoActual){
        //rotacion simple a derecha luego rotacion a izquierda
        nodoActual.setHijoDerecho(rotacionSimpleADerecha(nodoActual.getHijoDerecho()));
        return rotacionSimpleAIzquierda(nodoActual); 
    }
    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null){
            throw new IllegalArgumentException("Clave a eliminar no puede ser nula");
        }
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null){
            throw new IllegalArgumentException("La clave no existe en el arbol");
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return null;
    }

    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual, K claveAEliminar){
        K claveActual  = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) > 0){
            NodoBinario<K,V> posibleNuevoHDerecho = eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
            nodoActual.setHijoDerecho(posibleNuevoHDerecho);
            return nodoActual;//balancear
        }
        if (claveAEliminar.compareTo(claveActual) < 0){
            NodoBinario<K,V> posibleNuevoHIzquierdoo = eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
            nodoActual.setHijoIzquierdo(posibleNuevoHIzquierdoo);
            return nodoActual;//balancear
        }
        //SI LLEGO ACA ENCONTRO EL NODO CON LA CLAVE A ALEIMINAR
        //CASO 1
        if (nodoActual.esHoja()){
            return (NodoBinario<K,V>) NodoBinario.nodoVacio();
        }
        //CASO 2
        //2.1 si solo tiene hijo izquierdo
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoIzquierdo();
        }
        //2.2 si solo tiene hijo derecho
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoDerecho();
        }
        //CASO 3
        NodoBinario<K,V> nodoReemplazo = this.buscarNodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> posibleNuevoHDerecho = eliminar(nodoActual.getHijoIzquierdo(),nodoReemplazo.getClave());
        nodoActual.setHijoDerecho(posibleNuevoHDerecho);//puede ser que cambie dependiendo del caso 1 o 2
        //lo mas facil reemplazar la clave y el valor del nodo
        nodoActual.setClave(nodoReemplazo.getClave());
        nodoActual.setValor(nodoReemplazo.getValor());
        return nodoActual;
    }
}
