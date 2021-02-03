/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf310sb.arboles;

import com.inf310sb.arboles.excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author harol
 */
public class ArbolMViasBusqueda<K extends Comparable<K>, V> implements
        IArbolBusqueda<K, V> {
    //variables miembro
    protected NodoMVias<K,V> raiz;
    protected int orden;
    
    public ArbolMViasBusqueda(){
        this.orden = 3;
    }
    
    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido{
        if (orden < 3)
            throw new ExcepcionOrdenInvalido();
        this.orden = orden;
    }
    
    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int altura() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K minimo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K maximo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertar(K clave, V valor) {
        
    }

    @Override
    public V eliminar(K clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave) != null;
    }

    @Override
    public V buscar(K claveABuscar) {
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean huboCmabioDeNodoActual = false;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios()
                    && !huboCmabioDeNodoActual; i++) {
                K claveActual = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveActual) == 0) {
                    return nodoActual.getValor(i);
                }
                if (claveABuscar.compareTo(claveActual) < 0) {
                    huboCmabioDeNodoActual = true;
                    nodoActual = nodoActual.getHijo(i);
                }
            }
            //ultima hijo del nodo con calves no vacios
            if (!huboCmabioDeNodoActual) {
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios());
            }
        }
        return (V)NodoMVias.datoVacio();
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        //para implementacion recursiva, se necesita
        //un meotod que haga el grueso del trabajo
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para un caso base
        if (NodoMVias.esNodoVacio(nodoActual)) {//caso base n = 0
            return;
        }
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios(); i++) {
            //luego recorrer en InOrden supuesta izquierda
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);            
            //primero visito el primer nodo,clave
            recorrido.add(nodoActual.getClave(i));
        }
        //recorre la ultima clave 
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios()), recorrido);
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        //para implementacion recursiva, se necesita
        //un meotod que haga el grueso del trabajo
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para un caso base
        if (NodoMVias.esNodoVacio(nodoActual)) {//caso base n = 0
            return;
        }
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios(); i++) {
            //primero visito el primer nodo,clave 
            recorrido.add(nodoActual.getClave(i));
            //luego recorrer su hijo su primer nodo clave
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
        }
        //recorre la ultima clave ,ultimo hijo
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios()), recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        //para implementacion recursiva, se necesita
        //un meotod que haga el grueso del trabajo
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para un caso base
        if (NodoMVias.esNodoVacio(nodoActual)) {//caso base n = 0
            return;
        }
        //recorrer primer hijo
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios(); i++){
            //recorrer de el seguno hijo asi sucesivamente hasta el ultimo
            recorridoEnPostOrden(nodoActual.getHijo(i+1), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }
    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Queue<NodoMVias<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            NodoMVias<K,V> nodoActual = colaNodos.poll();
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios(); i++) {
                recorrido.add(nodoActual.getClave(i));
                if (!nodoActual.esHijoVacio(i)){
                    colaNodos.offer(nodoActual.getHijo(i));
                }
            }
            //ultimo hijo
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacios())){
                    colaNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios()+1));
                }
        }
        return recorrido;
    }
    
    public int cantidadDatosVacios(){
        return cantidadDatosVacios(this.raiz);
    }
    
    private int cantidadDatosVacios(NodoMVias<K,V> nodoActual){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }  
        int cantidad = 0;
        for (int i = 0; i < orden - 1; i++ ){
            //recorrido en in orden
            cantidad += cantidadDatosVacios(nodoActual.getHijo(i));
            if (nodoActual.esClaveVacia(i))
                cantidad++;
        }
        cantidadDatosVacios(nodoActual.getHijo(orden -1));
        return cantidad;
    }
    /**
     * retorna la cantidad de hojas en un arbol m vias
     * @return 
     */
    public int cantidadDeHojas(){
        return cantidadDeHojas(this.raiz);
    }
    
    private int cantidadDeHojas(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return 1;
        }
        int cantidad = 0;
        for (int i = 0; i < orden; i++) {
            cantidad += cantidadDeHojas(nodoActual.getHijo(i));
            /*
            if (nodoActual.esHoja)
                cantidad++;
             */
        }
        return cantidad;
    }
    /**
     * retorna la cantidad de hojas en un arbol m vias
     * apartir del nivel n
     * @return 
     */
    public int cantidadDeHojasDesdeNivel(int nivelBase){
        return cantidadDeHojasDesdeNivel(this.raiz,nivelBase, 0);
    }
    
    private int cantidadDeHojasDesdeNivel(NodoMVias<K, V> nodoActual, int nivelBase,
                                            int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nivelActual >= nivelBase) {
            if (nodoActual.esHoja()) {
                return 1;
            }
        }else {//para no entrar al for
            if (nodoActual.esHoja()){
                return 0;
            }
        }
        int cantidad = 0;
        for (int i = 0; i < orden; i++) {
            cantidad += cantidadDeHojasDesdeNivel(nodoActual.getHijo(i),nivelBase,
                                        nivelActual + 1);
        }
        return cantidad;
    }
    
}
