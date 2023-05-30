package org.example;

import java.util.ArrayList;

public class Piece implements Runnable{

    private int x;

    private int y;

    private String symbole;

    private int positionFinale_x;
    private int positionFinale_y;

    private Grille grille;

    public Piece(int x, int y, String symbole, Grille grille) {
        this.x = x;
        this.y = y;
        this.symbole = symbole;
        this.grille = grille;
    }

    public int getX() {
        return this.x;
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
        while (this.x != this.positionFinale_x || this.y != this.positionFinale_y) {
            if (this.x < this.positionFinale_x) {
                if(grille.estVide(this.x+1, this.y) == null){
                    this.x++;
                }
            } else if (this.x > this.positionFinale_x) {
                if(grille.estVide(this.x-1, this.y) == null){
                    this.x--;
                }
            }
            if (this.y < this.positionFinale_y) {
                if(grille.estVide(this.x, this.y+1) == null){
                    this.y++;
                }
            } else if (this.y > this.positionFinale_y) {
                if(grille.estVide(this.x, this.y-1) == null){
                    this.y--;
                }
            }
            System.out.println(this.symbole + " se déplace en " + this.x + " " + this.y);
            grille.afficherGrille();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.symbole + " est arrivé à destination");
    }
}
