
package serveur;

import client.Joueur;
import client.clientInterface;
import java.awt.Point;
import java.rmi.Remote;

public interface serveurInterface extends Remote{
    
    public void connecter(clientInterface client, Joueur j) throws java.rmi.RemoteException;
    public void seDeplacer(clientInterface client, Joueur j, Point newPosition) throws java.rmi.RemoteException;
    
}
