package polyhedra;

public class Vertex
{
    private double x;
    private double y;
    private double z;

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
    @Override
    public boolean equals(Object obj){
        if(obj==null){
            return false;
        }
        if(obj instanceof Vertex){
            return equals((Vertex)obj);
        }
        return false;
    }
    private boolean equals(Vertex vert){
        if(Utility.inRange(this.x,vert.x) && Utility.inRange(this.y,vert.y) && Utility.inRange(this.z,vert.z)){
            return true;
        }
        else{
            return false;
        }
    }
    public double distanceTo(Vertex a)
    {
        return Math.sqrt(Math.pow((a.getX() - this.getX()), 2) + Math.pow((a.getY() - this.getY()), 2) + Math.pow((a.getZ() - this.getZ()), 2));
    }
    
    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }
    
    public void set(double newX, double newY, double newZ)
    {
        x = newX;
        y = newY;
        z = newZ;
    }
}
