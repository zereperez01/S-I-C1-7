package Persistencia;

public class Nodo implements Comparable<Nodo> {
	private int id=0;
	private Estado estado;
	private int valor;
	private int profundidad;
	private int costo;
	private int heuristica;
	private Nodo padre;
	private String mov;

	public Nodo(int id, Estado estado, int valor, Nodo padre, String mov, int profundidad, int costo, int heuristica) {
		this.id = id;
		this.estado = estado;
		this.valor = valor;
		this.padre = padre;
		this.mov = mov;
		this.profundidad = profundidad;
		this.costo = costo;
		this.heuristica = heuristica;
	}

	public Nodo(int id, Estado estado, int valor, int profundidad, int costo, int heuristica) {
		this.id = id;
		this.estado = estado;
		this.valor = valor;
		this.profundidad = profundidad;
		this.costo = costo;
		this.heuristica = heuristica;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	public String getMov() {
		return mov;
	}

	public void setMov(String mov) {
		this.mov = mov;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public int getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(int heuristica) {
		this.heuristica = heuristica;
	}

	@Override
	public String toString() {
		return "[" + id + "][" + costo + ",(" + estado.getId()[0] + "," + estado.getId()[1]+ ")," + null +"," + mov + "," + profundidad + ","
				+ heuristica + "," + valor + "]";
	}

	@Override

	public int compareTo(Nodo nodo) {
		if (valor < nodo.valor) {
			return -1;
		}
		if (valor > nodo.valor) {
			return 1;
		}
		if (estado.getId()[0] < nodo.estado.getId()[0]) {
			return -1;
		}
		if (estado.getId()[0] > nodo.estado.getId()[0]) {
			return 1;
		}
		if (estado.getId()[1] < nodo.estado.getId()[1]) {
			return -1;
		}
		if (estado.getId()[1] > nodo.estado.getId()[1]) {
			return 1;
		}
		return 0;
	}
}
