package chess.pieces;

import chess.Board;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();
        validMoves.add(location);

        int[] directions = {7, 9, -7, -9};
        int[] distances = distanceToEdges();

        int i = 0;
        for (int movement : directions) {
            int check = location + movement;

            while (distances[i] > 0) {
                //Attacking own color
                if (checkAttackSquare(this.location, check, true)) {
                    break;
                //Attacking other color
                } else if (checkAttackSquare(this.location, check, false)) {
                    validMoves.add(check);
                    break;
                //Placed on empty square
                } else {
                    validMoves.add(check);
                }

                check += movement;
                distances[i] -= 1;
            }

            i++;
        }

        return validMoves;
    }

    //Returns whether attacking color of choice (same color if boolean true, other color if false)
    public boolean checkAttackSquare(int pieceLocation, int moveLocation, boolean sameColor) {
        //Protects cases where square is empty from null reference
        if (Board.getInstance().getSquare(moveLocation).getValue() == 0) {
            return false;
        //Checks for attacks on same color if sameColor true, otherwise other color
        } else if (this.isWhite == Board.getInstance().getSquare(moveLocation).getPiece().isWhite) {
            return sameColor;
        } else {
            return !sameColor;
        }
    }

    public int[] distanceToEdges() {
        int right = 7 - location % 8;
        int left = location % 8;
        int up = 7 - location/8;
        int down = location/8;

        return new int[] {Math.min(up, left), Math.min(up, right), Math.min(down, right), Math.min(down, left)};
    }
}
