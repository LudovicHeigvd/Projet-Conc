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
    private Lock lockbenne = new ReentrantLock();

    public Benne(int capacity)
    {
       this.capacity =capacity;
    }
    public int GetMax()
    {
        return max;
    }

    public int GetCapcity()
    {
        lockbenne.lock();
        try
        {
            return capacity;
        }
    finally {
            lockbenne.unlock();
        }
    }
    public  boolean Ispleine()
    {
        lockbenne.lock();
        try
        {
            return this.capacity == max;
        }
        finally {
            lockbenne.unlock();
        }

    }
    public  boolean IsVIde()
    {
        lockbenne.lock();
        try {
            return this.capacity == 0;
        }
        finally {
            lockbenne.unlock();
        }
    }
    public   void Addtronc(int kilos)
    {
        lockbenne.lock();
        try {
            capacity = capacity+ kilos;
        }
        finally {
            lockbenne.unlock();
        }
    }
    public  void Removetronc(int kilos)
    {
        lockbenne.lock();
        try {
            capacity = capacity-kilos;
        }
        finally {
            lockbenne.unlock();
        }
    }
}
