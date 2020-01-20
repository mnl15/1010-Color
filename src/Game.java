import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.LinkedList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.io.File;

/**
 * This class plays 1010!
 * @author monalee
 *
 */
public class Game{

    //Dimensions
    private static final int SQUARE_SIZE = 27;
    private static final int DISPLAY_WIDTH = 650;
    private static final int DISPLAY_HEIGHT = 550;
    private static final int LEFT_BOARD = 100;
    private static final int RIGHT_BOARD = 370;
    private static final int TOP_BOARD = 100;
    private static final int BOTTOM_BOARD = 370;

    //Box Display Dimensions
    //The x,y coordinates of the top left of the display section for the boxes
    private static final int[] BOX_Y_COORDS = new int[]{50,210,370};
    private static final int BOX_PLACE_X = 450;
    private static final int BOTTOM_OF_BOXES = 520;

    //Design Constants
    private static final Color BORDER_COLOR = Color.black;
    private static final Color BACKGROUND_COLOR = Color.white;
    private static final Color OUTLINE = Color.black;
    private static final Color GRID_COLOR = Color.darkGray;
    private static final Color SCORE_COLOR = Color.black;

    private int moveNum = 0;
    private String[] highScoresNames;
    private int[] highScores;

    public Piece[] pieces;
    private GameState gameState;
    private SimpleCanvas sc;
    private Mouse mouselistener;
    private Random r;
    private boolean isBoxSelect;
    private int boxSelected;
    private boolean updateBoxes = false;

    private int boxesOccupied; 
    
    private LinkedList<int[]> legalMoves;
    
