package JavaChess;

import chess.*;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;


public class AITest {

    @Test
    public void makeMoveTest() {
        Board board = new Board();
        board.fenToBoard("8/2p5/8/8/8/8/8/8");
        AIPlayer ai = new AIPlayer(board);

        ArrayList<Integer> move = new ArrayList<>(Arrays.asList(50, 42));
        ArrayList<Boolean> specialMoves = new ArrayList<>(Arrays.asList(false, false));

        Piece movingPiece = board.getSquare(move.get(0)).getPiece();

        ai.makeMove(move, board, specialMoves);

        assertNull(board.getSquare(50).getPiece());
        assertSame(board.getSquare(42).getPiece(), movingPiece);
    }

    @Test
    public void undoMoveTest() {
        Board board = new Board();
        board.fenToBoard("8/8/2p5/8/8/8/8/8");
        AIPlayer ai = new AIPlayer(board);

        ArrayList<Integer> move = new ArrayList<>(Arrays.asList(50, 42));
        ArrayList<Boolean> specialMoves = new ArrayList<>(Arrays.asList(false, false));

        Piece movingPiece = board.getSquare(move.get(1)).getPiece();

        board.getSquare(50).removePiece();
        board.getSquare(42).addPiece(movingPiece);

        ai.undoMove(move, board, null, specialMoves);

        assertNull(board.getSquare(42).getPiece());
        assertEquals(board.getSquare(50).getPiece(), movingPiece);
    }

    @Test
    public void moveOrderingTest() {
        Board board = new Board();
        board.fenToBoard("8/8/8/8/8/8/NpBQ4/pQNp4");
        AIPlayer ai = new AIPlayer(board);

        ArrayList<Integer> move1 = new ArrayList<>(Arrays.asList(0, 8));
        ArrayList<Integer> move2 = new ArrayList<>(Arrays.asList(1, 9));
        ArrayList<Integer> move3 = new ArrayList<>(Arrays.asList(2, 10));
        ArrayList<Integer> move4 = new ArrayList<>(Arrays.asList(3, 11));
        ArrayList<Integer> move5 = new ArrayList<>(Arrays.asList(3, 12));

        ArrayList<ArrayList<Integer>> moves = new ArrayList<>(Arrays.asList(move1, move2, move3, move4, move5));

        moves = ai.moveOrdering(board, moves);

        ArrayList<ArrayList<Integer>> expectedOrdering = new ArrayList<>(Arrays.asList(move4, move1, move3, move5, move2));

        assertEquals(expectedOrdering, moves);
    }

    @Test
    public void pieceValueTest() {
        AIPlayer ai = new AIPlayer();

        ArrayList<Integer> pieces = new ArrayList<>(Arrays.asList(0, 9, 14, 13, 17, 19, 12));
        ArrayList<Integer> expectedValues = new ArrayList<>(Arrays.asList(0, 1, 0, 9, 1, 3, 5));
        ArrayList<Integer> actualValues = new ArrayList<>();

        for (int i = 0; i < pieces.size(); i++) {
            actualValues.add(ai.calculatePieceValue(pieces.get(i)));
        }

        assertEquals(expectedValues, actualValues);
    }

    @Test
    public void simpleEvaluationTest() {
        Board board = new Board();
        AIPlayer ai = new AIPlayer();

        //Default board
        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        assertEquals(0, ai.simpleEvaluation(board));


        board.fenToBoard("8/8/7k/7K/8/8/NpBQ4/pQNp4");
        assertEquals(24, ai.simpleEvaluation(board));

        board.fenToBoard("8/8/kpq5/8/8/8/8/8");
        assertEquals(-10, ai.simpleEvaluation(board));

        board.fenToBoard("");
        assertEquals(0, ai.simpleEvaluation(board));
    }

    @Test
    public void findMovesTest() {
        Board board = new Board();
        AIPlayer ai = new AIPlayer();

        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        ArrayList<ArrayList<Integer>> moves = board.findMoves(true);
        assertEquals(20, moves.size());
        moves = board.findMoves(false);
        assertEquals(20, moves.size());

        board.fenToBoard("3k4/8/1B4N1/8/5b2/8/2K5/8");
        moves = board.findMoves(true);
        assertEquals(21, moves.size());
        moves = board.findMoves(false);
        assertEquals(4, moves.size());
    }



}
