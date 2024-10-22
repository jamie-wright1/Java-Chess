package chess.pieces;

import chess.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
    private boolean isEnPessantable;

    public Pawn(int value, int location, Board board) {
        super(value, location, board);
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
            if (inBounds(location + 7) && board.getSquare(location + 7).getValue() == 14) {
                return true;
            } else if (inBounds(location + 9) && board.getSquare(location + 9).getValue() == 14) {
                return true;
            }
        } else {
            if (inBounds(location - 7) && board.getSquare(location - 7).getValue() == 22) {
                return true;
            } else if (inBounds(location - 9) && board.getSquare(location - 9).getValue() == 22) {
                return true;
            }
        }

        return false;
    }

    //Pawn is only piece whose rules differ by color
    public ArrayList<Integer> findWhiteMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();

        if (location < 56 && board.getSquare(location + 8).getValue() == 0) {
            validMoves.add(location + 8);
            //Double move forward if on starting square
            if (location < 16 && board.getSquare(location + 16).getValue() == 0) {
                validMoves.add(location + 16);
            }
        }

        //Sets up diagonal conditions beforehand to simplify if statements
        boolean rightDiagonal = inBounds(location + 9) &&
                                board.getSquare(location + 9).getValue() < 15 &&
                                board.getSquare(location + 9).getValue() > 0;
        boolean leftDiagonal = inBounds(location + 7) &&
                                board.getSquare(location + 7).getValue() < 15 &&
                                board.getSquare(location + 7).getValue() > 0;
        boolean rightEnPessantable = inBounds(location + 9) &&
                                    board.getSquare(location + 1).getPiece() instanceof Pawn &&
                                    board.getSquare(location + 1).getValue() == 9 &&
                                    ((Pawn) board.getSquare(location + 1).getPiece()).isEnPessantable();
        boolean leftEnPessantable = inBounds(location + 7) &&
                                    board.getSquare(location - 1).getPiece() instanceof Pawn &&
                                    board.getSquare(location - 1).getValue() == 9 &&
                                    ((Pawn) board.getSquare(location - 1).getPiece()).isEnPessantable();

        if (rightDiagonal) {
            validMoves.add(location + 9);
        }
        if (leftDiagonal) {
            validMoves.add(location + 7);
        }
        if (rightEnPessantable) {
            //Checks if move opens up check
            Piece storePiece = board.getSquare(location + 1).getPiece();
            board.getSquare(location + 1).removePiece();
            board.getSquare(this.location).removePiece();
            board.getSquare(location + 9).addPiece(this);
            if (!board.checkForCheck(this.isWhite)) {
                validMoves.add(location + 9);
            }
            board.getSquare(location + 1).addPiece(storePiece);
            board.getSquare(this.location).addPiece(this);
            board.getSquare(location + 9).removePiece();
        }
        if (leftEnPessantable) {
            //Checks if move opens up check
            Piece storePiece = board.getSquare(location - 1).getPiece();
            board.getSquare(location - 1).removePiece();
            board.getSquare(this.location).removePiece();
            board.getSquare(location + 7).addPiece(this);
            if (!board.checkForCheck(this.isWhite)) {
                validMoves.add(location + 7);
            }
            board.getSquare(location - 1).addPiece(storePiece);
            board.getSquare(this.location).addPiece(this);
            board.getSquare(location + 7).removePiece();
        }

        return validMoves;
    }

    public ArrayList<Integer> findBlackMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();

        if (location > 7 && board.getSquare(location - 8).getValue() == 0) {
            validMoves.add(location - 8);
            //Double move forward if on starting square
            if (location > 47 && board.getSquare(location - 16).getValue() == 0) {
                validMoves.add(location - 16);
            }
        }

        //Sets up diagonal conditions beforehand to simplify if statements
        boolean rightDiagonal = inBounds(location - 7) &&
                                board.getSquare(location - 7).getValue() > 14;
        boolean leftDiagonal = inBounds(location - 9) &&
                                board.getSquare(location - 9).getValue() > 14;
        boolean rightEnPessantable = inBounds(location - 7) &&
                                    board.getSquare(location + 1).getPiece() instanceof Pawn &&
                                    board.getSquare(location + 1).getValue() == 17 &&
                                    ((Pawn) board.getSquare(location + 1).getPiece()).isEnPessantable();
        boolean leftEnPessantable = inBounds(location - 9) &&
                                    board.getSquare(location - 1).getPiece() instanceof Pawn &&
                                    board.getSquare(location - 1).getValue() == 17 &&
                                    ((Pawn) board.getSquare(location - 1).getPiece()).isEnPessantable();


        if (rightDiagonal) {
            validMoves.add(location - 7);
        } else if (leftDiagonal) {
            validMoves.add(location - 9);
        } else if (rightEnPessantable) {
            //Checks if move opens up check
            Piece storePiece = board.getSquare(location + 1).getPiece();
            board.getSquare(location + 1).removePiece();
            board.getSquare(this.location).removePiece();
            board.getSquare(location - 7).addPiece(this);
            if (!board.checkForCheck(this.isWhite)) {
                validMoves.add(location - 7);
            }
            board.getSquare(location + 1).addPiece(storePiece);
            board.getSquare(this.location).addPiece(this);
            board.getSquare(location - 7).removePiece();
        } else if (leftEnPessantable) {
            //Checks if move opens up check
            Piece storePiece = board.getSquare(location - 1).getPiece();
            board.getSquare(location - 1).removePiece();
            board.getSquare(this.location).removePiece();
            board.getSquare(location - 9).addPiece(this);
            if (!board.checkForCheck(this.isWhite)) {
                validMoves.add(location - 9);
            }
            board.getSquare(location - 1).addPiece(storePiece);
            board.getSquare(this.location).addPiece(this);
            board.getSquare(location - 9).removePiece();
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

    public boolean isEnPessantable() {
        return isEnPessantable;
    }

    public boolean moveIsPromotion(int moveLocation) {
        if ((isWhite && moveLocation > 55) || (!isWhite && moveLocation < 8)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean moveIsEnPessant (int moveLocation) {
        if (moveLocation == this.location + 9 || moveLocation == this.location - 7) {
            Piece rightPiece = board.getSquare(this.location + 1).getPiece();
            if (rightPiece instanceof Pawn && ((Pawn) rightPiece).isEnPessantable()) {
                return true;
            }
        } else if (moveLocation == this.location + 7 || moveLocation == this.location - 9) {
            Piece leftPiece = board.getSquare(this.location - 1).getPiece();
            if (leftPiece instanceof Pawn && ((Pawn) leftPiece).isEnPessantable()) {
                return true;
            }
        }
        return false;
    }

}
