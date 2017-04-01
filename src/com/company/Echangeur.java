package com.company;

/**
 * Created by ludovic on 01/04/2017.
 */
public class Echangeur {

    Observateur monObs;
    private boolean bloque=false;

    public Echangeur(Observateur obs){
        this.monObs = obs;
    }

    public synchronized void essaiEchange(int i,int j, Benne echangeable){
        if (monObs.GetStatus(j)==false){
            bloque=false;
            notify();
            System.out.println("l'échange peut avoir lieu");

            //System.out.println("le vecteur courant après l'échange vaut "+monObs.getStatus(1)+" "+monObs.getStatus(2)+" "+monObs.getStatus(3));

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
                System.out.println("on bloque le thread courant "+nom);
                try {
                    wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("on débloque le thread "+nom);
            }
        }
        monObs.ModifStatus(true,j);
    }

    public synchronized void evalCouple(int i, int j, Benne zone){
        //monObs.modifStatus(i); //ici changement de statut du pompier 2 se ferait deux fois de suite
        //ATTENTION: On modifie systématiquement le status même si l'échange précédent n'a pas eu lieu
        if (monObs.travail){

                essaiEchange(i,j,zone);

        } else System.out.println("ça ne sert à rien de continuer pour car son pote a fini");
    }

    public synchronized void lastEvalCouple(int i, int j, char zone){
        notify();//sinon un pote en mode wait serait bloqué indéfiniment
        bloque=false;
        monObs.travail=false;
        System.out.println("dernier état pour "+i+" ça ne sert à rien de changer");
    }
}
