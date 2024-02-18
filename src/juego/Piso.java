package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;


public class Piso {
	private double x;
	
	private double y;
	
	private double ancho;
	
	private double alto;
	
	private Image img;
	
	
	public Piso(){
		
		this.img = Herramientas.cargarImagen("suelo.png");

		this.x = 400;
		
		this.y = 560;
		
		this.ancho=1800;
		
		this.alto=200;

	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void dibujar(Entorno e) { //DIBUJA LA IMAGEN DEL PISO EN EL ENTORNO
		e.dibujarImagen(img,this.x, this.y, 0,1.9);
	}
}


