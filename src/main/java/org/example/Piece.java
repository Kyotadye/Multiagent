package org.example;

import java.util.List;
import java.util.Random;

import static org.example.Grille.THREAD_COUNT;
import static org.example.Grille.ITERATION_COUNTER;

public class Piece implements Runnable{

    private int x;

    private int y;

    private String symbole;

    private boolean isArrived = false;

    private int positionFinale_x;
    private int positionFinale_y;

    private int ordrePrecedent;

    private int lastOrdreDonné = -1;

    private int indice;

    private Grille grille;


    public Piece(int x, int y, String symbole, Grille grille, int indice) {
        this.x = x;
        this.y = y;
        this.symbole = symbole;
        this.grille = grille;
        this.indice = indice;
    }

    public int getX() {
        return this.x;
    }

    public boolean getIsArrived() {
        return this.isArrived;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Piece) {
            Piece other = (Piece) obj;
            return this.x == other.x && this.y == other.y;
        }
        return false;
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

    public int getIndice() {
        return this.indice;
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
        int maxIterations = 10000;  // Limite le nombre d'itérations
        int iterations = 0;

        while (iterations < maxIterations) {
            List<Node> path;

            // d'abord il va check la hashmap pour voir s'il a un ordre à son nom
            if (grille.ordres.get(this.indice) != null) {
                int nextX = this.x;
                int nextY = this.y;
                int currentOrdre = -1;
                int iterationsordre = 0;
                Random r = new Random();

                do {
                    int random = r.nextInt(4);
                    if (grille.estVide(nextX + 1, nextY) == null && random == 0 && nextX + 1 < grille.getTaille()) {
                        nextX = nextX + 1;
                        currentOrdre = 0;
                    }
                    if (grille.estVide(nextX - 1, nextY) == null && random == 1 && nextX - 1 >= 0) {
                        nextX = nextX - 1;
                        currentOrdre = 1;
                    }
                    if (grille.estVide(nextX, nextY + 1) == null && random == 2 && nextY + 1 < grille.getTaille()) {
                        nextY = nextY + 1;
                        currentOrdre = 2;
                    }
                    if (grille.estVide(nextX, nextY - 1) == null && random == 3 && nextY - 1 >= 0) {
                        nextY = nextY - 1;
                        currentOrdre = 3;
                    }
                    iterationsordre++;
                } while(iterationsordre < 5);
                if (currentOrdre != this.ordrePrecedent) {
                    Logger.log(this.symbole + " se déplace en " + nextX + " " + nextY + " suite à un ordre de " + grille.ordres.get(this.indice));
                    if(grille.estVide(nextX,nextY)== null){
                        this.x = nextX;
                        this.y = nextY;
                    }
                    grille.ordres.remove(this.indice);
                    path = null;
                    this.ordrePrecedent = currentOrdre;
                    if (isArrived) {
                        isArrived = false;
                    }
                } else {
                    Logger.log(this.symbole + " refuse l'odre de " + grille.ordres.get(this.indice));
                    grille.ordres.remove(this.indice);
                    grille.ordres.put(this.indice - 100, grille.ordres.get(this.indice));
                    this.ordrePrecedent = -1;
                    synchronized (grille.getLock()) {

                        path = Astar.findPath(grille, this.x, this.y, this.positionFinale_x, this.positionFinale_y);
                    }
                }
            } else {
                synchronized (grille.getLock()) {

                    path = Astar.findPath(grille, this.x, this.y, this.positionFinale_x, this.positionFinale_y);
                }
            }

            // Utilisation de l'objet de verrouillage de la grille
            synchronized (grille.getLock()) {
                int nextX = this.x;
                int nextY = this.y;
                if (path != null && path.size() > 1 && !isArrived) {
                    grille.ordres.remove(this.indice - 100);
                    // La première étape du chemin représente le nœud suivant vers lequel la pièce doit se déplacer
                    Node nextNode = path.get(1);
                    nextX = nextNode.x;
                    nextY = nextNode.y;
                    Piece piece = grille.estVide(nextX, nextY);
                    if (piece == null ) {
                        if(x != nextX && y != nextY) {
                            this.x = nextX;
                            this.y = nextY;
                            Logger.log(this.symbole + " se déplace en " + this.x + " " + this.y);
                        }
                    } else {
                        if(lastOrdreDonné != piece.getIndice() && grille.ordres.get(piece.getIndice()-100)==null) {
                            grille.ordres.put(piece.getIndice(), this.symbole);
                            Logger.log(this.symbole + " a donné un ordre à " + piece.getSymbole());
                            lastOrdreDonné = piece.getIndice();
                        }else{
                            lastOrdreDonné = -1;

                            this.x = nextX;
                            this.y = nextY;
                            Random r = new Random();
                            int random = r.nextInt(4);
                            if (grille.estVide(nextX+1,nextY)==null && random == 0 && nextX+1 < grille.getTaille()){
                                nextX = nextX+1;
                            }
                            if (grille.estVide(nextX-1,nextY)==null && random == 1 && nextX-1 >= 0){
                                nextX = nextX-1;
                            }
                            if (grille.estVide(nextX,nextY+1)==null && random == 2 && nextY+1 < grille.getTaille()){
                                nextY = nextY+1;
                            }
                            if (grille.estVide(nextX,nextY-1)==null && random == 3 && nextY-1 >= 0){
                                nextY = nextY-1;
                            }
                        }
                    }
                }
                this.x = nextX;
                this.y = nextY;
                //Logger.log(this.symbole + " se déplace en " + this.x + " " + this.y);

            }


            //grille.afficherGrille();
            if (this.x == this.positionFinale_x && this.y == this.positionFinale_y) {
                isArrived = true;
                THREAD_COUNT--;
                if(THREAD_COUNT != 0) ITERATION_COUNTER++;
                if(THREAD_COUNT >= 0) ITERATION_COUNTER = -1;grille.afficherGrille();
            }
            ITERATION_COUNTER++;
            grille.afficherGrille();
            iterations++;
            try {
                Thread.sleep(0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Logger.log(this.symbole + " a fini son travail");
    }

}
