/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf310sb.arboles;

import com.inf310sb.arboles.excepciones.ExcepcionClaveNoExiste;
import com.inf310sb.arboles.excepciones.ExcepcionOrdenInvalido;
import java.util.Stack;

/**
 *
 * @author harol
 */
public class ArbolB <K extends Comparable<K>,V> extends ArbolMViasBusqueda<K, V>{
    private int nroMaximoDeDatos;
    private int nroMinimoDeDatos;
    private int nroMinimoDeHijos;
    
    public ArbolB(){
        super();
        this.nroMaximoDeDatos = 2;
        this.nroMinimoDeDatos = 1;
        this.nroMinimoDeHijos = 2;
    }
    
    public ArbolB(int orden) throws ExcepcionOrdenInvalido{
        super(orden);
        this.nroMaximoDeDatos = super.orden -1;
        this.nroMinimoDeDatos = this.nroMaximoDeDatos / 2;
        this.nroMinimoDeHijos = this.nroMinimoDeDatos + 1;
    } 

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }
        
        if (super.esArbolVacio()){
            super.raiz = new NodoMVias<>(super.orden+1,claveAInsertar,valorAInsertar);
            return;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        Stack<NodoMVias<K,V>> pilaDeAncentros = new Stack<>();
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionClaveExiste = super.existeClaveEnNodo(nodoActual, claveAInsertar);
            if (posicionClaveExiste != POSICION_INVALIDA){
                nodoActual.setValor(posicionClaveExiste, valorAInsertar);
                nodoActual = NodoMVias.nodoVacio();//terminar
            } else {
                if (nodoActual.esHoja()){
                    super.insertarDatosEnOrdenadoEnNodo(nodoActual,claveAInsertar, valorAInsertar);
                    if (nodoActual.cantidadDeClavesNoVacios()>this.nroMaximoDeDatos){
                        this.dividir(nodoActual,pilaDeAncentros);
                    }
                    nodoActual = NodoMVias.nodoVacio();
                } else {
                    int posicionPorDondeBajar = super.porDondeBajar(nodoActual,claveAInsertar);
                    pilaDeAncentros.push(nodoActual);
                    nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                }
            }
        }//fin dw while nodoActual vacio   
    }

    private int dividir(NodoMVias<K,V> nodoActual, Stack<NodoMVias<K,V>> pila){
        return -1;
    }

    @Override
    public V eliminar(K claveAEliminar) {
         if (claveAEliminar == null) {
            throw new ExcepcionClaveNoExiste("Clave a eliminar no puede ser nula");
        }
        Stack<NodoMVias<K,V>> pilaDeAncestros = new Stack<>();
        NodoMVias<K,V> nodoActual = this.buscarNodoDeLaClave(claveAEliminar,pilaDeAncestros);
        if (NodoMVias.esNodoVacio(nodoActual)){
            throw new ExcepcionClaveNoExiste("La clave no existe en el arbol");
        }
        int posicionDeLaClaveEnElNodo = super.porDondeBajar(nodoActual, claveAEliminar)-1;
        V valorARetornar = nodoActual.getValor(posicionDeLaClaveEnElNodo);
        if (nodoActual.esHoja()){
            super.eliminarDatosDeNodo(nodoActual, nodoActual.getClave(posicionDeLaClaveEnElNodo));
            if (nodoActual.cantidadDeClavesNoVacios()< this.nroMinimoDeDatos){
                if (pilaDeAncestros.isEmpty()){
                    if (nodoActual.cantidadDeClavesNoVacios() == 0){
                        super.vaciar();
                    }
                }else {//implementar prestarse
                    this.prestarseOFusioanrse(nodoActual,pilaDeAncestros);
                }
            }
        } else{
            pilaDeAncestros.push(nodoActual);//implementar buscar nododel predecesor
            NodoMVias<K,V> nodoDelPredecedor = this.buscarNodoDelPredecedor(pilaDeAncestros, 
                    (K)nodoActual.getHijo(posicionDeLaClaveEnElNodo));
            int posicionDelPredecesor = nodoDelPredecedor.cantidadDeClavesNoVacios() -1;
            K clavePredecesora = nodoDelPredecedor.getClave(posicionDelPredecesor);
            V valorPredecesora = nodoDelPredecedor.getValor(posicionDelPredecesor);
            super.eliminarDatosDeNodo(nodoDelPredecedor, nodoDelPredecedor.getClave(posicionDelPredecesor));
            nodoActual.setClave(posicionDeLaClaveEnElNodo, clavePredecesora);
            nodoActual.setValor(posicionDeLaClaveEnElNodo, valorPredecesora);
            if (nodoDelPredecedor.cantidadDeClavesNoVacios()< this.nroMaximoDeDatos){
                this.prestarseOFusioanrse(nodoDelPredecedor,pilaDeAncestros);
            }
        }
        return valorARetornar;        
    }
    protected NodoMVias<K,V> buscarNodoDeLaClave(K claveABuscar,Stack<NodoMVias<K,V>> pila){
        return null;
    }
    private void prestarseOFusioanrse(NodoMVias<K,V> nodoActual, Stack<NodoMVias<K,V>>pilaDeAncestros){
        return;
    }
    protected NodoMVias<K,V> buscarNodoDelPredecedor(Stack<NodoMVias<K,V>> pila,K clave){
        return null;
    }
    
}
