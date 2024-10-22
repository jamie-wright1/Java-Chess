package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class AIPlayer extends Player {
    public AIPlayer() {
        super();
    }

    //Black by default
    public AIPlayer(Board board) {
        super("Bot", false, board);
    }

    public AIPlayer(Board board, boolean isWhite) {
        super("Bot", isWhite, board);
    }

    //False if no possible moves
    public boolean turn(Board board) {
        ArrayList<ArrayList<Integer>> validMoves;

        validMoves = findMoves(board, false);

        ArrayList<Integer> bestMove = minimax(validMoves, board);

        Piece movingPiece = board.getSquare(bestMove.get(0)).getPiece();
        board.piecePickedUp(board.getSquare(bestMove.get(0)), new ArrayList<Integer>(0));
        board.moveMade(board.getSquare(bestMove.get(1)), movingPiece);

        return true;
    }

    public ArrayList<ArrayList<Integer>> findMoves(Board board, boolean white) {
        ArrayList<Integer> validLocations = new ArrayList<>();
        ArrayList<ArrayList<Integer>> validMoves = new ArrayList<>();

        for (Integer square : board.getColor(white)) {
            Piece currentPiece = board.getSquare(square).getPiece();

            validLocations= currentPiece.findMoves();

            for (Integer location : validLocations) {
                validMoves.add(new ArrayList<Integer>(Arrays.asList(square, location)));
            }
        }

        return validMoves;
    }

    public ArrayList<Integer> minimax(ArrayList<ArrayList<Integer>> possibleMoves, Board board) {
        int depth = 3;

        int alpha = -999;
        int beta = 999;

        ArrayList<Integer> bestMove = null;
        possibleMoves = moveOrdering(board, possibleMoves);

        for (ArrayList<Integer> move : possibleMoves) {
            Piece taken;
            //First element represents promotion, second represents enPessant
            ArrayList<Boolean> specialMoves = new ArrayList<>(Arrays.asList(false, false));
            taken = makeMove(move, board, specialMoves);

            int v = max(board, alpha, beta, depth - 1);

            undoMove(move, board, taken, specialMoves);

            if (v <= alpha) {
                return move;
            }

            if (v < beta) {
                beta = v;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public int max(Board board, int alpha, int beta, int depth) {
        if (board.checkForMate(isWhite) || board.checkForMate(!isWhite)) {
            return calculateFavorability(board);
        }

        if (depth == 0) {
            return calculateFavorability(board);
        }

        ArrayList<ArrayList<Integer>> possibleMoves = findMoves(board, true);
        possibleMoves = moveOrdering(board, possibleMoves);

        for (ArrayList<Integer> move : possibleMoves) {
            Piece taken;
            //First element represents promotion, second represents enPessant
            ArrayList<Boolean> specialMoves = new ArrayList<>(Arrays.asList(false, false));
            taken = makeMove(move, board, specialMoves);

            int v = min(board, alpha, beta, depth - 1);

            undoMove(move, board, taken, specialMoves);

            if (v >= beta) {
                return v;
            }

            if (v > alpha) {
                alpha = v;
            }
        }

        return alpha;
    }

    public int min(Board board, int alpha, int beta, int depth) {
        if (board.checkForMate(!isWhite) || board.checkForMate(isWhite)) {
            return calculateFavorability(board);
        }

        if (depth == 0) {
            return calculateFavorability(board);
        }

        ArrayList<ArrayList<Integer>> possibleMoves = findMoves(board, false);
        possibleMoves = moveOrdering(board, possibleMoves);

        for (ArrayList<Integer> move : possibleMoves) {
            Piece taken;
            //First element represents promotion, second represents enPessant
            ArrayList<Boolean> specialMoves = new ArrayList<>(Arrays.asList(false, false));

            taken = makeMove(move, board, specialMoves);

            int v = max(board, alpha, beta, depth - 1);

            undoMove(move, board, taken, specialMoves);

            if (v <= alpha) {
                return v;
            }

            if (v < beta) {
                beta = v;
            }
        }

        return beta;
    }

    public Piece makeMove(ArrayList<Integer> move, Board board, ArrayList<Boolean> specialMoves) {
        Piece movingPiece = board.getSquare(move.get(0)).getPiece();
        Piece taken = board.getSquare(move.get(1)).getPiece();

        board.getSquare(move.get(0)).removePiece();
        board.getSquare(move.get(1)).addPiece(movingPiece);
        movingPiece.updateLocation(move.get(1));

        if (movingPiece instanceof Pawn && ((Pawn) movingPiece).moveIsEnPessant(move.get(1))) {
            if (movingPiece.isWhite()) {
                taken = board.getSquare(move.get(1) - 8).getPiece();
                board.getSquare(move.get(1) - 8).removePiece();
            } else {
                taken = board.getSquare(move.get(1) + 8).getPiece();
                board.getSquare(move.get(1) + 8).removePiece();
            }

            specialMoves.set(1, true);
        }

        if (movingPiece instanceof Pawn && ((Pawn) movingPiece).moveIsPromotion(move.get(1))) {
            if (movingPiece.isWhite()) {
                movingPiece = new Queen(21, move.get(1), board, true);
                board.getSquare(move.get(1)).addPiece(movingPiece);
            } else {
                movingPiece = new Queen(13, move.get(1), board, true);
                board.getSquare(move.get(1)).addPiece(movingPiece);
            }
            specialMoves.set(0, true);
        }

        return taken;
    }

    public void undoMove(ArrayList<Integer> move, Board board, Piece taken, ArrayList<Boolean> specialMoves) {
        Piece movingPiece = board.getSquare(move.get(1)).getPiece();

        //Put piece back
        if (specialMoves.get(0)) {
            if (movingPiece.isWhite()) {
                board.getSquare(move.get(0)).addPiece(new Pawn(17, move.get(0), board));
            } else {
                board.getSquare(move.get(0)).addPiece(new Pawn(9, move.get(0), board));
            }
        } else {
            board.getSquare(move.get(0)).addPiece(movingPiece);
        }

        //Replace taken piece
        if (specialMoves.get(1)) {
            if (movingPiece.isWhite()) {
                board.getSquare(move.get(1) - 8).addPiece(taken);
            } else {
                board.getSquare(move.get(1) + 8).addPiece(taken);
            }
        } else {
            board.getSquare(move.get(1)).addPiece(taken);
        }

        movingPiece.updateLocation(move.get(0));

        return;
    }

    public int calculateFavorability(Board board) {
        int favorability = 0;

        favorability += 10 * simpleEvaluation(board);

        favorability += towardsMiddle(board);
        /*
        favorability += parameters.kingStayPut(board)
        favorability += parameters.pawnsAdvanced(board)
        favorability += parameters.checks(board)
        */

        if (board.checkForCheck(true)) {
            favorability -= 20;
        } else if (board.checkForCheck(false)) {
            favorability += 20;
        }

        if (board.checkForMate(true)) {
            return -998;
        } else if (board.checkForMate(false)) {
            return 998;
        }

        return favorability;
    }

    public int simpleEvaluation(Board board) {
        int whiteValue = 0, blackValue = 0;

        for (Square square : board.getSquares()) {
            int value = square.getValue();

            if (value > 16) {
                whiteValue += calculatePieceValue(value);
            } else if (value > 0) {
                blackValue += calculatePieceValue(value);
            }
        }

        return whiteValue - blackValue;
    }

    public int calculatePieceValue(int value) {
        switch (value) {
            case 9:
            case 17:
                return 1;
            case 10:
            case 11:
            case 18:
            case 19:
                return 3;
            case 12:
            case 20:
                return 5;
            case 13:
            case 21:
                return 9;
            default:
                return 0;
        }
    }

    public int towardsMiddle(Board board) {
        int favorability = 0;

        for (Square square : board.getSquares()) {
            if (square.getLocation() % 8 > 2 && square.getLocation() % 8 < 6 && square.getValue() > 14) {
                favorability += 5;
            } else if (square.getLocation() % 8 > 2 && square.getLocation() % 8 < 6 && square.getValue() > 0) {
                favorability -= 5;
            }
        }

        return favorability;
    }

    public ArrayList<ArrayList<Integer>> moveOrdering(Board board, ArrayList<ArrayList<Integer>> possibleMoves) {
        for (ArrayList<Integer> move : possibleMoves) {
            int pieceMoving = board.getSquare(move.get(0)).getValue();
            int pieceTaken = board.getSquare(move.get(1)).getValue();

            if (pieceTaken == 0) {
                move.add(0);
            } else {
                move.add(calculatePieceValue(pieceTaken) - calculatePieceValue(pieceMoving));
            }
        }

        Collections.sort(possibleMoves, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o2.get(2).compareTo(o1.get(2));
            }
        });


        for (ArrayList<Integer> move : possibleMoves) {
            move.remove(2);
        }

        return possibleMoves;
    }

    public void tempPrint(Board board) {
        for (int i = 0; i < 64; i++) {
            if (board.getSquare(i).getValue() < 10) {
                System.out.print(0);
            }
            System.out.print(board.getSquare(i).getValue() + " ");
            if (i % 8 == 7) {
                System.out.println();
            }
        }
        System.out.println("\n");
    }
}

