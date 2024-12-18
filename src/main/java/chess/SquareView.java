package chess;

import chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SquareView extends JButton {
    int location;

    public SquareView(int row, int column, int squareType) {
        location = 8 * row + column;

        setBorder(null);

        if ((row + column) % 2 == 0) {
            setBackground(new Color(255, 211, 138).darker());
        } else {
            setBackground(new Color(143, 95, 36).darker());
        }

        setIcon(squareType);
    }

    public ImageIcon pieceDisplay(int value) {
        ImageIcon piece;

        switch (value) {
            case 9:
                piece = new ImageIcon("src/chessPieceImages/blackPieces/blackPawn.png");
                break;
            case 10:
                piece = new ImageIcon("src/chessPieceImages/blackPieces/blackKnight.png");
                break;
            case 11:
                piece = new ImageIcon("src/chessPieceImages/blackPieces/blackBishop.png");
                break;
            case 12:
                piece = new ImageIcon("src/chessPieceImages/blackPieces/blackRook.png");
                break;
            case 13:
                piece = new ImageIcon("src/chessPieceImages/blackPieces/blackQueen.png");
                break;
            case 14:
                piece = new ImageIcon("src/chessPieceImages/blackPieces/blackKing.png");
                break;
            case 17:
                piece = new ImageIcon("src/chessPieceImages/whitePieces/whitePawn.png");
                break;
            case 18:
                piece = new ImageIcon("src/chessPieceImages/whitePieces/whiteKnight.png");
                break;
            case 19:
                piece = new ImageIcon("src/chessPieceImages/whitePieces/whiteBishop.png");
                break;
            case 20:
                piece = new ImageIcon("src/chessPieceImages/whitePieces/whiteRook.png");
                break;
            case 21:
                piece = new ImageIcon("src/chessPieceImages/whitePieces/whiteQueen.png");
                break;
            case 22:
                piece = new ImageIcon("src/chessPieceImages/whitePieces/whiteKing.png");
                break;
            default:
                return null;
        }

        Image pieceResizable = piece.getImage();

        return new ImageIcon(pieceResizable.getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    }

    public void setIcon(Integer squareType) {
        super.setIcon(pieceDisplay(squareType));
    }

    public void highlight() {
        JPanel highlight = new JPanel();
        highlight.setBackground(new Color(200, 170, 230, 120));
        //this.setForeground(new Color(200, 170, 230, 120));

        this.add(highlight);

        this.repaint();
    }

    public void unHighlight() {
        //this.setForeground(null);
        this.removeAll();
        this.repaint();
    }

}
