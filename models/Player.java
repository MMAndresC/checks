package com.svalero.presencial.models;

public class Player {
	int damasQuedan;
	int damasMuertas;
	String status;
	int contarNoMov;
	int movColumna;
	int movLinea;
	boolean turno;
	int numero;

	public Player(boolean active, int number) {
		this.damasMuertas = 0;
		this.damasQuedan = 12;
		this.status = "playing";
		this.contarNoMov = 0;
		this.turno = active;
		this.numero = number;
	}

	public int getDamasQuedan() {
		return damasQuedan;
	}

	public void setDamasQuedan(int damasQuedan) {
		this.damasQuedan = damasQuedan;
	}

	public int getDamasMuertas() {
		return damasMuertas;
	}

	public void setDamasMuertas(int damasMuertas) {
		this.damasMuertas = damasMuertas;
	}

	public int getContarNoMov() {
		return contarNoMov;
	}

	public void setContarNoMov(int contarNoMov) {
		this.contarNoMov = contarNoMov;
	}

	public int getMovColumna() {
		return movColumna;
	}

	public void setMovColumna(int movColumna) {
		this.movColumna = movColumna;
	}

	public int getMovLinea() {
		return movLinea;
	}

	public void setMovLinea(int movLinea) {
		this.movLinea = movLinea;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isTurno() {
		return turno;
	}

	public void setTurno(boolean active) {
		this.turno = active;
	}



}
