import java.util.ArrayList;
public class Polyhedron
{
	private ArrayList<Vertex> points;
	private ArrayList<Edge> edges;
	private ArrayList<Face> sides;

	
	public Polyhedron(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges,ArrayList<Face> inputSides) {
		points=inputPoints;
		edges=inputedges;
		sides=inputsides;
}
	public void pointTrun(Vertex point, double chop){
		ArrayList<Edge> tempEdges=new ArrayList(); //used to store the edges before truncation
		ArrayList<Edge> newEdges=new ArrayList();
		ArrayList<Vertex> newPoints=new ArrayList();
		ArrayList<Face> newFaces=new ArrayList();
		ArrayList<Edge> newFace=new ArrayList();// used to make the new face created from truncating.
		//generates the list of edges that will be changed
		for(Edge e : edges){
			if(e.hasPoint(point)){
				tempEdges.add(e);
			}
		}
		//generates the list of faces that will be changed
		for(Edge e: tempEdges){
			for(Face f: sides){
				if(f.hasEdge(e)){
					newFaces.add(f);
				}
			}
		}
		//supermessy way of getting rid of duplicates faces
		for(int i=0;i<newFaces.size();i+=0){
			if(newFaces.indexOf(newFaces.get(i)).equals(newFaces.lastIndexOf(newFaces.get(i)))){
				i++;
			}
			else{
				newFaces.remove(newFaces.get(i));
				i=0;
			}
		}
		//removes the faces that are going to be changed from the actual list
		for(Face f: newFaces){
			if(sides.contains(f)){
				sides.remove(f);
			}
		}
		//removes  the edges that are going to be changed from the actual list
		for(Edge e: tempEdges){
			if(edges.contains(e)){
				edges.remove(e);
			}
		}
		/*This for loop does the truncation, aka, delete the old point, and creates the new point and edges
		*It will also add the new points and edges to the faces that get chopped.
		*At the end, the only left is to make the new face created by the truncation
		*/
		for(Edge e: tempEdges){
			//finds the point that does not change in the edge
			if(e.getVertices()[0].equals(point)){
				Vertex notTrunPt=e.getVertices()[1];
			}
			else{
				Vertex notTrunPt=e.getVertices()[0];
			}
			//creates the vector to get the new point
			double[] vector={notTrunPt.getX()-point.getX(),
					 notTrunPt.getY()-point.getY(),
					 notTrunPt.getZ()-point.getZ()};
			//creates the new point
			Vertex newVertex= new Vertex(point.getX()-(vector[0]/e.distance()*chop),
			                             point.getX()-(vector[1]/e.distance()*chop),
			                             point.getX()-(vector[2]/e.distance()*chop))
			//creates the new edge
			Edge newEdge= new Edge(notTrunpt,newVertex);
			//adds the new edge to the faces that had the old edge
			for(int i=0;i<newFaces.size();i++){
				Face f=newFaces.get(i);
				if(f.hasEdge(e)){
					newFaces.remove(f);
					f.remove(e);
					f.add(newEdge);
					newFaces.add(i,f);
				}
			}
			newEdges.add(e);
			newPoints.add(newVertex);
		}
		/* This will create the face that is created by truncating the point
		*It does so by going through the list of new points, and creating the new face from that
		*/
		
	}
	public boolean hasVertexAtXY(double x, double y)
	{
		for (Vertex v: points)
		{
			if (v.getX == x && v.getX == y)
			return true;
		}
		return false;
	}
	
	public boolean hasEdgeAtXY(double x, double y)
	{
		
	}
	
	public Vertex getVertexAtXY(double x, double y)
	{
		Vertex closest;	
		points.get(0).getZ = z		//gets a z coordinate to check against
		for (Vertex v: points)
		{
			if (v.getX == x && v.getX == y && v.getZ > z)	
			{
				closest = v;
				z = v.getZ;
			}
		}
		return closest;
	}
	public ArrayList<Faces> getFaces()
	{
		return sides;
	}
	
	public 
		
}
