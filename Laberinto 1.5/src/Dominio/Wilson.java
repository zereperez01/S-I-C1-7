package Dominio;

import java.util.ArrayList;
import java.util.ListIterator;

import Persistencia.Celda;

public class Wilson {
	
	public Celda[][] algoritmoWilson(Celda [][] M) {
		byte fila_posicion;
		byte columna_posicion;
		byte fila_inicial=elegir_Fila_o_Columna(M,(byte)0);
		byte columna_inicial=elegir_Fila_o_Columna(M,(byte)1);
		M[fila_inicial][columna_inicial].cambiarVisitado();
		boolean esCaminoAlternativo;
		byte eleccion=0;
		ArrayList<Celda> recorrido = new ArrayList<Celda>(); 
			while(!laberintoCreado(M)) {
				fila_posicion=elegir_Fila_o_Columna(M,(byte)0);
				columna_posicion=elegir_Fila_o_Columna(M,(byte)1);
				if(!M[fila_posicion][columna_posicion].devolverVisitado()) {
					M[fila_posicion][columna_posicion].cambiarVisitado();
					esCaminoAlternativo=false;
					recorrido.clear();
					recorrido.add(M[fila_posicion][columna_posicion]);
					while((!esDestino(fila_posicion, columna_posicion, fila_inicial, columna_inicial)) && (!esCaminoAlternativo)) {
						eleccion = ((byte) ((Math.random() * (5 - 1)) + 1));
						if (esPosible(M,fila_posicion,columna_posicion,eleccion)) {
							switch(eleccion) {
							case 1:
									M[fila_posicion][columna_posicion].cambiarNorte();
									--fila_posicion;
									M[fila_posicion][columna_posicion].cambiarSur();
									break;
							case 2:
									M[fila_posicion][columna_posicion].cambiarEste();
									++columna_posicion;
									M[fila_posicion][columna_posicion].cambiarOeste();
									break;
							case 3:
									M[fila_posicion][columna_posicion].cambiarSur();
									++fila_posicion;
									M[fila_posicion][columna_posicion].cambiarNorte();
									break;
							case 4:
									M[fila_posicion][columna_posicion].cambiarOeste();
									--columna_posicion;
									M[fila_posicion][columna_posicion].cambiarEste();
									break;
							}
							if(!M[fila_posicion][columna_posicion].devolverVisitado()) {
								M[fila_posicion][columna_posicion].cambiarVisitado();
								recorrido.add(M[fila_posicion][columna_posicion]);
							}else if(comprobarExistenciaBucle(M,recorrido,fila_posicion,columna_posicion))
								deshacerBucle(M,recorrido,fila_posicion,columna_posicion);
							else
								esCaminoAlternativo=true;
						}
					}
				}
			}
		return M;
	}
	
	public boolean esDestino(byte fila_posicion, byte columna_posicion,byte fila_inicial, byte columna_inicial) {
		if((fila_posicion==fila_inicial) && (columna_posicion==columna_inicial))
			return true;
		else
			return false;
	}
	
	public byte elegir_Fila_o_Columna(Celda [][] M, byte fc) {
		if(fc==0)
			return ((byte) ((Math.random() * (M.length - 0)) + 0));
		else
			return ((byte) ((Math.random() * (M[0].length - 0)) + 0));
	}
	
	public boolean laberintoCreado(Celda [][] M) {
		for(int f=0;f<M.length;f++) {
			for(int c=0;c<M[0].length;c++) {
				if(M[f][c].devolverVisitado()!=true)
					return false;
			}
		}
		return true;
	}
	
	public boolean esPosible(Celda [][] M, byte fila_posicion, byte columna_posicion, byte eleccion) {
		switch(eleccion) {
		case 1:
			if(((fila_posicion-1)>=0) && (!M[fila_posicion-1][columna_posicion].devolverSur()))
				return true;
			else
				return false;
		case 2:
			if(((columna_posicion+1)<M[0].length) && (!M[fila_posicion][columna_posicion+1].devolverOeste()))
				return true;
			else
				return false;
		case 3:
			if(((fila_posicion+1)<M.length) && (!M[fila_posicion+1][columna_posicion].devolverNorte()))
				return true;
			else
				return false;
		case 4:
			if(((columna_posicion-1)>=0) && (!M[fila_posicion][columna_posicion-1].devolverEste()))
				return true;
			else
				return false;
		default:
			return false;
		}
	}
	
	public boolean comprobarExistenciaBucle(Celda [][] M, ArrayList<Celda> recorrido, byte fila_posicion, byte columna_posicion) {
		ListIterator<Celda> iter = recorrido.listIterator(recorrido.size());
			while(iter.hasPrevious()) {
				if(iter.previous().equals(M[fila_posicion][columna_posicion]))
					return true;
			}
		return false;
	}
	
	public void deshacerBucle(Celda [][] M, ArrayList <Celda> recorrido, byte fila_posicion, byte columna_posicion) {
		ListIterator<Celda> iter = recorrido.listIterator(recorrido.size());
		Celda celda;
		boolean bucleDeshecho = false;
			while(iter.hasPrevious() && !bucleDeshecho) {
				celda=iter.previous();
				if(!(celda.equals(M[fila_posicion][columna_posicion]))) {
					celda.reponerParedes();
				}else {
					celda.reponerParedes();
					celda.cambiarVisitado();
					bucleDeshecho=true;
				}
			}
	}
}

//Corregir bucle infinito debido a la disponibilidad de una unica celda.