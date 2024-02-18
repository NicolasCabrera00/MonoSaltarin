package juego;

import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.Clip;

import entorno.Entorno;

import entorno.Herramientas;

import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	
	private Entorno entorno;  // El objeto Entorno que controla el tiempo y otros
	
	private Mono mono; 		//Controla al mono
	
	private Image fondo, perdiste;	//Imagen background del juego
	
	private Clip pou, monoGolpeado, monoSalta, tirarPiedra, golpeaEnemi ; //variales en las que se guardan los sonidos del juego 
	
	private Piso piso; //Objeto que manipula al suelo en el juego. Donde se posa el personaje, los enemigos y los árboles
	
	private Plataforma[] plataformas; //Array que contiene las plataformas en las que se posa el mono.
	
	private Proyectil[] proyectil; //Array que contiene las piedras que lanza el personaje
	
	private int puntaje;
	
	private int vidas;
	
	private boolean murio;
	
	// Arrays que contienen los enemigos del mono (Serpientes, tigres, aguilas)
	
	private Enemigo[] tigre;
	private Enemigo[] serpiente;  
	private Enemigo[] aguila;
	
	public Juego() {
		// Inicializa el objeto entorno
		
		// Inicializar lo que haga falta para el juego

		this.fondo = Herramientas.cargarImagen("fondo.jpg");
		
		this.perdiste = Herramientas.cargarImagen("perdiste.jpeg");
		
		this.entorno = new Entorno(this, "Titulo de TP - Grupo 3 - Ertel - Cabrera - Molina - V.Final", 800, 600);
		
		this.mono = new Mono(100.0,376.0);
		
		this.puntaje = 0;
		
		this.piso = new Piso();
		
		this.vidas = 3;
		
		this.murio = false;
		
		//SONIDOS DEL JUEGOS
		
				this.pou = Herramientas.cargarSonido("efectos/pou.wav");
				
				this.monoGolpeado = Herramientas.cargarSonido("efectos/monogolpeado.wav");
				
				this.monoSalta = Herramientas.cargarSonido("efectos/salto2.wav");
				
				this.tirarPiedra = Herramientas.cargarSonido("efectos/tirarpiedra.wav");
				
				this.golpeaEnemi = Herramientas.cargarSonido("efectos/matarenemigo.wav");
			
		this.plataformas = new Plataforma[5];
		
		this.plataformas[0] = new Plataforma(700,370,0.4);	
		this.plataformas[1] = new Plataforma(900, 390,0.3);
		this.plataformas[2] = new Plataforma(1200,370,0.4);
		
		this.proyectil =  new Proyectil[1];		
		
		this.tigre = new Enemigo[1]; 
		this.tigre[0] =	new Enemigo(999, 445, 50, 35, 2);
		
		this.serpiente = new Enemigo[2];
		this.serpiente[0] = new Enemigo(935, 370, 20, 20, 1);
		this.serpiente[1] = new Enemigo(1240, 340, 20, 20, 1);
		
		this.aguila = new Enemigo[1];
		this.aguila[0] = new Enemigo(3000, -1300, 50, 50, 4);
		
		this.entorno.iniciar();	
		// Inicia el juego!		
	}

	public void tick() {
		
		
		entorno.dibujarImagen(fondo, 300, 220, 0,1.4);
	
		pou.start(); //Sonido de fondo 
		

		for (int i = 0; i < plataformas.length; i++) { //Recorre el array plataformas
			
			if (plataformas[i] != null) { //busca espacios del array plataformas que no esten "null"
				plataformas[i].dibujar(entorno); //Si se cumple el if, dibuja las plataformas en el entorno
				plataformas[i].avanzar();  //Si se cumple el if, Hace que las plataformas se muevan en el eje x
				
			}
			
		
			if(plataformas[i] != null && plataformas[i].estaFuera()) {  //busca espacios del array plataformas que no esten "null" y que a la vez esten fuera del entorno
				plataformas[i]=null;//Si se cumple el if, el espacio encontrado se vuelve "null"
				}	
			//
			if(plataformas[i] != null && plataformas[i].pasoDe300()) { //Busca espacios el array que no esten "null" y que a la vez  
				Plataforma.agregarPlataforma(this.plataformas);
			}

		}
 
		if( mono.chocaConPlataformas(plataformas) || mono.chocaConPiso(piso)) { //Este if verifica si el mono esta en el piso o en la plataforma 
			mono.gravedad(entorno, 0); //Si se cumple, la gravedad del mono será 0
		}else {
			mono.gravedad(entorno, 2);//Si NO se cumple, la gravedad del mono será 2
		}
		
		if(entorno.sePresiono(entorno.TECLA_CTRL)) { //Si se presiona la tecla ctrl, el mono lanza una piedra 
			mono.shot(proyectil); 	//Objeto encargado del disparo
			tirarPiedra.loop(1);	//Hace un sonido en particular cuando el mono lanza un proyectil
		}
		
		
		for (int i = 0; i < proyectil.length; i++) { //RECORRE EL ARRAY PROYECTIL
			if (proyectil[i] != null) { //BUSCA ESPACIOS EN EL ARRAY QUE NO SEAN "NULL"
				proyectil[i].dibujar(entorno);//SI SE CUMPLE, DIBUJA EL PROYECTIL
				proyectil[i].avanzar();	//SI SE CUMPLE, HACE AVANZAR A PROYECTIL EN EL EJE X DEL ENTORNO
				
				if(proyectil[i].estaFuera()) {//VERIFICA SI UN ESPACIO DEL ARRAY SE ENCUENTRA FUERA DEL ENTORNO
					proyectil[i] = null;//SI SE CUMPLE, DICHO ESPACIO SE VUELVE NULL
				}
			}
		}
		
	if((entorno.sePresiono(entorno.TECLA_ARRIBA))//SE INICIA EL CONTADOR DEL TIEMPO DEL SALTO, SOLO SE PUEDE SALTAR SI EL CONTADOR ES 0, SI ESTA EN EL SUELO O EN UNA PLATAFORMA
					&& mono.getTiempoSalto()==0 
						&& ( mono.chocaConPlataformas(plataformas) 
							|| mono.chocaConPiso(piso)) ) { 
			
			mono.inicioSalto(); //SI SE CUMPLE, EL MONO AUMENTA SU VALOR EN EL EJE Y DEL ENTORNO
			monoSalta.loop(1); //CUANDO EL MONO SALTA, SE ESCUCHA UN SONIDO EN PARTICULAR, GRACIAS A ESTE OBJETO
			
		}	
		
		mono.salto(entorno);// SE ENCARGA DEL SALTO DEL MONO (ALTURA, VELOCIDAD DE SUBIDA Y DE BAJADA)		
		
		piso.dibujar(entorno);// OBEJTO QUE SE ENCARGA DEL ANCHO, ALTO Y APARIENCIA DEL SUELO 

		
		for(int i = 0; i < tigre.length; i++) {//RECORRE EL ARRAY TIGRE
			if(tigre != null) { //----------------->BUSCA ESPACIOS EN EL ARREGLO QUE NO SEAN "NULL"
				tigre[i].dibujarTigre(entorno);//----------------->SI SE CUMPLE, SE DIBUJA UN TIGRE 
				tigre[i].avanzar();//----------------->EL TIGRE DISMINUYE SU VALOR EN EL EJE X DEL ENTORNO
				
				//Tigre
				if(tigre[i].enemigoEstaFuera()) {//----------------->VERIFICA SI EL ESPACIO DEL ARRAY ESTA AFUERA
					tigre[i] = null;//----------------->SI SE CUMPLE, EL ESPACIO SE VUELVE NULL
					Enemigo.agregarTigre(this.tigre);//----------------->AGREGA UN TIGRE NUEVO AL ESPACIO
				}
				
				if(tigre[i].chocaConProyectil(this.proyectil)) {//VERIFICA SI EL TIGRE RECIBE UN DISPARO DEL MONO
					golpeaEnemi.loop(1);//----------------->CADA VEZ QUE UN TIGRE SEA ELIMINADO, SUENA UN PARTICULAR SONIDO
					tigre[i] = null;//----------------->SI SE CUMPLE, EL ESPACIO DEL ARRAY SE VUELVE "NULL"
					Enemigo.agregarTigre(this.tigre);//----------------->SE AGREGA UN NUEVO TIGRE EN EL ENTORNO 
					
				}	
			}
		}
		
		

		Enemigo.agregarSerpiente(this.serpiente, this.plataformas);//AGREGA ENEMIGO SERPIENTE CUANDO LOS ANTERIORES SEAN NULL 
		
		for(int i = 0; i < serpiente.length; i++) {//RECORRE EL ARRAY SERPIENTE
			if(serpiente[i] != null) {	//VERIFICA QUE SEA DIFERENTE DE NULL
				serpiente[i].dibujarSerpiente(entorno); //DIBUJA AL ENEMIGO EN EL ENTORNO
				serpiente[i].avanzar(); //LA SERPIENTE DISMINUYE SU VALOR EN EL EJE X DEL ENTORNO
				
				if(serpiente[i].chocaConProyectil(this.proyectil)) { //VERIFICA SI LA SERPIENTE RECIBE UN DISPARO DEL MONO
					golpeaEnemi.loop(1); //CADA VEZ QUE UNA SERPIENTE SEA ELIMINADA, SUENA UN PARTICULAR SONIDO
					serpiente[i] = null; //SI SE CUMPLE, EL ESPACIO DEL ARRAY SE VUELVE "NULL"
					Enemigo.agregarSerpiente(this.serpiente, this.plataformas); //SE AGREGA UNA NUEVA SERPIENTE EN EL ENTORNO
				}	
				
				if(serpiente[i].enemigoEstaFuera()) {//VERIFICA SI EL ESPACIO DEL ARRAY ESTA AFUERA
					serpiente[i] = null;//SI SE CUMPLE, EL ESPACIO SE VUELVE NULL
				}
				}
			}	
		
		//Aguilas
		for(int i=0; i < aguila.length; i++) {//RECORRE EL ARRAY AGUILA
			if(aguila[i] != null) {//VERIFICA QUE SEA DIFERENTE DE NULL
				aguila[i].dibujarAguila(entorno);//DIBUJA AL ENEMIGO EN EL ENTORNO
				aguila[i].avanzarAguila();//EL AGUILA DISMINUYE SU VALOR EN EL EJE X Y EL EJE Y DEL ENTORN
				
				
				if(aguila[i].enemigoEstaFuera()) {//VERIFICA SI EL ESPACIO DEL ARRAY ESTA AFUERA
					aguila[i] = null;//SI SE CUMPLE, EL ESPACIO SE VUELVE NULL
					Enemigo.agregarAguila(this.aguila);//AGREGA UN NUEVO ENEMIGO TIPO AGUILA AL ARRAY
				}
				
				if(aguila[i].chocaConProyectil(this.proyectil)) {//SI EL AGUILA RECIBE UN DISPARO DEL MONO
					golpeaEnemi.loop(1);//CADA VEZ QUE UN AGUILA SEA ELIMINADA, SUENA UN PARTICULAR SONIDO
					aguila[i] = null;//SI SE CUMPLE, EL ESPACIO DEL ARRAY SE VUELVE "NULL"
					Enemigo.agregarAguila(this.aguila);//SE AGREGA UN AGUILA EN EL ENTORNO
					
				}	
			}
			
				
		}
		
		//Puntaje 
		entorno.cambiarFont("Consolas", 25, Color.RED);
		entorno.escribirTexto("Puntaje: "+this.puntaje, 15, 30);
		entorno.escribirTexto("Vidas: "+this.vidas, 650, 30);
		
		
		if(mono.monoEnArbol(this.plataformas)) { //VERIFICA SI EL MONO SE SUBE A LA PLATAFOMA O ARBOL
			this.puntaje += 5;//SI SE CUMPLE, EL PUNTAJE DEL JUGADOR AUMENTA EN 5
		}
		
		//VERIFICA SI EL MONO CHOCA CON ALGUNO DE LOS DIFERENTES TIPOS DE ENEMIGO
		if(mono.chocaConEnemigos(this.serpiente) || mono.chocaConEnemigos(this.tigre) || mono.chocaConEnemigos(this.aguila)) { 
			//Efecto
			monoGolpeado.loop(1);//CADA VEZ QUE UN ENEMIGO TOQUE AL MONO, SUENA UN PARTICULAR SONIDO
			this.vidas -= 1;
			
		}
		
		if(this.murio == false) {
			mono.dibujar(entorno); //DIBUJA EN EL ENTORNO EL PERSONAJE MONO
		}else {
			mono.dibujarm(entorno);
		}
			
		if(this.vidas <= 0) {
			this.murio = true;
			pou.stop();
			mono.muere();
			entorno.cambiarFont("consolas", 50, Color.RED);
			entorno.escribirTexto("PERDISTE", 290, 100);	
			if(mono.seVaDeEntorno()) {
				entorno.dibujarImagen(this.perdiste, 400, 330, 0,1.0);
			}
			
		}	

				
		} 
			
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
