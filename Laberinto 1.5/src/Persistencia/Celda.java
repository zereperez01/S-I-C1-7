package Persistencia;

public class Celda {
	
	@Override
	public String toString() {
		return "[norte=" + norte + ", este=" + este + ", sur=" + sur + ", oeste="
				+ oeste + "]";
	}

	private boolean visitado;
	
	private boolean norte;
	private boolean este;
	private boolean sur;
	private boolean oeste;
	
	public boolean devolverVisitado() {
		return this.visitado;
	}
	
	public void cambiarVisitado() {
		this.visitado=true;
	}
	
	public Boolean devolverNorte() {
		return this.norte;
	}
	
	public Boolean devolverEste() {
		return this.este;
	}
	
	public Boolean devolverOeste() {
		return this.oeste;
	}
	
	public Boolean devolverSur() {
		return this.sur;
	}
	
	public void cambiarNorte() {
		this.norte=true;
	}
	
	public void cambiarEste() {
		this.este=true;
	}
	
	public void cambiarOeste() {
		this.oeste=true;
	}
	
	public void cambiarSur() {
		this.sur=true;
	}
	
	public void reponerParedes() {
		this.visitado=false;
		this.norte=false;
		this.este=false;
		this.oeste=false;
		this.sur=false;
	}
}
