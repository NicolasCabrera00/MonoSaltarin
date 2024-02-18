package juego;

import java.awt.Image;

import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Plataforma {
	private double x;
	
	private double y;
	
	private double ancho;
	
	private double alto;
	
	private double vel;
	
	private double escala;
	
	private boolean dioPuntos;
	
	private Image arbol;
	
	private Image rama;
	
	Plataforma(double x, double y, double escala){
		
		this.x = x;
		
		this.y = y;
		
		this.ancho=90;
		
		this.alto=10;
		
		this.vel = 1;
		
		this.escala = escala;
		
		this.arbol = Herramientas.cargarImagen("arbol.png");
		
		this.rama = Herramientas.cargarImagen("tronco.png");
		
		this.dioPuntos = false;

	}
		
	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public boolean getDioPuntos() {
		return dioPuntos;
	}

	public void setDioPuntos(boolean dioPuntos) {
		this.dioPuntos = dioPuntos;
	}

	public void dibujar(Entorno e) { //DIBUJA LA IMAGEN DEL ARBOL Y EL TRONCO EN EL ENTORNO
		e.dibujarImagen(this.arbol,this.x, this.y, 0, this.escala);
		
		e.dibujarImagen(this.rama,this.x, this.y, 0,0.2);
	}
	
	public void avanzar() { //DISMINUYE LAS COOREDENAS X DE LAS PLATAFORMAS
		this.x -= this.vel;
	}

	public static void agregarPlataforma(Plataforma[] plat) { //RECORRE UN ARRAY TIPO PLATAFORMA
		double var1 = 0;
		for(int i = 0; i < plat.length; i++) {
			if(plat[i] != null && plat[i].getX() > var1) { //VERIFICA CUALES DE ELLAS ES MAYOR EN LA COORDENADA X
				var1 = plat[i].getX();//GUARDA LA COORNDENADA EN UNA VARIABLE
			}
		}
		
		Random rnd = new Random(); //CREA UNA VARIABLE RANDOM 
		Plataforma[] opcion = new Plataforma[3]; //CREA UN ARRAY DE PLATAFORMAS
		int var = 0;
		opcion[0] = new Plataforma(var1+200,380,0.4); //DIFERENTES OPCIONES
		opcion[1] = new Plataforma(var1+300, 390,0.3);
		opcion[2] = new Plataforma(var1+400, 350,0.6);
 
		var = rnd.nextInt(0, 3); //CREA UNA VARIABLE PARA ACCEDER A UNA DE LAS PLATAFORMAS DEL ARRAY ANTERIOR
		for (int i=0 ; i<plat.length; i++) {//RECORRE UN ARRAY TIPO PLATAFORMA Y EN UN ESPACIO NULL CREA UN NUEVA PLATAFORMA
			if(plat[i] == null) {
				plat[i] = opcion[var]; //USA UNA PLATAFORMA DEL ARRAY OPCION
				return;
			}
		}
	}
	
	public boolean estaFuera() {//VERIFICA SI LA PLATAFORMA PASO DE LAS COORDENADAS X=0 
		if (x - ancho / 2 < 0-ancho) {
			return true;
		}
		return false;			
	}
	
	public boolean pasoDe300() {//VERIFICA SI LAS CORDENADAS DE LA PLATAFORMA PASARON DE X=300
		if (x - ancho / 2 < 300-ancho) {
			return true;
		}
		return false;	
	}	
	
}
