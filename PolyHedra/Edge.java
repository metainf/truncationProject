
/**
 * Write a description of class Edge here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Edge
{
    private Vertex a;
    private Vertex b;

    /**
     * Constructor for objects of class Edge
     */
    public Edge(Vertex q, Vertex w)
    {
        a = q;
        b = w;
    }

    public Vertex[] getVertexes()
    {
        Vertex[] points = new Vertex[2];
        points[0] = a;
        points[1] = b;
        return points;
    }
    public boolean equals(Edge edge){
        if()
    }
}
