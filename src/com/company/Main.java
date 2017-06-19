package com.company;
import java.util.LinkedList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Observateur obs = new Observateur();
        ParkingForet pf = new ParkingForet();
        ParkingUsine pu = new ParkingUsine();
    /*
        Bucheron mathilde = new Bucheron(0,pf,obs.bennesForetRemplir, obs.bennesUsineTranspot, obs.petriActrions ,obs);
        mathilde.start();
        Transporteur bertrand = new Transporteur(0,obs,pf,pu,
                obs.bennesForetRemplir,
                obs. bennesForetTransport,
                obs. bennesUsineVider,
                obs. bennesUsineTranspot,obs.petriActrions);
        bertrand.start();
        Ouvrier robert = new Ouvrier(0,pu,obs.bennesUsineVider, obs.bennesForetTransport,obs.petriActrions, obs);
        robert.start();
        Matrice mat = new Matrice(obs);
        Petri pet = new Petri(obs.petriActrions,obs,mat);
        pet.start();*/


        Random random = new Random();
       // obs.nbBenne=random.nextInt(15);
        obs.nbBenne=2;
        for(int i = 0; i <=obs.nbBenne ; i++) {
            Benne ben = new Benne(i);
            obs.bennesForetRemplir.addLast(ben);
        }


        //for (int i = 0; i <= random.nextInt(15); i ++) {
        for (int i = 0; i <2; i ++){
            Bucheron mathilde = new Bucheron(i,pf,obs.bennesForetRemplir, obs.bennesUsineTranspot, obs.petriActrions ,obs);
            mathilde.start();
        }

       // for (int i = 0; i <= random.nextInt(15); i ++) {
        for (int i = 0; i <2; i ++){
            Transporteur bertrand = new Transporteur(i,obs,pf,pu,
                    obs.bennesForetRemplir,
                    obs. bennesForetTransport,
                    obs. bennesUsineVider,
                    obs. bennesUsineTranspot,obs.petriActrions);
            bertrand.start();
        }

       // for (int i = 0; i <= random.nextInt(15); i ++)
         for (int i = 0; i <2; i ++){
            Ouvrier robert = new Ouvrier(i,pu,obs.bennesUsineVider, obs.bennesForetTransport,obs.petriActrions, obs);
            robert.start();
        }
        Matrice mat = new Matrice(obs);
        Petri pet = new Petri(obs.petriActrions,obs,mat);
        pet.start();

    }
}
