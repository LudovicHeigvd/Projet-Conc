package com.company;
import java.util.LinkedList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Observateur obs = new Observateur();
        ParkingForet pf = new ParkingForet();
        ParkingUsine pu = new ParkingUsine();
        Random random = new Random();


        for (int i = 0; i <= random.nextInt(15); i ++) {
            Bucheron mathilde = new Bucheron(i,pf,obs.bennesForetRemplir, obs.bennesUsineTranspot, obs);
            mathilde.start();
        }

        for (int i = 0; i <= random.nextInt(15); i ++) {
            Transporteur bertrand = new Transporteur(i,obs,pf,pu,
                    obs.bennesForetRemplir,
                    obs. bennesForetTransport,
                    obs. bennesUsineVider,
                    obs. bennesUsineTranspot);
            bertrand.start();
        }

        for (int i = 0; i <= random.nextInt(15); i ++) {
            Ouvrier robert = new Ouvrier(i,pu,obs.bennesUsineVider, obs.bennesForetTransport, obs);
            robert.start();
        }
        for(int i = 0; i <= random.nextInt(15); i++) {
            Benne ben = new Benne(i);
            obs.bennesForetRemplir.addLast(ben);
        }
    }
}
