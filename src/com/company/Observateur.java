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
    char valPompier[][]={
            {'R','P','X','V'},
            {'P','Y','V','X'},
            {'A','V','Y','P'}
    };

    synchronized  Benne getStatus(int i){
        return mesnenes[i-1];
    }

    synchronized  void modifStatus(int i){
        int k;
        monVecteurCode[i-1]=(monVecteurCode[i-1]+1)%4;
        k=monVecteurCode[i-1];
       // monVecteurSymb[i-1]=valPompier[i-1][k];
        System.out.println("le pompier "+i+" change d'état. Il va passer à "+this.getStatus(i));
        System.out.println("le vecteur courant vaut maintenant "+this.getStatus(1)+" "+this.getStatus(2)+" "+this.getStatus(3));
    }

	/*synchronized  void modifStatusCouple(int i, int j){
		modifStatus(i);
		modifStatus(j);
	}*/

}