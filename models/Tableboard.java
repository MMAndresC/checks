package com.svalero.presencial.models;

import java.util.Arrays;

public class Tableboard {
	private char[][] tablero;
	
	public Tableboard () {
		this.tablero = new char[8][8];
	}
	
	
	public void inicializarTablero() {
		int size = 8;
		for (int i = 0; i < size; i++) {
			Arrays.fill(this.tablero[i], 'L');
	    }
	}
	
	public void rellenarTablero() {
		int[] pares = {1, 5, 7};
		int[] impares = {0, 2, 6};
		for(int i = 0; i < this.tablero.length; i++) {
			if (i == 0) {
				this.tablero[pares[0]][i] = '1';
				this.tablero[pares[1]][i] = '2';
				this.tablero[pares[2]][i] = '2';
			}
			if(i % 2 == 0){
				this.tablero[pares[0]][i] = '1';
				this.tablero[pares[1]][i] = '2';
				this.tablero[pares[2]][i] = '2';
				
			}
			if(i % 2 != 0) {
				this.tablero[impares[0]][i] = '1';
				this.tablero[impares[1]][i] = '1';
				this.tablero[impares[2]][i] = '2';
			}
		}
	}
	public char[][] getTablero() {
		return tablero;
	}

	public void setTablero(char[][] tablero) {
		this.tablero = tablero;
	}

	public void MostrarTablero() {
		for(int i = 0; i < 8; i++) {
			if(i== 0) {
				System.out.print("   ");
				for(int j = 0; j < 8; j++) {
					System.out.print(j + "  ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.print(i + "  ");
			for(int j = 0; j < 8; j++) {
				System.out.print(this.tablero[i][j] + "  ");
			}
			System.out.println();
		}
	}
	
	public void modificarElementoTablero(int x, int y, char elemento) {
        this.tablero[x][y] = elemento;
    }
}
