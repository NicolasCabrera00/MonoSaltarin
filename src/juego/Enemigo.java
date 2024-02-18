package juego;



import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	
	private double x;
	
	private double y;
	
	private double ancho;
	
	private double alto;
	
	private double angulo;
	
	private double vel;
	
	private Image tigre;
	
	private Image serpiente;
	
	private Image aguila;
	
	private boolean dioPuntos;
	
	Enemigo(double x, double y, double ancho, double alto, double vel){
		
		this.x = x;
		
		this.y = y;
		
		this.ancho= ancho;
		
		this.alto=alto;
		
		this.angulo=0;
		
		this.vel= vel;
		
		this.tigre = Herramientas.cargarImagen("tigre.png");
		
		this.serpiente = Herramientas.cargarImagen("serpiente.png");		
		
		this.aguila = Herramientas.cargarImagen("aguila.png");
		
		this.dioPuntos = false;
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
	
	public void avanzar() {
		this.x -= vel;
	}
		
	public boolean getDioPuntos() {
		return dioPuntos;
	}

	public void setDioPuntos(boolean dioPuntos) {
		this.dioPuntos = dioPuntos;
	}

	public void dibujarTigre(Entorno e) { //DIBUJA ENEMIGO TIGRE A PARTIR DE UNA IMAGEN 
		e.dibujarImagen(tigre, x, y, 0,0.50);
	}
	
	public void dibujarSerpiente(Entorno e) { //DIBUJA ENEMIGO SERPIENTE A PARTIR DE UNA IMAGEN 
		e.dibujarImagen(serpiente, x, y, 0,0.15);
	}
	
	public void dibujarAguila(Entorno e) { //DIBUJA ENEMIGO AGUILA A PARTIR DE UNA IMAGEN 
//		e.dibujarCirculo(x, y, 50, Color.RED);
		e.dibujarImagen(aguila, x, y, 0.20,1);
	}
	
	public boolean enemigoEstaFuera() { //VERIFICA SI EL ENEMIGO PASO DE LAS CORDENADAS X=0 
		if (x - ancho / 2 < 0-ancho) {
			return true;
		}
		return false;	
	}

	public static void agregarTigre(Enemigo[] tigre) { //RECORRE UN ARRAY TIPO ENEMIGO Y EN UN ESPACIO NULL CREA UN NUEVO ENEMIGO 
		for (int i=0 ; i<tigre.length; i++) {
			if(tigre[i] == null) {
				tigre[i] = new Enemigo(999, 445, 50, 35, 2);
				return;
			}
		}
		
	}
	
	public static void agregarSerpiente(Enemigo[] serpiente, Plataforma[] plataformas) { 
		double var = 0;
		int pos = 0;
		for(int i = 0; i < plataformas.length; i++) { //RECORRE ARRAY TIPO PLATAFORMA
			if(plataformas[i] != null && plataformas[i].getX() > var) {//VERIFICA CUALES DE ELLAS ES MAYOR EN LA COORDENADA X
				var = plataformas[i].getX();//GUARDA LA COORNDENADA EN UNA VARIABLE
				pos = i;//GUARDA LA POSICION DE ESA PLATAFORMA CON "X" MAS GRANDE DEL ARRAY
			}
		}
		
		for (int i=0 ; i<serpiente.length; i++) { //RECORRE UN ARRAY TIPO ENEMIGO Y EN UN ESPACIO NULL CREA UN NUEVO ENEMIGO
			if(serpiente[i] == null) {
				serpiente[i] = new Enemigo(var+40, plataformas[pos].getY()-20, 20, 20, 1);//AGREGA UNA NUEVA SERPIENTE CON LAS COORDENADAS DE LAS VARIABLES ANTERIORES
				return;
			}
		}
		
	}

	

	public static void agregarAguila(Enemigo[] aguila) { //RECORRE UN ARRAY TIPO ENEMIGO Y EN UN ESPACIO NULL CREA UN NUEVO ENEMIGO
		for(int i = 0; i < aguila.length; i++) {
			if(aguila[i] == null) {
				aguila[i] = new Enemigo(3000, -1300, 50, 50, 4);
			}
		}
		
	}	
	public boolean chocaConProy(Proyectil p) { //VERIFICA SI EL ENEMIGO CHOCA CON UN PROYECTIL 
		return (x - ancho/2 < p.getX() + p.getDiametro()/2 && 
				p.getX() - p.getDiametro()/2 < x + ancho/2 &&
				
				y - alto/2 < p.getY() + p.getDiametro()/2 &&
				p.getY() - p.getDiametro()/2 < y + alto/2);
	}
	

	public boolean chocaConProyectil(Proyectil[] proyectil) { //RECORRE UN ARRAY TIPO PROFECTIL Y VERIFICA SI ALGUNO CHOCA CON UN ENEMIGO
		for(int i = 0; i < proyectil.length; i++) {
			if(proyectil[i] != null && chocaConProy(proyectil[i])) {
				proyectil[i] = null;
				return true;

			}
		}
		return false;
	}


	public void avanzarAguila() { //AVANZA EL AGUILA EN LAS COORDENADAS X E Y.
		this.x -= Math.cos(1+100) * this.vel;
		this.y += Math.sin(1-300) * this.vel;
	}


	
	
	
	
}	
	







	





