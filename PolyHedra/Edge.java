package polyhedra;

import java.util.ArrayList;

public class Edge
{
    private Vertex a;
    private Vertex b;

    public Edge()
    {
        
    }
    
    public Edge(Vertex q, Vertex w)
    {
        a = q;
        b = w;
    }
    
    public Vertex otherVert(Vertex point)
    {
    	if(point.equals(this.a))
    	{
    		return b;
    	}
    	return a;
    }
    
    public ArrayList<Vertex> getVertexList()
    {
        ArrayList<Vertex> points=new ArrayList<Vertex>();
        points.add(a);
        points.add(b);
        return points;
    }
    
    public Vertex[] getVertices()
    {
    	Vertex[] vertices = new Vertex[2];
    	vertices[0] = this.a;
    	vertices[1] = this.b;
    	return vertices;
    }

    public boolean sameAs(Edge edge)
    {
    	if(edge.hasVertex(a)&&edge.hasVertex(b))
    	{
    		return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean hasVertex(Vertex v)
    {
    	if(this.getVertices()[0].roughEquals(v)||this.getVertices()[1].roughEquals(v))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public double distance()
    {
    	return	a.distanceTo(b);
    }
    
    public String toString()
    {
    	return getMidpoint().toString();
    }
    
    public void setVertex(int index, Vertex v)
    {
    	if (index == 0)
    	{
    		a = v;
    	}
    	else if (index == 1)
    	{
    		b = v;
    	}
    	else
    	{
    		System.out.println("index must be 0 or 1");
    	}
    }
    
    public void replace(Vertex oldVertex, Vertex newVertex)
    {
    	if (oldVertex.equals(a))
    	{
    		a = newVertex;
    	}
    	else if (oldVertex.equals(b))
    	{
    		b = newVertex;
    	}
    }

	public Vertex getMidpoint()
	{
		return new Vertex((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2,
				(a.getZ() + b.getZ()) / 2);
	}
}
