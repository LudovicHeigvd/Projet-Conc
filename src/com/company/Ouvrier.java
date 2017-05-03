package com.company;

import javax.sound.midi.SysexMessage;
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
    private Observateur monObs;
    private Lock lockVider = new ReentrantLock();
    private Lock lockAvider = new ReentrantLock();

    public Ouvrier(LinkedList<Benne> bennesUsineVider,LinkedList<Benne> bennesForetTransport,Observateur obs) {
        this.bennesAvider =bennesUsineVider;
        this.bennesATransporter =bennesForetTransport;
        this.monObs=obs;
        Benne benne = new Benne(monObs.GetCapacity());
        this.bennesAvider.addLast(benne);

    }
    public void run() {
        int tours = 0;
        while (tours <= 100)
        {
            if(monObs.travail) {
                try{
                    System.out.println("l'ouvrier travail");
                    monObs.ModifStatus(true,2);
                    Thread.sleep((long) Math.ceil(Math.random() * 100));
                    if(monObs.travail)
                    {
                    ViderBenne();}
                    Thread.sleep((long) Math.ceil(Math.random() * 100));
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
        if (monObs.GetStatus(1) == false) {
            monObs.essaiEchange(1);
        }
        System.out.println("fin de l'ouvrier");
        System.out.println("l'ouvrier a fait "+tours+" nb tour");
        this.interrupt();
    }
    private  void Vider (Benne benne)
    {
        lockVider.lock();
        try {
            if (!benne.IsVIde()) {
                benne.Removetronc(25);
                bennesAvider.addFirst(benne);
            } else {
                System.out.println("la benne est vide");
                bennesATransporter.addLast(benne);
                if (monObs.GetStatus(1) == false) {
                    monObs.essaiEchange(1);
                }
            }
        }
        finally {
            lockVider.unlock();
        }
    }
    private   void ViderBenne() {
        lockAvider.lock();
        System.out.println("l 'ouvrier vide la benne");
        try {
            if (bennesAvider.size() != 0) {
                Benne ben = (Benne) bennesAvider.getFirst();
                bennesAvider.removeFirst();
                Vider(ben);
            } else {
                monObs.ModifStatus(false, 2);
                monObs.essaiEchange(1);
            }
        } finally {
            lockAvider.unlock();
        }
    }
}
