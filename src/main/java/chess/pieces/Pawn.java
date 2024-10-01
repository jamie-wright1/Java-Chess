package chess.pieces;

import chess.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
    private boolean isEnPessantable;

    public Pawn(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> validMoves = new ArrayList<Integer>();

        if (isWhite) {
            validMoves = findWhiteMoves();
        } else {
            validMoves = findBlackMoves();
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

    //Pawn is only piece whose rules differ by color
    public ArrayList<Integer> findWhiteMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();

        if (location < 56 && Board.getInstance().getSquare(location + 8).getValue() == 0) {
            validMoves.add(location + 8);
            //Double move forward if on starting square
            if (location < 16 && Board.getInstance().getSquare(location + 16).getValue() == 0) {
                validMoves.add(location + 16);
            }
        }

        //Sets up diagonal conditions beforehand to simplify if statements
        boolean rightDiagonal = (Board.getInstance().getSquare(location + 9).getValue() < 15 &&
                                Board.getInstance().getSquare(location + 9).getValue() > 0);
        boolean leftDiagonal = (Board.getInstance().getSquare(location + 7).getValue() < 15 &&
                                Board.getInstance().getSquare(location + 7).getValue() > 0);
        boolean rightEnPessantable = (Board.getInstance().getSquare(location + 1).getValue() == 9 &&
                                    Board.getInstance().getSquare(location + 1).getPiece().isEnPessantable());
        boolean leftEnPessantable = (Board.getInstance().getSquare(location - 1).getValue() == 9 &&
                                    Board.getInstance().getSquare(location - 1).getPiece().isEnPessantable());

        //Right diagonal check
        if (rightDiagonal || rightEnPessantable) {
            validMoves.add(location + 9);
        }
        //Left diagonal check
        if (leftDiagonal || leftEnPessantable) {
            validMoves.add(location + 7);
        }

        return validMoves;
    }

    public ArrayList<Integer> findBlackMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();

        if (location > 7 && Board.getInstance().getSquare(location - 8).getValue() == 0) {
            validMoves.add(location - 8);
            //Double move forward if on starting square
            if (location > 47 && Board.getInstance().getSquare(location - 16).getValue() == 0) {
                validMoves.add(location - 16);
            }
        }

        //Sets up diagonal conditions beforehand to simplify if statements
        boolean rightDiagonal = Board.getInstance().getSquare(location - 7).getValue() > 14;
        boolean leftDiagonal = Board.getInstance().getSquare(location - 9).getValue() > 14;
        boolean rightEnPessantable = (Board.getInstance().getSquare(location + 1).getValue() == 17 &&
                Board.getInstance().getSquare(location + 1).getPiece().isEnPessantable());
        boolean leftEnPessantable = (Board.getInstance().getSquare(location - 1).getValue() == 17 &&
                Board.getInstance().getSquare(location - 1).getPiece().isEnPessantable());

        //Right diagonal check
        if (rightDiagonal || rightEnPessantable) {
            validMoves.add(location - 7);
        }
        //Left diagonal check
        if (leftDiagonal || leftEnPessantable) {
            validMoves.add(location - 9);
        }

        return validMoves;
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

    public void setEnPessantable(boolean isEnPessantable) {
        this.isEnPessantable = isEnPessantable;
    }

    @Override
    public boolean isEnPessantable() {
        return isEnPessantable;
    }

}
