
package client;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;


public class Joueur implements Serializable{
    private String nom;
    private int score;
    private Color couleur;
    public Point position = new Point();
    
    public Joueur(){
        this.nom = "Joueur";
        this.score = 0;
        this.couleur = null;
        this.position.setLocation(0, 0);
    }
    
    public String getNom(){
        return nom;
    }
    
    public int getScore(){
        return score;
    }
    
    public Color getCouleur(){
        return couleur;
    }
    
    public void setNom(String newNom){
        nom = newNom;
    }
    
    public void setScore(int newScore){
        score = newScore;
    }
    
    public void setCouleur(Color newCouleur){
        couleur = newCouleur;
    }
    
    public void setJoueur(Joueur j){
        nom = j.getNom();
        score = j.getScore();
        couleur = j.getCouleur();
        position.setLocation(j.position);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Joueur other = (Joueur) obj;
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        return true;
    }
}
