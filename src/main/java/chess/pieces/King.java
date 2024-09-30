package chess.pieces;

import chess.Board;

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
                if (inBounds(check) && checkValidMove(location, check)) {
                    defaultMoves.add(check);
                }
            }
        }
        return trimMoves(defaultMoves);
    }


    //King can never check other king
    @Override
    public boolean isChecking() {
        return false;
    }

    public boolean checkValidMove(int pieceLocation, int moveLocation) {
        //Protects cases where square is empty from null reference
        if (Board.getInstance().getSquare(moveLocation).getValue() == 0) {
            return true;
            //Checks for attacks on same color
        } else if (this.isWhite == Board.getInstance().getSquare(moveLocation).getPiece().isWhite) {
            return false;
        }

        return true;
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

