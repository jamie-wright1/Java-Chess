package chess;

import chess.pieces.Piece;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Player implements PropertyChangeListener {
    private String name;
    private boolean isWhite;
    private boolean isInCheck;

    Piece pieceInHand;
    ArrayList<Integer> possibleMoves;

    public Player(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
        this.isInCheck = false;
        this.pieceInHand = null;
        this.possibleMoves = new ArrayList<>(0);
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
            Board.getInstance().piecePutDown(square, pieceInHand);
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
            Board.getInstance().moveMade(square, pieceInHand);
            pieceInHand = null;
            possibleMoves.clear();
        }

        return;
    }

    public void piecePickedUp(Square square) {
        pieceInHand = square.getPiece();
        possibleMoves.clear();
        possibleMoves = pieceInHand.findMoves();

        Board.getInstance().piecePickedUp(square, possibleMoves);

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
