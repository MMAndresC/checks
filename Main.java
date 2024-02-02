package com.svalero.presencial;

import com.svalero.presencial.game.Game;
import com.svalero.presencial.models.Player;
import com.svalero.presencial.models.Tableboard;

public class Main {
	
	public static boolean controlMov(Player player, Tableboard tablero) {
		int[] damaSeleccionada = new int[2];
		int[] coordMov = new int[2];
		boolean valido = false;
		boolean rendirse = false;
		char charNumerojugador, enemigo;
		char opcion = ' ';
		int siguienteLinea = -1, puedeMatar = -1;
		int linea = -1, cDerecha = -1, cIzquierda = -1;
		int[] coordDerecha = new int[2];
		int[] coordIzquierda = new int[2];
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
		System.out.println("Turno del jugador " + player.getNumero());
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
						siguienteLinea = damaSeleccionada[0] + 2 > 7 ? damaSeleccionada[0] + 2 : -1;
					}else {
						linea = damaSeleccionada[0] - 1;
						siguienteLinea = damaSeleccionada[0] - 2 < 0 ? damaSeleccionada[0] - 2 : -1;
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
									puedeMatar = cDerecha;
									coordDerecha[0] = siguienteLinea;
									coordDerecha[1] = cDerecha;
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
									puedeMatar = cIzquierda;
									coordIzquierda[0] = siguienteLinea;
									coordIzquierda[1] = cIzquierda;
								}
							}
						}
					}

				if(!valido) System.out.println("ERROR ficha mal seleccionada");
			}while(!valido);
			valido = false;
			System.out.print(coordDerecha[0]);System.out.print(coordDerecha[1]);
			System.out.print(coordIzquierda[0]);System.out.print(coordIzquierda[1]);
			do {
				System.out.print("Columna a la que quieres mover: ");
				coordMov[1] = Game.leerCoordenadas();
				if(coordMov[1] == coordDerecha[1] || coordMov[1] == coordIzquierda[1]){
					valido = true;
					coordMov[0] = coordMov[1] == coordDerecha[1] ? coordDerecha[0] : coordIzquierda[0];
				}
				if(!valido) System.out.println("Error, movimiento no permitido");
			}while(!valido);
		
			player.setMovLinea(coordMov[0]);
			player.setMovColumna(coordMov[1]);
			tablero.modificarElementoTablero(coordMov[0], coordMov[1], charNumerojugador);
			tablero.modificarElementoTablero(damaSeleccionada[0], damaSeleccionada[1], 'L');
		}else if(opcion == 's'){
			player.setContarNoMov(1);
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
				rendirse = controlMov(player1, tablero);
				if(!rendirse) {
					//checkSituation(tablero, player1, player2);
					tablero.MostrarTablero();
					System.out.println("Fichas comidas: " + player1.getDamasMuertas());
					player2.setTurno(true);
					player1.setTurno(false);
				}else {
					finJuego = true;
				}
			}else {
				rendirse = controlMov(player2, tablero);
				if(!rendirse) {
					//checkSituation(tablero, player2, player1);
					tablero.MostrarTablero();
					System.out.println("Fichas comidas: " + player2.getDamasMuertas());
					player1.setTurno(true);
					player2.setTurno(false);
				}else {
					finJuego = true;
				}
			}
			if(player1.getContarNoMov() == 1 && player2.getContarNoMov() == 1) finJuego = true;
			if(player1.getDamasQuedan() == 0 || player2.getDamasQuedan() == 0) finJuego = true;
		}while(!finJuego);
		
	}

	public static void checkSituation(Tableboard board, Player player, Player other) {
		int linea = -1, siguienteLinea = 0, cIzquierda = -1, cDerecha = -1;
		char enemigo = ' ', elemento =' ';
		boolean puedeMatar = false;
		linea = player.getMovLinea();
		if(player.getNumero() == 1 && (player.getMovLinea() + 2) < 8 ) {
			linea++;
			siguienteLinea = linea + 1;
			elemento = '1';
			enemigo = '2';
		}
		if(player.getNumero() == 2 && (player.getMovLinea() - 2) >= 0 ) {
			linea--;
			siguienteLinea = linea - 1;
			elemento = '2';
			enemigo = '1';
		} 
		if(player.getMovColumna() - 2 >= 0) {
			cIzquierda = player.getMovColumna() - 1;
		}
		if(player.getMovColumna() + 2 <= 7) {
			cDerecha = player.getMovColumna() + 1;
		}

		if(cIzquierda != -1 && linea != -1 && (board.getTablero()[linea][cIzquierda]) == enemigo ) {
			if(board.getTablero()[siguienteLinea][cIzquierda - 1] == 'L') {
				puedeMatar = true;
				board.modificarElementoTablero(siguienteLinea ,cIzquierda - 1, elemento);
				board.modificarElementoTablero(player.getMovLinea(),player.getMovColumna(), 'L');
				board.modificarElementoTablero(linea, cIzquierda, 'L');
				other.setDamasQuedan(other.getDamasQuedan() - 1);
				player.setDamasMuertas(player.getDamasMuertas() + 1);
			}
		}
		if(cDerecha != -1 && !puedeMatar && linea != -1 && (board.getTablero()[linea][cDerecha]) == enemigo ) {
			if(board.getTablero()[siguienteLinea][cDerecha + 1] == 'L') {
				puedeMatar = true;
				board.modificarElementoTablero(siguienteLinea ,cDerecha + 1, elemento);
				board.modificarElementoTablero(player.getMovLinea(),player.getMovColumna(), 'L');
				board.modificarElementoTablero(linea, cDerecha, 'L');
				other.setDamasQuedan(other.getDamasQuedan() - 1);
				player.setDamasMuertas(player.getDamasMuertas() + 1);
			}
		}
	}
}
