/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf310sb.arboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author harol
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements
        IArbolBusqueda<K, V> {

    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {

    }

    /**
     * Instanciar arbol reconstruyeron en base a sus recorrido inOrden y
     * (PreOrden a PostOrden). Si el parametro usandoPreOrden es verdadero, los
     * parametros clavesNoInOrden y valoresNoInOrden tendran el recorrido en
     * PreOrden del arbol, caso contrario seran el postOrden.
     *
     * @param clavesInOrden
     * @param valoresInOrden
     * @param clavesNoInOrden
     * @param valoresNoInOrden
     * @param usandoPreOrden
     */
    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesNoInOrden, List<V> valoresNoInOrden,
            boolean usandoPreOrden) {
        if (clavesInOrden.isEmpty() || clavesNoInOrden.isEmpty()
                || valoresInOrden.isEmpty() || valoresNoInOrden.isEmpty()) {
            throw new IllegalArgumentException("Los parametros no pueden ser vacios");
        }
        if (clavesInOrden == null || clavesNoInOrden == null
                || valoresInOrden == null || valoresNoInOrden == null) {
            throw new IllegalArgumentException("Los parametros no pueden ser nulos");
        }
        if (clavesInOrden.size() != clavesNoInOrden.size()
                || clavesInOrden.size() != valoresInOrden.size()
                || clavesInOrden.size() != valoresNoInOrden.size()) {
            throw new IllegalArgumentException("Los parametros no pueden ser listas dif tamaos");
        }
        if (usandoPreOrden) {
            this.raiz = reconstruirConPreOrden(clavesInOrden, valoresInOrden,
                    clavesNoInOrden, valoresNoInOrden);
        } else {
            this.raiz = reconstruirConPostOrden(clavesInOrden, valoresInOrden,
                    clavesNoInOrden, valoresNoInOrden);
        }
    }

    private NodoBinario<K, V> reconstruirConPreOrden(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesEnPreOrden, List<V> valoresEnPreOrden) {
        if (clavesInOrden.isEmpty()) {
            return null;
        }
        int posicionDeClavePadreEnPreOrden = 0;
        K clavePadre = clavesEnPreOrden.get(posicionDeClavePadreEnPreOrden);
        V valorPadre = valoresEnPreOrden.get(posicionDeClavePadreEnPreOrden);
        int posicionDeClavePadreEnInOrden = this.posicionDeClave(clavePadre, clavesInOrden);
        //para armar la rama izquierda
        List<K> clavesInOrdenPorIquierda = clavesInOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<V> valoresInOrdenPorIquierda = valoresInOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<K> clavesPreOrdenPorIquierda = clavesEnPreOrden.subList(1, posicionDeClavePadreEnInOrden + 1);
        List<V> valoresPreOrdenPorIquierda = valoresEnPreOrden.subList(1, posicionDeClavePadreEnInOrden + 1);
        NodoBinario<K, V> hijoIzquierdo = reconstruirConPreOrden(clavesInOrdenPorIquierda,
                valoresInOrdenPorIquierda, clavesPreOrdenPorIquierda,
                valoresPreOrdenPorIquierda);
        //para armar la rama derecha
        List<K> clavesInOrdenPorDerecha = clavesInOrden.subList(posicionDeClavePadreEnInOrden + 1,
                clavesInOrden.size());
        List<V> valoresInOrdenPorDerecha = valoresInOrden.subList(posicionDeClavePadreEnInOrden + 1,
                clavesInOrden.size());
        List<K> clavesPreOrdenPorDerecha = clavesEnPreOrden.subList(posicionDeClavePadreEnInOrden + 1,
                clavesEnPreOrden.size());
        List<V> valoresPreOrdenPorDerecha = valoresEnPreOrden.subList(posicionDeClavePadreEnInOrden + 1,
                valoresEnPreOrden.size());
        NodoBinario<K, V> hijoDerecho = reconstruirConPreOrden(clavesInOrdenPorDerecha,
                valoresInOrdenPorDerecha, clavesPreOrdenPorDerecha,
                valoresPreOrdenPorDerecha);
        //armando el nodoActual
        NodoBinario<K, V> nodoActual = new NodoBinario<>(clavePadre, valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        return nodoActual;
    }

    private NodoBinario<K, V> reconstruirConPostOrden(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesEnPostOrden, List<V> valoresEnPostOrden) {
        if (clavesInOrden.isEmpty()) {
            return null;
        }
        int posicionDeClavePadreEnPostOrden = clavesEnPostOrden.size() - 1;
        K clavePadre = clavesEnPostOrden.get(posicionDeClavePadreEnPostOrden);
        V valorPadre = valoresEnPostOrden.get(posicionDeClavePadreEnPostOrden);
        int posicionDeClavePadreEnInOrden = this.posicionDeClave(clavePadre, clavesInOrden);
        //para armar la rama izquierda
        List<K> clavesInOrdenPorIquierda = clavesInOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<V> valoresInOrdenPorIquierda = valoresInOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<K> clavesPostOrdenPorIzquierda = clavesEnPostOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<V> valoresPostOrdenPorIzquierda = valoresEnPostOrden.subList(0, posicionDeClavePadreEnInOrden);
        NodoBinario<K, V> hijoIzquierdo = reconstruirConPostOrden(clavesInOrdenPorIquierda,
                valoresInOrdenPorIquierda, clavesPostOrdenPorIzquierda,
                valoresPostOrdenPorIzquierda);
        //para armar la rama derecha
        List<K> clavesInOrdenPorDerecha = clavesInOrden.subList(posicionDeClavePadreEnInOrden + 1,
                clavesInOrden.size());
        List<V> valoresInOrdenPorDerecha = valoresInOrden.subList(posicionDeClavePadreEnInOrden + 1,
                clavesInOrden.size());
        List<K> clavesPostOrdenPorDerecha = clavesEnPostOrden.subList(posicionDeClavePadreEnInOrden,
                clavesEnPostOrden.size() - 1);
        List<V> valoresPostOrdenPorDerecha = valoresEnPostOrden.subList(posicionDeClavePadreEnInOrden,
                valoresEnPostOrden.size() - 1);
        NodoBinario<K, V> hijoDerecho = reconstruirConPreOrden(clavesInOrdenPorDerecha,
                valoresInOrdenPorDerecha, clavesPostOrdenPorDerecha,
                valoresPostOrdenPorDerecha);
        //armando el nodoActual
        NodoBinario<K, V> nodoActual = new NodoBinario<>(clavePadre, valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        return nodoActual;
    }

    private int posicionDeClave(K claveABuscar, List<K> listaDeClaves) {
        for (int i = 0; i < listaDeClaves.size(); i++) {
            K claveActual = listaDeClaves.get(i);
            if (claveActual.compareTo(claveABuscar) == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void vaciar() {
        this.raiz = (NodoBinario<K, V>) NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidadNodos = 0;
        Stack<NodoBinario<K, V>> pilaNodos = new Stack<>();
        pilaNodos.push(this.raiz);
        while (!pilaNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaNodos.pop();
            cantidadNodos++;
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return cantidadNodos;
    }

    @Override
    public int altura() { //metodo altura recursivo 
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }

        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha ? alturaPorIzquierda + 1 : alturaPorDerecha + 1;
    }

    //meotodo altura iterativo
    public int alturaIt() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int alturaDelArbol = 0;
        Queue<NodoBinario<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            int cantidadNodosEnLaCola = colaNodos.size();
            int i = 0;
            while (i < cantidadNodosEnLaCola) {
                NodoBinario<K, V> nodoActual = colaNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
            alturaDelArbol++;
        }
        return alturaDelArbol;
    }

    //metodo cantidad de hijos DERECHOS
    public int cantidadHijosDerechos() {
        return cantidadHijosDerechos(this.raiz);
    }

    private int cantidadHijosDerechos(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int hdRamaIzquierda = cantidadHijosDerechos(nodoActual.getHijoIzquierdo());
        int hdRamaDerecha = cantidadHijosDerechos(nodoActual.getHijoDerecho());
        if (!nodoActual.esVacioHijoDerecho()) {
            return hdRamaDerecha + hdRamaIzquierda + 1;
        }
        return hdRamaDerecha + hdRamaIzquierda;
    }

    @Override
    public int nivel() {
        return this.nivel(this.raiz);
    }

    private int nivel(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return -1;
        }
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha ? alturaPorIzquierda : alturaPorDerecha;
    }

    @Override
    public K minimo() {
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = (NodoBinario<K, V>) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior.getClave();
    }

    /*
    implementar un metodo que retorne si un arbol binario, tiene nodos completos,
    es decir nodos que tengan su dos hijos diferentes de vacio en el nivel n
     */
    public boolean tieneNodosCompletosEnNIvel(int nivelObjetivo) {
        return tieneNodosCompletosEnNivel(this.raiz, nivelObjetivo, 0);
    }

    private boolean tieneNodosCompletosEnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo,
            int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return true;
        }

        if (nivelActual == nivelObjetivo) {
            return !nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho();
        }
        boolean completoPorIzq = this.tieneNodosCompletosEnNivel(nodoActual.getHijoIzquierdo(),
                nivelObjetivo, nivelActual + 1);
        boolean completoPorDe = this.tieneNodosCompletosEnNivel(nodoActual.getHijoDerecho(),
                nivelObjetivo, nivelActual + 1);
        return completoPorIzq && completoPorDe;
    }

    //REPUESTA 1 PRACTICO
    @Override
    public K maximo() {
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = (NodoBinario<K, V>) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoDerecho();
        }
        return nodoAnterior.getClave();
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }

        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }

        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = (NodoBinario<K, V>) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if (claveAInsertar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else { //si ya existe reemplazo su valor
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        //crear nodo si no pillo la clave 
        NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        K clavePadre = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(clavePadre) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
    }

    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("Clave a eliminar no puede ser nula");
        }
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null) {
            throw new IllegalArgumentException("La clave no existe en el arbol");
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return null;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return null;
        }
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> posibleNuevoHDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(posibleNuevoHDerecho);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> posibleNuevoHIzquierdoo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(posibleNuevoHIzquierdoo);
            return nodoActual;
        }
        //SI LLEGO ACA ENCONTRO EL NODO CON LA CLAVE A ALEIMINAR
        //CASO 1
        if (nodoActual.esHoja()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }
        //CASO 2
        //2.1 si solo tiene hijo izquierdo
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //2.2 si solo tiene hijo derecho
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        //CASO 3
        NodoBinario<K, V> nodoReemplazo = this.buscarNodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> posibleNuevoHDerecho = eliminar(nodoActual.getHijoIzquierdo(), nodoReemplazo.getClave());
        nodoActual.setHijoDerecho(posibleNuevoHDerecho);//puede ser que cambie dependiendo del caso 1 o 2
        //lo mas facil reemplazar la clave y el valor del nodo
        nodoActual.setClave(nodoReemplazo.getClave());
        nodoActual.setValor(nodoReemplazo.getValor());
        return nodoActual;
    }

    protected NodoBinario<K, V> buscarNodoSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior;
        do {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return nodoAnterior;
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave) != null;
    }

    @Override
    public V buscar(K claveBuscar) {
        if (claveBuscar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K, V> nodoActual = this.raiz; //recorrer el arbol
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveBuscar.compareTo(claveActual) == 0) {//si lo encuentra
                return nodoActual.getValor();
            } else if (claveBuscar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
        //si no se encuentra la claveBuscar en el arbol
        return null;
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Queue<NodoBinario<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaNodos.poll();
            recorrido.add(nodoActual.getClave()); //
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }

    @Override  //RECURSIVO
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        //para implementacion recursiva, se necesita
        //un meotod que haga el grueso del trabajo
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    //recorrido en InOrden recursivo
    public List<K> recorridoEnInOrdenRe() {
        List<K> recorrido = new ArrayList<>();
        //para implementacion recursiva, se necesita
        //un meotod que haga el grueso del trabajo
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para un caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {//caso base n = 0
            return;
        }
        recorridoEnInOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrden(nodoActual.getHijoDerecho(), recorrido);
    }

    //recorrido en PreOrden recursivo
    public List<K> recorridoEnPreOrdenRe() {
        List<K> recorrido = new ArrayList<>();
        //para implementacion recursiva, se necesita
        //un meotod que haga el grueso del trabajo
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para un caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {//caso base n = 0
            return;
        }
        recorrido.add(nodoActual.getClave());
        recorridoEnPreOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPreOrden(nodoActual.getHijoDerecho(), recorrido);
    }

    //RECURSIVO en PostOrden Recursivo
    public List<K> recorridoEnPostOrdenRe() {
        List<K> recorrido = new ArrayList<>();
        //para implementacion recursiva, se necesita
        //un meotod que haga el grueso del trabajo
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para un caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {//caso base n = 0
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPostOrden(nodoActual.getHijoDerecho(), recorrido);
        recorrido.add(nodoActual.getClave());
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaNodos = new Stack<>();
        pilaNodos.push(this.raiz);
        while (!pilaNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaNodos.pop();
            recorrido.add(nodoActual.getClave()); //
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        //recorrer en postorden
        meterPilaParaPostOrden(pilaNodos, nodoActual);
        //ya llegamos al post orden y comenzara a iterar
        while (!pilaNodos.isEmpty()) {
            nodoActual = pilaNodos.pop();                           //na = 23 24 28 25 20
            recorrido.add(nodoActual.getClave());                   //23 24 28 25 20
            if (!pilaNodos.isEmpty()) {                              //nodo = 25,28,25 20,30
                NodoBinario<K, V> nodoDelTope = pilaNodos.peek();    //nt = 24 25 25 20 30
                if (!nodoDelTope.esVacioHijoDerecho()
                        && nodoDelTope.getHijoDerecho() != nodoActual) { //
                    //volver a hacer lo mismo bucle inial recorrer izquierda
                    meterPilaParaPostOrden(pilaNodos, nodoDelTope.getHijoDerecho());  //nt = 28
                }
            }
        }
        return recorrido;
    }

    //metodo extra aux para recorrer en posotden
    private void meterPilaParaPostOrden(Stack<NodoBinario<K, V>> pilaNodos, NodoBinario<K, V> nodoActual) {
        //proceso inicial antes de interar la pila
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaNodos.push(nodoActual);                             //25,25
            if (!nodoActual.esVacioHijoIzquierdo()) {                //
                nodoActual = nodoActual.getHijoIzquierdo();          //nt = 28
            } else {
                nodoActual = nodoActual.getHijoDerecho();            //
            }
        }
    }
//2. Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario    

    public int cantidadNodosSoloHIzquierdoNoVacioRec() {
        return cantidadNodosSoloHIzquierdoNoVacioRec(this.raiz);
    }

    private int cantidadNodosSoloHIzquierdoNoVacioRec(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadIzquierda = cantidadNodosSoloHIzquierdoNoVacioRec(nodoActual.getHijoIzquierdo());
        int cantidadDerecha = cantidadNodosSoloHIzquierdoNoVacioRec(nodoActual.getHijoDerecho());
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return cantidadDerecha + cantidadIzquierda + 1;
        }
        return cantidadDerecha + cantidadIzquierda;
    }

    //3. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario    
    public int cantidadNodosSoloHIzquierdoNoVacio() {
        if (NodoBinario.esNodoVacio(this.raiz)) {
            return 0;
        }
        int cantidad = 0;
        Queue<NodoBinario<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            int cantidadNodosEnLaCola = colaNodos.size();
            int i = 0;
            while (i < cantidadNodosEnLaCola) {
                NodoBinario<K, V> nodoActual = colaNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
                    cantidad++;
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
        }
    return cantidad ;
    }
//4. Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario, pero solo en el nivel N
public int cantidadNodosNivelNSoloHIzquierdoNoVacioRec(int nivel) {
        return cantidadNodosNivelNSoloHIzquierdoNoVacioRec(this.raiz,nivel);
    }

    private int cantidadNodosNivelNSoloHIzquierdoNoVacioRec(NodoBinario<K, V> nodoActual,int nivel) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadIzquierda = cantidadNodosNivelNSoloHIzquierdoNoVacioRec(nodoActual.getHijoIzquierdo(),nivel);
        int cantidadDerecha = cantidadNodosNivelNSoloHIzquierdoNoVacioRec(nodoActual.getHijoDerecho(),nivel);
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()
                && nivel < 0) {
            return cantidadDerecha + cantidadIzquierda + 1;
        }
        
        return cantidadDerecha + cantidadIzquierda;
    }    
