package com.company;

/**
 * Created by ludovic on 01/04/2017.
 */

public class Observateur {
    public static boolean travail=true;
    private   Benne benne1 = new Benne(0);
    private  Benne benne2 = new Benne(0);
    private  Benne benne3 = new Benne(0);
    public  Benne mesnenes[]={benne1,benne2,benne3};
    public boolean status[]={true,true,true}; // true = travail false = block 0= bu 1= trans 2 = ouvr

    synchronized  boolean GetStatus(int i){
        return status[i];
    }

    synchronized  void  ModifStatus(boolean value, int i){

        status[i]=value;
    }

	/*synchronized  void modifStatusCouple(int i, int j){
		modifStatus(i);
		modifStatus(j);
	}*/

}