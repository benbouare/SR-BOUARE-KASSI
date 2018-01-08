
package serveur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class serveurDriver {
    public static void main(String args[]) throws RemoteException, MalformedURLException {
     	Naming.rebind("Game", new serveurImpl());
        System.out.println("Serveur lancé !");
   }
}
