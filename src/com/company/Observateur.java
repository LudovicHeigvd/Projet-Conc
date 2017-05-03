package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ludovic on 01/04/2017.
 */

public class Observateur {
    public static boolean travail=true;
    private int capacity=255;
    private  boolean  status[]={true,true,true}; // true = travail false = block 0= bu 1= trans 2 = ouvr
    private boolean bloque;
    private Lock lockEssaieEchange = new ReentrantLock();
    private Condition conditionEssaie = lockEssaieEchange.newCondition();
    public synchronized  boolean GetStatus(int i){
        return status[i];
    }

   public synchronized  void  ModifStatus(boolean value, int i){

        status[i]=value;
    }
    public  int GetCapacity()
    {
      return capacity;
    }
	/*synchronized  void modifStatusCouple(int i, int j){
		modifStatus(i);
		modifStatus(j);
	}*/
    public  void essaiEchange(int j){
        lockEssaieEchange.lock();
        try {
            if (this.GetStatus(j) == false) {
                bloque = false;
                conditionEssaie.signal();
                System.out.println("l'échange peut avoir lieu");
            } else {
                bloque = true;
                while (bloque) {
                    String nom;
                    switch (j) {
                        case 0:
                            nom = "Bucheron";
                            break;
                        case 1:
                            nom = "Transporteur";
                            break;
                        case 2:
                            nom = "Ouvrier";
                            break;
                        default:
                            nom = "alien";
                    }
                    System.out.println("on attends le tread " + nom + " pour débloquer la situation ");
                    try {
                        conditionEssaie.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            this.ModifStatus(true, j);
        }
        finally {
            lockEssaieEchange.unlock();
        }
    }
}
