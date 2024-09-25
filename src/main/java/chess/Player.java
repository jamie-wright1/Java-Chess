package chess;

import chess.pieces.Piece;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Player implements PropertyChangeListener {
    private String name;
    private boolean isWhite;

    Piece pieceInHand;
    ArrayList<Integer> possibleMoves;

    public Player(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
        this.pieceInHand = null;
        this.possibleMoves = new ArrayList<>(0);
    }

    public void squareClicked(Square square) {
        Piece pieceClicked = square.getPiece();

        //Piece exists at clicked location
        if (pieceClicked != null) {
            //Picking up own-colored piece
            if (pieceClicked.isWhite() == this.isWhite && this.pieceInHand == null) {
                piecePickedUp(square);
            //Attacking other-colored piece
            } else if (pieceClicked.isWhite() != this.isWhite && this.pieceInHand != null) {
                piecePutDown(square);
            //Trying to pick up other-colored piece or attack own-colored piece
            } else {
                return;
            }
        //Piece doesn't exist at clicked location
        } else {
            //Placing down piece on empty square
            if (pieceInHand != null) {
                piecePutDown(square);
            //Trying to pick up at a square with no piece
            } else {
                return;
            }
        }
    }

    public void piecePutDown(Square square) {
        //Checks for piece put back down where picked up
        if (square.getLocation() == possibleMoves.get(0)) {
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
