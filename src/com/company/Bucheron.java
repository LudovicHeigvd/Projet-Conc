package com.company;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import java.util.LinkedList;
/**
 * Created by ludovic on 31/03/2017.
 */

public class Bucheron extends Thread
{
    private LinkedList<Benne> bennesARemplir;
    private LinkedList<Benne> bennesATransporter;
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
        System.out.println("fin du bucheron");
        this.interrupt();
    }
    private  synchronized void Remplir(Benne benne)
    {
        if(!benne.Ispleine())
        {
            benne.Addtronc(25);
            bennesARemplir.addFirst(benne);
        }
        else {
            bennesATransporter.addLast(benne);
            if (monObs.GetStatus(1) == false) {
                monObs.essaiEchange(1);
            }
        }
    }
    private synchronized void Prendrebenne()
    {
        if(bennesARemplir.size()!=0) {
            Benne ben = (Benne) bennesARemplir.getFirst();
            bennesARemplir.removeFirst();
            Remplir(ben);
        }
        else
        {
            monObs.ModifStatus(false,0);
            monObs.essaiEchange(1);

        }
    }
}