//5. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario, pero solo después del nivel N   
    public int cantidadNodosNivelNSoloHIzquierdoNoVacio(int nivel) {
        if (NodoBinario.esNodoVacio(this.raiz)) {
            return 0;
        }
        int cantidad = 0;
        Queue<NodoBinario<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            int cantidadNodosEnLaCola = colaNodos.size();
            int i = 0;
            while (i < cantidadNodosEnLaCola) {
                NodoBinario<K, V> nodoActual = colaNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()
                        && nivel < 0) {
                    cantidad++;
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
            nivel--;
        }
    return cantidad ;
    }
 //6. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario, pero solo antes del nivel N
    public int cantidadNodosNivelAntesNSoloHIzquierdoNoVacio(int nivel) {
        if (NodoBinario.esNodoVacio(this.raiz)) {
            return 0;
        }
        int cantidad = 0;
        Queue<NodoBinario<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()&& nivel > 0) {
            int cantidadNodosEnLaCola = colaNodos.size();
            int i = 0;
            while (i < cantidadNodosEnLaCola) {
                NodoBinario<K, V> nodoActual = colaNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
                    cantidad++;
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
            nivel--;
        }
    return cantidad ;
    }
 //9. Para un árbol binario implemente un método que retorne la cantidad de nodos que tienen
