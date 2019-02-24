package GeneticObjects;

import java.util.Arrays;
import java.util.Random;

/**
 * Contains an array of Node objects which represents a path through the cities.
 */
public class Chromosome implements Comparable<Chromosome> {

    private Node[] nodes;
    private int distance = -1; // Calculated once then cached.
    private Random random;

    /**
     * Construct the Chromosome from an array. The nodes are in the same order
     * as they are in the array. No shuffling is done.
     * @param nodes    the array of Node objects for construction
     */
    public Chromosome (Node[] nodes) {
        this.nodes = nodes.clone();
    }

    /**
     * Construct the Chromosome from an array of Node objects and shuffle them.
     * @param nodes    the array of Node objects for construction
     * @param random    the Random object for shuffling the Chromosome
     */
    public Chromosome (Node[] nodes, Random random) {
        this.nodes = nodes.clone();
        this.random = random;
        shuffle();
    }

    /**
     * Shuffles the cities in the Chromosome.
     */
    private void shuffle () {
        for (int i = 0; i < nodes.length; i++) {
            swap(i, random.nextInt(nodes.length));
        }
    }

    /**
     * Helper method for shuffling the nodes. Swaps two nodes.
     * @param i     the index of the first node
     * @param j     the index of the second node
     */
    private void swap (int i, int j) {
        Node temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }

    public Node[] getArray () {
        return nodes.clone();
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return getDistance() - chromosome.getDistance();
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes) {
            sb.append(node);
        }
        return (new String(sb)).hashCode();
    }

    public int getDistance () {

        // If this was already calculated, don't calculate it again.
        if (distance != -1) {
            return distance;
        }

       double distanceTravelled = 0;

        for (int i = 1; i < nodes.length; i++) {
            distanceTravelled += Node.distance(nodes[i-1], nodes[i]);
        }

        distanceTravelled += Node.distance(nodes[nodes.length-1], nodes[0]);
        this.distance = (int)distanceTravelled;
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Chromosome)) {
            return false;
        }

        Chromosome c = (Chromosome) o;

        return Arrays.equals(c.nodes, nodes);
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("[ ");
        for (Node item : nodes) {
            sb.append(item.getName());
            sb.append(" ");
        }
        sb.append("]");
        return new String(sb);
    }
}
