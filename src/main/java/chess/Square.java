package chess;

import chess.pieces.Piece;

public class Square {
    private int location;
    private Piece piece;

    public Square(int location) {
        this.location = location;
        this.piece = null;
    }

    public void addPiece(Piece piece) { this.piece = piece; }

    public void removePiece() {
        this.piece = null;
    }

    public int getLocation() { return location; }

    public Piece getPiece() { return piece; }

    public Integer getValue() {
        if (piece != null) {
            return piece.getValue();
        } else {
            return 0;
        }
    }

}
