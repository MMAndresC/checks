package com.svalero.presencial;

import com.svalero.presencial.game.Game;
import com.svalero.presencial.models.Player;
import com.svalero.presencial.models.Tableboard;

public class Main {

	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED = "\u001B[31m";
	
	public static boolean controlMov(Player player, Tableboard tablero, Player other) {
		int[] damaSeleccionada = new int[2];
		int[] coordMov = new int[2];
		boolean valido = false;
		boolean rendirse = false;
		char charNumerojugador, enemigo;
		char opcion = ' ';
		int siguienteLinea = -1;
		int linea = -1, cDerecha = -1, cIzquierda = -1;
		int[] coordDerecha = new int[2];
		int[] coordIzquierda = new int[2];
		String posibilidades = "";
		coordIzquierda[0] = -1;
		coordIzquierda[1] = -1;
		coordDerecha[0] = -1;
		coordDerecha[1] = -1;
		if(player.getNumero() == 1) {
			charNumerojugador = '1';
			enemigo = '2';
		}else{
			charNumerojugador = '2';
			enemigo = '1';
		}
		System.out.println();
		System.out.println();
		if(player.getNumero() == 1){
			System.out.println("Turno de las damas rojas");
		}else{
			System.out.println("Turno de las damas blancas");
		}
		opcion = Game.leerPasoTurno();
		if(opcion == 'n') {
			do {
				cDerecha = -1;
				cIzquierda = -1;
				System.out.print("Fila de la ficha que vas a mover: ");
				damaSeleccionada[0] = Game.leerCoordenadas();
				System.out.print("Columna de la ficha que vas a mover: ");
				damaSeleccionada[1] = Game.leerCoordenadas();
				if(tablero.getTablero()[damaSeleccionada[0]][damaSeleccionada[1]] == charNumerojugador) {
					if(player.getNumero() == 1) {
						linea = damaSeleccionada[0] + 1;
						siguienteLinea = damaSeleccionada[0] + 2 <= 7 ? damaSeleccionada[0] + 2 : -1;
					}else {
						linea = damaSeleccionada[0] - 1;
						siguienteLinea = damaSeleccionada[0] - 2 >= 0 ? damaSeleccionada[0] - 2 : -1;
					}
					if(damaSeleccionada[1] - 1 >= 0) {
						cIzquierda = damaSeleccionada[1] - 1;
					}
					if(damaSeleccionada[1] + 1 <= 7) {
						cDerecha = damaSeleccionada[1] + 1;
					}
					if(cDerecha != -1) {
						if(tablero.getTablero()[linea][cDerecha] == 'L'){
							valido = true;
							coordDerecha[0] = linea;
							coordDerecha[1] = cDerecha;
						}else if(tablero.getTablero()[linea][cDerecha] == enemigo && siguienteLinea != -1)
							if(cDerecha + 1 <= 7){
								if(tablero.getTablero()[siguienteLinea][cDerecha + 1] == 'L'){
									valido= true;
									coordDerecha[0] = siguienteLinea;
									coordDerecha[1] = cDerecha + 1;
								}
							}
						}
					}
					if(cIzquierda != -1) {
						if(tablero.getTablero()[linea][cIzquierda] == 'L') {
							valido = true;
							coordIzquierda[0] = linea;
							coordIzquierda[1] = cIzquierda;
						}else if(tablero.getTablero()[linea][cIzquierda] == enemigo && siguienteLinea != -1){
							if(cIzquierda - 1 >= 0){
								if(tablero.getTablero()[siguienteLinea][cIzquierda - 1] == 'L'){
									valido = true;
									coordIzquierda[0] = siguienteLinea;
									coordIzquierda[1] = cIzquierda - 1;
								}
							}
						}
					}

				if(!valido) System.out.println(ANSI_RED + "ERROR ficha mal seleccionada" + ANSI_RESET);
			}while(!valido);
			valido = false;
			if(coordIzquierda[1] != -1){
				posibilidades = Integer.toString(coordIzquierda[1]);
			}
			if(coordDerecha[1] != -1){
				posibilidades = posibilidades != "" ? posibilidades + " y " + coordDerecha[1] : Integer.toString(coordDerecha[1]);
			}
			System.out.println(ANSI_GREEN + "Posibles movimientos columnas: " + posibilidades + ANSI_RESET);
			do {
				System.out.print("Columna a la que quieres mover: ");
				coordMov[1] = Game.leerCoordenadas();
				if(coordMov[1] == coordDerecha[1] || coordMov[1] == coordIzquierda[1]){
					valido = true;
					coordMov[0] = coordMov[1] == coordDerecha[1] ? coordDerecha[0] : coordIzquierda[0];
				}
				if(!valido) System.out.println(ANSI_RED + "Error, movimiento no permitido" + ANSI_RESET);
			}while(!valido);
		
			player.setMovLinea(coordMov[0]);
			player.setMovColumna(coordMov[1]);
			tablero.modificarElementoTablero(coordMov[0], coordMov[1], charNumerojugador);
			tablero.modificarElementoTablero(damaSeleccionada[0], damaSeleccionada[1], 'L');
			//Esto es para diferenciar si se ha comido una ficha para borrar la ficha comida del tablero
			//Añade la ficha al contador del jugador que se la ha comido
			if(player.getMovLinea() != linea){
				other.setDamasQuedan(other.getDamasQuedan() - 1);
				player.setDamasMuertas(player.getDamasMuertas() + 1);
				tablero.modificarElementoTablero(linea, coordMov[1], 'L');
				if(coordIzquierda[1] == coordMov[1]){
					tablero.modificarElementoTablero(linea, coordMov[1] + 1, 'L');
				}else{
					tablero.modificarElementoTablero(linea, coordMov[1] - 1, 'L');
				}
			}
		}else if(opcion == 's'){
			player.setContarNoMov(player.getContarNoMov() + 1);
		}else if(opcion == 'r') {
			rendirse = true;
		}
		return rendirse;
	}
	

	public static void main(String[] args) {
		boolean finJuego = false;
		Tableboard tablero = new Tableboard();
		Player player1 = new Player(true, 1);
		Player player2 = new Player(false, 2);
		boolean rendirse;
		tablero.inicializarTablero();
		tablero.rellenarTablero();
		tablero.MostrarTablero();
		do {
			if(player1.isTurno()) {
				rendirse = controlMov(player1, tablero, player2);
				if(!rendirse) {
					tablero.MostrarTablero();
					System.out.println(ANSI_BLUE + "Fichas comidas: " + player1.getDamasMuertas() + ANSI_RESET);
					player2.setTurno(true);
					player1.setTurno(false);
				}else {
					finJuego = true;
				}
			}else {
				rendirse = controlMov(player2, tablero, player1);
				if(!rendirse) {
					tablero.MostrarTablero();
					System.out.println(ANSI_BLUE + "Fichas comidas: " + player2.getDamasMuertas() + ANSI_RESET);
					player1.setTurno(true);
					player2.setTurno(false);
				}else {
					finJuego = true;
				}
			}
			//Si los dos jugadores pasan de mover se acaba el juego
			if(player1.getContarNoMov() == 1 && player2.getContarNoMov() == 1) finJuego = true;
			//Si un jugador se queda sin damas
			if(player1.getDamasQuedan() == 0 || player2.getDamasQuedan() == 0) finJuego = true;
			//Si un jugador pasa de mover 3 turnos seguidos
			if(player1.getContarNoMov() == 3 || player2.getContarNoMov() == 3) finJuego = true;
		}while(!finJuego);
	}

}
