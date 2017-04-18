package com.company;

import java.util.LinkedList;

/**
 * Created by ludovic on 31/03/2017.
 */
public class Transporteur extends Thread
{
    LinkedList<Benne> bennesForetRemplir;
    LinkedList<Benne> bennesForetTransport;
    LinkedList<Benne> bennesUsineVider;
    LinkedList<Benne> bennesUsineTranspot;
    Observateur monObs;
    public Transporteur(Observateur obs,
                        LinkedList<Benne> bennesForetRemplir,
                        LinkedList<Benne> bennesForetTransport,
                        LinkedList<Benne> bennesUsineVider,
                        LinkedList<Benne> bennesUsineTranspot
    )
    {
        this.bennesForetRemplir = bennesForetRemplir;
        this.bennesForetTransport = bennesForetTransport;
        this.bennesUsineVider = bennesUsineVider;
        this.bennesUsineTranspot = bennesUsineTranspot;
        this.monObs=obs;
        Benne benne = new Benne(0);
        bennesForetTransport.add(benne);
    }
    public void run(){
        while (monObs.travail){
            try {
                Benne benne = null;
                monObs.ModifStatus(true,1);
                AmmarerBuecheron();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
    }
    public void AmmarerBuecheron()throws InterruptedException  {
        System.out.println("Amène la benne en forêt");
        Thread.sleep((long) Math.ceil(Math.random() * 100));
        if(bennesUsineTranspot.size()==0) {
            monObs.ModifStatus(false,1);
            monObs.essaiEchange(0);
        }
        Benne  benne = bennesUsineTranspot.getFirst();
        bennesUsineTranspot.removeFirst();
        monObs.ModifStatus(true,1);
        TransportToOuvrier(benne);
    }

    public void TransportToOuvrier(Benne benne)throws InterruptedException{
        System.out.println("donne la benne à l'ouvrier");
        Thread.sleep((long) Math.ceil(Math.random() * 100));
        bennesUsineVider.addLast(benne);
        if(monObs.GetStatus(2)==false) {
            monObs.essaiEchange(2);
        }
        AmareAtOuvrier();
    }

    public void AmareAtOuvrier() throws InterruptedException{
        Thread.sleep((long) Math.ceil(Math.random() * 100));
        System.out.println("Prends la benne vide dans l'usine");
        if(bennesForetTransport.size()==0) {
            monObs.ModifStatus(false,1);
            monObs.essaiEchange(2);
        }
        Benne benne = bennesForetTransport.getFirst();
        bennesForetTransport.removeFirst();
        monObs.ModifStatus(true,1);
        TransportToBucheron(benne);
    }

    public void TransportToBucheron(Benne benne) throws InterruptedException{
        Thread.sleep((long) Math.ceil(Math.random() * 100));
        System.out.println("Amène la benne vide chez le bucheron");
        bennesForetRemplir.addLast(benne);
        if(monObs.GetStatus(0)==false) {
            monObs.essaiEchange(0);
        }
        AmmarerBuecheron();
    }
}
