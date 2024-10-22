package chess.pieces;

import chess.Board;
import chess.Game;

import java.util.ArrayList;

public class King extends Piece {
    private boolean isCastleable;

    public King(int value, int location, Board board) {
        super(value, location, board);

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


    @Override
    public boolean isChecking() {
        for (int i = -1; i <= 1; i ++) {
            for (int j = -1; j <= 1; j++) {
                int check = location + j + 8*i;
                if (inBounds(check)) {
                    if (isWhite && board.getSquare(check).getValue() == 14) {
                        return true;
                    } else if (!isWhite && board.getSquare(check).getValue() == 22) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkValidMove(int pieceLocation, int moveLocation) {
        //Protects cases where square is empty from null reference
        if (board.getSquare(moveLocation).getValue() == 0) {
            return true;
            //Checks for attacks on same color
        } else if (this.isWhite == board.getSquare(moveLocation).getPiece().isWhite) {
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
        if (this.isCastleable == false || board.checkForCheck(this.isWhite)) {
            return new ArrayList<>();
        }
        if (this.isWhite && this.location != 4) {
            return new ArrayList<>();
        } else if (!this.isWhite && this.location != 60) {
            return new ArrayList<>();
        }

        ArrayList<Integer> validCastles = new ArrayList<>();
        Piece rightCastle = board.getSquare(location + 3).getPiece();
        Piece leftCastle = board.getSquare(location - 4).getPiece();

        if (rightCastle instanceof Rook && ((Rook) rightCastle).isCastleable()) {
            if (board.getSquare(location + 1).getPiece() == null &&
                board.getSquare(location + 2).getPiece() == null &&
                !oppositeColorAttackingSquare(location + 1)) {
                validCastles.add(location + 2);
            }
        }
        if (leftCastle instanceof Rook && ((Rook) leftCastle).isCastleable()) {
            if (board.getSquare(location - 1).getPiece() == null &&
                board.getSquare(location - 2).getPiece() == null &&
                board.getSquare(location - 3).getPiece() == null &&
                !oppositeColorAttackingSquare(location - 1)) {
                validCastles.add(location - 2);
            }
        }

        return validCastles;
    }

    public boolean oppositeColorAttackingSquare(int square) {
        boolean attacking = false;
        Piece storePiece = board.getSquare(square).getPiece();

        board.getSquare(square).addPiece(this);
        board.getSquare(this.location).removePiece();

        if (board.checkForCheck(this.isWhite)) {
            attacking = true;
        }

        board.getSquare(square).addPiece(storePiece);
        board.getSquare(this.location).addPiece(this);

        return attacking;
    }

    public void setCastleable(boolean isCastleable) {
        this.isCastleable = isCastleable;
    }

    public boolean isCastleable() {
        return isCastleable;
    }

}