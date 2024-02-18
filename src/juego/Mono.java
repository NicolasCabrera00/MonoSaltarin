package juego;

import java.awt.Image;

import entorno.Entorno;

import entorno.Herramientas;

public class Mono {
	private double x;
	
	private double y;
	
	private double ancho;
	
	private double alto;
	
	private double angulo;
	
	private double vel;
	
	private Image img, img2;
	
	int tiempoSalto;
	
	Mono(double x, double y){
		
		this.x = x;
		
		this.y = y;
		
		this.ancho=50;
		
		this.alto=75;
		
		this.angulo=0;
		
		this.vel= 1;
		
		this.img = Herramientas.cargarImagen("mono.png");
		
		this.img2 = Herramientas.cargarImagen("monomuerto.png");
	}
	
	public int getTiempoSalto() {
		return tiempoSalto;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public void dibujar(Entorno e) { //DIBUJA PERSONAJE MONO A PARTIR DE UNA IMAGEN 
		e.dibujarImagen(img, x, y, 0, 0.30);
	
	}
	
	public void dibujarm(Entorno e) {
		e.dibujarImagen(img2, x, y, 0, 0.30);
		
	}
	
	//Salto
	void inicioSalto() { //INICIO DEL CONTADOR SALTO
		tiempoSalto =20;
	}
	
	public void salto(Entorno e) { //MIENTRAS TIEMPO SALTO SEA MAYOR A 0, LA COORDENADA Y DEL MONO DISMINUYE CREANDO EL EFECTO DEL SALTO
		if(tiempoSalto>0) {
		this.y -= vel*10;
		tiempoSalto --;
		}
	}
	
	
	public void gravedad(Entorno e, double grav) { //AUMENTA LA COORDENADA Y DEL MONO CREANDO EL EFECTO DE GRAVEDAD
		this.y += grav;	
	}
	
	
	public boolean chocaConPiso(Piso b) { //VERIFICA SI EL BORDE DEL MONO CHOCA CON EL BORDE DEL PISO
		return (b.getX() - b.getAncho() < x + alto/2 && 
				x - alto/2 < b.getX() + b.getAncho()/2 &&
				
				b.getY() - b.getAlto()/2 < y + alto/2 &&
				y - alto/2 < b.getY() + b.getAlto()/2);
	}
	
	public boolean chocaConPlat(Plataforma b) {	//VERIFICA SI EL BORDE DE LA PLATAFORMA CHOCA CON EL BORDE INFERIOR DEL MONO	
		return (b.getX() - b.getAncho()/2 < x + ancho/2 && 
				x - ancho/2 < b.getX() + b.getAncho()/2 &&
				
				b.getY() - b.getAlto()/2 < y + alto/2 &&
				y + alto/2 < b.getY() + b.getAlto()/2);	//borde de arriba
	}
	

	public boolean chocaConPlataformas(Plataforma[] plataformas) { //RECORRE UN ARRAY TIPO PLATAFORMA, DEVUELVE TRUE SI EL MONO CHOCA CON UNA DE ELLAS
		for(int i = 0; i < plataformas.length; i++) {
			if(plataformas[i] != null && chocaConPlat(plataformas[i]))
				return true;
	}
		return false;
	}
	
	public boolean monoEnArbol(Plataforma[] plataformas) {	//RECORRE UN ARRAY TIPO PLATAFORMA, VERIFICA SI LA PLATAFORMA ENTREGO PUNTOS- 												
		for(int i = 0; i < plataformas.length; i++) { 		// -Y SI CHOCA CON LA PLATAFORMA SE TERMINA EL BUCLE
			if(plataformas[i] != null) {
				if(plataformas[i].getDioPuntos() == false) {
					if(chocaConPlat(plataformas[i])) {
						plataformas[i].setDioPuntos(true);
						return true;
					}
				}	
			}
		}
		return false;
	}
	
	public boolean chocaConEnemigo(Enemigo b) { //VERIFICA SI EL MONO CHOCA CON EL ENEMIGO
		return (b.getX() - b.getAncho()/2 < x + ancho/2 && 
				x - ancho/2 < b.getX() + b.getAncho()/2 && b.getY() - b.getAlto()/2 < y + alto/2 &&
				y - alto/2 < b.getY() + b.getAlto()/2);
	}
	
	public boolean chocaConEnemigos(Enemigo[] enemigo) { //RECORRE UN ARRAY TIPO ENEMIGO, VERIFICA SI EL ENEMIGO ENTREGO PUNTOS, Y SI CHOCA CON LA PLATAFORMA SE TERMINA EL BUCLE
		for(int i=0; i< enemigo.length; i++) {
			if(enemigo[i] != null) {
				if(enemigo[i].getDioPuntos() == false) {
					if(chocaConEnemigo(enemigo[i])) {
						enemigo[i].setDioPuntos(true);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void shot(Proyectil[] proyectil) { //CREA UN NUEVO PROYERCTIL EN LAS COORDENANAS DEL MONO.
		for (int i=0 ; i<proyectil.length; i++) {
			if(proyectil[i] ==null) {
				proyectil[i] = new Proyectil(x,y+10);
				return;
			}
		}
	}

	public void muere() {
		//this.x -= vel*10;
		this.y -= vel*3;
	}
	
	public boolean seVaDeEntorno() {
		if (y - alto / 2 < -100) {
			return true;
		}
		return false;	
	}




}

