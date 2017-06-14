package com.company;

import javax.sound.midi.SysexMessage;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ludovic on 31/03/2017.
 */
public class Ouvrier extends Thread
{
    private LinkedList<Benne> bennesAvider;
    private LinkedList<Benne> bennesATransporter;
    LinkedList<Integer> petriActrions;
    private Observateur monObs;
    private Lock lockVider = new ReentrantLock();
    private Lock lockAvider = new ReentrantLock();
    private int id;
    private ParkingUsine pu;
    public Ouvrier(int id,ParkingUsine pu,LinkedList<Benne> bennesUsineVider,LinkedList<Benne> bennesForetTransport,LinkedList<Integer> petriActrions,Observateur obs) {
        this.bennesAvider =bennesUsineVider;
        this.bennesATransporter =bennesForetTransport;
        this.monObs=obs;
        this.id=id;
        Benne benne = new Benne(monObs.GetCapacity());
        this.bennesAvider.addLast(benne);
        this.pu =pu;
        this.petriActrions= petriActrions;

    }
    public void run() {
        int tours = 0;
        while (tours <= 100)
        {
            if(monObs.travail) {
                try{
                    System.out.println("l'ouvrier" +id+" travail");
                //   monObs.ModifStatus(true,2);
                    Thread.sleep((long) Math.ceil(Math.random() * 100));
                    if(monObs.travail)
                    {
                    ViderBenne();}
                     Thread.sleep((long) Math.ceil(Math.random() * 100));
                    System.out.println("l'ouvrier"+id+" pose le bois dans la chaine de monage");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else
            {
                break;
            }
            tours ++;
        }
        monObs.travail =false;
        if (pu.trans.size() != 0) {
            pu.LastEchange(1);
        }
        System.out.println("fin de l'ouvrier"+id);
        System.out.println("l'ouvrier"+id+" a fait "+tours+" nb tour");
        if(pu.ouvs.size()!=0) {
            pu.LastEchange(2);
        }
        this.interrupt();
    }
    private  void Vider (Benne benne)
    {
        pu.lockTrilisteOuvrier.lock();
        try {
            if(benne!=null) {
                if (!benne.IsVIde()) {
                    benne.Removetronc(25);
                    bennesAvider.addFirst(benne);
                    pu.TriOuvrier(bennesAvider);
                } else {
                    System.out.println("la benne est vide");
                    bennesATransporter.addLast(benne);
                    if (pu.trans.size() != 0) {
                        pu.essaiEchange(1, id);
                    }
                }
            }
        }
        finally {
          //  TriBenne(bennesAvider);
            pu.lockTrilisteOuvrier.unlock();

        }
    }
    private   void ViderBenne() {
        pu.lockTrilisteOuvrier.lock();
        System.out.println("l 'ouvrier"+id+" vide la benne");
        try {
            if (bennesAvider.size() != 0) {
                Benne ben = (Benne) bennesAvider.getFirst();
                bennesAvider.removeFirst();
                Vider(ben);
            } else {
               // monObs.ModifStatus(false, 2);
                pu.essaiEchange(1,id);
            }
        } finally {
            pu.lockTrilisteOuvrier.unlock();
        }
    }
}
