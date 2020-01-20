import java.util.LinkedList;
import java.awt.Color;
import java.util.Random;

/**
 * This class models the game pieces
 */
public class Piece {
    private static final Color[] PIECE_COLORS = {Color.red, Color.blue, 
                                                  Color.green, Color.yellow, 
                                                  Color.pink, Color.magenta,
                                                  Color.cyan, Color.orange};
    private Random r;
    private LinkedList<int[]> offsets; // the offsets for the actual squares in this piece 
    private Color color;             // the color used for this piece 
    private int xSize, ySize;         // the extent of this piece on each axis

    /** Initialize this piece  
    *  Each value in xs will be either: 
    *  a two-digit number MN representing a square with x-offset = M and y-offset = N or 
    *  a one-digit number  N representing a square with x-offset = 0 and y-offset = N 
    *  */
    public Piece(int[] xs) {
        r = new Random();
        offsets = new LinkedList<int[]>();
        
        int x;
        int y;
        int xMax = 0;
        int yMax = 0;
        
        for (int i = 0; i < xs.length; i++) {
            x = xs[i] / 10;
            y = xs[i] % 10;
            offsets.add(i, new int[]{x,y});
            if (x > xMax) {
                xMax = x;
            }
            if (y > yMax) {
                yMax = y;
            }
        }
        xSize = xMax + 1;
        ySize = yMax + 1;
        color = PIECE_COLORS[r.nextInt(PIECE_COLORS.length)];
    }

    /**Return piece offsets */
    public LinkedList<int[]> getOffsets() {
        return offsets;
    }
    
    /**Return piece color */
    public Color getColor() {
        return color;
    }
    
    /**Return piece x size */
    public int getxSize() {
        return xSize;
    }

    /**Return piece y size */
    public int getySize() {
        return ySize;
    }
}