//ambos hijos desde el nivel N.
    public int cantidadNodosCompletosNivelN(int nivel) {
        if (NodoBinario.esNodoVacio(this.raiz)) {
            return 0;
        }
        int cantidad = 0;
        Queue<NodoBinario<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            int cantidadNodosEnLaCola = colaNodos.size();
            int i = 0;
            while (i < cantidadNodosEnLaCola) {
                NodoBinario<K, V> nodoActual = colaNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()
                        && nivel <= 0) {
                    cantidad++;
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
            nivel--;
        }
    return cantidad ;
    }
//10. Implementar un método que retorne un nuevo árbol binario de búsqueda invertido.
    public ArbolBinarioBusqueda<K,V> arbolInvertido(){
        ArbolBinarioBusqueda<K,V> newArbolInvertido = new ArbolBinarioBusqueda<>();
        Queue<NodoBinario<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            int cantidadNodosEnLaCola = colaNodos.size();
            int i = 0;
            while (i < cantidadNodosEnLaCola) {
                NodoBinario<K, V> nodoActual = colaNodos.poll();
                //insertar nodos al arbol invertido
                K clave = nodoActual.getClave();
                V valor = nodoActual.getValor();
                newArbolInvertido.insertarInvertido(clave, valor);
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
        }
        return newArbolInvertido; 
    }
    public void insertarInvertido(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }

        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }

        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = (NodoBinario<K, V>) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if (claveAInsertar.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else { //si ya existe reemplazo su valor
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        //crear nodo si no pillo la clave 
        NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        K clavePadre = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(clavePadre) > 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
    }
//11. Implementar un método que retorne verdadero si un árbol binario esta lleno.    
    public boolean arbolLeno () {
        if (NodoBinario.esNodoVacio(this.raiz)) {
            return false;
        }
        int cantidad = 0;
        Queue<NodoBinario<K, V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            int cantidadNodosEnLaCola = colaNodos.size();
            int i = 0;
            while (i < cantidadNodosEnLaCola) {               
                NodoBinario<K, V> nodoActual = colaNodos.poll();
                if (!nodoActual.esHoja()){
                    if (nodoActual.esVacioHijoIzquierdo() || nodoActual.esVacioHijoDerecho()) {
                        return false;
                }
                }
                if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
                    cantidad++;
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
        }
    return true ;
    }
}

