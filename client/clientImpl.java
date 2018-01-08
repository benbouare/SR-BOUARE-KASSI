
package client;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveur.serveurInterface;

public class clientImpl extends java.rmi.server.UnicastRemoteObject implements clientInterface,Runnable{

    public Fenetre fenetre;
    public serveurInterface serveur;
    private ArrayList<clientInterface> clients;
    KeyAdapter keyAdapter;
    
    public clientImpl(serveurInterface serv) throws RemoteException, NotBoundException, MalformedURLException{
        
        super();
        this.serveur = serv;
        initialisation();
    }
    
    public void initialisation(){
        //serveur.connecter(this, this.fenetre.grid.getMonJoueur());
        fenetre = new Fenetre();
        clients = new ArrayList<clientInterface>();
        keyAdapter = new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                Point p = null;
                if(isValidKey(e)){
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            p = allerEnHaut();
                            break;
                        case KeyEvent.VK_DOWN:
                            p = allerEnBas();
                            break;
                        case KeyEvent.VK_LEFT:
                            p = allerAGauche();
                            break;
                        case KeyEvent.VK_RIGHT:
                            p = allerADroite();
                            break;
                        default:
                            p = null;
                    }
                    deplacer(p);
                }
            } 
        };
        fenetre.addKeyListener(keyAdapter);
    }
    
    private Point allerEnBas() {
        int y = fenetre.grid.monJoueur.position.y;
        if ((y + 2 * Constante.tailleElement) > Constante.tailleEcran + Constante.decallageEcran) {
            y = Constante.decallageEcran;
        } else {
            y += Constante.tailleElement;
        }
        return new Point(fenetre.grid.monJoueur.position.x, y);
    }

    private Point allerEnHaut() {
        int y = fenetre.grid.monJoueur.position.y;
        if (y - Constante.tailleElement < Constante.decallageEcran) {
            y = Constante.tailleEcran + Constante.decallageEcran - Constante.tailleElement;
        } else {
            y -= Constante.tailleElement;
        }
        return new Point(fenetre.grid.monJoueur.position.x, y);
    }

    private Point allerADroite() {
        int x = fenetre.grid.monJoueur.position.x;
        if ((x + 2 * Constante.tailleElement) > Constante.tailleEcran + Constante.decallageEcran) {
            x = Constante.decallageEcran;
        } else {
            x += Constante.tailleElement;
        }
        return new Point(x, fenetre.grid.monJoueur.position.y);
    }

    private Point allerAGauche() {
        int x = fenetre.grid.monJoueur.position.x;
        if (x - Constante.tailleElement < Constante.decallageEcran) {
            x = Constante.tailleEcran + Constante.decallageEcran - Constante.tailleElement;
        } else {
            x -= Constante.tailleElement;
        }
        return new Point(x, fenetre.grid.monJoueur.position.y);
    }
    public void deplacer(Point p){
        try {
            serveur.seDeplacer(this, fenetre.grid.getMonJoueur(), p);
        } catch (RemoteException ex) {
            System.out.println("Erreur déplacement client : "+ex);
        }
    }
    
    private boolean isValidKey(KeyEvent ke) {
        return (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_UP
                || ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_RIGHT);
    }
    
    /*private Point newPosition(int[] delta){
        Point p = new Point();
        p.x = (fenetre.grid.monJoueur.position.x+delta[0]+20)%20 ;
        p.y = (fenetre.grid.monJoueur.position.y+delta[1]+20)%20 ;
        return p;
    }*/
    
    @Override
    public void diffuserJoueur(ArrayList<clientInterface> listeClient, Joueur j) throws RemoteException {
        this.clients.clear();
        this.clients.addAll(clients);
        if(j != null){
            fenetre.grid.setMonJoueur(j);
            fenetre.grid.repaint();
        }
    }

    @Override
    public void diffuserDeplacement(Joueur j, ArrayList<Joueur> joueurs, ArrayList<Point> bonbons) throws RemoteException {
        if(j != null){
            fenetre.grid.setMonJoueur(j);
        }
        fenetre.grid.setListeBonbon(bonbons);
        fenetre.grid.setListeJoueur(joueurs);
        fenetre.grid.repaint();
    }
    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        serveurInterface serveur = (serveurInterface)Naming.lookup("rmi://localhost/Game");
        clientImpl clt = new clientImpl(serveur);
        new Thread(clt).start();
    }

    @Override
    public void run() {
        try {
            serveur.connecter(this, fenetre.grid.getMonJoueur());
        } catch (RemoteException ex) {
            Logger.getLogger(clientImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}