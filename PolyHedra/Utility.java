package polyhedra;

import java.util.ArrayList;

/**
 * The utility class for UI
 */
public class Utility
{
    public static String INSTRUCTIONS =
    		"When using the \"Select and truncate\" tool, click on the "
    		+ "vertex or edge that you wish to truncate.  When it is selected, "
    		+ "it will become blue, and the truncation slider and button will "
    		+ "became usable.  Use the slider to select the percentage to "
    		+ "truncate by, and click the \"Truncate\" button to perform the "
    		+ "truncation.\n\nWhen using the \"Rotate\" tool, click and drag "
    		+ "the mouse to rotate the polyhedron.\n\nTo change polyhedra, "
    		+ "select a new polyhedron from the drop-down list.  If the "
    		+ "current polyhedron is selected from the list, it will be reset "
    		+ "to its original state.\n\nHOTKEYS\nSpacebar: Switch tools\nR: "
    		+ "Reset the current polyhedron\nScroll Wheel (Mouse): Zoom in or "
    		+ "out\nArrow Keys: Rotate the polyhedron";
    public static String CREDITS =
    		"This program was created for Mr. Atlas's 2013-2014 Linear "
    		+ "Algebra and Kaleidocycles seminar.\n\nHardcore Math: Isaac "
    		+ "Xia\nDisplay and User Interface: Michael Vrablik\nData "
    		+ "Structure: Taylor Murphy and Bobby Li\nRotation: Isaac Xia, "
    		+ "Taylor Murphy, and Michael Vrablik\nTruncation: Bobby Li and "
    		+ "Michael Vrablik\n\nThe source code for this project can be "
    		+ "found at https://github.com/mvrablik/truncationProject.  You "
    		+ "may use code from this project freely as long as full credit "
    		+ "is given to the original authors.";
    public static String HELP_TEXT =
    		"This project is no longer being worked on by the original "
    		+ "developers, but some major issues remain.  If you are "
    		+ "interested in further developing it, you are certainly welcome "
    		+ "to do so.  The source code for this project can be found at "
    		+ "https://github.com/mvrablik/truncationProject.";
    
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
        ArrayList<Edge> edges=new ArrayList<Edge>();
        Vertex startingVertex=vertexIn.get(0);
        double smallestDistance=Double.MAX_VALUE;
        Vertex currentVertex=vertexIn.get(0);
        Vertex nextVertex=vertexIn.get(0);
        vertexIn.remove(0);
        while(vertexIn.size()!=0)
        {
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
        return new Face(edges);
    }
}
