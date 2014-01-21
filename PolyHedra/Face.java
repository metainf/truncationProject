 


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
        	edges=new ArrayList<>(inputEdges);
        }
	/**
	*returns all the edges of a face
	*
	*@return	ArrayList<Edges> edges of the face.
	*/
	public ArrayList<Edge> returnEdges(){
		return new ArrayList<>(edges);
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
    			x = x + edges.get(i).getVerticies()[k].getCoords()[0];
    			y = y + edges.get(i).getVerticies()[k].getCoords()[1];
    			z = z + edges.get(i).getVerticies()[k].getCoords()[2];
    			n++;
    			
    		}
    		
    	}
    	x = x / n;
    	y = y / n;
    	z = z / n;
    	Vertex centroid = new Vertex(x, y, z);
    	return centroid;
    }
    
    public int getNumVerticies()
    {
    	return edges.size();
    }
    
    public ArrayList<Vertex> getOrderedVerticies()
    {
        ArrayList<Edge> tempEdges = new ArrayList();
        tempEdges = edges;
        Edge e;
        Vertex a, b;
        int n = 2;
        ArrayList orderedVerticies = new ArrayList<Vertex>();
        orderedVerticies.add(tempEdges.get(0).getVerticies()[0]);
        orderedVerticies.add(tempEdges.remove(0).getVerticies()[1]);

        do
        {
            for (int i = 0; i < tempEdges.size(); i ++)
            {
                e = tempEdges.get(i);
                a = e.getVerticies()[0];
                b = e.getVerticies()[1];
                n = orderedVerticies.size() - 1;
                if (orderedVerticies.get(n).equals(a))
                {
                    orderedVerticies.add(b);
                    tempEdges.remove(i);
                    break;
                }
                else if (orderedVerticies.get(n).equals(b))
                {
                    orderedVerticies.add(a);
                    tempEdges.remove(i);
                    break;
                }
            }
        }
        while (tempEdges.size() > 0);
        
        return orderedVerticies;
    }
}
    
    
