package com.company;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ludovic on 01/04/2017.
 */

public class Observateur {
    public static boolean travail=true;
    private int capacity=250;
    LinkedList<Integer> bobs = new LinkedList<Integer>();
    LinkedList<Integer> ouvs = new LinkedList<Integer>();
    LinkedList<Integer> trans = new LinkedList<Integer>();
   // private  int  status[]={bobs,trans,ouvs}; // true = travail false = block 0= bu 1= trans 2 = ouvr
    private boolean bloque;
    private Lock lockEssaieEchange = new ReentrantLock();
    private Lock lockgetstatuts = new ReentrantLock();
    private Lock lockTrilisteBcheron = new ReentrantLock();
    private Lock lockTrilisteOuvrier = new ReentrantLock();
    private Lock locksetstatuts = new ReentrantLock();
    private Condition conditionEssaie = lockEssaieEchange.newCondition();
    public LinkedList<Benne> bennesForetRemplir = new LinkedList<Benne>();
    public LinkedList<Benne> bennesForetTransport = new LinkedList<Benne>();
    public LinkedList<Benne> bennesUsineVider = new LinkedList<Benne>();
    public LinkedList<Benne> bennesUsineTranspot = new LinkedList<Benne>();

   public int GetStatut(int i) {
        lockgetstatuts.lock();
        try {
            int size=-1;
            switch (i) {
                case 0:
                    size=bobs.size();
                    break;
                case 1:
                    size = trans.size();
                    break;
                case 2:
                    size = ouvs.size();
                    break;
            }
            return size;
        }
        finally {
            lockgetstatuts.unlock();
        }
    }
  /* public  void  ModifStatus(boolean value, int i){
       locksetstatuts.lock();
       try {
           status[i] = value;
       }
       finally {
           locksetstatuts.unlock();
       }
    }*/
    public  int GetCapacity()
    {
      return capacity;
    }
	/*synchronized  void modifStatusCouple(int i, int j){
		modifStatus(i);
		modifStatus(j);
	}*/
    public  void essaiEchange(int j, int id){
        lockEssaieEchange.lock();
        try {
            int size =-1;
            switch (j) {
                case 0:
                    size=bobs.size();
                    break;
                case 1:
                    size = trans.size();
                    break;
                case 2:
                    size = ouvs.size();
                    break;
            }
            if(size!=0 || size !=-1)
            {
                bloque = false;
                conditionEssaie.signalAll();
                switch (j) {
                    case 0:
                        bobs.removeAll(bobs);
                        break;
                    case 1:
                        trans.removeAll(trans);
                        break;
                    case 2:
                        ouvs.removeAll(ouvs);
                        break;
                }

              //  System.out.println("l'échange peut avoir lieu");
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
                        switch (j) {
                            case 0:
                                bobs.add(id);
                                break;
                            case 1:
                                trans.add(id);
                                break;
                            case 2:
                                ouvs.add(id);
                                break;
                        }
                        conditionEssaie.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
          //  this.ModifStatus(true, j);
        }
        finally {
            lockEssaieEchange.unlock();
        }
    }
    public void LastEchange(int j)
    {
        conditionEssaie.signalAll();
    }

    public void Tribucheron(LinkedList<Benne> bennesARemplir)
    {
        lockTrilisteBcheron.lock();
        try {
            if(bennesARemplir.size()>1) {
                bennesARemplir.sort(new Comparator<Benne>() {
                    @Override
                    public int compare(Benne o1, Benne o2) {
                        if (o1.GetCapcity() > o2.GetCapcity()) {
                            return 1;
                        } else if (o1.GetCapcity() < o2.GetCapcity()) {
                            return -1;
                        }
                        return 0;
                    }
                });
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        finally {
            lockTrilisteBcheron.unlock();
        }
    }
    public void TriOuvrier(LinkedList<Benne> bennesAvider) {
        lockTrilisteOuvrier.lock();
        try {
            if (bennesAvider.size() > 1) {
                bennesAvider.sort(new Comparator<Benne>() {
                    @Override
                    public int compare(Benne o1, Benne o2) {
                        if (o1.GetCapcity() < o2.GetCapcity()) {
                            return 1;
                        } else if (o1.GetCapcity() > o2.GetCapcity()) {
                            return -1;
                        }
                        return 0;
                    }
                });
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        finally {
            lockTrilisteOuvrier.unlock();
        }
    }

}
