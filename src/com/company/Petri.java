package com.company;

import java.util.LinkedList;

/**
 * Created by Ludovic on 11.06.2017.
 */
public class Petri extends Thread
{
    private LinkedList<Integer> petriActrions;
    private Observateur obs;
    private Matrice mat;

    public Petri(LinkedList<Integer> petriActrions,Observateur obs, Matrice mat)
    {
      this.petriActrions=petriActrions;
      this.obs=obs;
      this.mat=mat;
    }
    public void run() {

        while(true)
        {
            if(petriActrions.size()!=0)
            {
                int element_petri = petriActrions.getFirst();
                mat.evolveMatrice[element_petri]+=1;
                mat.ParcoursMatrice(element_petri);
                mat.evolveMatrice[element_petri]-=1;
            }
        }
    }
}
