 package juego;

import java.awt.Image;

import entorno.Entorno;

import entorno.Herramientas;

public class Proyectil {
	
	private double x;
	
	private double y;
	
	private double diametro;
	
	private Image img;
	
	private double velocidad;
	
	public Proyectil(double x, double y) {
		
		this.x=x;
		
		this.y=y;
		
		this.diametro = 10;

		this.velocidad = 10;
		
		this.img = Herramientas.cargarImagen("piedra.png");
	}
	
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDiametro() {
		return diametro;
	}

	public void avanzar() { //AUMENTA LAS COORDENADAS X DEL PROYECTIL 
		this.x += 1 * this.velocidad;
	}
	
	public void dibujar(Entorno e) { //DIBUJA AL PROYECTIL EN EL ENTORNO
		e.dibujarImagen(img, x, y, 0,0.06);

	}

	public boolean estaFuera() { //VERIFICA SI EL PROYECTIL PASO DE LAS COORDENADAS X=800 
		if (x - diametro / 2 > 800-diametro) {
			return true;
		}
		return false;	
	}



	

		
}


