/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
 import java.util.ArrayList:
public class Face
{
    // instance variables - replace the example below with your own
    private ArrayList<Edge> edges;
    /**
     * Constructor for objects of class Face
     */
    public Face(ArrayList<Edge> inputEdges)
    {
        // initialise instance variables
        edges=inputEdges;
    }
	/**
	*returns all the edges of a face
	*
	*@return	ArrayList<Edges> edges of the face.
	*/
	public ArrayList<Edge> returnEdges(){
		return edges;
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
    	double x, y, z;
    	int n;
    	for (int i = 0; i < edges.size; i ++)
    	{
    		for (int k = 0 k < 2; k ++)
    		{
    			x = x + edges.get(i).getVerticies()[k].getCoords()[0];
    			y = y + edges.get(i).getVerticies()[k].getCoords()[1];
    			z = z + edges.get(i).getVerticies()[k].getCoords()[2];
    			n ++
    			
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
    	int n; 
    	n = edges.size * 2;
    	return n;
    }
}
    
    
