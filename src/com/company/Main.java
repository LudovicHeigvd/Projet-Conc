package com.company;

public class Main {
    public static boolean travail=true;
    public static void main(String[] args) {
        Observateur obs = new Observateur();
	    Bucheron mathilde = new Bucheron(obs);
        mathilde.start();
    }
}
