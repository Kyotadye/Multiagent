package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.Grille.THREAD_COUNT;

public class Main {
    public static void main(String[] args) {
        Grille grille = new Grille(5,3);
        List <Piece> pieces = List.of(
                new Piece(0, 3, "A", grille, 0),
                new Piece(3, 0, "B", grille, 1),
                new Piece(0, 0, "C", grille, 2),
                new Piece(0, 2, "D", grille, 3),
                new Piece(2, 2, "E", grille, 4),
                new Piece(3, 4, "F", grille, 5),
                new Piece(1, 3, "G", grille, 6),
                new Piece(4, 1, "H", grille, 7),
                new Piece(4, 4, "I", grille, 8),
                new Piece(1, 2, "J", grille, 9),
                new Piece(1, 4, "K", grille, 10)

        );
        THREAD_COUNT = pieces.size();
        grille.max_thread = THREAD_COUNT+1;

        pieces.get(0).setPositionFinale(0, 4);
        pieces.get(1).setPositionFinale(0, 0);
        pieces.get(2).setPositionFinale(4, 0);
        pieces.get(3).setPositionFinale(1, 2);
        pieces.get(4).setPositionFinale(2, 4);
        pieces.get(5).setPositionFinale(1, 0);
        pieces.get(6).setPositionFinale(3, 3);
        pieces.get(7).setPositionFinale(3, 0);
        pieces.get(8).setPositionFinale(0, 2);
        pieces.get(9).setPositionFinale(3, 4);
        pieces.get(10).setPositionFinale(4, 3);

        for (Piece piece : pieces) {
            grille.addPiece(piece);
        }

        grille.afficherGrille();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads.add(new Thread(pieces.get(i)));
        }
        for (Thread thread : threads) {
            thread.start();
        }

    }
}