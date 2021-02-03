/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf310sb.iuArbol;

import com.inf310sb.arboles.AVL;
import com.inf310sb.arboles.ArbolBinarioBusqueda;
import com.inf310sb.arboles.ArbolMViasBusqueda;
import com.inf310sb.arboles.IArbolBusqueda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://drive.google.com/file/d/1XWlthsjRifnsjE5V-5htYMcm5l0VHwNW/view?usp=sharing
 * https://drive.google.com/file/d/1hJ9NoyBzH6lcVz_qPdMismTwAgTxD2w4/view?usp=sharing
 * https://drive.google.com/file/d/1S_9NRYlIkVx1VGqFD9VHSGU-sVjETP_n/view?usp=sharing
 * https://drive.google.com/file/d/1WW7dQahAZEzPW3zk0CGcYsFBlUuL0vw-/view?usp=sharing
 *@author harol
 */
public class TestArbol {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        List<Integer> listKInOrden = new ArrayList<>
//                    (Arrays.asList(1,2,3,4,5,6,7,8,9,10,11));
//        List<String> listVInOrden = new ArrayList<>
//        (Arrays.asList("CA","CF","CP","CZ","CK","FE","HM","LP","MK","TA","VB"));
//        //LISTAS PREORDEN
//        List<Integer> listKPreOrden = new ArrayList<>
//                    (Arrays.asList(7,5,3,1,2,4,6,10,9,8,11));   
//        List<String> listVPreOrden = new ArrayList<>
//        (Arrays.asList("HM","CK","CP","CA","CF","CZ","FE","TA","MK","LP","VB"));
//        //LISTAS POSTORDEN
//        List<Integer> listKPostOrden = new ArrayList<>
//                    (Arrays.asList(2,1,4,3,6,5,8,9,11,10,7));   
//        List<String> listVPostOrden = new ArrayList<>
//        (Arrays.asList("CF","CA","CZ","CP","FE","CK","LP","MK","VB","TA","HM"));
//        IArbolBusqueda<Integer, String> arbolPrueba = new ArbolBinarioBusqueda<>
//                    (listKInOrden,listVInOrden,listKPostOrden,listVPostOrden,false);
        
       // IArbolBusqueda<Integer, String> arbolPrueba = new ArbolBinarioBusqueda<>();
       IArbolBusqueda<Integer, String> arbolPrueba = new AVL<>();
        ArbolBinarioBusqueda<Integer,String> arbolPruebaBinario = (ArbolBinarioBusqueda<Integer,String>)arbolPrueba;
      //  AVL<Integer,String> arbolAVL = new  AVL<>();
//ejemplos del la diapositiva
        arbolPrueba.insertar(7, "HM");
        arbolPrueba.insertar(5, "CK");
        arbolPrueba.insertar(3, "CP");
        arbolPrueba.insertar(1, "CA");
        arbolPrueba.insertar(2, "CF");
        arbolPrueba.insertar(4, "CZ");
        arbolPrueba.insertar(6, "FE");
        arbolPrueba.insertar(10, "TA");
        arbolPrueba.insertar(9, "MK");
        arbolPrueba.insertar(8, "LP");
        arbolPrueba.insertar(11, "VB");
        
