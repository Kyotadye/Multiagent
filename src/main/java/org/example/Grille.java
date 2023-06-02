package org.example;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class Grille {
    private int taille;
    private ArrayList<Piece> pieces;

    private JFrame frame; // Fenêtre Swing
    private JLabel[][] labels; // Tableau de labels pour représenter la grille

    private final Object lock;

    public Grille(int taille) {
        this.taille = taille;
        this.pieces = new ArrayList<Piece>();
        this.lock = new Object();

        SwingUtilities.invokeLater(this::initSwingComponents);
    }

    public int getTaille() {
        return this.taille;
    }

    public Object getLock() {
        return this.lock;
    }

    public ArrayList<Piece> getPieces() {
        return this.pieces;
    }

    public void addPiece(Piece piece) {
        this.pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        this.pieces.remove(piece);
    }

    public Piece estVide(int x, int y) {
        for (Piece piece : this.pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        return null;
    }


    public void afficherGrille() {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.red);
        colors.add(Color.blue);
        colors.add(Color.green);
        colors.add(Color.orange);
        colors.add(Color.pink);
        colors.add(Color.cyan);
        colors.add(Color.magenta);
        colors.add(Color.gray);
        colors.add(Color.darkGray);
        colors.add(Color.lightGray);
        SwingUtilities.invokeLater(() -> {
            if (frame.isVisible()) {
                int indice = 0;
                // Mettre à jour les labels existants
                for (int i = 0; i < this.taille; i++) {
                    for (int j = 0; j < this.taille; j++) {
                        Piece piece = this.estVide(i, j);
                        if (piece == null) {
                            labels[i][j].setText(".");
                        } else {
                            labels[i][j].setText(piece.getSymbole());
                            labels[i][j].setForeground(Color.black);
                            if(!piece.getIsArrived()){
                                labels[i][j].setForeground(colors.get(piece.getIndiceColor() % colors.size()));
                            }
                            labels[piece.getPositionFinale_x()][piece.getPositionFinale_y()].setBackground(colors.get(piece.getIndiceColor()%colors.size()));
                            labels[piece.getPositionFinale_x()][piece.getPositionFinale_y()].setOpaque(true);
                            indice = (indice + 1) % colors.size();
                        }
                    }
                }
            } else {
                // Créer et afficher l'interface Swing
                initSwingComponents();
                frame.setVisible(true);
            }
        });
    }

    private void initSwingComponents() {
        // Création de la fenêtre Swing
        frame = new JFrame("Grille");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(this.taille, this.taille));
        int windowSize = this.taille * 100; // Ajustez la valeur 100 selon vos besoins

        // Création des labels pour représenter la grille
        labels = new JLabel[this.taille][this.taille];
        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < this.taille; j++) {
                JLabel label = new JLabel(".", SwingConstants.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setFont(new Font("Arial", Font.BOLD, 20)); // Ajustement de la taille de la police
                /*label.setForeground(Color.BLUE);
                label.setBackground(Color.RED);
                label.setOpaque(true);*/
                labels[i][j] = label;
                frame.add(label);
            }
        }

        // Ajustement de la taille de la fenêtre en fonction du contenu
        frame.setSize(windowSize, windowSize);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        this.afficherGrille();
    }

}



