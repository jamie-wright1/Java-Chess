package chess.pieces;

import chess.Board;
import chess.Game;

import java.util.ArrayList;

public class King extends Piece {
    boolean isCastleable;

    public King(int value, int location) {
        super(value, location);

        isCastleable = true;
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int check = location + j + 8*i;
                if (inBounds(check) && checkValidMove(location, check)) {
                    validMoves.add(check);
                }
            }
        }

        validMoves.addAll(checkForCastles());

        return trimMoves(validMoves);
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

    public ArrayList<Integer> checkForCastles() {
        if (this.isCastleable == false) {
            return new ArrayList<>();
        }

        ArrayList<Integer> validCastles = new ArrayList<>();
        Piece rightCastle = Board.getInstance().getSquare(location + 3).getPiece();
        Piece leftCastle = Board.getInstance().getSquare(location - 4).getPiece();

        if (rightCastle instanceof Rook && ((Rook) rightCastle).isCastleable) {
            if (Board.getInstance().getSquare(location + 1).getPiece() == null &&
                Board.getInstance().getSquare(location + 2).getPiece() == null &&
                !oppositeColorAttackingSquare(location + 1)) {
                validCastles.add(location + 2);
            }
        }
        if (leftCastle instanceof Rook && ((Rook) leftCastle).isCastleable) {
            if (Board.getInstance().getSquare(location - 1).getPiece() == null &&
                Board.getInstance().getSquare(location - 2).getPiece() == null &&
                Board.getInstance().getSquare(location - 3).getPiece() == null &&
                !oppositeColorAttackingSquare(location - 1)) {
                validCastles.add(location - 2);
            }
        }

        return validCastles;
    }

    public boolean oppositeColorAttackingSquare(int square) {
        boolean attacking = false;
        Piece storePiece = Board.getInstance().getSquare(square).getPiece();

        Board.getInstance().getSquare(square).addPiece(this);
        Board.getInstance().getSquare(this.location).removePiece();

        if (Game.getInstance().checkForCheck(this.isWhite)) {
            attacking = true;
        }

        Board.getInstance().getSquare(square).addPiece(storePiece);
        Board.getInstance().getSquare(this.location).addPiece(this);

        return attacking;
    }

    public void setCastleable(boolean isCastleable) {
        this.isCastleable = isCastleable;
    }

    public boolean isCastleable() {
        return isCastleable;
    }

}

