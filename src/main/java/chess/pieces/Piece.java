package chess.pieces;

import java.util.ArrayList;

public abstract class Piece {
    protected int value;
    protected int location;
    protected boolean isWhite;
    //protected ArrayList<Integer> defaultMoves;

    public Piece(int value, int location) {
        this.value = value;
        this.location = location;
        isWhite = value > 16;
        //defaultMoves = new ArrayList<>(0);
    }

    public enum PieceValue {
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING;

        public final int value = 1 + ordinal();
    };

    public enum ColorValue {
        BLACK,
        WHITE;

        public final int value = 8 * (1 + ordinal());
    }

    static class Move {
        private final int startLocation;
        private final int moveLocation;

        public Move(int startLocation, int moveLocation) {
            this.startLocation = startLocation;
            this.moveLocation = moveLocation;
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void updateLocation(int location) {
        this.location = location;
    }

    public abstract ArrayList<Integer> findMoves();

    public boolean checkValidMove (int pieceLocation, int moveLocation) {
        return false;
    };

}
