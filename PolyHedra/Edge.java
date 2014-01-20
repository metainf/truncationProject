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

    public ArrayList<Vertex> getVertexes()
    {
        Arraylist<Vertex> points=new ArrayList();
        points.add(a);
        points.add(b);
        return points;
    }
    public Vertex[] getVertices()
    {
    	Vertex[] verticies = new Vertex[2];
    	verticies[0] = a;
    	verticies[1] = b;
    	return verticies;
    }
    public boolean equals(Edge edge){
        if((edge.getVertexes[0].equals(this.a)&& edge.getVertexes[1].equals(this.b))||
            (edge.getVertexes[1].equals(this.a)&& edge.getVertexes[0].equals(this.b))){
            return true;
        }
    
        else{
            return false;
        }
    }
	public boolean hasPoint(Vertex v){
		if(edge.getVertexes[0].equals(v)||edge.getVertexes[1].equals(v)){
			return true;
		}
		else{
			return false;
		}
}