    public Game() {
    	JFrame frame = new JFrame("1010!");
    	frame.setLocation(100, 100);
    	Dimension window = new Dimension(650, 550);
    	frame.setPreferredSize(window);
    	
    	sc = this.new SimpleCanvas();
    	mouselistener = new Mouse();
    	sc.addMouseListener(mouselistener);
    	
    	setUpGame();
    	
    	frame.add(sc);
    	frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**Sets up the board for a new game */
    private void setUpGame() {
        pieces = new Piece[19];
        createPieces();
        r = new Random();
        gameState = new GameState();
        highScores = new int[3];
        highScoresNames = new String[3];
        readInCurrentHighScore();
        isBoxSelect = true; 
        putNewPiecesInBox(new int[] {r.nextInt(pieces.length),r.nextInt(pieces.length),r.nextInt(pieces.length)} );
        legalMoves = gameState.allLegalMoves();
    }

    public void setBoard(Square[][] board) {
    	gameState.setBoard(board);
    }

    public Square[][] getBoard(){
    	return gameState.getBoard();
    }
    
    public int getScore() {
    	return gameState.getScore();
    }
    
    public void setPieces(Piece[] newPieces) {
    	for (int i = 0; i < 3; i++) {
            try {
                gameState.getBox(i).fillBox(newPieces[i]);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Input contains an invalid piece number.");
            }
        }
    }

    public void setMouseClick(int x, int y) {
    	MouseEvent e = new MouseEvent(getCanvas(), MouseEvent.MOUSE_CLICKED, 2, 
				MouseEvent.BUTTON1, x, y, 1, false);
    	mouselistener.mouseClicked(e);
    }
    
    public SimpleCanvas getCanvas() {
    	return sc;
    }
    
    public boolean getUpdateBoxes() {
    	return updateBoxes;
    }
    
    public int getBoxesOccupied() {
    	return boxesOccupied;
    }    
    
    public LinkedList<int[]> getLegalMoves(){ 
    	return legalMoves;
    }
    
    /**
     * Puts new pieces in boxes
     * @param newPieces int[] of piece numbers
     */
    private void putNewPiecesInBox(int[] newPieces) {
        for (int i = 0; i < 3; i++) {
            try {
                gameState.getBox(i).fillBox(pieces[newPieces[i]]);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Input contains an invalid piece number.");
            }
        }
    }

    /** Creates and defines the game pieces */
    private void createPieces() {
        //Pieces 0-3
        pieces[0] = new Piece(new int[]{0,1});
        pieces[1] = new Piece(new int[]{0,1,2});
        pieces[2] = new Piece(new int[]{0,1,2,3});
        pieces[3] = new Piece(new int[]{0,1,2,3,4});
        //Pieces 4-7
        pieces[4] = new Piece(new int[]{0,10});
        pieces[5] = new Piece(new int[]{0,10,20});
        pieces[6] = new Piece(new int[]{0,10,20,30});
        pieces[7] = new Piece(new int[]{0,10,20,30,40});
        //Pieces 8-10
        pieces[8] = new Piece(new int[]{0});
        pieces[9] = new Piece(new int[]{0,10,11,1});
        pieces[10] = new Piece(new int[]{0,10,20,1,11,21,2,12,22});
        //Pieces 11-14
        pieces[11] = new Piece(new int[]{0,1,2,12,22});
        pieces[12] = new Piece(new int[]{0,1,11});
        pieces[13] = new Piece(new int[]{10,11,1});
        pieces[14] = new Piece(new int[]{20,21,22,12,2});
        //Pieces 15-18
        pieces[15] = new Piece(new int[]{0,10,20,1,2});
        pieces[16] = new Piece(new int[]{0,1,10});
        pieces[17] = new Piece(new int[]{0,10,20,21,22});
        pieces[18] = new Piece(new int[]{0,10,11});

    }

    /** Gets the current high score */
    private void readInCurrentHighScore() {
        int i = 0;
        try {
            FileReader inputFile = new FileReader("files/highScores.txt");
            for (String line : inputFile.lines) {
            	highScoresNames[i] = line;
            	String highScore = (line.split(" ", 2))[0];
                highScores[i] = Integer.parseInt(highScore);
                i++;
            }
        }
        catch (NullPointerException e) {
            highScoresNames[0] = "100  Mona";
            highScoresNames[1] = "50   Mona";
            highScoresNames[2] = "25   Mona";
        	highScores[0] = 100;
            highScores[1] = 50;
            highScores[2] = 25;
        }
    }

    /** Update high score record */
    private void writeHighScore() {
        String newScoreName = gameState.getScore() + "  " + gameState.getName();
    	if (gameState.getScore() > highScores[0]) {
    		highScores[2] = highScores[1];
    		highScoresNames[2] = highScoresNames[1];
            highScores[1] = highScores[0];
            highScoresNames[1] = highScoresNames[0];
            highScores[0] = gameState.getScore();
            highScoresNames[0] = newScoreName;
        }
        else if (gameState.getScore() > highScores[1]) {
            highScores[2] = highScores[1];
            highScoresNames[2] = highScoresNames[1];
            highScores[1] = gameState.getScore();
            highScoresNames[1] = newScoreName;
        }
        else if (gameState.getScore() > highScores[2]) {
            highScores[2] = gameState.getScore();
            highScoresNames[2] = newScoreName;
        }
        try {
            PrintWriter writer = new PrintWriter("files/highScores.txt");
            writer.println(highScoresNames[0]);
            writer.println(highScoresNames[1]);
            writer.println(highScoresNames[2]);
            writer.close();
        } catch (java.io.FileNotFoundException e) {
            File file = new File("files/highScores.txt");
            writeHighScore();
        }
    }

    
    @SuppressWarnings("serial")
    private class SimpleCanvas extends JPanel {
        private CanvasPane canvas;
        private Graphics graphic;
        private Image canvasImage;
        
        public SimpleCanvas() {
            canvas = new CanvasPane();
        }
        
        public void drawLine(int x1, int y1, int x2, int y2, Color c) {
            graphic.setColor(c);
            graphic.drawLine(x1,y1,x2,y2);
            canvas.repaint();
        }
        
        public void drawRectangle(int x1, int y1, int x2, int y2, Color c) {
            graphic.setColor(c);
            for (int i = x1; i < x2; i++)
                graphic.drawLine(i, y1, i, y2 - 1);
            canvas.repaint();
        }
        
        private void drawPiece(Piece p, int x, int y) { 
            Color piececolor = p.getColor();
            //Loops through individual squares of piece, drawing them at their correct positions
            for (int[] xy : p.getOffsets()) {
                int topLeftX = x + xy[0] * SQUARE_SIZE;
                int topLeftY = y + xy[1] * SQUARE_SIZE;
                drawSquare(topLeftX, topLeftY, piececolor);
            }
        }
        
        private void drawSquare(int x,int y, Color c) {
            drawRectangle(x, y, x + SQUARE_SIZE, y + SQUARE_SIZE, c);
            //Draws black border around each individual shape
            drawLine(x, y, x + SQUARE_SIZE, y, OUTLINE);
            drawLine(x, y, x, y + SQUARE_SIZE, OUTLINE);
            drawLine(x + SQUARE_SIZE, y, x + SQUARE_SIZE, y + SQUARE_SIZE, OUTLINE);
            drawLine(x, y + SQUARE_SIZE, x + SQUARE_SIZE, y + SQUARE_SIZE, OUTLINE);
        }
        
        class CanvasPane extends JPanel {
            public void paint(Graphics g) {
                g.drawImage(canvasImage,0,0,null);
            }
        }
        
        public void drawScore() {
            String scoreString = "Score: " + Integer.toString(gameState.getScore());
            graphic.setColor(SCORE_COLOR);
            graphic.drawString(scoreString, 100,400);
            graphic.drawString("High Scores", 200,400);
            graphic.drawString(highScoresNames[0], 200,422);
            graphic.drawString(highScoresNames[1], 200,442);
            graphic.drawString(highScoresNames[2], 200,462);
        }
        
        private void drawOccupiedSquares() {
            //Loops through each square of the board, draws it if occupied
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (gameState.getBoard()[i][j].status()) {
                        drawSquare(LEFT_BOARD + SQUARE_SIZE * i, TOP_BOARD + SQUARE_SIZE * j,gameState.getBoard()[i][j].getColor());
                    }
                }
            }
        }
        
