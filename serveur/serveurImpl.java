
package serveur;

import client.Constante;
import client.Joueur;
import client.clientImpl;
import client.clientInterface;
import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class serveurImpl extends java.rmi.server.UnicastRemoteObject implements serveurInterface{

    private ArrayList<clientInterface> listeClient = new ArrayList<clientInterface>();
    private final ReentrantLock lock = new ReentrantLock();
    private ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();
    private ArrayList<Point> listeBonbon = new ArrayList<Point>();
    private Point[] positionJoueur = new Point[2];
    private Color[] couleurJoueur = new Color[2];
    
    public serveurImpl()throws RemoteException{
        super();
        positionJoueur[0] = new Point(0, 0);
        positionJoueur[1] = new Point(Constante.tailleEcran - Constante.tailleElement, Constante.tailleEcran - Constante.tailleElement);
        
        couleurJoueur[0] = Color.BLUE;
        couleurJoueur[1] = Color.darkGray;
    }
    
    public ArrayList<Joueur> getListeJoueur() {
	return listeJoueur;
    }
    
    public ArrayList<clientInterface> getListeClient() {
	return listeClient;
    }
    
    private ArrayList<Point> getListePositionJoueur() {
        ArrayList<Point> resultat = new ArrayList<Point>();
	for (Joueur j : listeJoueur) {
		resultat.add(j.position);
	}
	return resultat;
    }
    
    @Override
    public void connecter(clientInterface client, Joueur j) throws RemoteException {
        lock.lock();
        if(listeClient.size()<2){
            if(!listeClient.contains(client)){
                listeClient.add(listeClient.size(),client);
                j.setCouleur(couleurJoueur[listeClient.size()-1]);
                j.setScore(0);
                j.position.setLocation(positionJoueur[listeClient.size()-1]);
                listeJoueur.add(j);
                //compteur++;
                //afficher son joueur
                client.diffuserJoueur(listeClient, j);
                //diffuser nouveau joueur aux autres
                for(clientInterface clt : listeClient){
                    clt.diffuserJoueur(listeClient, null);
                }
            }
            System.out.println("Il y'a "+listeClient.size()+" clients connectés");
            if(listeClient.size() == 2){
                //clientImpl.enJeu = true;
                initialisation();
            }
        }
        lock.unlock();
    }
    
    @Override
    public void deconnecter(clientInterface client, Joueur j) throws RemoteException {
        lock.lock();
        if(listeClient.contains(client)){
            listeClient.remove(client);
            for(clientInterface clt : listeClient){
                    clt.diffuserJoueur(listeClient, null);
                }
        }
        lock.unlock();
    }

    @Override
    public void seDeplacer(clientInterface clt, Joueur j, Point newPosition) throws RemoteException {
        try{
            lock.lock();
            if(!listeBonbon.isEmpty()){
                ArrayList<Point> positionJoueurs = getListePositionJoueur();
                if(!positionJoueurs.contains(newPosition)){
                    //déplacer
                    listeJoueur.remove(j);
                    //enlever bonbon si il y en a
                    System.out.println("Il reste "+listeBonbon.size()+" bonbons");
                    if(listeBonbon.contains(newPosition)){
                        listeBonbon.remove(newPosition);
                        j.setScore(j.getScore()+1);//augmenter et afficher le score
                    }
                    j.position.setLocation(newPosition); // déplacer le joueur
                    listeJoueur.add(j);
                    //diffuser déplacement
                    clt.diffuserDeplacement(j, listeJoueur, listeBonbon);
                    for(clientInterface client : listeClient){
                        if(!client.equals(j)){
                            client.diffuserDeplacement(null, listeJoueur, listeBonbon);
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Erreur deplacement : "+e);
        }
        finally{
            lock.unlock();
        }
    }

    private void initialisation() {
        //générer les bonbons au hasard
        int x, y;
	Random r = new Random();
        for (int i = 0; i < Constante.nombreBonbons; i++) {
            Point point = null;
            boolean bool = false;
            while (!bool) {
                x = r.nextInt(Constante.tailleEcran - Constante.tailleElement);
		y = r.nextInt(Constante.tailleEcran - Constante.tailleElement);;
		point = new Point(x, y);
		if (x % Constante.tailleElement == 0 && y % Constante.tailleElement == 0 && !listeBonbon.contains(point)) {
                    point.setLocation(x + Constante.decallageEcran, y + Constante.decallageEcran);
                    bool = true;
		}
            }
            listeBonbon.add(point);
	}
        System.out.println(listeBonbon.size()+" bonbons créés");
        //diffuser les positions à tous les joueurs
        for(clientInterface clt : listeClient){
            try {
                clt.diffuserDeplacement(null, listeJoueur, listeBonbon);
            } catch (RemoteException ex) {
                System.out.println("Erreur initialisation du grid : "+ex);
            }
        }
    }

    
}
