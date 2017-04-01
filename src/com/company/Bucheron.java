package com.company;

/**
 * Created by ludovic on 31/03/2017.
 */

public class Bucheron extends Thread
{
   private Benne benne;
    private Observateur monObs;
    private Echangeur monech;
    private int i =0;
    public Bucheron(Observateur obs,Echangeur ech) {
        monObs=obs;
        monech = ech;
    }
    public void run() {
        int tours = 0;
        while (tours <= 100)
        {
            if(monObs.travail) {
                try{
                    System.out.println("le bucheron coupe du bois");
                    Thread.sleep((long) Math.ceil(Math.random() * 100));//couper du bois
                    System.out.println("le bucheronamène tout le bois vers la benne");
                    Thread.sleep((long) Math.ceil(Math.random() * 100));//aème le bois vers la beine
                    System.out.println("le bucheron remplis la benne");
                    if(monObs.mesnenes[i].IsVIde()) {
                        System.out.println("le bucheron remplis la benne");
                        remplir(monObs.mesnenes[i]);
                    }
                    else
                    {
                        int j=i+1%3;
                        if(monObs.mesnenes[j].IsVIde()) {
                            monech.evalCouple(i,1, monObs.mesnenes[i]);
                        }
                        else
                        {
                            monObs.ModifStatus(false,0);
                        }
                    }
                    i++;
                    i=i%3;
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
    }
    private  synchronized void remplir(Benne benne)
    {
        int i=1;
        while(!benne.Ispleine())
        {
            benne.SetCapacity(i);
            i++;
        }
    }
}
