package com.company;

/**
 * Created by ludovic on 01/04/2017.
 */
public class Echangeur {

    boolean travail=true;
    Observateur monObs;
    boolean bloque=false;

    public Echangeur(Observateur obs){
        this.monObs = obs;
    }

    public synchronized void essaiEchange(int i, int j, Benne zone){
        if (monObs.getStatus(j)==zone){
            bloque=false;
            notify();
            System.out.println("l'échange peut avoir lieu entre "+ i + " et "+j);
            //System.out.println("le vecteur courant après l'échange vaut "+monObs.getStatus(1)+" "+monObs.getStatus(2)+" "+monObs.getStatus(3));
        } else {
            bloque=true;
            while (bloque){
                System.out.println("on bloque le thread courant "+i+" car "+j+" n'est pas encore arrivé");
                try {
                    wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("on débloque le thread courant "+i+" grâce à "+j);
            }
        }
        monObs.modifStatus(i);
    }

    public synchronized void evalCouple(int i, int j, Benne zone){
        //monObs.modifStatus(i); //ici changement de statut du pompier 2 se ferait deux fois de suite
        //ATTENTION: On modifie systématiquement le status même si l'échange précédent n'a pas eu lieu
        if (travail){
            if (monObs.getStatus(i)==zone){
                essaiEchange(i,j,zone);
            }
        } else System.out.println("ça ne sert à rien de continuer pour "+i+" car son pote a fini");
    }

    public synchronized void lastEvalCouple(int i, int j, char zone){
        notify();//sinon un pote en mode wait serait bloqué indéfiniment
        bloque=false;
        travail=false;
        System.out.println("dernier état pour "+i+" ça ne sert à rien de changer");
    }
}
