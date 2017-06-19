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

   // private  int  status[]={bobs,trans,ouvs}; // true = travail false = block 0= bu 1= trans 2 = ouvr
    public LinkedList<Benne> bennesForetRemplir = new LinkedList<Benne>();
    public LinkedList<Benne> bennesForetTransport = new LinkedList<Benne>();
    public LinkedList<Benne> bennesUsineVider = new LinkedList<Benne>();
    public LinkedList<Benne> bennesUsineTranspot = new LinkedList<Benne>();
    public LinkedList<Integer> petriActrions = new LinkedList<Integer>();
    public int nbBenne;
    public  int GetCapacity()
    {
        return capacity;
    }





}
