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
        ArrayList<Integer> defaultMoves = new ArrayList<>(0);

        ArrayList<Integer> validMoves = new ArrayList<>(0);

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

        return trimMoves(validMoves);
    }

    @Override
    public boolean isChecking() {
        ArrayList<Integer> moves = new ArrayList<>
            (Arrays.asList(location + 10, location - 10, location + 6, location - 6,
                            location + 15, location + 17, location - 15, location - 17));

        for (Integer move : moves) {
            if (isWhite) {
                if (inBounds(move) && Board.getInstance().getSquare(move).getValue() == 14) {
                    return true;
                    }
            } else {
                if (inBounds(move) && Board.getInstance().getSquare(move).getValue() == 22) {
                    return true;
                }
            }
        }

        return false;
    }

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

    public boolean inBounds(int moveLocation) {
        int difference = this.location - moveLocation;

        if (moveLocation > 63 || moveLocation < 0) {
            return false;
        }

        //Checks for wrap-arounds
        if (this.location % 8 < 6) {
            if (difference == 10 || difference == -6 || difference == 17 || difference == -15) {
                return true;
            }
        } else if (this.location % 8 == 6) {
            if (difference == 17 || difference == -15) {
                return true;
            }
        }
        if (this.location % 8 > 1) {
            if (difference == 6 || difference == -10 || difference == 15 || difference == -17) {
                return true;
            }
        } else if (this.location % 8 == 1) {
            if (difference == 15 || difference == -17) {
                return true;
            }
        }

        return true;
    }

}
