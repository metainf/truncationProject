
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
