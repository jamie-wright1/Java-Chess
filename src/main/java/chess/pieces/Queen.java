package chess.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends Piece{
    public Queen(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> defaultMoves = new ArrayList<>();

        int[] directions = {1, -1, 8, -8, 7, 9, -7, -9};
        int[] distances = distanceToEdges();

        int i = 0;
        for (int movement : directions) {
            int check = location + movement;

            while (distances[i] > 0) {
                defaultMoves.add(check);

                check += movement;
                distances[i]--;
            }

            i++;
        }

        return defaultMoves;
    }

    public int[] distanceToEdges() {
        int right = 7 - location % 8;
        int left = location % 8;
        int up = 7 - location/8;
        int down = location/8;

        int[] straights = {right, left, up, down};
        int[] diagonals = {Math.min(up, left), Math.min(up, right), Math.min(down, right), Math.min(down, left)};


        int[] concatenated = Arrays.copyOf(straights, 8);
        System.arraycopy(diagonals, 0, concatenated, 4, 4);
        return concatenated;
    }
}
