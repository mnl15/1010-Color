import java.awt.Color;

/**
 * This class models squares on a 1010! board.
 * 
 */
public class Square {
    public static final Color bgColor = Color.white; 
    
    private boolean on;   // whether the square is occupied 
    private Color color; // the current color of the square 

    /** set the square to unoccupied */
    public Square() {
        unset();
    }
    
    /** return the square's current color */ 
    public Color getColor() {
        return color;
    }

    /** return the square's current status */
    public boolean status() {
        return on; 
    }
    
    /** set the square to occupied with color c */
    public void set(Color c)
    {
        on = true;
        color = c;
    }
    
    /** set the square to unoccupied */
    public void unset()
    {
        on = false;
        color = bgColor;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (on ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (on != other.on)
			return false;
		return true;
	}
}
