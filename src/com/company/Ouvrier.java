package com.company;

import javax.sound.midi.SysexMessage;
import java.util.LinkedList;

/**
 * Created by ludovic on 31/03/2017.
 */
public class Ouvrier extends Thread
{
    private LinkedList<Benne> bennesAvider;
    private LinkedList<Benne> bennesATransporter;
    private Observateur monObs;
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
        System.out.println("fin de l'ouvrier");
        this.interrupt();
    }
    private  synchronized void Vider (Benne benne)
    {
        int i=benne.GetMax();
        while(!benne.IsVIde())
        {
            benne.SetCapacity(i);
            i--;
        }
        bennesATransporter.addLast(benne);
        if(monObs.GetStatus(1)==false) {
            monObs.essaiEchange(1);
        }
    }
    private  synchronized  void ViderBenne()
    {
        if(bennesAvider.size()!=0) {
            Benne ben = (Benne) bennesAvider.getFirst();
            bennesAvider.removeFirst();
            System.out.println("l 'ouvrier vide la benne");
            Vider(ben);
        }
        else
        {
            monObs.ModifStatus(false,2);
            monObs.essaiEchange(1);

        }
    }
}
