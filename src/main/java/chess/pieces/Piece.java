package chess.pieces;

import chess.Board;
import chess.Game;

import java.util.ArrayList;

public abstract class Piece {
    protected int value;
    protected int location;
    protected boolean isWhite;
    //protected ArrayList<Integer> defaultMoves;

    Board board;

    public Piece(int value, int location, Board board) {
        this.value = value;
        this.location = location;
        isWhite = value > 16;
        //defaultMoves = new ArrayList<>(0);

        this.board = board;
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

    public int getValue() {
        return value;
    }

    public int getLocation() { return location; }

    public boolean isWhite() {
        return isWhite;
    }

    public void updateLocation(int location) {
        this.location = location;
    }

    public abstract ArrayList<Integer> findMoves();

    //Checks if move puts own king in check
    public ArrayList<Integer> trimMoves(ArrayList<Integer> moves) {
        ArrayList<Integer> trimMoves = new ArrayList<>();
        Piece storeMoveLocation;

        for (Integer move : moves) {
            storeMoveLocation = board.getSquare(move).getPiece();

            board.getSquare(location).removePiece();
            board.getSquare(move).addPiece(this);

            //Checking if own color is in check
            if (board.checkForCheck(isWhite) == false) {
                trimMoves.add(move);;
            }

            board.getSquare(location).addPiece(this);
            board.getSquare(move).addPiece(storeMoveLocation);
        }

        return trimMoves;
    }

    public abstract boolean isChecking();

}
