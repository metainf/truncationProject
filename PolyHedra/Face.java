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
	public boolean removeEdge(Edge e){
		return edges.remove(e);
	}
	public void addEdge(Edge e){
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
    	int n;
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
    
    public int getNumVerticies()
    {
    	return edges.size();
    }
    
    public ArrayList<Vertex> getOrderedVerticies()
    {
    	ArrayList<Edge> tempEdges = new ArrayList();
    	tempEdges = edges;
    	ArrayList orderedVerticies = new ArrayList<Vertex>();
    	
    	orderedVerticies.add(tempEdges.get(0).getVertices()[0]);
    	orderedVerticies.add(tempEdges.remove(0).getVertices()[1]);
    	
    	do
    	{
    		for (Edge e: tempEdges)
    		{
    			if (e.getVertices().contains(orderedVerticies.get(i - 1)))
    			{
    				if (orderedVerticies.get(i - 1).equals(e.getVertices()[0]))
    				{
    					orderedVerticies.add(tempEdges.remove(0).getVertices()[1]);
    				}
    				else if (orderedVerticies.get(i - 1).equals(e.getVertices()[1]))
    				{
    					orderedVerticies.add(tempEdges.remove(0).getVertices()[0]);
    				}
    			}
    		}
    	}
    	while (tempEdges.size() > 0);
    	return orderedVerticies;
    }
}
    
    
