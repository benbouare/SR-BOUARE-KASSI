
package client;

import java.awt.Point;
import java.rmi.Remote;
import java.util.ArrayList;

public interface clientInterface extends Remote{
    
    public void diffuserJoueur(ArrayList<clientInterface> listeClient, Joueur j) throws java.rmi.RemoteException;
    public void diffuserDeplacement(Joueur j, ArrayList<Joueur> joueurs, ArrayList<Point> bonbons) throws java.rmi.RemoteException;
}
