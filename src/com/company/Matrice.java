package com.company;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ludovic on 07.06.2017.
 */
public class Matrice
{
    public ReentrantLock lockmatrice = new ReentrantLock();
   // int impressionMatriceTousLesXEvenements = 50;
    //int impressionMatrice = 1;

    public int[][] matricePetriPreincident= new int[8][8];
    public int[][] matricePetriPosincident = new int[8][8];
    public int[] evolveMatrice= new int[8];
    public Matrice()
    {
      for (int i = 0; i < 8; i++)
      {
          for (int j = 0; j < 8; j++)
          {
              if (i != j)
              {
                  matricePetriPreincident[i][j] = 0;
              }
              else
              {
                  matricePetriPreincident[i][j] = 1;
              }
          }
      }
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
               matricePetriPosincident[7][0] = 1;

                    if (i+1 != j) {
                        matricePetriPosincident[i][j] = 0;
                    } else {
                        matricePetriPosincident[i][j] = 1;
                    }
            }
        }
        for (int i = 0; i < 8; i++) {
            evolveMatrice[i] = 0;
        }
    }
  public  void printMatrice(int[][] matrice) {
        lockmatrice.lock();
        try {
            for (int row = 0; row < matrice[0].length; row++) {
                for (int column = 0; column < matrice.length; column++) {
                    System.out.printf("%d", matrice[column][row]);
                }
                System.out.println();
            }
        }
        finally {
            lockmatrice.unlock();
        }
    }
    public  void ParcoursMatrice(int element)
    {

    }
    
}
