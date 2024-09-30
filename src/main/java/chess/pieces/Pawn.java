package chess.pieces;

import chess.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
    boolean isEnPessantable;


    public Pawn(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> defaultMoves = new ArrayList<Integer>
        (Arrays.asList(location + 8, location + 16, location + 7, location + 9,
        location - 8, location - 16, location - 7, location - 9));

        ArrayList<Integer> validMoves = new ArrayList<>(0);

        for (Integer move : defaultMoves) {
            if (checkValidMove(location, move)) {
                validMoves.add(move);
            }
        }

        return trimMoves(validMoves);
    }

    @Override
    public boolean isChecking() {
        if (isWhite) {
            if (inBounds(location + 7) && Board.getInstance().getSquare(location + 7).getValue() == 14) {
                return true;
            } else if (inBounds(location + 9) && Board.getInstance().getSquare(location + 9).getValue() == 14) {
                return true;
            }
        } else {
            if (inBounds(location - 7) && Board.getInstance().getSquare(location - 7).getValue() == 22) {
                return true;
            } else if (inBounds(location - 9) && Board.getInstance().getSquare(location - 9).getValue() == 22) {
                return true;
            }
        }

        return false;
    }

    public boolean checkValidMove(int pieceLocation, int moveLocation) {
        if (this.isWhite) {
            return checkValidWhiteMoves(pieceLocation, moveLocation);
        } else {
            return checkValidBlackMoves(pieceLocation, moveLocation);
        }
    }

    //Pawn only piece whose rules differ by color
    public boolean checkValidWhiteMoves(int pieceLocation, int moveLocation) {
        //Checks for movement in right direction
        if (moveLocation < pieceLocation) {
            return false;
        }

        if (!inBounds(moveLocation)) {
            return false;
        }

        //Checks for double-forward move validity
        if (pieceLocation > 15 && moveLocation == pieceLocation + 16) {
            return false;
        }

        //Checks if forward move blocked
        if (moveLocation == pieceLocation + 8 && Board.getInstance().getSquare(moveLocation).getValue() != 0) {
            return false;
        }

        //Checks if diagonals are black
        if ((moveLocation == pieceLocation + 9 || moveLocation == pieceLocation + 7) &&
        (Board.getInstance().getSquare(moveLocation).getValue() > 14 ||
        Board.getInstance().getSquare(moveLocation).getValue() == 0)) {
            return false;
        }

        return true;
    }

    public boolean checkValidBlackMoves(int pieceLocation, int moveLocation) {
        //Checks for movement in right direction
        if (moveLocation > pieceLocation) {
            return false;
        }

        if (!inBounds(moveLocation)) {
            return false;
        }

        //Checks for double-forward move validity
        if (pieceLocation < 50 && moveLocation == pieceLocation - 16) {
            return false;
        }

        //Checks if forward move blocked
        if (moveLocation == pieceLocation - 8 && Board.getInstance().getSquare(moveLocation).getValue() != 0) {
            return false;
        }

        //Checks if diagonals are white
        if ((moveLocation == pieceLocation - 9 || moveLocation == pieceLocation - 7) &&
            (Board.getInstance().getSquare(moveLocation).getValue() < 17)) {
            return false;
        }

        return true;
    }

    public boolean inBounds(int moveLocation) {
        if (moveLocation > 63 || moveLocation < 0) {
            return false;
        }

        //Checks for wrap-arounds
        if (this.location % 8 == 7) {
            if( moveLocation == this.location - 7 || moveLocation == this.location + 9) {
                return false;
            }
        } else if (this.location % 8 == 0) {
            if (moveLocation == this.location - 9 || moveLocation == this.location + 7) {
                return false;
            }
        }

        return true;
    }

}
