package JavaChess;

import chess.*;
import chess.pieces.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class PieceTest {
    @Test
    public void findKingMoves() {
        Board board = new Board();
        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        ArrayList<Integer> moves = board.getSquare(5).getPiece().findMoves();
        ArrayList<Integer> expectedMoves = new ArrayList<>();
        assertEquals(expectedMoves, moves);

        board.fenToBoard("1k2r3/8/8/8/8/8/8/3K1q1Q");

        moves = board.getSquare(3).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(10, 11));
        assertEquals(expectedMoves, moves);

        moves = board.getSquare(57).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(48, 50, 58));
        assertEquals(expectedMoves, moves);
    }

    @Test
    public void findQueenMoves() {
        Board board = new Board();
        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        ArrayList<Integer> moves = board.getSquare(4).getPiece().findMoves();
        ArrayList<Integer> expectedMoves = new ArrayList<>();
        assertEquals(expectedMoves, moves);

        board.fenToBoard("1k2r3/6R1/5pqp/6PP/8/3Q4/2K5/8");

        moves = board.getSquare(19).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(28, 37, 46));
        assertEquals(expectedMoves, moves);

        moves = board.getSquare(46).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(19, 28, 37, 38, 39, 53, 54, 55));
        assertEquals(expectedMoves, moves);
    }

    @Test
    public void findRookMoves() {
        Board board = new Board();
        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        ArrayList<Integer> moves = board.getSquare(0).getPiece().findMoves();
        ArrayList<Integer> expectedMoves = new ArrayList<>();
        assertEquals(expectedMoves, moves);

        board.fenToBoard("6k1/1r6/5pRp/6PP/8/8/2K5/8");

        moves = board.getSquare(46).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(45, 47, 54, 62));
        assertEquals(expectedMoves, moves);

        moves = board.getSquare(49).getPiece().findMoves();
        expectedMoves = new ArrayList<>(Arrays.asList(54));
        assertEquals(expectedMoves, moves);
    }

    @Test
    public void findBishopMoves() {
        Board board = new Board();
        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        ArrayList<Integer> moves = board.getSquare(2).getPiece().findMoves();
        ArrayList<Integer> expectedMoves = new ArrayList<>();
        assertEquals(expectedMoves, moves);

        board.fenToBoard("4k3/8/5pBp/3b1P1P/8/8/2K5/8");

        moves = board.getSquare(35).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(53));
        assertEquals(expectedMoves, moves);

        moves = board.getSquare(46).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(53, 55, 60));
        assertEquals(expectedMoves, moves);
    }

    @Test
    public void findKnightMoves() {
        Board board = new Board();
        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        ArrayList<Integer> moves = board.getSquare(1).getPiece().findMoves();
        Collections.sort(moves);
        ArrayList<Integer> expectedMoves = new ArrayList<>(Arrays.asList(16, 18));
        assertEquals(expectedMoves, moves);

        board.fenToBoard("3k4/4p3/1B4Np/3nP2P/8/8/2K5/8");

        moves = board.getSquare(35).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(41, 50));
        assertEquals(expectedMoves, moves);

        moves = board.getSquare(46).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(29, 31, 52, 61, 63));
        assertEquals(expectedMoves, moves);
    }

    @Test
    public void findPawnMoves() {
        Board board = new Board();
        board.fenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        ArrayList<Integer> moves = board.getSquare(8).getPiece().findMoves();
        ArrayList<Integer> expectedMoves = new ArrayList<>(Arrays.asList(16, 24));
        assertEquals(expectedMoves, moves);

        board.fenToBoard("4k3/4ppp1/5PB1/3b3P/8/8/2K5/4R3");

        moves = board.getSquare(52).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(36, 44));
        assertEquals(expectedMoves, moves);

        moves = board.getSquare(45).getPiece().findMoves();
        Collections.sort(moves);
        expectedMoves = new ArrayList<>(Arrays.asList(52, 54));
        assertEquals(expectedMoves, moves);
    }

}
