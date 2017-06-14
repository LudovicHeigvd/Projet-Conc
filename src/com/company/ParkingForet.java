package com.company;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ludovic on 31.05.2017.
 */
public class ParkingForet {

    public Lock lockTrilisteBcheron = new ReentrantLock();
    private Lock lockgetstatuts = new ReentrantLock();
    private boolean bloque;
    private Lock lockEssaieEchangepf = new ReentrantLock();
    private Condition conditionEssaiepf = lockEssaieEchangepf.newCondition();

    LinkedList<Integer> trans = new LinkedList<Integer>();
    LinkedList<Integer> bobs = new LinkedList<Integer>();

    public void Tribucheron(LinkedList<Benne> bennesARemplir) {
        lockTrilisteBcheron.lock();
        try {
            if (bennesARemplir.size() > 1) {
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
        } catch (Exception e) {
            e.getMessage();
        } finally {
            lockTrilisteBcheron.unlock();
        }
    }
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
            }
            return size;
        }
        finally {
            lockgetstatuts.unlock();
        }
    }
    public  void essaiEchange(int j, int id){
        lockEssaieEchangepf.lock();
        try {
            int size =-1;
            switch (j) {
                case 0:
                    size=bobs.size();
                    break;
                case 1:
                    size = trans.size();
                    break;
            }
            if(size!=0 || size !=-1)
            {
                bloque = false;
                conditionEssaiepf.signalAll();
                switch (j) {
                    case 0:
                        bobs.removeAll(bobs);
                        break;
                    case 1:
                        trans.removeAll(trans);
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
                        }
                        conditionEssaiepf.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            //  this.ModifStatus(true, j);
        }
        finally {
            lockEssaieEchangepf.unlock();
        }
    }
    public void LastEchange(int j)
    {
        conditionEssaiepf.signalAll();
    }
}
