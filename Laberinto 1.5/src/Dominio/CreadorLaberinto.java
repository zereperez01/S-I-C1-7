package Dominio;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import Persistencia.Celda;
public class CreadorLaberinto {

	public static void main(String[] args) {
		JSONObject object = new JSONObject();
		Wilson w = new Wilson();
		Celda [][] M;
		M=w.algoritmoWilson(crearMatriz()).clone();
		JSONArray listaDeCeldas= new JSONArray();
		for(int f=0;f<M.length;f++) {
			for(int c=0;c<M[0].length;c++) {
				String celda ="("+f+","+c+")"+": "+M[f][c].toString()+"\n";
				listaDeCeldas.put(celda);
				
				System.out.print("Fila: "+f+" Columna: "+c+" Paredes: {"+M[f][c].devolverNorte()+","+M[f][c].devolverEste()+","
						+M[f][c].devolverSur()+","+M[f][c].devolverOeste()+"}\n");
			}
			
		}
		//object.put("Filas",M.length); 
		
		object.put("Columnas",M[0].length); 
		object.put("Nº Vecinos",4);
		int [][] movimientos= {{-1,0},{0,1},{1,0},{0,-1}};
		String [] id_movimientos = {"N","E","S","O"};
		object.put("Movimientos",movimientos); 
		object.put("Id_Movimientos",id_movimientos); 
		object.put("Lista de Celdas",listaDeCeldas); 
		JSONArray lista=(JSONArray) object.get("Lista de Celdas");
		for(int i=0; i<M.length*M[0].length;i++) System.out.println(lista.get(i).toString());
	}
	
	public static Celda [][] crearMatriz() {
		boolean noError=false;
		byte filas=0;
		byte columnas=0;
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		do {
		try {
			System.out.print("Seleccione el número de filas del laberinto: ");
			filas=teclado.nextByte();
			if(filas<=1)
				throw new Exception("\nLas filas tienen que ser un valor positivo.");
			System.out.print("\nSeleccione el número de columnas del laberinto: ");
			columnas=teclado.nextByte();
			if(columnas<=1)
				throw new Exception("\nLas columnas tienen que ser un valor positivo.");
			noError=true;
		}catch(Exception e) {
			System.out.print("\nError al introducir los datos.\nError: "+e.toString()+"\n");
			teclado = new Scanner(System.in);
		}
		}while(!noError);
		Celda Matriz[][]= new Celda[filas][columnas];
			for(int f=0; f<Matriz.length; f++) {
				for(int c=0; c<Matriz[0].length; c++) {
					Matriz[f][c] = new Celda();
				}
			}
		return Matriz;
	}
}
