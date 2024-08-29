package chess.pieces;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> defaultMoves = new ArrayList<>();

        int[] directions = {7, 9, -7, -9};
        int[] distances = distanceToEdges();

        int i = 0;
        for (int movement : directions) {
            int check = location + movement;

            while (distances[i] > 0) {
                defaultMoves.add(check);
                check += movement;
                distances[i] -= 1;
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

        return new int[] {Math.min(up, left), Math.min(up, right), Math.min(down, right), Math.min(down, left)};
    }
}
