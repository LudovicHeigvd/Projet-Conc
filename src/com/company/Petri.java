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
    private  Boolean isok;
    public Petri(LinkedList<Integer> petriActrions,Observateur obs, Matrice mat)
    {
      this.petriActrions=petriActrions;
      this.obs=obs;
      this.mat=mat;
      isok = true;
    }
    public void run() {

        while(isok)
        {
            if(!petriActrions.isEmpty())
            {
                try {


                    int message = petriActrions.peekFirst();
                    mat.printArray(mat.evolveMatrice);
                    System.out.println(message);
                    petriActrions.removeFirst();
                    if (mat.evolveMatrice[message] < 1) {
                        System.out.println("problème entre le réseau et le programme");
                    } else {
                        mat.evolveMatrice[message] -= 1;
                        mat.evolveMatrice[mat.DestroyCreate(message)] += 1;
                        MessageSystem(message);
                    }
                }
                catch (Exception e)
                {
                    e.getMessage();
                }
            }
          if( petriActrions.size()==0) {
              if (!obs.travail)
              {
                  isok =false;
                  this.interrupt();
              }
          }

        }

    }
    private void MessageSystem(int message)
    {
        switch (message) {
            case 0:
                System.out.println("Benne disponible pour être remplie");
                break;
            case 1:
                System.out.println("Benne remplie et disponible pour un transporteur");
                break;
            case 2:
                System.out.println("Benne est transportée de la forêt à l'usine");
                break;
            case 3:
                System.out.println("Transporteur désamarre la benne");
                break;
            case 4:
                System.out.println("Benne disponible pour être vidée");
                break;
            case 5:
                System.out.println("Benne vidée et disponible pour un transporteur");
                break;
            case 6:
                System.out.println("Benne est transportée de l'usine à la forêt");
                break;
            case 7:
                System.out.println("Transporteur désamarre la benne");
                break;
        }
    }
}
