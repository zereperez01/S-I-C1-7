package Persistencia;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Estado {
	
	private int[] id = new int[2];	
	private int valor;
	private Celda celda;
	private Celda[] vecinos = new Celda[4];
	
	public Estado(int valor, int[] id, Celda[][] laberinto) {
		this.valor = valor;
		this.id = id.clone();
		celda=laberinto[id[0]][id[1]];
		if(celda.devolverNorte())
			vecinos[0]=laberinto[id[0]-1][id[1]];
		if(celda.devolverEste())
			vecinos[1]=laberinto[id[0]][id[1]+1];
		if(celda.devolverSur())
			vecinos[2]=laberinto[id[0]+1][id[1]];
		if(celda.devolverOeste())
			vecinos[3]=laberinto[id[0]][id[1]-1];
	}
	
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id.clone();
	}

	public Celda getCelda() {
		return celda;
	}

	public void setCelda(Celda celda) {
		this.celda = celda;
	}

	public Celda[] getVecinos() {
		return vecinos;
	}

	public void setVecinos(Celda[] vecinos) {
		this.vecinos = vecinos.clone();
	}

	public Queue<Sucesor> sucesores(Celda[][] laberinto) {
		Queue<Sucesor> sucesores= new LinkedList<Sucesor>();
		int[] nuevo= new int[2];

			if(celda.devolverNorte()) {
				nuevo[0]=id[0]-1;
				nuevo[1]=id[1];			
				Estado vecino= new Estado(valor+1,nuevo,laberinto);
				sucesores.add(new Sucesor("N",vecino,1));
			}else
				sucesores.add(null);
			if(celda.devolverEste()) {
				nuevo[0]=id[0];
				nuevo[1]=id[1]+1;
				Estado vecino= new Estado(valor+1,nuevo,laberinto);
				sucesores.add(new Sucesor("E",vecino,1));
			}else
				sucesores.add(null);
			if(celda.devolverSur()) {
				nuevo[0]=id[0]+1;
				nuevo[1]=id[1];
				Estado vecino= new Estado(valor+1,nuevo,laberinto);
				sucesores.add(new Sucesor("S",vecino,1));
			}else
				sucesores.add(null);
			if(celda.devolverOeste()) {
				nuevo[0]=id[0];
				nuevo[1]=id[1]-1;
				Estado vecino= new Estado(valor+1,nuevo,laberinto);
				sucesores.add(new Sucesor("O",vecino,1));
			}else
				sucesores.add(null);
		return sucesores;
	}

	@Override
	public String toString() {
		return "Estado [id=" + Arrays.toString(id) + ", valor=" + valor + ", celda=" + celda.toString() + ", vecinos="
				+ Arrays.toString(vecinos) + "]";
	}

	public String mostrarId() {
		System.out.print("(" + id[0] + "," + id[1] + ")");
		return null;
	}
}
