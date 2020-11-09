package Dominio;

import org.json.JSONObject;
import Persistencia.Celda;
import Persistencia.Estado;
import Persistencia.Nodo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RecorridoyEstados {
	
	public static void pruebaLongitudMaxima(int max) {
		try {
			PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>(); // lista donde vamos a organizar los elementos que se podran elegir a continuacion
			for(int i=0; i<max ;i++) {
				Nodo x= new Nodo(i, null, i, i, i, i);
				frontera.add(x);
				System.out.println("Nº "+i);
			}
		}catch(Exception e) {
			System.out.print("Frontera desbordada: "+e.toString());
			System.exit(1);
		}
	}
	
	public static void pruebaOrdenarFrontera(Celda [][]M) {
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>(); // lista donde vamos a organizar los elementos que se podran elegir a continuacion
		int valor;
		int[] coordenada= new int[2];
		for(int i=0; i<100 ;i++) {
			valor=(int) ((Math.random() * (50 - 0)) + 0);;
			coordenada[0]=(int) ((Math.random() * (M.length - 0)) + 0);
			coordenada[1]=(int) ((Math.random() * (M[0].length - 0)) + 0);
			Estado y= new Estado(valor, coordenada, M);
			Nodo x= new Nodo(i, y, valor, i, i, i);
			frontera.add(x);
		}
		while(!frontera.isEmpty()) System.out.println(frontera.remove().toString());
	}
	
	public static void problema_salir_del_laberinto(Celda [][]M, String ruta) {
		JSONObject problema = new JSONObject();
		byte fila_inicial = 0, columna_inicial = 0, fila_final = 0, columna_final = 0;
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>(); // lista donde vamos a organizar los elementos que se podran elegir a continuacion
		Stack<Nodo> recorrido= new Stack<Nodo>();
		boolean noError=false;
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		do {
		System.out.print("Introduzca las coordenadas de la casilla origen y la casilla final."); // Describimos las coordenadas del nodo inicio 
		try {
		do {
			System.out.print("\nFila inicio:");
			fila_inicial = teclado.nextByte();
			if(fila_inicial>=M.length || fila_inicial<0)
				System.out.print("Valores validos comprendidos entre [0-"+ (M.length-1) +"]");
		} while(fila_inicial>=M.length || fila_inicial<0);
		do {
			System.out.print("\nColumna inicio:");
			columna_inicial = teclado.nextByte();
			if(columna_inicial>=M.length || fila_inicial<0)
				System.out.print("Valores validos comprendidos entre [0-"+ (M[0].length-1) +"]");
		} while(columna_inicial>=M[0].length || columna_inicial<0);
		do {
			do {
				System.out.println("\nFila destino:");
				fila_final = teclado.nextByte();
				if(fila_final>=M.length || fila_inicial<0)
					System.out.print("Valores validos comprendidos entre [0-"+ (M.length-1) +"]");
			} while(fila_final>=M.length || fila_final<0);
			do {
				System.out.println("\nColumna destino:");
				columna_final = teclado.nextByte();
				if(columna_final>=M.length || fila_inicial<0)
					System.out.print("\nValores validos comprendidos entre [0-"+ (M.length-1) +"]");
			} while(columna_final>=M[0].length || columna_final<0);
		}while(fila_final == fila_inicial && columna_inicial == columna_final);
		noError=true;
		}catch(Exception e) {
			System.out.print("\nError al introducir los datos.\nError: "+e.toString()+"\n");
			teclado = new Scanner(System.in);
		}
		}while(!noError);
		
		//Metemos en la frontera el nodo inicial
		int[] coordenadas= new int[2]; // Indicamos las coordenadas del origen
		coordenadas[0]=fila_inicial;
		coordenadas[1]=columna_inicial;
		Estado estado_inicial =new Estado(0,coordenadas,M);
		problema.put("INITIAL",coordenadas.clone());
		coordenadas[0]=fila_final;
		coordenadas[1]=columna_final;
		Estado estado_final =new Estado(0,coordenadas,M);
		problema.put("OBJECTIVE",coordenadas.clone());
		problema.put("MAZE",ruta);
		
		try {
            FileWriter fileWriter = new FileWriter("problema.json");
            fileWriter.write(problema.toString());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Json del problema creado.");
        } catch (IOException ex) {
            System.out.println("Error al crear el json del problema.");
            System.exit(1);
        }
	}
	
	public static boolean Objetivo(Estado actual, Estado destino) {
		return actual.getId().equals(destino.getId());
	}
}
