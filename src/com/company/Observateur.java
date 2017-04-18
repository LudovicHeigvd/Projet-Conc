package com.company;

/**
 * Created by ludovic on 01/04/2017.
 */

public class Observateur {
    public static boolean travail=true;
    private int capacity=255;
    private  boolean  status[]={true,true,true}; // true = travail false = block 0= bu 1= trans 2 = ouvr
    private boolean bloque;
    public synchronized  boolean GetStatus(int i){
        return status[i];
    }

   public synchronized  void  ModifStatus(boolean value, int i){

        status[i]=value;
    }
    public synchronized int GetCapacity()
    {
      return capacity;
    }
	/*synchronized  void modifStatusCouple(int i, int j){
		modifStatus(i);
		modifStatus(j);
	}*/
    public synchronized void essaiEchange(int j){
        if (this.GetStatus(j)==false){
            bloque=false;
            notifyAll();
            System.out.println("l'échange peut avoir lieu");
        } else {
            bloque=true;
                while (bloque){
                String nom;
                switch (j) {
                    case 0:
                        nom ="Bucheron";
                        break;
                    case 1:
                        nom="Transporteur" ; break;
                    case 2:
                        nom = "Ouvrier"; break;
                    default:
                        nom ="alien";
                }
                System.out.println("on attends le tread "+nom+ " pour débloquer la situation ");
                try {
                    wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        this.ModifStatus(true,j);
    }

    public synchronized void lastEvalCouple(int i, int j, char zone){
        notify();//sinon un pote en mode wait serait bloqué indéfiniment
        bloque=false;
        this.travail=false;
        System.out.println("dernier état pour "+i+" ça ne sert à rien de changer");
    }
}