        private void drawGrid() {
            //Draw Borders of Board
            drawLine(LEFT_BOARD,TOP_BOARD,LEFT_BOARD,BOTTOM_BOARD, BORDER_COLOR);
            drawLine(RIGHT_BOARD,TOP_BOARD,RIGHT_BOARD,BOTTOM_BOARD, BORDER_COLOR);
            drawLine(LEFT_BOARD,TOP_BOARD,RIGHT_BOARD,TOP_BOARD, BORDER_COLOR);
            drawLine(LEFT_BOARD,BOTTOM_BOARD,RIGHT_BOARD,BOTTOM_BOARD, BORDER_COLOR);
            //Draws grid of squares
            for (int i = LEFT_BOARD + SQUARE_SIZE; i < RIGHT_BOARD; i = i + SQUARE_SIZE) {
                drawLine(i,TOP_BOARD,i,BOTTOM_BOARD,GRID_COLOR);
            }
            for (int j = TOP_BOARD + SQUARE_SIZE; j < BOTTOM_BOARD; j = j + SQUARE_SIZE) {
                drawLine(LEFT_BOARD,j,RIGHT_BOARD,j,GRID_COLOR);
            }
        }
        
        private void drawBoxes() {
            Piece piece;
            for (int i = 0; i < 3; i++) {
                if (gameState.getBox(i).occupied()){
                    piece = gameState.getBox(i).getPiece();
                    drawPiece(piece, BOX_PLACE_X, BOX_Y_COORDS[i]);
                }
            }
        }
        
        private void drawInstructions() {
        	drawRectangle(285, 70, 370, 90, Color.black);
        	drawRectangle(286, 71, 369, 89, Color.darkGray);
        	graphic.setColor(Color.white);
        	graphic.drawString("Instructions", 289, 84);
        	graphic.setColor(Color.black);
        	graphic.setFont(new Font("Lucida Grande", Font.BOLD, 60));
        	graphic.drawString("1010!", 100, 70);
        	graphic.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        }
        
        private void drawGameOverScreen() {
            drawRectangle(0,0,DISPLAY_WIDTH,DISPLAY_HEIGHT,BACKGROUND_COLOR);
            graphic.setColor(Color.black);
            graphic.setFont(new Font("Lucida Grande", Font.BOLD, 40));
            graphic.drawString("Game Over", 140, 140);
            if(gameState.getScore() > highScores[0]) {
            	graphic.drawString("New High Score", 140,200);
            }
            writeHighScore();
            String scoreString = "Score: " + Integer.toString(gameState.getScore());
            graphic.drawString(scoreString, 140,200);
        }
        
