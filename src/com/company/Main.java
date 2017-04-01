package com.company;

public class Main {

    public static void main(String[] args) {
        Observateur obs = new Observateur();
        Echangeur echTB = new Echangeur(obs);
        Echangeur echTO = new Echangeur(obs);
	    Bucheron mathilde = new Bucheron(obs,echTB);
        mathilde.start();
    }
}
