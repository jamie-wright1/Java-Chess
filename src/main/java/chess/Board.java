package chess;

import chess.pieces.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;


public class Board {
    private ArrayList<Square> squares;
    private PropertyChangeSupport propertyChangeSupport;


    public Board() {
        propertyChangeSupport = new PropertyChangeSupport(this);

        squares = new ArrayList<>(0);
        for (int i = 0; i < 64; i++) {
            squares.add(new Square(i));
        }
    }

    public void fenToBoard(String fenString) {
        char[] fenArray = fenString.toCharArray();
        Piece newPiece = null;

        ArrayList<Integer> emptyBoard = getBoardList();

        int i = 56, j = 0;
        int value = 0;
        boolean isPiece;

        //Reset board
        for (Square square : squares) {
            square.removePiece();
        }

        for (char piece : fenArray) {
            isPiece = true;

            switch (piece) {
                case 'P':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.PAWN.value;
                    newPiece = new Pawn(value, i, this);
                    break;
                case 'p':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.PAWN.value;
                    newPiece = new Pawn(value, i, this);
                    break;
                case 'R':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.ROOK.value;
                    newPiece = new Rook(value, i, this);
                    break;
                case 'r':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.ROOK.value;
                    newPiece = new Rook(value, i, this);
                    break;
                case 'N':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.KNIGHT.value;
                    newPiece = new Knight(value, i, this);
                    break;
                case 'n':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.KNIGHT.value;
                    newPiece = new Knight(value, i, this);
                    break;
                case 'B':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.BISHOP.value;
                    newPiece = new Bishop(value, i, this);
                    break;
                case 'b':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.BISHOP.value;
                    newPiece = new Bishop(value, i, this);
                    break;
                case 'Q':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.QUEEN.value;
                    newPiece = new Queen(value, i, this);
                    break;
                case 'q':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.QUEEN.value;
                    newPiece = new Queen(value, i, this);
                    break;
                case 'K':
                    value = Piece.ColorValue.WHITE.value | Piece.PieceValue.KING.value;
                    newPiece = new King(value, i, this);
                    break;
                case 'k':
                    value = Piece.ColorValue.BLACK.value | Piece.PieceValue.KING.value;
                    newPiece = new King(value, i, this);
                    break;
                case '/':
                    isPiece = false;
                    i-=1;
                    j-=1;
                    break;
                default:
                    isPiece = false;
                    int spaces = piece - '0';
                    i-=1;
                    j-=1;
                    i += spaces;
                    j+= spaces;
                    break;
            }

            if (isPiece) {
                squares.get(i).addPiece(newPiece);
            }

            i++;
            j++;

            if (j >= 8) {
                i -= j + 8;
                j = 0;
            }
        }

        ArrayList<Integer> filledBoard = getBoardList();
        propertyChangeSupport.firePropertyChange("boardUpdated", emptyBoard, filledBoard);
    }

    public Square getSquare(int location) {
        return squares.get(location);
    }

