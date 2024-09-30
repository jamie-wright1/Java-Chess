package chess.pieces;

import chess.Board;

import java.util.ArrayList;

public class Rook extends Piece {
    boolean isCastleable;
    final int[] directions = {1, -1, 8, -8};

    public Rook(int value, int location) {
        super(value, location);
    }

    @Override
    public ArrayList<Integer> findMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();

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
                distances[i]--;
            }

            i++;
        }

        return trimMoves(validMoves);
    }

    @Override
    public boolean isChecking() {
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
                    if (isWhite && Board.getInstance().getSquare(check).getValue() == 14) {
                        return true;
                    } else if (!isWhite && Board.getInstance().getSquare(check).getValue() == 22) {
                        return true;
                    } else {
                        break;
                    }
                }

                //Placed on empty square
                check += movement;
                distances[i]--;
            }

            i++;
        }
        return false;
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

        return new int[] {right, left, up, down};
    }
}
