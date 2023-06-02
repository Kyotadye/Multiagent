package org.example;

class Node implements Comparable<Node> {
    int x;
    int y;
    int g;  // Coût du chemin depuis le point de départ
    int h;  // Heuristique (estimation du coût jusqu'à l'arrivée)
    int f;  // Coût total : f = g + h
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = 0;
        this.h = 0;
        this.f = 0;
        this.parent = null;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }
}
