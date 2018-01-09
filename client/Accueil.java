
package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Accueil extends JFrame{
    
    private JLabel labBonjour = new JLabel("BONJOUR");
    private JLabel labNom = new JLabel("Entrer votre nom : ");
    private JTextField textNom = new JTextField(20);
    JButton btnValider = new JButton("Valider");
    Fenetre fenetre;
    
    public Accueil(){
        setTitle("Accueil");
        setSize(600,400);
        setResizable(false);
        JPanel panel = new JPanel();
        
        Font fBonjour = new Font("ARIAL",Font.BOLD,30);
        Font fNom = new Font("ARIAL",Font.BOLD,15);
        labBonjour.setFont(fBonjour);
        labNom.setFont(fNom);
        
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx=2;
	gbc.gridy=0;
	gbc.gridheight=8;
	gbc.gridwidth = GridBagConstraints.REMAINDER;
	gbc.anchor= GridBagConstraints.BASELINE_LEADING;
	gbc.insets= new Insets(20,200,100,200);
	panel.add(labBonjour,gbc);
                
	gbc.gridx=0;
	gbc.gridy=10;
        gbc.gridheight=8;
	gbc.gridwidth = GridBagConstraints.REMAINDER;
	gbc.anchor= GridBagConstraints.BASELINE_LEADING;
	gbc.insets= new Insets(0,0,100,400);
	panel.add(labNom,gbc);
		
	gbc.gridx=1;
	gbc.gridy=10;
	gbc.gridheight=8;
	gbc.gridwidth = GridBagConstraints.REMAINDER;
	gbc.anchor= GridBagConstraints.BASELINE_LEADING;
	gbc.insets= new Insets(0,200,100,0);
	panel.add(textNom,gbc);
        
        gbc.gridx=2;
	gbc.gridy=14;
	gbc.gridheight=8;
	gbc.gridwidth = GridBagConstraints.REMAINDER;
	gbc.anchor= GridBagConstraints.BASELINE_LEADING;
	gbc.insets= new Insets(20,200,100,200);
	panel.add(btnValider,gbc);
        
        btnValider.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    fenetre = new Fenetre();
            }
            
        });
        
        getContentPane().add(panel);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
