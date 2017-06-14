package com.company;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ludovic on 31/03/2017.
 */
public class Transporteur extends Thread
{
    LinkedList<Benne> bennesForetRemplir;
    LinkedList<Benne> bennesForetTransport;
    LinkedList<Benne> bennesUsineVider;
    LinkedList<Benne> bennesUsineTranspot;
    LinkedList<Integer> petriActrions;

    private Lock lockAmmarrerBucheron = new ReentrantLock();
    private Lock lockTransporttoOuvrier = new ReentrantLock();
    private Lock lockAmmarrerOuvrier = new ReentrantLock();
    private Lock lockTransporttoBucheron = new ReentrantLock();

    ParkingForet pf;
    ParkingUsine pu;
    private int id;
    int tours =0;
    Observateur monObs;
    public Transporteur(int id,Observateur obs,ParkingForet pf,ParkingUsine pu,
                        LinkedList<Benne> bennesForetRemplir,
                        LinkedList<Benne> bennesForetTransport,
                        LinkedList<Benne> bennesUsineVider,
                        LinkedList<Benne> bennesUsineTranspot,
                        LinkedList<Integer> petriActrions
    )
    {
        this.bennesForetRemplir = bennesForetRemplir;
        this.bennesForetTransport = bennesForetTransport;
        this.bennesUsineVider = bennesUsineVider;
        this.bennesUsineTranspot = bennesUsineTranspot;
        this.monObs=obs;
        this.id=id;
        Benne benne = new Benne(monObs.GetCapacity());
        bennesUsineTranspot.addLast(benne);
        this.pf=pf;
        this.pu=pu;
        this.petriActrions=petriActrions;

    }
    public void run(){
        while (monObs.travail){
            try {
                Benne benne = null;
                //monObs.ModifStatus(true,1);
                benne=AmmarerBuecheron();
                if(monObs.travail) {
                    TransportToOuvrier(benne);
                }
                if(monObs.travail) {
                    AmareAtOuvrier();
                }
                if(monObs.travail) {
                    TransportToBucheron(benne);
                }

            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
            tours ++;
        }
        System.out.println("fin du transporteur"+id);
        System.out.println("le transporteur"+id+" a fait "+tours+" nb tour");
        if(pf.bobs.size()!=0) {
            pf.LastEchange(0);
        }
        if(pu.ouvs.size()!=0) {
            pu.LastEchange(2);
        }
        this.interrupt();

    }
    private Benne AmmarerBuecheron()throws InterruptedException  {
        lockAmmarrerBucheron.lock();
        try {
            System.out.println(" le transporteur"+id+" amène la benne en forêt");
            Thread.sleep((long) Math.ceil(Math.random() * 100));
            Benne benne =null;
            while (bennesUsineTranspot.size() == 0) {
               // monObs.ModifStatus(false, 1);
                if(!monObs.travail)
                {
                    return benne;
                }
                pf.essaiEchange(0,id);
            }
            bennesUsine:
            if(bennesUsineTranspot.size()>0) {
                benne = bennesUsineTranspot.getFirst();
                bennesUsineTranspot.removeFirst();
            }
            else {
                break bennesUsine;
            }
          //  monObs.ModifStatus(true, 1);
            return benne;
        }
        finally {
            lockAmmarrerBucheron.unlock();
        }
    }

    private  void TransportToOuvrier(Benne benne)throws InterruptedException{
        pu.lockTrilisteOuvrier.lock();
        try {
            System.out.println(" le transporteur"+id+" donne la benne à l'ouvrier");
            Thread.sleep((long) Math.ceil(Math.random() * 100));
            bennesUsineVider.addLast(benne);
            if (pu.ouvs.size() != 0) {
                pu.essaiEchange(2,id);
            }
        }
        finally {
            pu.lockTrilisteOuvrier.unlock();
        }
    }

    private  Benne AmareAtOuvrier() throws InterruptedException{
        lockAmmarrerOuvrier.lock();
        try {
            Thread.sleep((long) Math.ceil(Math.random() * 100));
            System.out.println(" le transporteur"+id+" prends la benne vide dans l'usine");
            Benne benne =null;

            while (bennesForetTransport.size() == 0) {
              //  monObs.ModifStatus(false, 1);

                if(!monObs.travail)
                {
                    return benne;
                }
                pu.essaiEchange(2,id);
            }
            bennevide:
            try {
                if (bennesForetTransport.size() > 0) {
                    benne = bennesForetTransport.getFirst();
                    bennesForetTransport.removeFirst();
                } else {
                    break bennevide;
                }
            }
            catch (Exception e){}

            //monObs.ModifStatus(true, 1);
            return benne;
        }
        finally {
            lockAmmarrerOuvrier.unlock();
        }
    }

    private void TransportToBucheron(Benne benne) throws InterruptedException{
        pf.lockTrilisteBcheron.lock();
        try {
            Thread.sleep((long) Math.ceil(Math.random() * 100));
            System.out.println(" le transporteur"+id+" amène la benne vide chez le bucheron");
            bennesForetRemplir.addLast(benne);
            if (pf.bobs.size() !=0 ) {
                pf.essaiEchange(0,id);
            }
        }
        finally {
            pf.lockTrilisteBcheron.unlock();
        }
     }
}
