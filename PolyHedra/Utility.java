package polyhedra;

import java.util.ArrayList;

/**
 * The utility class for UI
 *
 * @author (Michael Vrablik)
 */
public class Utility
{
    public static String HELP_TEXT = "Help text to be written";
    
    /**
     * Returns (in radians, over the interval [0, 2pi)) the standard angle of the line from (0, 0) to (x, y)
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the standard angle of the line from (0, 0) to (x, y)
     */
    public static double angleOf(double x, double y)
    {
        if (x > 0.0 && y >= 0.0)
        {
            return Math.atan(y / x);
        }
        else if (x == 0.0 && y > 0.0)
        {
            return Math.PI / 2;
        }
        else if (x < 0.0)
        {
            return Math.PI + Math.atan(y / x);
        }
        else if (x == 0.0 && y < 0.0)
        {
            return 3 * Math.PI / 2;
        }
        else if (x > 0.0 && y < 0.0)
        {
            return (2 * Math.PI) + Math.atan(y / x);
        }
        else //if (x == 0.0 && y == 0.0)
        {
            System.out.println("ERROR: Utility.angleOf(0.0, 0.0) CANNOT BE EVALUATED");
            return 0.0;
        }
    }
    
    public static boolean inRange(double d1, double d2)
    {
        if((d1-d2)<.02 &&d1-d2>-.02)
        {
            return true;
        }
        return false;
    }
    
    //creates a convex face given a set of points.
    public static Face createFace(ArrayList<Vertex> vertexIn)
    {
        //System.out.println("started");
        ArrayList<Edge> edges=new ArrayList<Edge>();
        Vertex startingVertex=vertexIn.get(0);
        double smallestDistance=Double.MAX_VALUE;
        Vertex currentVertex=vertexIn.get(0);
        Vertex nextVertex=vertexIn.get(0);
        vertexIn.remove(0);
        while(vertexIn.size()!=0)
        {
            //System.out.println(vertexIn.size());
            for(Vertex testVertex:vertexIn)
            {
                if(testVertex.distanceTo(currentVertex)<smallestDistance)
                {
                    smallestDistance=testVertex.distanceTo(currentVertex);
                    nextVertex=testVertex;
                }
            }
            edges.add(new Edge(currentVertex,nextVertex));
            vertexIn.remove(currentVertex);
            currentVertex=nextVertex;
            smallestDistance=Double.MAX_VALUE;
        }
        edges.add(new Edge(startingVertex,currentVertex));
        //System.out.println("finished");
        return new Face(edges);
    }
}
