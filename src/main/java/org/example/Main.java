package org.example;

public class Main {
    public static void main(String[] args) {
        Grille grille = new Grille(5,3);
        Piece piece1 = new Piece(0, 3, "X", grille, 0);
        Piece piece2 = new Piece(4, 0, "O", grille, 1);
        Piece piece3 = new Piece(0, 0, "Y", grille, 2);
        //Piece piece4 = new Piece(0, 2, "U", grille, 3);
        piece1.setPositionFinale(0, 4);
        piece2.setPositionFinale(0, 0);
        piece3.setPositionFinale(4, 0);
        //piece4.setPositionFinale(1, 2);
        grille.addPiece(piece1);
        grille.addPiece(piece2);
        grille.addPiece(piece3);
        //grille.addPiece(piece4);
        grille.afficherGrille();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread1 = new Thread(piece1);
        Thread thread2 = new Thread(piece2);
        Thread thread3 = new Thread(piece3);
        //Thread thread4 = new Thread(piece4);

        thread1.start();
        thread2.start();
        thread3.start();
        //thread4.start();

    }
}