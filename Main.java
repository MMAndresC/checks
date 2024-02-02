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
		char charNumerojugador;
		char opcion = ' ';
		if(player.getNumero() == 1) {
			charNumerojugador = '1';
		}else{
			charNumerojugador = '2';
		}
		int linea, cDerecha, cIzquierda;
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
					}else {
						linea = damaSeleccionada[0] - 1;
					}
					if(damaSeleccionada[1] - 1 >= 0) {
						cIzquierda = damaSeleccionada[1] - 1;
					}
					if(damaSeleccionada[1] + 1 <= 7) {
						cDerecha = damaSeleccionada[1] + 1;
					}
					if(cDerecha != -1) {
						if(tablero.getTablero()[linea][cDerecha] == 'L') valido = true;
					}
					if(cIzquierda != -1) {
						if(tablero.getTablero()[linea][cIzquierda] == 'L') valido = true;
					}
				}
				if(!valido) System.out.println("ERROR ficha mal seleccionada");
			}while(!valido);
			valido = false;
			cIzquierda = -1;
			cDerecha = -1;
			do {
				if(player.getNumero() == 1) {
					if((damaSeleccionada[0] + 1) < 8) coordMov[0] = damaSeleccionada[0] + 1;
				}
				if(player.getNumero() == 2) {
					if((damaSeleccionada[0] - 1) >=0) coordMov[0] = damaSeleccionada[0] - 1;
				}
				
				System.out.print("Columna a la que quieres mover: ");
				coordMov[1] = Game.leerCoordenadas();
				if(damaSeleccionada[1] - 1 >= 0) cIzquierda = damaSeleccionada[1] - 1;
				if(damaSeleccionada[1] + 1 <= 7) cDerecha = damaSeleccionada[1] + 1;
				if(coordMov[1] == cIzquierda || coordMov[1] == cDerecha)valido = true;
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
					checkSituation(tablero, player1, player2);
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
					checkSituation(tablero, player2, player1);
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
		char enemy = ' ', element =' ';
		boolean puedeMatar = false;
		linea = player.getMovLinea();
		if(player.getNumero() == 1 && (player.getMovLinea() + 2) < 8 ) {
			linea++;
			siguienteLinea = linea + 1;
			element = '1';
			enemy = '2';
		}
		if(player.getNumero() == 2 && (player.getMovLinea() - 2) >= 0 ) {
			linea--;
			siguienteLinea = linea - 1;
			element = '2';
			enemy = '1';
		} 
		if(player.getMovColumna() - 2 >= 0) {
			cIzquierda = player.getMovColumna() - 1;
		}
		if(player.getMovColumna() + 2 <= 7) {
			cDerecha = player.getMovColumna() + 1;
		}
		if(cIzquierda != -1 && linea != -1 && (board.getTablero()[linea][cIzquierda]) == enemy ) {
			if(board.getTablero()[siguienteLinea][cIzquierda - 1] == 'L') {
				puedeMatar = true;
				board.modificarElementoTablero(siguienteLinea ,cIzquierda - 1, element);
				board.modificarElementoTablero(player.getMovLinea(),player.getMovColumna(), 'L');
				board.modificarElementoTablero(linea, cIzquierda, 'L');
				other.setDamasQuedan(other.getDamasQuedan() - 1);
				player.setDamasMuertas(player.getDamasMuertas() + 1);
			}
		}
		if(cDerecha != -1 && !puedeMatar && linea != -1 && (board.getTablero()[linea][cDerecha]) == enemy ) {
			if(board.getTablero()[siguienteLinea][cDerecha + 1] == 'L') {
				puedeMatar = true;
				board.modificarElementoTablero(siguienteLinea ,cDerecha + 1, element);
				board.modificarElementoTablero(player.getMovLinea(),player.getMovColumna(), 'L');
				board.modificarElementoTablero(linea, cDerecha, 'L');
				other.setDamasQuedan(other.getDamasQuedan() - 1);
				player.setDamasMuertas(player.getDamasMuertas() + 1);
			}
		}	
	}
}
