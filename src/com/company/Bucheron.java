package com.company;

/**
 * Created by ludovic on 31/03/2017.
 */

public class Bucheron extends Thread
{
   private Benne beinne;
    public Bucheron(Benne benne){

    }
    public static   boolean travail = true;
    public void run() {
        int tours = 0;
        while (tours <= 100)
        {
            if(travail) {
                try {
                    Thread.sleep((long) Math.ceil(Math.random() * 100));//couper du bois
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
        travail =false;
    }
    private  synchronized void remplir(Benne beinne)
    {
        this.beinne =beinne;
        int i=1;
        while(!beinne.Ispleine())
        {
            this.beinne.SetCapacity(i);
            i++;
        }
    }
}
