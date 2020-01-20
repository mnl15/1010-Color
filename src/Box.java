/**
 * This class models the boxes that hold the pieces to be placed by the user 
 *
 */
public class Box
{
    private boolean full; // Whether the box is occupied
    private Piece piece;  // The piece currently stored in the box

    /** Set up an empty box */
    public Box() {
        full = false;
        piece = null;
    }

    /** Return true if the box currently holds a piece */
    public boolean occupied() {
        return full;
    }

    /** Return the current piece */
    public Piece getPiece() {
        return piece;
    }

    /** Return the current piece, and set the box to empty */
    public Piece useBox() {
        full = false;
        return piece;
    }

    /** Place a piece in the box */
    public void fillBox(Piece p) {
        full = true;
        piece = p;
    }
}
