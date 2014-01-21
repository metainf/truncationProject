 

/**
 * Write a description of class Point here.
 * 
 * @author Taylor 
 * @version (a version number or a date)
 */
public class Vertex
{
    private double x;
    private double y; 
    private double z;
    /**
     * Constructor for objects of class Point
     */
    public Vertex(){
        
    }
    public Vertex(double a, double b, double c)
    {
        x = a;
        y = b;
        z = c;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }
    
    public double[] getCoords()
    {
        double[] coords = new double[3];
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
    public double distance(Vertex a)
    {
        return Math.sqrt(Math.pow((a.getX() - this.getX()), 2) + Math.pow((a.getY() - this.getY()), 2) + Math.pow((a.getZ() - this.getZ()), 2));
    }
}
