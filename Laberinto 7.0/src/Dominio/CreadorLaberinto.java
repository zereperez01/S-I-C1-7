package Dominio;

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import Persistencia.Celda;
import org.json.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class CreadorLaberinto {
	
	private static Celda [][] M;
	private static String ruta=null;

	public static void main(String[] args) {
		byte opcion = seleccionarOpcion();
		switch(opcion) {
			case 1:
				Wilson w = new Wilson();
				M=w.algoritmoWilson(crearMatriz()).clone();
				mostrarMatrizLaberinto();
				dibujarLaberinto();
				crearJson();
				break;
			case 2:
				M=leerJson().clone();
				if(!comprobarInconsistencias()) {
				mostrarMatrizLaberinto();
				dibujarLaberinto();
				}else {
					System.out.print("Inconsistencia encontrada en el archivo json cargado.");
					System.exit(1);
				}break;
		}
		opcion = menu();
		switch(opcion) {
			case 1:
				int valor = introducirValor();
				RecorridoyEstados.pruebaLongitudMaxima(valor);
				break;
			case 2:
				RecorridoyEstados.pruebaOrdenarFrontera(M);
				break;
			case 3:
				RecorridoyEstados.problema_salir_del_laberinto(M, ruta);
				break;
		}
	}

	private static int introducirValor() {
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		int valor=0;
		boolean noError=false;
		do {
			try {
				System.out.print("Introduzca el número de interacciones.\n");
				valor=teclado.nextInt();
				noError=true;
			}catch(Exception e) {
				System.out.print("Error al introducir los datos.\nError: "+e.toString()+"\n");
				teclado = new Scanner(System.in);
			}
			}while(!noError);
		return valor;
	}

	private static byte menu() {
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		byte opcion=0;
		boolean noError=false;
		do {
			try {
				System.out.print("Seleccione la acción deseada:\n1. Realizar prueba de maxima longitud admitida en la frontera.\n2. Realizar prueba de frontera ordenada.\n3. Generar Json del problema.\n");
				opcion=teclado.nextByte();
				if(opcion<1 || opcion>3)
					throw new Exception("Solo se puede introducir una de las dos opciones.\n");
				noError=true;
			}catch(Exception e) {
				System.out.print("Error al introducir los datos.\nError: "+e.toString()+"\n");
				teclado = new Scanner(System.in);
			}
			}while(!noError);
		return opcion;
	}

	private static boolean comprobarInconsistencias() {
		for(int f=0;f<M.length;f++) {
			for(int c=0;c<M[0].length;c++) {
				if(f-1>=0 && (M[f][c].devolverNorte() != M[f-1][c].devolverSur()))
					return true;
				if(c+1<M[0].length && (M[f][c].devolverEste() != M[f][c+1].devolverOeste()))
					return true;
				if(f+1<M.length && (M[f][c].devolverSur() != M[f+1][c].devolverNorte()))
					return true;
				if(c-1>=0 && (M[f][c].devolverOeste() != M[f][c-1].devolverEste()))
					return true;
			}
		}
		return false;
	}

	private static void mostrarMatrizLaberinto() {
		for(int f=0;f<M.length;f++) {
			for(int c=0;c<M[0].length;c++) {
				System.out.print("Fila: "+f+" Columna: "+c+" Paredes: {"+M[f][c].devolverNorte()+","+M[f][c].devolverEste()+","
						+M[f][c].devolverSur()+","+M[f][c].devolverOeste()+"}\n");
			}
		}
	}

	private static byte seleccionarOpcion() {
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		byte opcion=0;
		boolean noError=false;
		do {
			try {
				System.out.print("Seleccione la acción deseada:\n1. Crear laberinto.\n2. Cargar laberinto.\n");
				opcion=teclado.nextByte();
				if(opcion<1 || opcion>2)
					throw new Exception("Solo se puede introducir una de las dos opciones.\n");
				noError=true;
			}catch(Exception e) {
				System.out.print("Error al introducir los datos.\nError: "+e.toString()+"\n");
				teclado = new Scanner(System.in);
			}
			}while(!noError);
		return opcion;
	}

	public static void dibujarLaberinto(){
        int size=20;
		BufferedImage bf = new BufferedImage((M[0].length*size)+6,(M.length*size)+6,BufferedImage.TYPE_INT_RGB);
		Graphics g = bf.createGraphics();
        int h = 3;
        int v = 3;
        int cf=0;
        int cc=0;
        g.setColor(Color.white);
        g.fillRect(0, 0, (M[0].length*size)+10, (M.length*size)+10);
        g.setColor(Color.black);
        for(int f=0;f<M.length;f++) {
			for(int c=0;c<M[0].length;c++) {
				if(cf!=f) {
					v=v+size;
					cf=f;
					h=3;
					cc=c;
				}if(cc!=c) {
					h=h+size;
					cc=c;
				}if(!M[f][c].devolverNorte())
					g.drawLine(h, v, h+size, v);
				if(!M[f][c].devolverEste())
					g.drawLine(h+size, v, h+size, v+size);
				if(!M[f][c].devolverOeste())
					g.drawLine(h, v, h, v+size);
				if(!M[f][c].devolverSur())
					g.drawLine(h, v+size, h+size, v+size);
			}
		}
        g.dispose();
        try {
        	ImageIO.write(bf, "jpg", new File("NewLaberinto.jpg"));
        	System.out.print("Imagen del laberinto creado.\n");
        }catch(Exception e) {
        	System.out.print("Error al crear la imagen del laberinto.\n");
        }
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
	
	public static void crearJson() {
		JSONObject objectJson = new JSONObject();
		objectJson.put("rows",M.length);
		objectJson.put("cols",M[0].length);
		objectJson.put("max_n",4);
		JSONArray movimientos = new JSONArray();
		ArrayList<Integer> movimiento = new ArrayList<Integer>();
		movimiento.add(-1);
		movimiento.add(0);
		movimientos.put(movimiento);
		movimiento.clear();
		movimiento.add(0);
		movimiento.add(1);
		movimientos.put(movimiento);
		movimiento.clear();
		movimiento.add(1);
		movimiento.add(0);
		movimientos.put(movimiento);
		movimiento.clear();
		movimiento.add(0);
		movimiento.add(-1);
		movimientos.put(movimiento);
		objectJson.put("mov",movimientos);
		String []id_movimientos = {"N","E","S","O"};
		objectJson.put("id_mov",id_movimientos);
		JSONObject listaCeldas = new JSONObject();
		for (int f=0;f<M.length;f++) {
			for (int c=0;c<M[0].length;c++) {
				JSONObject celda = new JSONObject();
                boolean[] paredes = {M[f][c].devolverNorte(),M[f][c].devolverEste(),M[f][c].devolverSur(),M[f][c].devolverOeste()};
                celda.put("neighbors",paredes);
                listaCeldas.put("("+String.valueOf(f)+", "+String.valueOf(c)+")",celda);
            }
        }
        objectJson.put("cells", listaCeldas);
        try {
            FileWriter fileWriter = new FileWriter((ruta="NewLaberinto.json"));
            fileWriter.write(objectJson.toString());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Json del laberinto creado.");
        } catch (IOException ex) {
            System.out.println("Error al crear el json del laberinto.");
            System.exit(1);
        }
	}
	
	public static Celda[][] leerJson() {
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		System.out.print("Introduzca el nombre del archivo a cargar.\n");
		String rutaJson = teclado.next();
        FileReader fileReader = null;
        try {
        	fileReader = new FileReader((ruta=rutaJson));
        }catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo: "+rutaJson+"\n");
            System.exit(1);
        }
        Celda[][] Matriz = null;
        try {
        JsonParser jsonParser = new JsonParser();
        JsonElement dataJson = jsonParser.parse(fileReader);
        JsonObject objectJson = dataJson.getAsJsonObject();
        byte filas = objectJson.get("rows").getAsJsonPrimitive().getAsByte();
        byte columnas = objectJson.get("cols").getAsJsonPrimitive().getAsByte();
        JsonObject cell = objectJson.get("cells").getAsJsonObject();
        Matriz = new Celda[filas][columnas];
        for (int f=0;f<Matriz.length;f++) {
            for (int c=0;c<Matriz[0].length;c++) {
            	Matriz[f][c] = new Celda();
            	JsonObject celda = cell.get("("+f+", "+c+")").getAsJsonObject();
            	JsonArray paredes = celda.get("neighbors").getAsJsonArray();
            	if(paredes.get(0).getAsBoolean())
            		Matriz[f][c].cambiarNorte();
            	if(paredes.get(1).getAsBoolean())
            		Matriz[f][c].cambiarEste();
            	if(paredes.get(2).getAsBoolean())
            		Matriz[f][c].cambiarSur();
            	if(paredes.get(3).getAsBoolean())
            		Matriz[f][c].cambiarOeste();
            	Matriz[f][c].cambiarVisitado();
            }
        }
        }catch (Exception e) {
            System.out.println("Error al leer el archivo: "+rutaJson+"\n");
            System.exit(1);
        }
        System.out.print("Laberinto cargado.\n");
		return Matriz;
	}
}
