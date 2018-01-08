
package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;


public class GridJeu extends JPanel{
    public ArrayList<Joueur> listeJoueur;
    public ArrayList<Point> listeBonbon;
    public Joueur monJoueur;
    
    public ArrayList<Point> getListeBonbon(){
        return listeBonbon;
    }
    
    public ArrayList<Joueur> getListeJoueur(){
        return listeJoueur;
    }
    
    public Joueur getMonJoueur(){
        return monJoueur;
    }
    
    public void setListeBonbon(ArrayList<Point> bonbons){
        listeBonbon = bonbons;
    }
    
    public void setListeJoueur(ArrayList<Joueur> joueurs){
        listeJoueur = joueurs;
    }
    
    public void setMonJoueur(Joueur j){
        monJoueur = j;
    }
    
    public GridJeu(){
        monJoueur = new Joueur();
        System.out.println("Nouveau joueur : "+monJoueur.getNom()+", "+monJoueur.getScore());
        listeBonbon = new ArrayList<Point>();
        listeJoueur = new ArrayList<Joueur>();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(Constante.decallageEcran, Constante.decallageEcran, Constante.tailleEcran, Constante.tailleEcran);
        
        for(Joueur j : listeJoueur){
            g.setColor(j.getCouleur());
            g.fillRect(j.position.x, j.position.y, Constante.tailleElement, Constante.tailleElement);
        }
        
        g.setColor(Color.red);
        for(Point p : listeBonbon){
            g.fillOval(p.x, p.y, Constante.tailleElement, Constante.tailleElement);
        }
    }
}
