package com.company;

/**
 * Created by ludovic on 31/03/2017.
 */
public  class  Benne
{
    private int max =250;
    private int capacity;
    public Benne(int capacity)
    {
       this.capacity =capacity;
    }
    public int GetMax()
    {
        return max;
    }
    public synchronized boolean Ispleine()
    {
        return this.capacity == max;
    }
    public synchronized boolean IsVIde()
    {
        return this.capacity == 0;
    }
    public synchronized  void SetCapacity(int kilos)
    {
        capacity =kilos;
    }

}
