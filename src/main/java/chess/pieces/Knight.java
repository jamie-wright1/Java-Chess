package chess.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece {


    public Knight(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        int[] moveLocations = {6, -6, 10, -10, 15, -15, 17, -17};

        for (int moveLocation : moveLocations) {
            if (inbounds(location + moveLocation)) {
                moves.add(location + moveLocation);
            }

        }

        return moves;
    }

    public boolean inbounds(int moveLocation) {
        if (moveLocation > 64 || moveLocation < 0) {
            return false;
        }

        return true;
    }
}
