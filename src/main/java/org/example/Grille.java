package org.example;

import java.util.ArrayList;

public class Grille {
    private int taille;
    private ArrayList<Piece> pieces;

    public Grille(int taille) {
        this.taille = taille;
        this.pieces = new ArrayList<Piece>();
    }

    public int getTaille() {
        return this.taille;
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

    public void afficherGrille(){
        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < this.taille; j++) {
                Piece piece = this.estVide(i, j);
                if (piece == null) {
                    System.out.print(".");
                } else {
                    System.out.print(piece.getSymbole());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }




}
