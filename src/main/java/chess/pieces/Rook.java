package chess.pieces;

import java.util.ArrayList;

public class Rook extends Piece {
    boolean isCastleable;

    public Rook(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> defaultMoves = new ArrayList<>();

        int[] directions = {1, -1, 8, -8};
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

        return new int[] {right, left, up, down};
    }
}
