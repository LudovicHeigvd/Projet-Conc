package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ludovic on 31/03/2017.
 */
public  class  Benne
{
    private int max =250;
    private int capacity;
    private Lock lockbennePlein = new ReentrantLock();
    private Lock lockbenneVide = new ReentrantLock();
    private Lock lockaddTronc = new ReentrantLock();
    private Lock lockRemoveTronc = new ReentrantLock();
    public Benne(int capacity)
    {
       this.capacity =capacity;
    }
    public int GetMax()
    {
        return max;
    }
    public  boolean Ispleine()
    {
        lockbennePlein.lock();
        try
        {
            return this.capacity == max;
        }
        finally {
            lockbennePlein.unlock();
        }

    }
    public  boolean IsVIde()
    {
        lockbenneVide.lock();
        try {
            return this.capacity == 0;
        }
        finally {
            lockbenneVide.unlock();
        }
    }
    public   void Addtronc(int kilos)
    {
        lockaddTronc.lock();
        try {
            capacity = capacity+ kilos;
        }
        finally {
            lockaddTronc.unlock();
        }
    }
    public  void Removetronc(int kilos)
    {
        lockRemoveTronc.lock();
        try {
            capacity = capacity-kilos;
        }
        finally {
            lockRemoveTronc.unlock();
        }
    }

}
