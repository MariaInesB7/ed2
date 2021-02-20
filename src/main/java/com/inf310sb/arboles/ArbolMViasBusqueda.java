/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf310sb.arboles;

import com.inf310sb.arboles.excepciones.ExcepcionClaveNoExiste;
import com.inf310sb.arboles.excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author harol
 */
public class ArbolMViasBusqueda<K extends Comparable<K>, V> implements
        IArbolBusqueda<K, V> {
    //variables miembro
    protected NodoMVias<K,V> raiz;
    protected int orden;
    protected int POSICION_INVALIDA = -1;   
    
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
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidad = 0;
        Queue<NodoMVias<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            NodoMVias<K,V> nodoActual = colaNodos.poll();
            cantidad++;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios(); i++) {
                if (!nodoActual.esHijoVacio(i)){
                    colaNodos.offer(nodoActual.getHijo(i));
                }
            }
            //ultimo hijo
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacios())){
                    colaNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios()+1));
                }
        }
        return cantidad;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaMayor = 0;
        for (int i = 0; i < this.orden;i++){
            int alturaDeHijo = altura(nodoActual.getHijo(i));
            if (alturaDeHijo > alturaMayor){
                alturaMayor = alturaDeHijo;
            }
            }
        return alturaMayor + 1;
    }

    @Override
    public int nivel() {
        return nivelRec(this.raiz);
    }

    private int nivelRec(NodoMVias<K,V> nodoActual){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return -1;
        }
        int nivelMayor = -1;
        for (int i = 0; i<orden; i++){
            int nivelDeHijo = nivelRec(nodoActual.getHijo(i));
            if (nivelDeHijo>nivelMayor){
                nivelMayor = nivelDeHijo;
            }
        }
        return nivelMayor - 1;
    }
    
    @Override
    public K minimo() {
        if (this.esArbolVacio()){
            return null;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        NodoMVias<K,V> nodoAnterior = (NodoMVias<K,V>)NodoMVias.nodoVacio();
        while(!NodoMVias.esNodoVacio(nodoActual)){
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijo(0);
        }
        return nodoAnterior.getClave(0);
    }

    @Override
    public K maximo() {
        if (this.esArbolVacio()){
            return null;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        NodoMVias<K,V> nodoAnterior = (NodoMVias<K,V>)NodoMVias.nodoVacio();
        while(!NodoMVias.esNodoVacio(nodoActual)){
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios());
        }
        //pa mi que es nodoAnterior.cantidadDeClavesNoVacios-1
        return nodoAnterior.getClave(nodoAnterior.cantidadDeClavesNoVacios()-1);
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }
        if (this.esArbolVacio()){
            this.raiz = new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
            return;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        
        while(!NodoMVias.esNodoVacio(nodoActual)){
            int posicionClaveExiste = this.existeClaveEnNodo(nodoActual, claveAInsertar);
            if (posicionClaveExiste != POSICION_INVALIDA){
                nodoActual.setValor(posicionClaveExiste, valorAInsertar);
                return;
            } else {
                if (nodoActual.esHoja()){//si insertar en hoja
                    if (nodoActual.estanClavesLenas()){
                        int posicionPorDondeBajar = this.porDondeBajar(nodoActual,claveAInsertar);
                        NodoMVias<K,V> nuevoHijo = new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                    }else {
                        this.insertarDatosEnOrdenadoEnNodo(nodoActual,claveAInsertar,valorAInsertar);
                    }
                    return;
                }else {
                    int posicionPorDondeBajar = this.porDondeBajar(nodoActual,claveAInsertar);
                    if (nodoActual.esHijoVacio(posicionPorDondeBajar)){
                        NodoMVias<K,V> nuevoHijo = new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                        nodoActual = NodoMVias.nodoVacio();
                    }else{
                        nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                    }
                }
            }
        }
    }
    //falta
    protected void insertarDatosEnOrdenadoEnNodo(NodoMVias<K,V> nodoActual,
            K claveAInsertar,V valorAInsertar){
        if (NodoMVias.datoVacio() == nodoActual.getClave(0)){
            nodoActual.setClave(0, claveAInsertar);
            nodoActual.setValor(0, valorAInsertar);
        }
        int posicion = nodoActual.cantidadDeClavesNoVacios() -1;
        K KMayor = nodoActual.getClave(posicion);
        V VMayor = nodoActual.getValor(posicion);
        nodoActual.setClave(posicion + 1, KMayor);
        nodoActual.setValor(posicion+1,VMayor);
        for (int i = posicion; claveAInsertar.compareTo(KMayor) < 0; i--){
            nodoActual.setClave(i+1, KMayor);
            nodoActual.setValor(i+1, VMayor);
            nodoActual.setClave(i, KMayor);
            nodoActual.setValor(i, VMayor);
            if (i - 1 >= 0){
                KMayor = nodoActual.getClave(i-1);
            }else {
                KMayor = claveAInsertar;
            }
        }
    }
    //falta
    protected int porDondeBajar(NodoMVias<K, V> nodoActual, K claveAInsertar) {
        int porDondeBajar = 0;
        K claveActual = nodoActual.getClave(porDondeBajar);
        while (claveAInsertar.compareTo(claveActual) > 0) {
            porDondeBajar++;
            //preguntar si es llego a la ultima clave para bajar
            if (porDondeBajar < nodoActual.cantidadDeClavesNoVacios()){
                claveActual = nodoActual.getClave(porDondeBajar);
            } else {
                    claveActual = claveAInsertar;
            }
        }
        return porDondeBajar;
    }
    
    protected int existeClaveEnNodo(NodoMVias<K,V> nodoActual,K claveABuscar){
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios(); i++){
            K claveActual = nodoActual.getClave(i);
            if (claveActual.compareTo(claveABuscar)==0){
                return i;
            }
        }
        return POSICION_INVALIDA;
    }

    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null) {
            throw new ExcepcionClaveNoExiste("Clave a eliminar no puede ser nula");
        }
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null) {
            throw new ExcepcionClaveNoExiste("La clave no existe en el arbol");
        }
        
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorARetornar;
    }
    
    private NodoMVias<K,V> eliminar(NodoMVias<K,V> nodoActual,K claveAEliminar){
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return null;
        }
        for (int i = 0; i< nodoActual.cantidadDeClavesNoVacios(); i++){
            K claveActual = nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveActual)==0){
                if (nodoActual.esHoja()){// si en caso sea hoja la clave a eliminar
                    this.eliminarDatosDeNodo(nodoActual,claveAEliminar);
                    if (nodoActual.cantidadDeClavesNoVacios() == 0){
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                //si no es hoja la clave es un nodo no hoja
                //implementar 3 metodos
                K claveDeReemplazo ;
                if (this.hayHijosMasAdelante(nodoActual,i)){
                    claveDeReemplazo = this.buscarClaveSucesoraInOrden(nodoActual,claveAEliminar);
                } else {
                    claveDeReemplazo = this.buscarClavePredecesoraInOrden(nodoActual,claveAEliminar);
                }
                V valorDeReemplazo = this.buscar(claveDeReemplazo);
                nodoActual = eliminar(nodoActual,claveDeReemplazo);
                nodoActual.setClave(i, claveDeReemplazo);
                nodoActual.setValor(i, valorDeReemplazo);
                return nodoActual;
            }   
            if (claveAEliminar.compareTo(claveActual)<0){
                NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(i),claveAEliminar);
                nodoActual.setHijo(i, supuestoNuevoHijo);
                return nodoActual;
            }
        }//fin del for ultimo hijo
        NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(
                nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios()),claveAEliminar);
        nodoActual.setHijo(nodoActual.cantidadDeClavesNoVacios(), supuestoNuevoHijo);
        return nodoActual;
    }
    protected void eliminarDatosDeNodo(NodoMVias<K,V> nodoActual,K claveAEliminar){
        int posicion = nodoActual.cantidadDeClavesNoVacios() - 1;
        K datoActual = (K) nodoActual.getClave(posicion);
        nodoActual.setClave(nodoActual.cantidadDeClavesNoVacios() - 1, null);
        for (int i = posicion - 1; claveAEliminar.compareTo(datoActual) != 0; i--) {
            K datoAuxiliar = nodoActual.getClave(i);
            nodoActual.setClave(i, datoActual);
            datoActual = datoAuxiliar;
        }
    }
    private boolean hayHijosMasAdelante(NodoMVias<K,V> nodoActual, Integer posicion) {
        if (!NodoMVias.esNodoVacio(nodoActual.getHijo(posicion + 1)) ||
                nodoActual.getClave(posicion + 1) != NodoMVias.datoVacio()) {
            return true;
        }
        return false;
    }
    
      private K buscarClaveSucesoraInOrden(NodoMVias<K,V> nodoActual, K claveAEliminar) {
        K sucesor = (K) NodoMVias.datoVacio();

        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios(); i++) {
            sucesor = (K) nodoActual.getClave(i);
            if (claveAEliminar.compareTo(sucesor) == 0) {
                if (NodoMVias.esNodoVacio(nodoActual.getHijo(i + 1))) {
                    return (K) nodoActual.getClave(i + 1);
                }
                nodoActual = nodoActual.getHijo(i + 1);
                i = nodoActual.cantidadDeClavesNoVacios() + 1;
            }
        }
        while (!NodoMVias.esNodoVacio(nodoActual.getHijo(0))) {
            nodoActual = nodoActual.getHijo(0);
        }
        sucesor = (K) nodoActual.getClave(0);
        return sucesor;
    }  
      
        private K buscarClavePredecesoraInOrden(NodoMVias<K,V> nodoActual, K datoAEliminar) {
        K predecesor = (K) NodoMVias.datoVacio();
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacios(); i++) {
            predecesor = (K) nodoActual.getClave(i);
            if (datoAEliminar.compareTo(predecesor) == 0) {
                nodoActual = nodoActual.getHijo(i);
                predecesor = nodoActual.getClave(nodoActual.cantidadDeClavesNoVacios() - 1);
            }
        }

        while (!NodoMVias.esNodoVacio(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios()))) {
            nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacios());
            predecesor = nodoActual.getClave(nodoActual.cantidadDeClavesNoVacios() - 1);
        }
        return predecesor;
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
