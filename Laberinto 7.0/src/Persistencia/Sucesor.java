package Persistencia;

public class Sucesor {
	String mov;
	Estado estado;
	int costo;

	public Sucesor(String mov, Estado estado, int costo) {
		super();
		this.mov = mov;
		this.estado = estado;
		this.costo = costo;
	}

	public String getMov() {
		return mov;
	}

	public void setMov(String mov) {
		this.mov = mov;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	@Override
	public String toString() {
		return "Sucesor [mov=" + mov + ", estado=(" + estado.getId()[0] + "," +estado.getId()[1] + "), costo=" + costo + "]";
	}

}
