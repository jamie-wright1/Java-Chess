package chess.pieces;

import java.util.ArrayList;

public class Pawn extends Piece {
    boolean isEnPessantable;


    public Pawn(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> defaultMoves = new ArrayList<>();

        if (isWhite) {
            defaultMoves.add(location + 8);
            defaultMoves.add(location + 16);
            defaultMoves.add(location + 7);
            defaultMoves.add(location + 9);
        } else {
            defaultMoves.add(location - 8);
            defaultMoves.add(location - 16);
            defaultMoves.add(location - 7);
            defaultMoves.add(location - 9);
        }

        return defaultMoves;
    }

    public boolean inBounds(int moveLocation) {
        int column = location % 8;

        if (moveLocation > 63 || moveLocation < 0) {
            return false;
        }

        if (column == 0) {
            if (location - moveLocation == 9 || location - moveLocation == 9 || location - moveLocation == -7) {
                return false;
            }
        } else if (column == 7) {
            if (location - moveLocation == -1 || location - moveLocation == -9 || location - moveLocation == 7) {
                return false;
            }
        }

        return true;
    }

}
