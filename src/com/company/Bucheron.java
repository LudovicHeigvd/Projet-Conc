package com.company;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ludovic on 31/03/2017.
 */

public class Bucheron extends Thread
{
    private LinkedList<Benne> bennesARemplir;
    private LinkedList<Benne> bennesATransporter;
    private Lock lockRemplir = new ReentrantLock();
    private Lock lockPrendreBenne = new ReentrantLock();
    private Observateur monObs;

    public Bucheron(LinkedList<Benne> bennesForetRemplir,LinkedList<Benne> bennesUsineTranspot,Observateur obs) {
        this.bennesARemplir =bennesForetRemplir;
        this.bennesATransporter =bennesUsineTranspot;
        this.monObs=obs;
        Benne benne = new Benne(0);
        this.bennesARemplir.addLast(benne);
    }
    public void run() {
        int tours = 0;
        while (tours <= 100)
        {
            if(monObs.travail) {
                try{
                    monObs.ModifStatus(true,0);
                    System.out.println("le bucheron coupe du bois");
                    Thread.sleep((long) Math.ceil(Math.random() * 100));//couper du bois
                    System.out.println("le bucheron amène le bois vers la benne");
                    Thread.sleep((long) Math.ceil(Math.random() * 100));//aème le bois vers la beine
                    System.out.println("le bucheron remplis la benne");
                    if(monObs.travail)
                    {
                    Prendrebenne();}
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
        System.out.println("fin du bucheron");
        System.out.println("le bucheron a fait "+tours+" nb tour");
        this.interrupt();
    }
    private void Remplir(Benne benne)
    {
        lockRemplir.lock();
        try {
            if (!benne.Ispleine()) {
                benne.Addtronc(250);
                bennesARemplir.addFirst(benne);
            } else {
                bennesATransporter.addLast(benne);
                System.out.println("la benne bucheron remplis");
                if (monObs.GetStatus(1) == false) {
                    monObs.essaiEchange(1);
                }
            }
        }
        finally {
            lockRemplir.unlock();
        }
    }
    private  void Prendrebenne()
    {
        lockPrendreBenne.lock();
        try {
            if (bennesARemplir.size() != 0) {
                Benne ben = (Benne) bennesARemplir.getFirst();
                bennesARemplir.removeFirst();
                Remplir(ben);
            } else {
                monObs.ModifStatus(false, 0);
                monObs.essaiEchange(1);

            }
        }
        finally {
            lockPrendreBenne.unlock();
        }
    }
}
