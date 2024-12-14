package chess;

import chess.Database.SQLConnection;
import chess.pieces.Piece;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args) throws SQLException, ClassNotFoundException, IOException {
        SQLConnection connection = SQLConnection.getInstance();
        Game game = new Game();

        /*
        Board board = new Board();
        AIPlayer whitePlayer = new AIPlayer();
        ArrayList<Boolean> whiteSpecials = new ArrayList<>(Arrays.asList(false, false));
        AIPlayer blackPlayer = new AIPlayer();
        ArrayList<Boolean> blackSpecials = new ArrayList<>(Arrays.asList(false, false));

        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        //Game chessGame = new Game();
        System.out.println(moveGenerationTest(true, 1, whitePlayer, blackPlayer, board, whiteSpecials, blackSpecials));
        System.out.println(moveGenerationTest(true, 2, whitePlayer, blackPlayer, board, whiteSpecials, blackSpecials));
        System.out.println(moveGenerationTest(true, 3, whitePlayer, blackPlayer, board, whiteSpecials, blackSpecials));
        System.out.println(moveGenerationTest(true, 4, whitePlayer, blackPlayer, board, whiteSpecials, blackSpecials));
        System.out.println(moveGenerationTest(true, 5, whitePlayer, blackPlayer, board, whiteSpecials, blackSpecials));
        System.out.println(moveGenerationTest(true, 6, whitePlayer, blackPlayer, board, whiteSpecials, blackSpecials));
        */
    }


    public static long moveGenerationTest(boolean isWhite, int depth, AIPlayer whitePlayer, AIPlayer blackPlayer, Board board,
    ArrayList<Boolean> whiteSpecials, ArrayList<Boolean> blackSpecials) {
        if (depth == 0) {
            return 1;
        }

        ArrayList<ArrayList<Integer>> moves = board.findMoves(isWhite);
        long positions = 0;

        for (ArrayList<Integer> move : moves) {
            if (isWhite) {
                Piece taken = whitePlayer.makeMove(move, board, whiteSpecials);
                positions += moveGenerationTest(!isWhite, depth - 1, whitePlayer, blackPlayer, board, whiteSpecials, blackSpecials);
                whitePlayer.undoMove(move, board, taken, whiteSpecials);
            } else {
                Piece taken = blackPlayer.makeMove(move, board, blackSpecials);
                positions+= moveGenerationTest(!isWhite, depth - 1, blackPlayer, whitePlayer, board, whiteSpecials, blackSpecials);
                blackPlayer.undoMove(move, board, taken, blackSpecials);
            }
        }

        return positions;
    }
}
