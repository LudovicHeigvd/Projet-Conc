package com.company;

/**
 * Created by ludovic on 31/03/2017.
 */
public class Ouvrier extends Thread
{
    Observateur monObs;
    Echangeur monech;
    int i=2;
    public Ouvrier(Observateur obs,Echangeur ech) {
        monObs=obs;
        monech = ech;
    }
    boolean travail =true;
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
                        Vider(monObs.mesnenes[i]);
                    }
                    else
                    {
                        int j=i+1%3;
                        if(monObs.mesnenes[j].Ispleine()) {
                            monech.evalCouple(i,1, monObs.mesnenes[i]);
                        }
                        else
                        {
                            monObs.ModifStatus(false,2);
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
    private  synchronized void Vider (Benne benne)
    {
        int i=benne.GetMax();
        while(!benne.IsVIde())
        {
            benne.SetCapacity(i);
            i--;
        }
    }
}
