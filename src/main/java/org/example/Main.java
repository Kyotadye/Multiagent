package org.example;

public class Main {
    public static void main(String[] args) {
        Grille grille = new Grille(5);
        Piece piece1 = new Piece(0, 0, "X", grille);
        Piece piece2 = new Piece(1, 1, "O", grille);
        Piece piece3 = new Piece(2, 2, "Y", grille);
        Piece piece4 = new Piece(0, 2, "U", grille);
        piece1.setPositionFinale(1, 0);
        piece2.setPositionFinale(1, 4);
        piece3.setPositionFinale(2, 3);
        piece4.setPositionFinale(1, 2);
        grille.addPiece(piece1);
        grille.addPiece(piece2);
        grille.addPiece(piece3);
        grille.addPiece(piece4);
        grille.afficherGrille();

        Thread thread1 = new Thread(piece1);
        Thread thread2 = new Thread(piece2);
        Thread thread3 = new Thread(piece3);
        Thread thread4 = new Thread(piece4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();



    }
}