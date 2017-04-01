package com.company;

/**
 * Created by ludovic on 01/04/2017.
 */

public class Observateur {
    private   Benne benne1 = new Benne(0);
    private  Benne benne2 = new Benne(0);
    private  Benne benne3 = new Benne(0);
    public  Benne mesnenes[]={benne1,benne2,benne3};
    int monVecteurCode[]={0,2,1};

    synchronized  Benne getStatus(int i){
        return mesnenes[i];
    }

    synchronized  void modifStatus(int i){

    }

	/*synchronized  void modifStatusCouple(int i, int j){
		modifStatus(i);
		modifStatus(j);
	}*/

}