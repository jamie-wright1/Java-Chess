package chess;

import chess.pieces.Piece;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Player implements PropertyChangeListener {
    protected String name;
    protected boolean isWhite;
    //Each player is playing on a specific board
    protected Board board;

    Piece pieceInHand;
    ArrayList<Integer> possibleMoves;

    public Player(String name, boolean isWhite, Board board) {
        this.name = name;
        this.isWhite = isWhite;
        this.pieceInHand = null;
        this.possibleMoves = new ArrayList<>(0);
        this.board = board;
    }

    public Player() {
        this.name = "Default";
        this.isWhite = true;
        this.pieceInHand = null;
        this.possibleMoves = new ArrayList<>(0);
        this.board = null;
    }

    public void squareClicked(Square square) {
        Piece pieceClicked = square.getPiece();

        if (pieceInHand == null) {
            //Picking up own-colored piece
            if (pieceClicked != null && pieceClicked.isWhite() == this.isWhite) {
                piecePickedUp(square);
            }
        } else {
            //Attacking
            piecePutDown(square);
        }
    }

    public void piecePutDown(Square square) {
        //Checks for piece put back down where picked up
        if (square.getLocation() == pieceInHand.getLocation()) {
            board.piecePutDown(square, pieceInHand);
            pieceInHand = null;
            possibleMoves.clear();
        }

        boolean possibleMove = false;

        for (int location : possibleMoves) {
            if (square.getLocation() == location) {
                possibleMove = true;
                break;
            }
        }

        if (possibleMove) {
            board.moveMade(square, pieceInHand);
            pieceInHand = null;
            possibleMoves.clear();
        }

        return;
    }

    public void piecePickedUp(Square square) {
        pieceInHand = square.getPiece();
        possibleMoves.clear();
        possibleMoves = pieceInHand.findMoves();

        board.piecePickedUp(square, possibleMoves);

        return;
    }

    public void resign() {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        int oldValue = (Integer) evt.getOldValue();
        int newValue = (Integer) evt.getNewValue();

        if (propertyName.equals("piecePickedUp")) {

        }
    }
}
