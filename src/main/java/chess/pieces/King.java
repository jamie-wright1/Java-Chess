package chess.pieces;

import java.util.ArrayList;

public class King extends Piece {
    boolean isUnderCheck;
    boolean isCastleable;

    public King(int value, int location) {
        super(value, location);

        isUnderCheck = false;
        isCastleable = true;
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> defaultMoves = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int check = location + j + 8*i;
                if (inBounds(check)) {
                    defaultMoves.add(check);
                }
            }
        }
        return defaultMoves;
    }

    public boolean inBounds(int moveLocation) {
        int column = location % 8;

        if (moveLocation > 63 || moveLocation < 0) {
            return false;
        }

        if (column == 0) {
            if (location - moveLocation == 1 || location - moveLocation == 9 || location - moveLocation == -7) {
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

