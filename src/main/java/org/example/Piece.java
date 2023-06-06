package org.example;

import java.util.List;

import static org.example.Grille.THREAD_COUNT;
import static org.example.Grille.ITERATION_COUNTER;

public class Piece implements Runnable{

    private int x;

    private int y;

    private String symbole;

    private boolean isArrived = false;

    private int positionFinale_x;
    private int positionFinale_y;

    private int indiceColor;

    private Grille grille;


    public Piece(int x, int y, String symbole, Grille grille, int indiceColor) {
        this.x = x;
        this.y = y;
        this.symbole = symbole;
        this.grille = grille;
        this.indiceColor = indiceColor;
    }

    public int getX() {
        return this.x;
    }

    public boolean getIsArrived() {
        return this.isArrived;
    }

    public int getY() {
        return this.y;
    }

    public String getSymbole() {
        return this.symbole;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIndiceColor() {
        return this.indiceColor;
    }

    public void setPositionFinale(int x, int y) {
        this.positionFinale_x = x;
        this.positionFinale_y = y;
    }

    public int getPositionFinale_x() {
        return positionFinale_x;
    }

    public int getPositionFinale_y() {
        return positionFinale_y;
    }

    @Override
    public void run() {
        int maxIterations = 100;  // Limite le nombre d'itérations
        int iterations = 0;

        while (iterations < maxIterations) {
            List<Node> path;
            path = Astar.findPath(grille, this.x, this.y, this.positionFinale_x, this.positionFinale_y);
            synchronized (grille.getLock()) { // Utilisation de l'objet de verrouillage de la grille

                if (path != null && path.size() > 1) {
                    // La première étape du chemin représente le nœud suivant vers lequel la pièce doit se déplacer
                    Node nextNode = path.get(1);
                    int nextX = nextNode.x;
                    int nextY = nextNode.y;

                    if (grille.estVide(nextX, nextY) == null) {
                        this.x = nextX;
                        this.y = nextY;
                        System.out.println(this.symbole + " se déplace en " + this.x + " " + this.y);
                    }
                }
                else {
                    int nextY = this.y++;
                    System.out.println(this.y);
                    if (grille.estVide(this.x, nextY) == null) {
                        this.y = nextY;
                        System.out.println(this.symbole + " se déplace en " + this.x + " " + this.y);
                    }else {
                        int nextX = this.x++;
                        if (grille.estVide(nextX, this.y) == null) {
                            this.x = nextX;
                            System.out.println(this.symbole + " se déplace en " + this.x + " " + this.y);
                        }else{
                        nextY = this.y--;
                        if (grille.estVide(this.x, nextY) == null) {
                            this.y = nextY;
                            System.out.println(this.symbole + " se déplace en " + this.x + " " + this.y);
                        }else {
                            nextX = this.x--;
                            if (grille.estVide(nextX, this.y) == null) {
                                this.x = nextX;
                                System.out.println(this.symbole + " se déplace en " + this.x + " " + this.y);
                            }
                        }
                    }

                    }
                }
            }


            //grille.afficherGrille();
            if (this.x == this.positionFinale_x && this.y == this.positionFinale_y) {
                System.out.println(this.symbole + " est arrivé à destination");
                isArrived = true;
                THREAD_COUNT--;
                if(THREAD_COUNT != 0) ITERATION_COUNTER++;
                if(THREAD_COUNT >= 0) ITERATION_COUNTER = -1;grille.afficherGrille();
                break;
            }
            ITERATION_COUNTER++;
            grille.afficherGrille();
            iterations++;
        try {
            Thread.sleep(100);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }

}
