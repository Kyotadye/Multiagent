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
                //11 pièces seuil de fonctionnement tester maximum à l'instant T
                /*new Piece(2, 0, "L", grille, 11),
                new Piece(3, 1, "M", grille, 12),
                new Piece(4, 2, "N", grille, 13),
                new Piece(2, 1, "O", grille, 14),
                new Piece(4, 3, "P", grille, 15),
                new Piece(1, 1, "Q", grille, 16),
                new Piece(2, 3, "R", grille, 17)
                //18 pièces seuil de fonctionnement tester maximum à l'instant T+1
                /*new Piece(0, 1, "S", grille, 18),
                new Piece(3, 3, "T", grille, 19),
                new Piece(4, 0, "U", grille, 20)
                /*new Piece(1, 0, "V", grille, 21),
                new Piece(2, 4, "W", grille, 22),
                new Piece(3, 2, "X", grille, 23)
                /*new Piece(0, 4, "Y", grille, 24)
                //nombre de pièces maximum possibles dans la grille */
        );
        boolean hasDuplicates = pieces.stream()
                .anyMatch(piece1 -> pieces.stream()
                        .anyMatch(piece2 -> piece1 != piece2 && piece1.equals(piece2)));
        if (hasDuplicates) {
            throw new IllegalArgumentException("La liste contient des doublons");
        }
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
        //11 pièces seuil de fonctionnement tester maximum à l'instant T
        /*pieces.get(11).setPositionFinale(2, 2);
        pieces.get(12).setPositionFinale(4, 1);
        pieces.get(13).setPositionFinale(1, 3);
        pieces.get(14).setPositionFinale(2, 1);
        pieces.get(15).setPositionFinale(4, 4);
        pieces.get(16).setPositionFinale(0, 1);
        pieces.get(17).setPositionFinale(3, 1);
        //18 pièces seuil de fonctionnement tester maximum à l'instant T+1
        /*pieces.get(18).setPositionFinale(1, 1);
        pieces.get(19).setPositionFinale(2, 3);
        pieces.get(20).setPositionFinale(4, 2);
        /*pieces.get(21).setPositionFinale(1, 4);
        pieces.get(22).setPositionFinale(2, 0);
        pieces.get(23).setPositionFinale(0, 3);
        pieces.get(24).setPositionFinale(3, 2);
        //nombre de pièces maximum possibles dans la grille */

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