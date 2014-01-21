package PolyHedra;

/**
 * Write a description of class Edge here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class Edge
{
    private Vertex a;
    private Vertex b;

    /**
     * Constructor for objects of class Edge
     */
    public Edge(){
        
    }
    public Edge(Vertex q, Vertex w)
    {
        a = q;
        b = w;
    }
	public Vertex otherVert(Vertex point){
		if(point.equals(this.a)){
			return b;
		}
		return a;
	}
    public ArrayList<Vertex> getVertexes()
    {
        ArrayList<Vertex> points=new ArrayList();
        points.add(a);
        points.add(b);
        return points;
    }
    public Vertex[] getVerticies()
    {
    	Vertex[] verticies = new Vertex[2];
    	verticies[0] = this.a;
    	verticies[1] = this.b;
    	return verticies;
    }
    public boolean equals(Edge edge){
        if((edge.getVerticies()[0].equals(this.a)&& edge.getVerticies()[1].equals(this.b))||
            (edge.getVerticies()[1].equals(this.a)&& edge.getVerticies()[0].equals(this.b))){
            return true;
        }
    
        else{
            return false;
        }
    }
	public boolean hasPoint(Vertex v){
		if(this.getVerticies()[0].equals(v)||this.getVerticies()[1].equals(v)){
			return true;
		}
		else{
			return false;
		}
	}
	public double distance()
	{
		return	a.distance(b);
	}
	
}
