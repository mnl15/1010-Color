import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

public class GameTest {
	
	/** Helper function to make an empty 10x10 board with no pieces on it */
	private Square[][] makeBoard(){
		Square[][] board = new Square[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = new Square();
			}
		}
		return board;
	}
	
	/** Helper function to determine if two boards are the same. */
	private boolean sameBoard(Square[][] bd1, Square[][] bd2) {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (! bd1[x][y].equals(bd2[x][y])) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Test 
	public void testPiecePlacedEmptyBoard() {
		// test when a piece is placed on an empty board  
		Game g = new Game();
		Piece[] pieces = new Piece[] {g.pieces[8], g.pieces[8], g.pieces[8]};
		g.setPieces(pieces);
		Color c = pieces[0].getColor();
		// Select the 1 square piece 
		g.setMouseClick(451, 51);
		g.setMouseClick(101, 101);
		// appears on board 
		Square[][] testBoard = makeBoard();
		testBoard[0][0].set(c);
		assertTrue(sameBoard(testBoard, g.getBoard()));
		// score is updated 
		assertEquals("score is updated", 1, g.getScore());
	}
	
	@Test 
	public void testThirdPiecePlaced() {
		// test when the last piece in the 3 set is placed 
		Game g = new Game();
		Piece[] pieces = new Piece[] {g.pieces[8], g.pieces[8], g.pieces[8]};
		g.setPieces(pieces);
		Square[][] testBoard = makeBoard();
		// piece 1 
		Color c = pieces[0].getColor();
		g.setMouseClick(451, 51);
		g.setMouseClick(101, 101);
		testBoard[0][0].set(c);
		// piece 2 
		c = pieces[1].getColor();
		g.setMouseClick(451, 211);
		g.setMouseClick(129, 101);
		testBoard[1][0].set(c);
		// last piece 
		c = pieces[2].getColor();
		g.setMouseClick(451, 371);
		g.setMouseClick(157, 101);
		testBoard[2][0].set(c);
		// no more piece in boxes
		assertTrue(g.getUpdateBoxes());
		assertEquals("no occupied boxes", 0, g.getBoxesOccupied());
		assertTrue(sameBoard(testBoard, g.getBoard()));
		// the pieces are refilled 
	}
	
	@Test 
	public void testGameOver() {
		// test when no more pieces can be placed and the game is over 
		Game g = new Game();
		Square[][] testBoard = makeBoard();
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (x % 2 == y % 2) {
					Square sq = new Square();
					sq.set(Color.black);
					testBoard[x][y] = sq;
				}
			}
		}
		g.setBoard(testBoard);
		Piece[] pieces = new Piece[] {g.pieces[0], g.pieces[0], g.pieces[0]};
		g.setPieces(pieces);
		g.setMouseClick(451, 51);
		// game over screen is showing
		assertFalse(g.getLegalMoves().isEmpty());
		assertFalse(g.getUpdateBoxes());
	} 
	
	@Test 
	public void testRowClear() {
		// test a row disappears when full
		Game g = new Game();
		Square[][] testBoard = makeBoard();
		for (int x = 0; x < 9; x++) {
			Square sq = new Square();
			sq.set(Color.black);
			testBoard[x][0] = sq;
		}
		g.setBoard(testBoard);
		Piece[] pieces = new Piece[] {g.pieces[8], g.pieces[8], g.pieces[8]};
		g.setPieces(pieces);
		g.setMouseClick(451, 51);
		g.setMouseClick(369, 101);
		g.setMouseClick(1, 1);
		// row is cleared and score is updated 
		assertTrue(sameBoard(g.getBoard(), makeBoard()));
		assertEquals("score increases by 10", g.getScore(), 11);
	} 
	
	@Test
	public void testColClear() {
		// test a column disappears when full
		Game g = new Game();
		Square[][] testBoard = makeBoard();
		for (int y = 0; y < 9; y++) {
			Square sq = new Square();
			sq.set(Color.black);
			testBoard[0][y] = sq;
		}
		g.setBoard(testBoard);
		Piece[] pieces = new Piece[] {g.pieces[8], g.pieces[8], g.pieces[8]};
		g.setPieces(pieces);
		g.setMouseClick(451, 51);
		g.setMouseClick(101, 369);
		g.setMouseClick(1, 1);
		// col is cleared and score is updated 
		assertTrue(sameBoard(g.getBoard(), makeBoard()));
		assertEquals("score increases by 10", g.getScore(), 11);
	} 
	
	@Test 
	public void testRowColClear() {
		// test when a row and column are cleared at the same time 
		Game g = new Game();
		Square[][] testBoard = makeBoard();
		for (int i = 0; i < 9; i++) {
			Square sq = new Square();
			sq.set(Color.black);
			testBoard[9][i] = sq;
			testBoard[i][9] = sq;
		}
		g.setBoard(testBoard);
		Piece[] pieces = new Piece[] {g.pieces[8], g.pieces[8], g.pieces[8]};
		g.setPieces(pieces);
		g.setMouseClick(451, 51);
		g.setMouseClick(369, 369);
		g.setMouseClick(1, 1);
		// last row and last col are cleared and score is updated 
		assertTrue(sameBoard(g.getBoard(), makeBoard()));
		assertEquals("score increases by 20", g.getScore(), 21);
	}
	
	@Test
	public void testMultipleLineClear() {
		// test when a piece is placed and multiple lines are cleared 
		Game g = new Game();
		Square[][] testBoard = makeBoard();
		for (int i = 0; i < 9; i++) {
			Square sq = new Square();
			sq.set(Color.black);
			testBoard[i][0] = sq;
			testBoard[i][1] = sq;
		}
		g.setBoard(testBoard);
		Piece[] pieces = new Piece[] {g.pieces[0], g.pieces[0], g.pieces[0]};
		g.setPieces(pieces);
		g.setMouseClick(451, 51);
		g.setMouseClick(369, 101);
		g.setMouseClick(1, 1);
		// last row and last col are cleared and score is updated 
		assertTrue(sameBoard(g.getBoard(), makeBoard()));
		assertEquals("score increases by 20", g.getScore(), 22);
	}
}