        System.out.println("RECORRIDOS");
        System.out.println("Rec Niveles" + arbolPrueba.recorridoPorNiveles()); 
        System.out.println("Rec InOrden Recursivo" + arbolPruebaBinario.recorridoEnInOrdenRe());
        System.out.println("Rec PosOrden" + arbolPrueba.recorridoEnPostOrden());
        System.out.println("Rec PosOrden Recursivo" + arbolPruebaBinario.recorridoEnPostOrdenRe());
        //arbolPruebaBinario.eliminar(1);
        System.out.println("Rec PreOrden" +arbolPrueba.recorridoEnPreOrden());
        //arbolPrueba.eliminar(3);
        System.out.println("Rec PreOrden Recursivo" + arbolPruebaBinario.recorridoEnPreOrdenRe());
        System.out.println("Altura Arbol: " + arbolPrueba.altura());
        System.out.println("Altura Arbol It: "+ arbolPruebaBinario.alturaIt());
        System.out.println("Max Arbol It: "+ arbolPrueba.maximo());
        System.out.println("Min Arbol it: "+ arbolPrueba.minimo());
        System.out.println("Nivel Arbol It: "+ arbolPrueba.nivel());
        System.out.println("Cantidad HijosDerecho Arbol Rec: "+ arbolPruebaBinario.cantidadHijosDerechos());
//mostar to string en preorden una sola cadena
        //implementacio
        //recorrido por nivel iterativo
        //insertar recursivo, buscar rec
/*        Trabajo práctico sobre arboles binarios
Instrucciones:
• Crear un proyecto java usando Maven
• En dicho proyecto implementar métodos como respuesta a cada uno de los siguientes
ejercicios
• El proyecto se debe almacenar en un repositorio en github (Para esto puede usar una
cuenta que ya disponga o crearse una)
• Incluir un programa en consola que haga la prueba de los métodos resueltos indicando
mediante mensajes a que pregunta es respuesta cada método probado
• En la plataforma copiar el enlace para descargarse el repositorio. Asegurarse que su
repositorio tiene acceso de lectura al público. En caso de que su repositorio no se
pueda descargar al momento de la revisión no se tomará en cuenta su trabajo.
• En cada método implementado como respuesta a las preguntas colocar el comentario
de a que pregunta corresponde la respuesta.
• NO SE ACEPTARÁ PROYECTOS COMPRIMIDOS.*/
//1. Implementar los métodos que no se implementaron en clases o que se implementaron a
//medias de árboles binarios de búsqueda y AVL
//2. Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario
System.out.println("2. cantidadNodosSoloHIzquierdoNoVacio Rec " 
        + arbolPruebaBinario.cantidadNodosSoloHIzquierdoNoVacioRec());

//3. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario
System.out.println("3. cantidadNodosSoloHIzquierdoNoVacio " +
        arbolPruebaBinario.cantidadNodosSoloHIzquierdoNoVacio());

//4. Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario, pero solo en el nivel N
//falta
System.out.println("cantidadNodosSoloNivelNHIzquierdoNoVacio Rec " + 
        arbolPruebaBinario.cantidadNodosNivelNSoloHIzquierdoNoVacioRec(2));
//5. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario, pero solo después del nivel N
System.out.println("5. cantidadNodosSoloNivelNHIzquierdoNoVacio " + 
        arbolPruebaBinario.cantidadNodosNivelNSoloHIzquierdoNoVacio(1));

//6. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo
//izquierdo no vacío en un árbol binario, pero solo antes del nivel N
System.out.println("6. cantidadNodosSoloNivel Antes de N HIzquierdoNoVacio " + 
        arbolPruebaBinario.cantidadNodosNivelAntesNSoloHIzquierdoNoVacio(2));
//7. Implemente un método recursivo que reciba como parámetro otro árbol binario de
//búsqueda que retorne verdadero, si el árbol binario es similar al árbol binario recibido como
//parámetro, falso en caso contrario.

//8. Implemente el método eliminar de un árbol AVL
//System.out.println("8. eliminar " + 
//        arbolPrueba.eliminar(3));
//9. Para un árbol binario implemente un método que retorne la cantidad de nodos que tienen
//ambos hijos desde el nivel N.
System.out.println("9. cantidadNodosConAmbosHijos desde nivel " + 
        arbolPruebaBinario.cantidadNodosCompletosNivelN(2));

//10. Implementar un método que retorne un nuevo árbol binario de búsqueda invertido.
System.out.println("10. Retornar nuevo arbol binario de busqueda invertido ");
 ArbolBinarioBusqueda<Integer,String> newArbolInvertido = new ArbolBinarioBusqueda<>();
 newArbolInvertido = arbolPruebaBinario.arbolInvertido();
 System.out.println(newArbolInvertido.recorridoEnInOrden());
//11. Implementar un método que retorne verdadero si un árbol binario esta lleno.
System.out.println("11. ÁrbolBinario lleno ");
        if (arbolPruebaBinario.arbolLeno()){
            System.out.println("TRUE");
        } else {
            System.out.println("FALSE");
        }
    }
}
