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
    int tour=0;
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
        Benne benne = new Benne(monObs.GetCapacity());
        bennesUsineTranspot.addLast(benne);
    }
    public void run(){
        while (monObs.travail){
            try {
                Benne benne = null;
                monObs.ModifStatus(true,1);
                if(monObs.travail) {
                    benne = AmmarerBuecheron();
                }
                if(monObs.travail) {
                    TransportToOuvrier(benne);
                }
                if(monObs.travail) {
                    AmareAtOuvrier();
                }
                if(monObs.travail) {
                    TransportToBucheron(benne);
                }
            tour++;
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }

        }
        System.out.println("fin du transporteur");
        System.out.println("le transporteur a fait nb "+tour);
        this.interrupt();

    }
    private synchronized Benne AmmarerBuecheron()throws InterruptedException  {
        System.out.println(" le transporteur amène la benne en forêt");
        Thread.sleep((long) Math.ceil(Math.random() * 100));
        Benne  benne=null;
        while (bennesUsineTranspot.size()==0) {
            monObs.ModifStatus(false, 1);
            if(!monObs.travail)
            {
                return benne;
            }
            monObs.essaiEchange(0);
        }
         benne = bennesUsineTranspot.getFirst();
        bennesUsineTranspot.removeFirst();
        monObs.ModifStatus(true,1);
        return benne;
    }

    private synchronized void TransportToOuvrier(Benne benne)throws InterruptedException{
        System.out.println(" le transporteur donne la benne à l'ouvrier");
        Thread.sleep((long) Math.ceil(Math.random() * 100));
        bennesUsineVider.addLast(benne);
        if(monObs.GetStatus(2)==false) {
            monObs.essaiEchange(2);
        }

    }

    private synchronized Benne AmareAtOuvrier() throws InterruptedException{
        Thread.sleep((long) Math.ceil(Math.random() * 100));
        System.out.println(" le transporteur prends la benne vide dans l'usine");
        Benne benne = null;
        while(bennesForetTransport.size()==0) {
            monObs.ModifStatus(false,1);
            if(!monObs.travail)
            {
                return benne;
            }
            monObs.essaiEchange(2);
        }
        benne = bennesForetTransport.getFirst();
        bennesForetTransport.removeFirst();
        monObs.ModifStatus(true,1);
        return  benne;
    }

    private synchronized void TransportToBucheron(Benne benne) throws InterruptedException{
        Thread.sleep((long) Math.ceil(Math.random() * 100));
        System.out.println(" le transporteur amène la benne vide chez le bucheron");
        bennesForetRemplir.addLast(benne);
        if(monObs.GetStatus(0)==false) {
            monObs.essaiEchange(0);
        }
     }
}
