package chess;

import chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class SquareView extends JButton {
    int location;



    public SquareView(int row, int column, int squareType) {
        location = 8 * row + column;

        setBorder(null);

        if ((row + column) % 2 == 0) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(Color.GRAY);
        }

        setIcon(squareType);
    }

    public ImageIcon pieceDisplay(int value) {
        ImageIcon piece;

        switch (value) {
            case 9:
                piece = new ImageIcon("src/chessPieces/blackPieces/blackPawn.png");
                break;
            case 10:
                piece = new ImageIcon("src/chessPieces/blackPieces/blackKnight.png");
                break;
            case 11:
                piece = new ImageIcon("src/chessPieces/blackPieces/blackBishop.png");
                break;
            case 12:
                piece = new ImageIcon("src/chessPieces/blackPieces/blackRook.png");
                break;
            case 13:
                piece = new ImageIcon("src/chessPieces/blackPieces/blackQueen.png");
                break;
            case 14:
                piece = new ImageIcon("src/chessPieces/blackPieces/blackKing.png");
                break;
            case 17:
                piece = new ImageIcon("src/chessPieces/whitePieces/whitePawn.png");
                break;
            case 18:
                piece = new ImageIcon("src/chessPieces/whitePieces/whiteKnight.png");
                break;
            case 19:
                piece = new ImageIcon("src/chessPieces/whitePieces/whiteBishop.png");
                break;
            case 20:
                piece = new ImageIcon("src/chessPieces/whitePieces/whiteRook.png");
                break;
            case 21:
                piece = new ImageIcon("src/chessPieces/whitePieces/whiteQueen.png");
                break;
            case 22:
                piece = new ImageIcon("src/chessPieces/whitePieces/whiteKing.png");
                break;
            default:
                piece = new ImageIcon();
                return piece;
        }

        Image pieceResizable = piece.getImage();

        return new ImageIcon(pieceResizable.getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    }

    public void setIcon(Integer squareType) {
        super.setIcon(pieceDisplay(squareType));
    }

}
