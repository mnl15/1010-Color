import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * This class models the functionality of the game
 *
 * @author monalee
 *
 */
public class GameState {

    private Square[][] board; // the current state of the board
    private Box[] boxes;      // the current state of the boxes
    private int score;        // the current score
    private String name;      // the name of the player 

    public GameState() {
    	// Asks and gets the users name 
    	name = JOptionPane.showInputDialog("What is your name?");   	
    	// Sets up 10x10 board
        board = new Square[10][10];
        //Sets up 3 boxes to hold pieces waiting to be placed
        boxes = new Box[3];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Square();
            }
        }
        for (int k = 0; k < 3; k++) {
            boxes[k] = new Box();
        }
        score = 0;
    }
    
    /** Return the current player's name */
    public String getName() {
    	return name;
    }

    /** Return the current state of the board */
    public Square[][] getBoard() {
        return board;
    } 
    
    /** Set board for testing purposes */
    public void setBoard(Square[][] board) {
    	this.board = board;
    }

    /** Return the current contents of Box i*/
    public Box getBox(int i) {
        if (0 <= i && i < 3) {
            return boxes[i];
        }
        else {
            return null;
        }
    }

    /** Return the current score*/
    public int getScore() {
        return score;
    }

    /** Return "ok" if p can be legally placed with its "top-left" corner at the location
     * Or return "offboard" if any part of the piece would be off the board
     * Or return "occupied" if any part of the piece would be where there is already a piece */
    public String canPlacePiece(Piece p, int x, int y) {
        int xOffset = 0;
        int yOffset = 0;
        for (int[] pieceSquare : p.getOffsets()) {
            xOffset = pieceSquare[0];
            yOffset = pieceSquare[1];
            try{
                if(board[x + xOffset][y + yOffset].status()) {
                    return "occupied";
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                return "offboard";
            }
        }
        return "ok";
    }

    /** return a list holding all legal moves using the current set of boxes on the current board
    * each move is represented as an int[] of length 3:
    * {i, x, y} denotes that the piece in Box i can be legally placed with its top-left corner at 
    * Square x,y */
    public LinkedList<int[]> allLegalMoves() {
        LinkedList<int[]> legalMoves = new LinkedList<int[]>();
        Piece piece;
        //loops over the three boxes
        for (int boxIndex = 0; boxIndex < 3; boxIndex++) {
            if (boxes[boxIndex].occupied()) {
                piece = boxes[boxIndex].getPiece();
                //iterates over each [x,y] position on board
                for (int i = 0; i < 10;i++) {
                    for (int j = 0; j < 10; j++) {
                        //if it is okay to be placed there, adds to legalMoves
                        if (canPlacePiece(piece, i,j).equals("ok")) {
                            legalMoves.add(new int[]{boxIndex, i,j});
                        }
                    }
                }
            }
        }
        return legalMoves;
    }
    
    /** Returns the number of full columns on the board */
    private LinkedList<Integer> getFullColumns() {
        LinkedList<Integer> fullColumns = new LinkedList<Integer>();
        //Loops through each column
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!(board[x][y].status())) {
                    break;
                }
                //If all square including 9 occupied, adds column num to fullColums
                else if (board[x][y].status() && y == 9) {
                    fullColumns.add(x);
                }
            }
        }
        return fullColumns;
    }
    
    /** Returns the number of full rows on the board */
    private LinkedList<Integer> getFullRows() {
        LinkedList<Integer> fullRows = new LinkedList<Integer>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (!(board[x][y].status())) {
                    break;
                }
                else if ((board[x][y].status()) && x == 9) {
                    fullRows.add(y);
                }
            }
        }
        return fullRows;
    }
    
    /** Clear row */
    private void clearRow(int row) {
        for (int x = 0; x < 10; x++) {
            board[x][row].unset();
        }
        score = score + 10;
    }
    
    /** Clear column */
    private void clearColumn(int column) {
        for (int y = 0; y < 10; y++) {
            board[column][y].unset();
        }
        score = score + 10;
    }

    /** Place p on the board with its "top-left" corner at Square x,y
    * clear columns and rows as appropriate 
    */
    public void placePiece(Piece p, int x, int y) {
        LinkedList<int[]> offsets = p.getOffsets();
        int xOccupied, yOccupied;
        LinkedList<Integer> fullRows, fullColumns;

        //add x,y to each position in offsets and sets that square to occupied
        if (canPlacePiece(p,x,y).equals("ok")) {
            for (int[] offset : offsets) {
                xOccupied = x + offset[0];
                yOccupied = y + offset[1];
                board[xOccupied][yOccupied].set(p.getColor());
                score = score + 1;
            }
        }
        fullRows = getFullRows();
        fullColumns = getFullColumns();
        for (int row : fullRows) {
            clearRow(row);
        }
        for (int column : fullColumns) {
            clearColumn(column);
        }
    }
}
