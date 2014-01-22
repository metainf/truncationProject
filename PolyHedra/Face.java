package PolyHedra;

 


/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class Face
{
	// instance variables - replace the example below with your own
	private ArrayList<Edge> edges;
	/**
	* Constructor for objects of class Face
	*/
	public Face(ArrayList<Edge> inputEdges){
        	edges=new ArrayList<Edge>(inputEdges);
        }
	/**
	*returns all the edges of a face
	*
	*@return	ArrayList<Edges> edges of the face.
	*/
	public ArrayList<Edge> returnEdges(){
		return new ArrayList<Edge>(edges);
	}
	public boolean remove(Edge e){
		return edges.remove(e);
	}
	public void add(Edge e){
		edges.add(e);
	}


    public boolean hasEdge(Edge e){
		if(edges.contains(e)){
			return true;
		}
		return false;
    }
    public boolean equals(Face f){
    	for (int i=0;i<this.edges.size();i++){
    		if (!(f.edges.contains(this.edges.get(i)))) return false;
    	
    	}
    	return true;
    }
    public Vertex getCentroid()
    {
    	double x=0, y=0, z=0;
    	int n=0;
    	for (int i = 0; i < edges.size(); i ++)
    	{
    		for (int k = 0; k < 2; k ++)
    		{
    			x = x + edges.get(i).getVertices()[k].getCoords()[0];
    			y = y + edges.get(i).getVertices()[k].getCoords()[1];
    			z = z + edges.get(i).getVertices()[k].getCoords()[2];
    			n++;
    			
    		}
    		
    	}
    	x = x / n;
    	y = y / n;
    	z = z / n;
    	Vertex centroid = new Vertex(x, y, z);
    	return centroid;
    }
    
    public int getNumVertices()
    {
    	return edges.size();
    }
    
    public ArrayList<Vertex> getOrderedVertices()
    {
        ArrayList<Edge> tempEdges = new ArrayList();
        tempEdges = edges;
        Edge e;
        Vertex a, b;
        int n = 2;
        ArrayList orderedVertices = new ArrayList<Vertex>();
        orderedVertices.add(tempEdges.get(0).getVertices()[0]);
        orderedVertices.add(tempEdges.remove(0).getVertices()[1]);

        do
        {
            for (int i = 0; i < tempEdges.size(); i ++)
            {
                e = tempEdges.get(i);
                a = e.getVertices()[0];
                b = e.getVertices()[1];
                n = orderedVertices.size() - 1;
                if (orderedVertices.get(n).equals(a))
                {
                    orderedVertices.add(b);
                    tempEdges.remove(i);
                    break;
                }
                else if (orderedVertices.get(n).equals(b))
                {
                    orderedVertices.add(a);
                    tempEdges.remove(i);
                    break;
                }
            }
        }
        while (tempEdges.size() > 0);
        
        return orderedVertices;
    }
}
    
    