        @Override 
        public void paintComponent(Graphics gc) {
        	super.paintComponent(gc);
        	graphic = gc;
        	if (!(legalMoves.isEmpty())) {
        		drawScore();
        		drawOccupiedSquares();
        		drawGrid();
        		drawBoxes();
        		drawInstructions();
        		if (updateBoxes) {
                    putNewPiecesInBox(new int[] {r.nextInt(pieces.length), 
                    		r.nextInt(pieces.length), r.nextInt(pieces.length)});
                    updateBoxes = false;
                    drawScore();
            		drawOccupiedSquares();
            		drawGrid();
            		drawBoxes();
            		drawInstructions();
                }
                //Updates legal move after move has been played
                legalMoves = gameState.allLegalMoves();
            }
        	else {
        		drawGameOverScreen();
        	}
        }
    }
    
    private class Mouse extends MouseAdapter {
    	@Override
    	public void mouseClicked(MouseEvent e) { 
    		int x = e.getX();
    		int y = e.getY();
    		
    		// display instructions 
    		if (x >= 285 && x <= 370 && y >= 70 && y <= 90) {
    			String html = "<html><body width='%1s'><h1>1010 Instructions</h1>"
    					+ "<p>1010 is a simple puzzle game where on a 10x10 board, "
                        + "the user must combine puzzle blocks and clear the board by "
                        + "placing them strategically to create lines.<br><br>"
                        + "<p>How to Play: "
                        + "<p>At each round, 3 pieces are presented which must be fit on the "
                        + "grid, in any order. The game stops when no more pieces can be "
                        + "placed. Points are attributed when putting a piece and when rows "
                        + "or columns are removed. <br><br>"
                        + "<p>To select a piece, click on the piece from the right side of the "
                        + "screen. To place the piece, click on the board where the top left "
                        + "square of the piece will land. If the piece does not have a "
                        + "corresponding top left square, click where this piece would "
                        + "appear. If none of the remaining pieces can be placed, the game "
                        + "is over. ";
    			int w = 450;
                JOptionPane.showMessageDialog(null, String.format(html, w, w), "Instructions", 
              			JOptionPane.DEFAULT_OPTION);
    		} 
    		
    		// check whether a piece on the right is clicked to be placed 
    		if (x > BOX_PLACE_X && y > BOX_Y_COORDS[0]) {
    			if ( y < BOX_Y_COORDS[1] ) {
    				boxSelected = 0;
    				isBoxSelect = !isBoxSelect;
    			}
    			if (y > BOX_Y_COORDS[1] && y < BOX_Y_COORDS[2]) {
    				boxSelected = 1;
    				isBoxSelect = !isBoxSelect;
    			}
    			if (y > BOX_Y_COORDS[2] && y < BOTTOM_OF_BOXES ) {
    				boxSelected = 2;
    				isBoxSelect = !isBoxSelect;
    			}
    		}
        
    		// place piece on board if click is within board 
    		boolean isOnBoard = LEFT_BOARD < x && x < RIGHT_BOARD && TOP_BOARD < y && y < BOTTOM_BOARD;
       		if (!isBoxSelect && isOnBoard) {
    			//Check if box select inside square, check move okay then place piece
    			int squarex = (x - LEFT_BOARD) / SQUARE_SIZE;
    			int squarey = (y - TOP_BOARD) / SQUARE_SIZE;
    			if (gameState.canPlacePiece(gameState.getBox(boxSelected).getPiece(), squarex, squarey).equals("ok")) {
    				gameState.placePiece(gameState.getBox(boxSelected).useBox(), squarex, squarey);
    				isBoxSelect = true;
    				moveNum++;
    				//If all boxes have been used, refills them
    				if (moveNum % 3 == 0) {
    					updateBoxes = true;
    				}
    			}
    		} 
    		sc.repaint(); 
    	}
    }
    
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Game();
			}
		});
    }

}
