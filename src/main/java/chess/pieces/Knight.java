package chess.pieces;

import chess.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece {


    public Knight(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> defaultMoves = new ArrayList<Integer> (0);

        ArrayList<Integer> validMoves = new ArrayList<>(0);
        validMoves.add(location);

        //Checks for wrap-arounds
        if (this.location % 8 < 6) {
            defaultMoves.add(this.location + 10);
            defaultMoves.add(this.location - 6);

            defaultMoves.add(this.location + 17);
            defaultMoves.add(this.location - 15);
        } else if (this.location % 8 == 6) {
            defaultMoves.add(this.location + 17);
            defaultMoves.add(this.location - 15);
        }
        if (this.location % 8 > 1) {
            defaultMoves.add(this.location + 6);
            defaultMoves.add(this.location - 10);

            defaultMoves.add(this.location + 15);
            defaultMoves.add(this.location - 17);
        } else if (this.location % 8 == 1) {
            defaultMoves.add(this.location + 15);
            defaultMoves.add(this.location - 17);
        }

        for (Integer move : defaultMoves) {
            if (checkValidMove(location, move)) {
                validMoves.add(move);
            }
        }

        return validMoves;
    }

    @Override
    public boolean checkValidMove(int pieceLocation, int moveLocation) {
        //Inbounds check
        if (moveLocation > 63 || moveLocation < 0) {
            return false;
        }

        //Protects cases where square is empty from null reference
        if (Board.getInstance().getSquare(moveLocation).getValue() == 0) {
            return true;
        //Checks for attacks on same color
        } else if (this.isWhite == Board.getInstance().getSquare(moveLocation).getPiece().isWhite) {
            return false;
        }

        return true;
    }

}
