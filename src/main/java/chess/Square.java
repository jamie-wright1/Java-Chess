package chess;

import chess.pieces.Piece;

public class Square {
    private int location;
    private Piece piece;

    public Square(int location) {
        this.location = location;
        this.piece = null;
    }

    public void addPiece(Piece piece) {
        this.piece = piece;

    }

    public void removePiece() {
        this.piece = null;
    }

    public String returnID() {
        if (piece == null) {
            return Integer.toString(this.location);
        } else {
            return Integer.toString(this.location) + Integer.toString(piece.getValue());
        }
    }
}
