package org.example;

import java.util.*;

public class Astar {
    private static final int[] dx = {0, 0, 1, -1};  // Directions possibles : haut, bas, gauche, droite

    private static final int[] dy = {1, -1, 0, 0};

    public static List<Node> findPath(Grille grille, int startX, int startY, int endX, int endY) {
        int taille = grille.getTaille();

        // Vérification des limites de la grille
        if (!isValidPosition(startX, startY, taille) || !isValidPosition(endX, endY, taille)) {
            return null;
        }

        Piece endPiece = grille.estVide(endX, endY);

        // Vérification si le point de départ ou d'arrivée est occupé par une pièce
        if (endPiece != null) {
            return null;
        }

        Node startNode = new Node(startX, startY);
        Node endNode = new Node(endX, endY);

        // Initialisation des listes ouvertes et fermées
        PriorityQueue<Node> openQueue = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();

        openQueue.offer(startNode);

        // Boucle principale de l'algorithme A*
        while (!openQueue.isEmpty()) {
            Node currentNode = openQueue.poll();

            // Vérification si on a atteint le nœud d'arrivée
            if (currentNode.x == endNode.x && currentNode.y == endNode.y) {
                List<Node> path = new ArrayList<>();
                Node current = currentNode;
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(currentNode);

            // Parcours des voisins du nœud actuel
            for (int i = 0; i < dx.length; i++) {
                int newX = currentNode.x + dx[i];
                int newY = currentNode.y + dy[i];

                // Vérification si la nouvelle position est dans les limites de la grille
                if (!isValidPosition(newX, newY, taille)) {
                    continue;
                }

                // Vérification si la nouvelle position est occupée par une pièce
                Piece piece = grille.estVide(newX, newY);
                if (piece != null) {
                    continue;
                }

                Node neighbor = new Node(newX, newY);
                neighbor.g = currentNode.g + 1;
                neighbor.h = Math.abs(newX - endNode.x) + Math.abs(newY - endNode.y);
                neighbor.f = neighbor.g + neighbor.h;
                neighbor.parent = currentNode;

                // Vérification si le voisin est déjà dans la liste fermée
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                // Vérification si le voisin est déjà dans la liste ouverte avec un coût inférieur
                boolean isOpen = false;
                for (Node openNode : openQueue) {
                    if (neighbor.x == openNode.x && neighbor.y == openNode.y && neighbor.g >= openNode.g) {
                        isOpen = true;
                        break;
                    }
                }
                if (isOpen) {
                    continue;
                }

                openQueue.offer(neighbor);
            }
        }

        return null;  // Aucun chemin trouvé
    }

    private static boolean isValidPosition(int x, int y, int taille) {
        return x >= 0 && x < taille && y >= 0 && y < taille;
    }
}
