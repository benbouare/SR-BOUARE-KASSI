
package client;

import javax.swing.JFrame;


public class Fenetre extends JFrame{
    
    public GridJeu grid;
    
    public Fenetre(){
        grid = new GridJeu();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setSize(Constante.tailleEcran + Constante.tailleElement, Constante.tailleEcran+Constante.decallageEcran+100);
        setContentPane(grid);
        setVisible(true);
        
    }
}
