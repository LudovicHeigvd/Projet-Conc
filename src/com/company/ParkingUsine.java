package com.company;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ludovic on 31.05.2017.
 */
public class ParkingUsine {

    public   Lock lockTrilisteOuvrier = new ReentrantLock();
    private Lock lockgetstatuts = new ReentrantLock();
    private boolean bloque;
    private Lock lockEssaieEchangepu = new ReentrantLock();
    private Condition conditionEssaiepu = lockEssaieEchangepu.newCondition();

    LinkedList<Integer> ouvs = new LinkedList<Integer>();
    LinkedList<Integer> trans = new LinkedList<Integer>();

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
    public int GetStatut(int i) {
        lockgetstatuts.lock();
        try {
            int size=-1;
            switch (i) {
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
    public  void essaiEchange(int j, int id){
        lockEssaieEchangepu.lock();
        try {
            int size =-1;
            switch (j) {
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
                conditionEssaiepu.signalAll();
                switch (j) {
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
                            case 1:
                                trans.add(id);
                                break;
                            case 2:
                                ouvs.add(id);
                                break;
                        }
                        conditionEssaiepu.await();

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            //  this.ModifStatus(true, j);
        }
        finally {
            lockEssaieEchangepu.unlock();
        }
    }
    public void LastEchange(int j)
    {
        conditionEssaiepu.signalAll();
    }

    public void wakeUpOuvrier()
    {
        if(ouvs.size()>1)
        {
            conditionEssaiepu.signal();
        }
    }

}
