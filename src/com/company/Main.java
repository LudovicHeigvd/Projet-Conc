package com.company;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        Observateur obs = new Observateur();
        LinkedList<Benne> bennesForetRemplir = new LinkedList<Benne>();
        LinkedList<Benne> bennesForetTransport = new LinkedList<Benne>();
        LinkedList<Benne> bennesUsineVider = new LinkedList<Benne>();
        LinkedList<Benne> bennesUsineTranspot = new LinkedList<Benne>();

        Bucheron mathilde = new Bucheron(bennesForetRemplir,bennesUsineTranspot,obs);

        Transporteur bertrand = new Transporteur(obs,
                bennesForetRemplir,
                bennesForetTransport,
                bennesUsineVider,
                bennesUsineTranspot);

        Ouvrier robert = new Ouvrier(bennesUsineVider,bennesForetTransport,obs);
        mathilde.start();
        bertrand.start();
        robert.start();
    }
}
