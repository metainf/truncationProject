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
    
    @Override
    public boolean equals(Object obj)
    {
    	if(obj==null)
    	{
    		return false;
    	}
    	if(obj instanceof Edge)
    	{
    		return equals((Edge)obj);
    	}
    	return false;
    }

    private boolean equals(Edge edge)
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
    	if(this.getVertices()[0].equals(v)||this.getVertices()[1].equals(v))
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
    	String out=""+(a.getX()+b.getX())/2+" "+(a.getY()+b.getY())/2
    			+" "+(a.getZ()+b.getZ())/2;
    	return out;
    }
    
    public void setVertices(int index, Vertex v)
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
}