    public ArrayList<Square> getSquares() { return squares; }

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
        propertyChangeSupport.firePropertyChange("moveMade", oldList, newList);
        propertyChangeSupport.firePropertyChange("turnOver", oldList, newList);
    }

    //Returns piece in case of promotion
    public Piece specialRules(Square square, Piece piece) {
        int moveLocation = square.getLocation();
        int pieceLocation = piece.getLocation();

        //Checks enPessant capture
        if (piece instanceof  Pawn) {
            if (moveLocation == pieceLocation + 9 || moveLocation == pieceLocation - 7) {
                Piece rightPiece = squares.get(pieceLocation + 1).getPiece();
                if (rightPiece instanceof Pawn && ((Pawn) rightPiece).isEnPessantable()) {
                    squares.get(pieceLocation + 1).removePiece();
                }
            } else if (moveLocation == pieceLocation + 7 || moveLocation == pieceLocation - 9) {
                Piece leftPiece = squares.get(pieceLocation - 1).getPiece();
                if (leftPiece instanceof Pawn && ((Pawn) leftPiece).isEnPessantable()) {
                    squares.get(pieceLocation - 1).removePiece();
                }
            }
        }

        // Resets enPessants
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
        if (piece instanceof King) {
            if (moveLocation == pieceLocation + 2) {
                squares.get(pieceLocation + 1).addPiece(squares.get(pieceLocation + 3).getPiece());
                squares.get(pieceLocation + 1).getPiece().updateLocation(pieceLocation + 1);
                squares.get(pieceLocation + 3).removePiece();
            } else if (moveLocation == pieceLocation - 2) {
                squares.get(pieceLocation - 1).addPiece(squares.get(pieceLocation - 4).getPiece());
                squares.get(pieceLocation - 1).getPiece().updateLocation(pieceLocation - 1);
                squares.get(pieceLocation - 4).removePiece();
            }
        }


        //Resets castleability
        if (piece instanceof Rook) {
            ((Rook) piece).setCastleable(false);
        } else if (piece instanceof King) {
            ((King) piece).setCastleable(false);
        }

        //Promotion
        if(piece instanceof Pawn && (square.getLocation() > 55 || square.getLocation() < 8)) {
            int newValue;

            if (piece.isWhite()) {
                newValue = Piece.ColorValue.WHITE.value | Piece.PieceValue.QUEEN.value;
            } else {
                newValue = Piece.ColorValue.BLACK.value | Piece.PieceValue.QUEEN.value;
            }

            piece = new Queen(newValue, piece.getLocation(), this, true);
        }

        return piece;
    }

    public King getKing(boolean isWhite) {
        int kingValue;

        if (isWhite) {
            kingValue = 22;
        } else {
            kingValue = 14;
        }

        for (int i = 0; i < 64; i++) {
            if (squares.get(i).getValue() == kingValue) {
                return (King) squares.get(i).getPiece();
            }
        }

        return null;
    }

    public ArrayList<ArrayList<Integer>> findMoves(boolean white) {
        ArrayList<Integer> validLocations = new ArrayList<>();
        ArrayList<ArrayList<Integer>> validMoves = new ArrayList<>();

        for (Integer square : getColor(white)) {
            Piece currentPiece = getSquare(square).getPiece();

            validLocations= currentPiece.findMoves();

            for (Integer location : validLocations) {
                validMoves.add(new ArrayList<Integer>(Arrays.asList(square, location)));
            }
        }

        return validMoves;
    }

    public ArrayList<Integer> findPins(boolean white) {
        ArrayList<Integer> pins = new ArrayList<>();


        for (Integer location : getColor(white)) {
            Square square = getSquare(location);

            if (square.getPiece() instanceof Rook) {
                pins.addAll(rookPins(square));
            } else if (square.getPiece() instanceof Bishop) {
                pins.addAll(bishopPins(square));
            } else if (square.getPiece() instanceof Queen) {
                pins.addAll(queenPins(square));
            }
        }

        return pins;
    }

   /* public ArrayList<Integer> possibleAttacksOnKing(boolean color) {
        ArrayList<Integer> ultraVision = new ArrayList<>();

        int i = 0;
        for (Square squares : getSquares()) {
            int square = squares.getValue();

            if ((square > 0 && square < 16 && color) || (square > 16 && color)) {
                ArrayList<Integer> pieceVision = PieceSight(square, i, this, true);

                pieceVision = [move.movePosition for move in pieceVision];

                if (!pieceVision.isEmpty()) {
                    ultraVision.addAll(pieceVision);

                    return ultraVision;
                }
            }
        }
    } */

    public ArrayList<Integer> rookPins(Square square) {
        ArrayList<Integer> pins = new ArrayList<>();
        int[] directions = {1, -1, 8, -8};
        int[] distances = ((Rook) square.getPiece()).distanceToEdges();

        int i = 0;
        for (Integer direction : directions) {
            ArrayList<Integer> movesSet = new ArrayList<>(Arrays.asList(square.getLocation()));
            int check = square.getLocation() + direction;

            while (distances[i] > 0) {
                boolean attackingOpposite = (22 > getSquare(check).getValue() && 16 < getSquare(check).getValue() && square.getLocation() < 16);
                attackingOpposite = attackingOpposite || (getSquare(check).getValue() < 14 && square.getLocation() > 16);

                if (getSquare(check).getValue() == 0 || attackingOpposite) {
                    movesSet.add(check);
                    check += direction;
                    distances[i]--;
                    i++;
                } else if (getSquare(check).getValue() == 14 && square.getValue() > 16 || getSquare(check).getValue() == 22 && square.getValue() < 16) {
                    pins.addAll(movesSet);
                    distances[i] = 0;
                } else {
                    distances[i] = 0;
                }
            }
        }

        return pins;
    }

    public ArrayList<Integer> bishopPins(Square square) {
        ArrayList<Integer> pins = new ArrayList<>();
        int[] directions = {7, -7, 9, -9};
        int[] distances = ((Bishop) square.getPiece()).distanceToEdges();

        int i = 0;
        for (Integer direction : directions) {
            ArrayList<Integer> movesSet = new ArrayList<>(Arrays.asList(square.getLocation()));
            int check = square.getLocation() + direction;

            while (distances[i] > 0) {
                boolean attackingOpposite = (22 > getSquare(check).getValue() && 16 < getSquare(check).getValue() && square.getLocation() < 16);
                attackingOpposite = attackingOpposite || (getSquare(check).getValue() < 14 && square.getLocation() > 16);

                if (getSquare(check).getValue() == 0 || attackingOpposite) {
                    movesSet.add(check);
                    check += direction;
                    distances[i]--;
                    i++;
                } else if (getSquare(check).getValue() == 14 && square.getValue() > 16 || getSquare(check).getValue() == 22 && square.getValue() < 16) {
                    pins.addAll(movesSet);
                    distances[i] = 0;
                } else {
                    distances[i] = 0;
                }
            }
        }

        return pins;
    }

    public ArrayList<Integer> queenPins(Square square) {
        ArrayList<Integer> pins = new ArrayList<>();
        int[] directions = {1, -1, 8, -8, 7, -7, 9, -9};
        int[] distances = ((Queen) square.getPiece()).distanceToEdges();

        int i = 0;
        for (Integer direction : directions) {
            ArrayList<Integer> movesSet = new ArrayList<>(Arrays.asList(square.getLocation()));
            int check = square.getLocation() + direction;

            while (distances[i] > 0) {
                boolean attackingOpposite = (22 > getSquare(check).getValue() && 16 < getSquare(check).getValue() && square.getLocation() < 16);
                attackingOpposite = attackingOpposite || (getSquare(check).getValue() < 14 && square.getLocation() > 16);

                if (getSquare(check).getValue() == 0 || attackingOpposite) {
                    movesSet.add(check);
                    check += direction;
                    distances[i]--;
                    i++;
                } else if (getSquare(check).getValue() == 14 && square.getValue() > 16 || getSquare(check).getValue() == 22 && square.getValue() < 16) {
                    pins.addAll(movesSet);
                    distances[i] = 0;
                } else {
                    distances[i] = 0;
                }
            }
        }

        return pins;
    }

    //Checks if parameter color is UNDER check
    public boolean checkForCheck(boolean checkForWhite) {
        ArrayList<Integer> colorSquares = this.getColor(!checkForWhite);

        for (Integer square: colorSquares) {
            if (this.getSquare(square).getPiece().isChecking()) {
                return true;
            }
        }

        return false;
    }

    public boolean noPossibleMoves(boolean checkForWhite) {
        ArrayList<Integer> colorSquares = this.getColor(checkForWhite);
        ArrayList<Integer> totalPossibleMoves = new ArrayList<>();

        for (Integer square: colorSquares) {
            totalPossibleMoves.addAll(this.getSquare(square).getPiece().findMoves());
        }

        return totalPossibleMoves.isEmpty();
    }

    public boolean checkForMate(boolean checkForWhite) {
        ArrayList<Integer> colorSquares = this.getColor(checkForWhite);
        ArrayList<Integer> totalPossibleMoves = new ArrayList<>();

        for (Integer square: colorSquares) {
            totalPossibleMoves.addAll(this.getSquare(square).getPiece().findMoves());
        }

        if (totalPossibleMoves.isEmpty()) {
            return checkForCheck(checkForWhite);
        } else {
            return false;
        }
    }

    public String getSquareNum(int location) {
        String squareLoc = "";

        switch (location % 8) {
            case 0:
                squareLoc = "A";
                break;
            case 1:
                squareLoc = "B";
                break;
            case 2:
                squareLoc = "C";
                break;
            case 3:
                squareLoc = "D";
                break;
            case 4:
                squareLoc = "E";
                break;
            case 5:
                squareLoc = "F";
                break;
            case 6:
                squareLoc = "G";
                break;
            case 7:
                squareLoc = "H";
        }

        squareLoc += location/8;
        return squareLoc;
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
