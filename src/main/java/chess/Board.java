package chess;

import chess.pieces.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


//Singleton board storing data for squares and their pieces
public class Board {
    private static Board board;

    private ArrayList<Square> squares;
    private PropertyChangeSupport propertyChangeSupport;

    private Board() {
        propertyChangeSupport = new PropertyChangeSupport(this);

        squares = new ArrayList<>(0);
        for (int i = 0; i < 64; i++) {
            squares.add(new Square(i));
        }

    }

    public static Board getInstance() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    public void fenToBoard(String fenString) {
        char[] fenArray = fenString.toCharArray();
        Piece newPiece = null;
        ArrayList<Integer> emptyBoard = getBoardList();

        int i = 0;
        int value = 0;
        boolean isPiece;

        for (char piece : fenArray) {
            isPiece = true;

            switch (piece) {
                case 'p':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.PAWN.value;
                    newPiece = new Pawn(value, i);
                    break;
                case 'P':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.PAWN.value;
                    newPiece = new Pawn(value, i);
                    break;
                case 'r':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.ROOK.value;
                    newPiece = new Rook(value, i);
                    break;
                case 'R':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.ROOK.value;
                    newPiece = new Rook(value, i);
                    break;
                case 'n':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.KNIGHT.value;
                    newPiece = new Knight(value, i);
                    break;
                case 'N':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.KNIGHT.value;
                    newPiece = new Knight(value, i);
                    break;
                case 'b':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.BISHOP.value;
                    newPiece = new Bishop(value, i);
                    break;
                case 'B':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.BISHOP.value;
                    newPiece = new Bishop(value, i);
                    break;
                case 'q':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.QUEEN.value;
                    newPiece = new Queen(value, i);
                    break;
                case 'Q':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.QUEEN.value;
                    newPiece = new Queen(value, i);
                    break;
                case 'k':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.KING.value;
                    newPiece = new King(value, i);
                    break;
                case 'K':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.KING.value;
                    newPiece = new King(value, i);
                    break;
                case '/':
                    isPiece = false;
                    i-=1;
                    break;
                default:
                    isPiece = false;
                    int spaces = piece - '0';
                    i-=1;
                    i += spaces;
                    break;
            }

            if (isPiece) {
                squares.get(i).addPiece(newPiece);
            }

            i++;
        }

        ArrayList<Integer> filledBoard = getBoardList();
        propertyChangeSupport.firePropertyChange("boardUpdated", emptyBoard, filledBoard);
    }

    public Square getSquare(int location) {
        return squares.get(location);
    }

    public ArrayList<Integer> getColor (boolean getWhite) {
        ArrayList<Integer> color = new ArrayList<>();

        for (Square square : squares) {
            int squareValue = square.getValue();

            if (squareValue > 14 && getWhite) {
                color.add(square.getLocation());
            } else if (squareValue < 15 && squareValue > 0 && !getWhite) {
                color.add(square.getLocation());
            }
        }

        return color;
    }

    public ArrayList<Integer> getBoardList() {
        ArrayList<Integer> list = new ArrayList<>();

        for (Square square : squares) {
            list.add(square.getValue());
        }

        return list;
    }

    public void piecePickedUp(Square square, ArrayList<Integer> possibleMoves) {
        ArrayList<Integer> oldList = getBoardList();

        square.removePiece();

        ArrayList<Integer> newList = getBoardList();

        propertyChangeSupport.firePropertyChange("newHighlights", square, possibleMoves);
        propertyChangeSupport.firePropertyChange("boardUpdated", oldList, newList);
    }

    //Piece put back down in same spot
    public void piecePutDown(Square square, Piece piece) {
        ArrayList<Integer> oldList = getBoardList();

        square.addPiece(piece);

        ArrayList<Integer> newList = getBoardList();

        propertyChangeSupport.firePropertyChange("boardUpdated", oldList, newList);
        propertyChangeSupport.firePropertyChange("unHighlight", oldList, newList);
    }

    public void moveMade(Square square, Piece piece) {
        ArrayList<Integer> oldList = getBoardList();

        //Takes care of enPessant/Castling/Promotion things
        piece = specialRules(square, piece);

        square.removePiece();
        square.addPiece(piece);
        piece.updateLocation(square.getLocation());

        ArrayList<Integer> newList = getBoardList();

        propertyChangeSupport.firePropertyChange("boardUpdated", oldList, newList);
        propertyChangeSupport.firePropertyChange("turnOver", oldList, newList);
    }

    //Returns piece in case of promotion
    public Piece specialRules(Square square, Piece piece) {
        //Resets enPessants
        for (Square individualSquare : squares) {
            if (individualSquare.getPiece() instanceof Pawn) {
                ((Pawn) individualSquare.getPiece()).setEnPessantable(false);
            }
        }

        //Sets enPessant if conditions met
        if (piece instanceof Pawn && (square.getLocation() == piece.getLocation() + 16 || square.getLocation() == piece.getLocation() - 16)) {
            ((Pawn) piece).setEnPessantable(true);
        }

        //Castleability


        //Promotion
        if(piece instanceof Pawn && (square.getLocation() > 55 || square.getLocation() < 8)) {
            int newValue;

            if (piece.isWhite()) {
                newValue = Piece.ColorValue.WHITE.value | Piece.PieceValue.QUEEN.value;
            } else {
                newValue = Piece.ColorValue.BLACK.value | Piece.PieceValue.QUEEN.value;
            }

            piece = new Queen(newValue, piece.getLocation());
        }

        return piece;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
