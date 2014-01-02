
/**
 * Write a description of class Point here.
 * 
 * @author Taylor 
 * @version (a version number or a date)
 */
public class Vertex
{
    private int x;
    private int y; 
    private int z;
    /**
     * Constructor for objects of class Point
     */
    public Vertex(int a, int b, int c)
    {
        x = a;
        y = b;
        z = c;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }
    
    public int[] getCoords()
    {
        int[] coords = new int[3];
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
        return coords;
    }
    public boolean equals(Vertex vert){
        if(this.x==vert.x && this.y==vert.y && this.z==vert.z){
            return true;
        }
        else{
            return false;
        }
    }
    
}
