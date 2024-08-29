package chess;

import chess.pieces.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Board {
    private ArrayList<Square> squares;
    private ArrayList<Piece> boardList;

    private ArrayList<Integer> currentPossibleMoves;
    private Piece pieceClicked;
    private int clickedSquare;
    private PropertyChangeSupport propertyChangeSupport;

    public Board() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        pieceClicked = null;
        clickedSquare = 64;
        boardList = new ArrayList<>(0);
        squares = new ArrayList<>(0);
        currentPossibleMoves = new ArrayList<>(0);
    }

    public void fenToBoard(String fenString) {
        char[] fenArray = fenString.toCharArray();
        Piece newPiece = null;

        int i = 0;
        int value;

        for (char piece : fenArray) {
            switch (piece) {
                case 'p':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.PAWN.value;
                    newPiece = new Pawn(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'P':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.PAWN.value;
                    newPiece = new Pawn(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'r':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.ROOK.value;
                    newPiece = new Rook(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'R':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.ROOK.value;
                    newPiece = new Rook(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'n':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.KNIGHT.value;
                    newPiece = new Knight(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'N':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.KNIGHT.value;
                    newPiece = new Knight(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'b':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.BISHOP.value;
                    newPiece = new Bishop(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'B':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.BISHOP.value;
                    newPiece = new Bishop(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'q':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.QUEEN.value;
                    newPiece = new Queen(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'Q':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.QUEEN.value;
                    newPiece = new Queen(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'k':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.KING.value;
                    newPiece = new King(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case 'K':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.KING.value;
                    newPiece = new King(value, i);
                    boardList.add(newPiece);
                    squares.get(i).addPiece(newPiece);
                    propertyChangeSupport.firePropertyChange("valueChanged", i, value);
                    break;
                case '/':
                    i-=1;
                    break;
                default:
                    int spaces = piece - '0';
                    i-=1;
                    i += spaces;
                    for (int j = 0; j < spaces; j++) {
                        boardList.add(null);
                    }
                    break;
            }

            i++;
        }
    }

    public ArrayList<Integer> getBoardValues() {
        ArrayList<Integer> values = new ArrayList<>();
        for (Piece piece: boardList) {
            values.add(piece.getValue());
        };

        return values;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void squareClicked(int square) {
        if (pieceClicked != null) {
            piecePutDown(square);
        } else if (boardList.get(square) != null) {
            piecePickedUp(square);
        }
    }

    private boolean squareInPossibleMoves(int square) {
        for (int possibleSquare : currentPossibleMoves) {
            if (square == possibleSquare) {
                return true;
            }
        }

        return false;
    }

    public void piecePickedUp(int square) {
        currentPossibleMoves = boardList.get(square).findMoves();
        clickedSquare = square;
        pieceClicked = boardList.get(square);
        boardList.set(square, null);
        squares.get(square).removePiece();
        propertyChangeSupport.firePropertyChange("highlightSquare", square, -1);
        for (int possibleSquare : currentPossibleMoves) {
            propertyChangeSupport.firePropertyChange("highlightSquare", possibleSquare, -1);
        }
    }

    public void piecePutDown(int square) {
        if (!squareInPossibleMoves(square)) {
            return;
        }
        boardList.set(square, pieceClicked);
        boardList.get(square).updateLocation(square);
        propertyChangeSupport.firePropertyChange("valueChanged", square, (pieceClicked.getValue()+100));
        propertyChangeSupport.firePropertyChange("valueChanged", clickedSquare, 101);//Arbitrary number
        propertyChangeSupport.firePropertyChange("highlightSquare", clickedSquare, 100);
        pieceClicked = null;
        clickedSquare = 64;
        for (int possibleSquare : currentPossibleMoves) {
            propertyChangeSupport.firePropertyChange("highlightSquare", possibleSquare, 100);
        }
        currentPossibleMoves = null;
    }
}
