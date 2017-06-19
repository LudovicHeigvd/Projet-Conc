package com.company;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.text.Collator;
import java.util.Comparator;
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
    private LinkedList<Integer> petriActrions;
    private Lock lockRemplir = new ReentrantLock();
    private Lock lockPrendreBenne = new ReentrantLock();
    private Lock lockTrilisteBcheron = new ReentrantLock();
    private Observateur monObs;
    private int id;
    ParkingForet pf;
    public Bucheron(int id, ParkingForet pf,LinkedList<Benne> bennesForetRemplir,LinkedList<Benne> bennesUsineTranspot,LinkedList<Integer> petriActrions,Observateur obs) {
        this.bennesARemplir =bennesForetRemplir;
        this.bennesATransporter =bennesUsineTranspot;
        this.monObs=obs;
        Benne benne = new Benne(0);
        this.bennesARemplir.addLast(benne);
        this.id =id;
        this.pf=pf;
        this.petriActrions= petriActrions;

    }
    public void run() {
        int tours = 0;
        while (tours <= 100)
        {
            if(monObs.travail) {
                try{
                   // monObs.ModifStatus(true,0);
                //    System.out.println("le bucheron "+id+" coupe du bois");
                    Thread.sleep((long) Math.ceil(Math.random() * 100));//couper du bois
                 //   System.out.println("le bucheron" +id+" amène le bois vers la benne");
                    Thread.sleep((long) Math.ceil(Math.random() * 100));//aème le bois vers la beine
                 //   System.out.println("le bucheron"+id+" remplis la benne");
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
        if (pf.trans.size()!= 0) {
            pf.LastEchange(1);
        }
        if (pf.bobs.size()!= 0) {
            pf.LastEchange(0);
        }
        System.out.println("fin du bucheron"+id);
        System.out.println("le bucheron"+id+" a fait "+tours+" nb tour");
        this.interrupt();
    }
    private void Remplir(Benne benne)
    {
        lockTrilisteBcheron.lock();
        try {
            if (!benne.Ispleine()) {
                benne.Addtronc(25);
                bennesARemplir.addFirst(benne);
                pf.Tribucheron(bennesARemplir);


            } else {
                bennesATransporter.addLast(benne);
               // System.out.println("la benne est remplie par le bucheron"+id);
                petriActrions.add(1);
                 pf.essaiEchange(1,id);

            }
        }
        catch (Exception e)
        {
           // e.getMessage();
        }
        finally {
          //  TriBenne(bennesARemplir);
            lockTrilisteBcheron.unlock();

        }
    }
    private  void Prendrebenne() {
        lockPrendreBenne.lock();
        try {
            if (bennesARemplir.size() != 0) {
                Benne ben=null;
                ben = (Benne) bennesARemplir.getFirst();
                bennesARemplir.removeFirst();
                Remplir(ben);
            } else {
                //  monObs.ModifStatus(false, 0);
                pf.essaiEchange(1, id);

            }
        }
        catch (Exception e)
        {
          //  e.getMessage();
        }
        finally {
            lockPrendreBenne.unlock();
        }
    }
}
