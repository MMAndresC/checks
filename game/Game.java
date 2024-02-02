package com.svalero.presencial.game;

import java.util.Scanner;

public class Game {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static int leerCoordenadas() {
        int num = 0;
        String aux;
        boolean numeroValido = false;
        Scanner input = new Scanner(System.in);
        do{
            aux = input.nextLine();
            try{
                num = Integer.parseInt(aux);
                if(num >= 0 && num <= 7){
                    numeroValido = true;
                }else {
                    System.out.println(ANSI_RED + "ERROR coordenada no existente" + ANSI_RESET);
                }
            }catch(Exception err){
                System.out.println(ANSI_RED + "ERROR debe introducir un numero" + ANSI_RESET);
            }
        }while(!numeroValido);
        return num;
    }
    
    public static char leerPasoTurno() {
        String respuesta;
        boolean esCharValido = false;
        Scanner input = new Scanner(System.in);
        do {
            System.out.print("Pasas turno? S/N o te rindes? R:");
            respuesta = input.nextLine().toLowerCase();
            if(respuesta.length() == 1) {
                if(respuesta.charAt(0) == 'n' || respuesta.charAt(0) == 's' || respuesta.charAt(0) == 'r' ){
                    esCharValido = true;
                }
            }
            if(!esCharValido) System.out.println(ANSI_RED + "ERROR teclea S, N o R" + ANSI_RESET);
        }while(!esCharValido);
        return respuesta.charAt(0);
    }
}
